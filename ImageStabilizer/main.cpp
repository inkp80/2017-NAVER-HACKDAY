//
// Created by inkp on 2017. 11. 15..
//

#include <opencv2/opencv.hpp>

using namespace cv;
using namespace std;

int main() {
    Mat srcImage1 = imread("img1.jpeg", IMREAD_GRAYSCALE);
    Mat srcImage2 = imread("img2.jpeg", IMREAD_GRAYSCALE);

    if (srcImage1.empty() || srcImage2.empty())
        return -1;

    //Step 1, 2 : detect the keypoints & descriptors
    vector<KeyPoint> keypoints1, keypoints2;
    Mat descriptors1, descriptors2;


    //ORB
    // FAST-9 을 이용하여 특징점 검출한 후 Intensity Centroid를 이용해 특징점의 방향(orientation)을 계산
    Ptr<ORB> orbF = ORB::create(1000);
    orbF->detectAndCompute(srcImage1, noArray(), keypoints1, descriptors1);
    orbF->detectAndCompute(srcImage2, noArray(), keypoints2, descriptors2);
    cout << "keypoints1.size() = " << keypoints1.size() << endl;

    //BRISK
    //Scale space 상에서 FAST-9을 이용하여 FAST score가 극대인 점을 특징점으로 검출
//    Ptr<BRISK> briskF = BRISK::create();
//    briskF->detectAndCompute(srcImage1, noArray(), keypoints1, descriptors1);
//    briskF->detectAndCompute(srcImage2, noArray(), keypoints2, descriptors2);
//    cout << "keypoints1.size() = " << keypoints1.size() << endl;


    //Step 3 : Matching descriptor vectors
    vector<DMatch> matches;
    BFMatcher matcher(NORM_HAMMING);
    matcher.match(descriptors1, descriptors2, matches);

    cout << "matches.size() = " << matches.size() << endl;

    if (matches.size() < 4)
        return 0;

    //find goodMatches such that matches[i].distance <= 4 * minDist
    double minDist, maxDist;
    minDist = maxDist = matches[0].distance;
    for (int i = 1; i < matches.size(); i++) {
        double dist = matches[i].distance;
        if (dist < minDist) minDist = dist;
        if (dist > maxDist) maxDist = dist;
    }

    cout << "minDist = " << minDist << endl;
    cout << "maxDist = " << maxDist << endl;

    vector<DMatch> goodMatches;
    double fTh = 4 * minDist;
    for (int i = 0; i < matches.size(); i++) {
        if (matches[i].distance <= max(fTh, 0.02))
            goodMatches.push_back(matches[i]);
    }
    cout << "goodMatches.size() = " << goodMatches.size() << endl;
    if (goodMatches.size() < 4)
        return 0;

    //draw good_matches
    Mat imgMatches;
    drawMatches(srcImage1, keypoints1, srcImage2, keypoints2,
                goodMatches, imgMatches, Scalar(255, 0, 0), Scalar(255, 0, 0),
                vector<char>(), DrawMatchesFlags::NOT_DRAW_SINGLE_POINTS);


    //find Homography between keypoints1 and keypoints2
    vector<Point2f> obj;
    vector<Point2f> scene;

    for (int i = 0; i < goodMatches.size(); i++) {
        //Get the keypoints from the good matches
        obj.push_back(keypoints1[goodMatches[i].queryIdx].pt);
        scene.push_back(keypoints2[goodMatches[i].trainIdx].pt);
    }
    Mat H = findHomography(obj, scene, RANSAC);
    Mat RH = H.inv();

    vector<Point2f> objP(4);
    objP[0] = Point2f(0, 0);
    objP[1] = Point2f(srcImage1.cols, 0);
    cout << objP[1].x << endl << objP[1].y << endl;
    objP[2] = Point2f(srcImage1.cols, srcImage1.rows);
    objP[3] = Point2f(0, srcImage1.rows);

    vector<Point2f> sceneP(4);
    perspectiveTransform(objP, sceneP, H);


    //draw sceneP in imgMatches
    for (int i = 0; i < 4; i++)
        sceneP[i] += Point2f(srcImage1.cols, 0);
    for (int i = 0; i < 4; i++)
        line(imgMatches, sceneP[i], sceneP[(i + 1) % 4], Scalar(0, 0, 255), 4);


    resize(imgMatches, imgMatches, Size(imgMatches.cols / 6, imgMatches.rows / 6)); // to half size or even smaller
    namedWindow("Display frame", CV_WINDOW_AUTOSIZE);
    imshow("Display frame", imgMatches);
    imwrite("res1.jpg", imgMatches);

    waitKey();

    Mat tem = imread("img1.jpeg");
    Mat one = imread("img2.jpeg");

    for (int i = 0; i < tem.rows; i++)
        for (int j = 0; j < tem.cols; j++)
            tem.at<Vec3b>(i, j) = Vec3b(0, 0, 0);


    for (int i = 0; i < one.cols; i++) {
        for (int j = 0; j < one.rows; j++) {
            vector<Point2f> before(1);
            before[0] = Point2f(i, j);

            vector<Point2f> after(1);
            perspectiveTransform(before, after, H);
            int x = (int) after[0].x;
            int y = (int) after[0].y;
            if (x < 0 || y < 0 || x >= one.cols || y >= one.rows) continue;
            tem.at<Vec3b>(y, x) = one.at<Vec3b>(j, i);
        }
    }

    resize(tem, tem, Size(tem.cols/2, tem.rows/2)); // to half size or even smaller
    namedWindow( "Display frame",CV_WINDOW_AUTOSIZE);
    imshow("Display frame", tem);

    imwrite("res2.jpg", tem);
    waitKey();
    return 0;
}
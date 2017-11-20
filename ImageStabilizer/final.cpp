//
// Created by inkp on 2017. 11. 15..
//

#include <iostream>
#include <opencv2/opencv.hpp>
using namespace std;
using namespace cv;

const string IMG_NAME = "think.png";

vector<Mat> origin;


void bouge(int object, int standard) {

    Mat srcImage1;
    cvtColor(origin[object], srcImage1, CV_RGB2GRAY);
    Mat srcImage2;
    cvtColor(origin[standard], srcImage2, CV_RGB2GRAY);

    vector<KeyPoint> keypoints1, keypoints2;
    Mat descriptors1, descriptors2;


    //OpenCV
    Ptr<ORB>orbF = ORB::create(1000);
    orbF->detectAndCompute(srcImage1, noArray(), keypoints1, descriptors1);
    orbF->detectAndCompute(srcImage2, noArray(), keypoints2, descriptors2);

    //Step 3 : Matching descriptor vectors
    vector<DMatch> matches;
    BFMatcher matcher(NORM_HAMMING);
    matcher.match(descriptors1, descriptors2, matches);


    if (matches.size() < 4) {
        puts("BAD MATCH");
        return;
    }

    //find goodMatches such that matches[i].distance <= 4 * minDist
    double minDist, maxDist;
    minDist = maxDist = matches[0].distance;
    for (int i = 1; i < matches.size(); i++) {
        double dist = matches[i].distance;
        if (dist < minDist) minDist = dist;
        if (dist > maxDist) maxDist = dist;
    }

    vector<DMatch> goodMatches;
    double fTh = 4 * minDist;
    for (int i = 0; i < matches.size(); i++) {
        if (matches[i].distance <= max(fTh, 0.02))
            goodMatches.push_back(matches[i]);
    }

    if (goodMatches.size() < 4) {
        puts("BAD MATCH");
        return;
    }

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

    Mat tem(origin[standard].rows, origin[standard].cols, origin[standard].type(), Scalar(0, 0, 0));

    vector<Point2f> before, after;
    for (int i = 0; i < origin[object].cols; i++) {
        for (int j = 0; j < origin[object].rows; j++) {
            before.push_back(Point2f(i, j));
        }
    }
    perspectiveTransform(before, after, RH);
    for (int k = 0; k < after.size(); ++k) {
        int x = (int)after[k].x;
        int y = (int)after[k].y;
        if (x < 0 || y < 0 || x >= origin[object].cols || y >= origin[object].rows) continue;
        else {
            int i = k / origin[object].rows;
            int j = k % origin[object].rows;
            tem.at<Vec3b>(j, i) = origin[object].at<Vec3b>(y, x);
        }
    }

    tem.copyTo(origin[object]);
}



int main() {
    std::cout << "OpenCV Version : " << CV_VERSION << std::endl;
    cv::Mat img;
    cv::namedWindow("EXAMPLE02", 1);
    cv::VideoCapture cap;

    cap.open(0); // 노트북 카메라는 cap.open(1) 또는 cap.open(-1)
    // USB 카메라는 cap.open(0);
    while (cap.isOpened())
    {
        cap >> img;
        cv::imshow("EXAMPLE02", img);
        if (cv::waitKey(1)==0)
        {
            break;
        }
    }
    cv::destroyWindow("EXAMPLE02");
    return 0;
}
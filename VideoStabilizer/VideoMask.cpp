//
// Created by inkp on 2017. 11. 19..
//
#include <opencv2/opencv.hpp>
#include "opencv2/imgcodecs/imgcodecs.hpp"
#include "opencv2/videoio/videoio.hpp"
#include "stdlib.h"
#include "stdio.h"
#include <iostream>

using namespace std;
using namespace cv;

int main() {
    //Java to C++ : Input file's path, output file's path
    string outputFilePath = "results.mp4";
    string inputFilePath = "test.mp4";

    int startHeight, endHeight;
    int startWidth, endWidth;

    VideoCapture cap(inputFilePath);
    assert(cap.isOpened());

    Mat currentFrame, firstFrame;
    cap >> firstFrame;

    int videoWidth = firstFrame.cols;
    int videoHeight = firstFrame.rows;
//    VideoWriter outputVideo("stabilized.avi",CV_FOURCC('M','J','P','G'), 30, Size(w, h), true);
    VideoWriter outputVideo(outputFilePath, CV_FOURCC('X','2','6','4'), 30, Size(videoWidth, videoHeight), true);
    while (true) {
        cap >> currentFrame;
        if (currentFrame.data == NULL) {
            break;
        }

        // Cover current frame with first frame
        for(int i=0; i<videoHeight; i++){
            if(startHeight < i && i < endHeight){
                continue;
            }
            for(int j=0; j<videoWidth; j++){
                if(startWidth < j && j < endWidth){
                        continue;
                }
                currentFrame.at<Vec3b>(i, j) = firstFrame.at<Vec3b>(i, j);
            }
        }

        outputVideo.write(currentFrame);
        imshow("before and after", currentFrame);
        waitKey(10);
    }
    return 0;
}

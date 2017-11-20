package com.example.inkp.javacvtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.bytedeco.javacpp.avutil;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "JavaCV-TEST";
    private final String OUTPUT_FILEPATH = "/sdcard/output.mp4";

    TextView tvStatus;
    EditText etFilePath;
    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = (TextView) findViewById(R.id.main_tv_status);
        etFilePath = (EditText) findViewById(R.id.main_et_filepath);
        btStart = (Button) findViewById(R.id.main_bt_start);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStatus.setText("We're making video! this processing gonna take some time. go get some a cup of coffee.");
                String fileName = etFilePath.getText().toString();
                makeVideo(fileName);
            }
        });
    }


    // Prototype -
    private void makeVideo(String fileName) {

        int videoWidth = 0;
        int videoHeight = 0;

        Point composeStartPoint;
        Point composeEndPoint;

        FFmpegFrameGrabber ffmpegFrameGrabber = new FFmpegFrameGrabber("/sdcard/" + fileName + ".mp4");
        try {
            ffmpegFrameGrabber.start();
            videoWidth = ffmpegFrameGrabber.getImageWidth();
            videoHeight = ffmpegFrameGrabber.getImageHeight();
        } catch (FrameGrabber.Exception e) {
            Log.e(TAG, "Grabber Exception : " + e);
            e.printStackTrace();
            return;
        }

        FFmpegFrameRecorder ffmpegFrameRecorder = new FFmpegFrameRecorder(
                OUTPUT_FILEPATH,
                videoWidth,
                videoHeight
        );

        initFFmpegRecorder(ffmpegFrameRecorder);


        try {
            Frame firstFrame = ffmpegFrameGrabber.grabFrame();
            OpenCVFrameConverter.ToMat frame2MatConverter = new OpenCVFrameConverter.ToMat();

            opencv_core.Mat firstMat = frame2MatConverter.convert(firstFrame);
            UByteBufferIndexer firstBufIndexer = firstMat.createIndexer();

            //FrameRecorder initialize
            Log.d(TAG, "FrameRecorder start");

            Frame currentGrabbedFrame;
            opencv_core.Mat curMat;

            while ((currentGrabbedFrame = ffmpegFrameGrabber.grab()) != null) {
                curMat = frame2MatConverter.convert(currentGrabbedFrame);
                UByteBufferIndexer bufferIndexer = null;
                if (curMat != null) {
                    bufferIndexer = curMat.createIndexer();
                }

                // Cover selected area with first frame's pixel
                for (int i = 0; i < videoHeight; i++) {
                    if (videoHeight / 2 < i && i < videoHeight) {
                    continue;
                    }
                    for (int j = 0; j < videoWidth; j++) {
                        if ( videoWidth / 2 < j && j < videoWidth) {
                            continue;
                        }
                        if (bufferIndexer != null) {
                            //bufferIndexer.put(i, j, firstBufIndexer.get(i,j));
                            bufferIndexer.put(i, j, 0);
                        }
                    }
                }
                ffmpegFrameRecorder.record(currentGrabbedFrame);
            }

            ffmpegFrameRecorder.stop();
            ffmpegFrameGrabber.stop();
            Log.d(TAG, "record done, stop");
            tvStatus.setText("DONE!");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "record fail - " + e);
            tvStatus.setText("ERROR..... T.T");

        }
    }

    private class Point {
        int x;
        int y;

        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    // TODO : Option setup with params
    private void initFFmpegRecorder(FFmpegFrameRecorder ffmpegFrameRecorder) {
        try {
            ffmpegFrameRecorder.setVideoCodec(13);
            ffmpegFrameRecorder.setFormat("mp4");
            ffmpegFrameRecorder.setAudioChannels(1);
            ffmpegFrameRecorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            ffmpegFrameRecorder.setFrameRate(30);
            ffmpegFrameRecorder.setVideoBitrate(10 * 1024 * 1024);
            ffmpegFrameRecorder.start();
        } catch (FrameRecorder.Exception e){
            Log.e(TAG, "Recorder Exception :" + e);
            e.printStackTrace();
        }
    }
}
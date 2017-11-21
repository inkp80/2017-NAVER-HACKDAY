package com.example.inkp.cinemagraph;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.inkp.cinemagraph.common.RequestCodes;
import com.example.inkp.cinemagraph.crop.CropImageView;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;

import static com.example.inkp.cinemagraph.common.RequestCodes.VIDEO_GALLERY_REQUEST_CODE;

/**
 * Created by macbook on 2017. 11. 21..
 */

public class VideoComposeActivity extends AppCompatActivity{

    private static final String TAG = VideoComposeActivity.class.getSimpleName();
    private static final String TEMPERARAL_FRAME_FILE_PATH = "/sdcard/temp_frame.png";
    private ProgressDialog progressDialog;
    FfmpegModule ffmpegModule;

    private CropImageView cropViewer;
    private RelativeLayout lnSelectLayout;
    private Button btSelectVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_compose);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);

        ffmpegModule = new FfmpegModule(this);

        try {
            ffmpegModule.loadFfmpegBinary(new LoadBinaryResponseHandler(){
                @Override
                public void onFailure() {
                    super.onFailure();
                }

                @Override
                public void onSuccess() {
                    super.onSuccess();
                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
        } catch (FFmpegNotSupportedException e){
            Log.e(TAG, "FFmpeg support issue " + e);
            e.printStackTrace();
        }

        lnSelectLayout = (RelativeLayout) findViewById(R.id.composer_ln_selector);

        btSelectVideo = (Button) findViewById(R.id.composer_bt_select_video);
        btSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RequestCodes.VIDEO_GALLERY_REQUEST_CODE);
            }
        });

        cropViewer = (CropImageView) findViewById(R.id.composer_cv_crop_image_view);
        cropViewer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_GALLERY_REQUEST_CODE) {

            Uri selectedImageUri = data.getData();

            if (selectedImageUri != null) {

                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImageUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String videoPath = cursor.getString(columnIndex);

                cursor.close();

                Toast.makeText(this, videoPath, Toast.LENGTH_SHORT).show();
                getFrameFromVideo(videoPath);

                // TODO : request to user select section for compose video
                // requestUserToSelectSection()?
            }
        } else {
            return;
        }
    }

    private void getFrameFromVideo(final String videoPath){

        File file = new File(TEMPERARAL_FRAME_FILE_PATH);
        if(file.exists()){
            Log.d(TAG, "file exist, remove this file");
            file.delete();
        }

        String extractFrame = "-i " + videoPath  + " -r 1/1 " + TEMPERARAL_FRAME_FILE_PATH;
        String[] cmd = extractFrame.split(" ");
        try {
            ffmpegModule.execFfmpegBinary(cmd, new ExecuteBinaryResponseHandler() {
                @Override
                public void onSuccess(String message) {
                    super.onSuccess(message);
                    Log.d(TAG, message);
                    Toast.makeText(VideoComposeActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onProgress(String message) {
                    super.onProgress(message);
                    Log.d(TAG, "progress: " + message);
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Log.d(TAG, message);
                    Toast.makeText(VideoComposeActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
                    startComposeVideo(videoPath, TEMPERARAL_FRAME_FILE_PATH);
                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e){
            Log.e(TAG, "Command already running exception : " + e);
            e.printStackTrace();
        }
//        cropViewer.setImageBitmap();
    }

    private void startComposeVideo(String targetVideoPath, String targetFramePath){
        // TODO : rename outputPath, Need to decide how to make output-file-name
        String outputPath = "/sdcard/res.mp4";

        String overlay ="-i /sdcard/train.mp4 -i /sdcard/abc.png -filter_complex overlay=1:1 /sdcard/results.mp4";
        String cmdStr = "-i " + targetVideoPath + " -i " + targetFramePath + "-filter_complex overlay=1:1 " + outputPath;
        String cmd = "-i " + targetVideoPath + " -i " + targetFramePath + " -filter_complex overlay=1:1 " + outputPath;

        String[] command = cmd.split(" ");
        try {
            ffmpegModule.execFfmpegBinary(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                }

                @Override
                public void onSuccess(String s) {
                }

                @Override
                public void onProgress(String s) {
                    Log.d(TAG, "Started command : ffmpeg ");
                    progressDialog.setMessage("Processing\n" + s);
                }

                @Override
                public void onStart() {
//                outputLayout.removeAllViews();
                    Log.d(TAG, "Started command : ffmpeg ");
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "Finished command : ffmpeg ");
                    progressDialog.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e){
            Log.e(TAG, "Command already running exception : " + e);
            e.printStackTrace();
        }
    }


    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(VideoComposeActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.device_not_supported))
                .setMessage(getString(R.string.device_not_supported_message))
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        VideoComposeActivity.this.finish();
                    }
                })
                .create()
                .show();
    }
}

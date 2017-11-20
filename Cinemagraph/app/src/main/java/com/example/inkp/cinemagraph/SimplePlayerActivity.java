package com.example.inkp.cinemagraph;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import static com.example.inkp.cinemagraph.MainActivity.VIDEO_FILE_PATH_KEY;

/**
 * Created by macbook on 2017. 11. 18..
 */

public class SimplePlayerActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;
    private ImageButton btPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        btPlay = (ImageButton) findViewById(R.id.bt_play);
        videoView = (VideoView) findViewById(R.id.vw_video_player);
        mediaController = new MediaController(this);

//        String filePath = Environment.getExternalStorageDirectory() + "/video.mp4";
//        Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String videoPath = extras.getString(VIDEO_FILE_PATH_KEY);
            videoView.setVideoPath(videoPath);
            mediaController.setMediaPlayer(videoView);
            videoView.setMediaController(mediaController);
            videoView.requestFocus();

            if(savedInstanceState != null){
                Log.d("gad","asdadaf");
                videoView.seekTo(savedInstanceState.getInt("currentPos"));
            }
//            mediaController.show(0);
//            videoView.start();
        }

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(SimplePlayerActivity.this, "video stop!", Toast.LENGTH_SHORT).show();
                videoView.seekTo(0);
                btPlay.setVisibility(View.VISIBLE);
            }
        });

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.start();
                if(videoView.isPlaying()){
                    btPlay.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("sada","asda");

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mediaController.show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            mediaController.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(videoView.isPlaying()){
            Log.d("gad","playing");
            outState.putInt("currentPos", videoView.getCurrentPosition());
        }
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mediaController.hide();
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}

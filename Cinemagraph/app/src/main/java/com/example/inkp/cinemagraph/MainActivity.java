package com.example.inkp.cinemagraph;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.inkp.cinemagraph.common.RequestCodes.VIDEO_GALLERY_REQUEST_CODE;
import static com.example.inkp.cinemagraph.common.StringKeyValues.VIDEO_FILE_PATH_KEY;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    private Button btSelectVideo;
    private Button btComposeVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSelectVideo = (Button) findViewById(R.id.main_bt_select_video);
        btSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, VIDEO_GALLERY_REQUEST_CODE);
            }
        });

        btComposeVideo = (Button) findViewById(R.id.main_bt_compose_video);
        btComposeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoComposeActivity.class);
                startActivity(intent);
            }
        });

        checkDangerousPermissions();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
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

                Intent intentForSimplePlayer =
                        new Intent(MainActivity.this, SimplePlayerActivity.class);

                intentForSimplePlayer.putExtra(VIDEO_FILE_PATH_KEY, videoPath);
                intentForSimplePlayer.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intentForSimplePlayer);
            }
        } else {
            return;
        }
    }
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Permission granted.

        } else {
            // Permission denied.

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                // Should show request permission rationale.
            } else {
                // Request permission.
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted.

                } else {
                    // Permission denied.

                }
            }
        }
    }
}

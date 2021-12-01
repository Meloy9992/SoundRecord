package com.example.soundrecording;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Permission;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.view.View.VISIBLE;

public class EditActivity extends AppCompatActivity {

    private TextView timeLength;
    private FloatingActionButton buttonRec;
    private FloatingActionButton buttonStop;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    private Random random;
    public static final int RequestPermissionCode = 1;
    private String filePath = "null";
    private String fileName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        init();
    }


    public void init(){
        timeLength = findViewById(R.id.soundLength);
        buttonStop = findViewById(R.id.recStop);
        buttonRec = findViewById(R.id.recStart);

        random = new Random();
        accessPermission();
    }

    private void createAudioRecorder(){

    }

    public void recordStart(View view){
     fileName = Environment.getExternalStorageDirectory() + "/record.3gpp";
     try {
         releaseRecorder();
         File outFile = new File(fileName);
         if (outFile.exists()){
             outFile.delete();
         }

         mediaRecorder = new MediaRecorder();
         mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
         mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
         mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
         mediaRecorder.setOutputFile(fileName);
         mediaRecorder.prepare();
         mediaRecorder.start();
     } catch (IOException e) {
         e.printStackTrace();
     }
    }

    public void recordStop(View view) throws IOException {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        releaseRecorder();
    }

    public void timer(){

    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playStart(View v) {
        try {
            releasePlayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fileName);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void accessPermission(){
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }
}

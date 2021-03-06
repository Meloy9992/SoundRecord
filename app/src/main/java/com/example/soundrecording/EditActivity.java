package com.example.soundrecording;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.soundrecording.dataBase.ManagerDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EditActivity extends AppCompatActivity {

    private ManagerDb manager;
    private FloatingActionButton buttonRec;
    private FloatingActionButton buttonStop;
    private MediaRecorder mediaRecorder;
    private String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    private Random random;
    private Chronometer chronometer;
    public static final int RequestPermissionCode = 1;
    private String nameRec = "null";
    private String filePath = "null";
    private String fileName = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        init();
    }


    private void init(){
        buttonStop = findViewById(R.id.recStop);
        chronometer = findViewById(R.id.soundLength);
        buttonRec = findViewById(R.id.recStart);
        manager = new ManagerDb(this);
        random = new Random();
        accessPermission();
    }

    public void recordStart(View view){
        buttonStop.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
        nameRec = createRandomAudioFileName(5);
        fileName = Environment.getExternalStorageDirectory() + "/" + nameRec +  "record.3gpp";
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
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String timeAdded(){
        Date createdDate = new java.util.Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.format(createdDate);
    }

    public void recordStop(View view) throws IOException {
        view.setVisibility(View.GONE);
        buttonRec.setVisibility(View.VISIBLE);
        if (mediaRecorder != null) {
            mediaRecorder.stop();
        }
        chronometer.stop();
        manager.insertToDb(nameRec, fileName, (String) chronometer.getText(), timeAdded());
        Toast.makeText(this, "???????? ???????????? ?????????????????? ??: " + fileName, Toast.LENGTH_LONG).show();
        manager.closeDb();
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        manager.openDb();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseRecorder();
    }

    private void releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }



    public void accessPermission(){
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }
    public String createRandomAudioFileName(int string){
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

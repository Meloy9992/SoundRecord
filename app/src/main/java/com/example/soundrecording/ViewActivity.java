package com.example.soundrecording;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soundrecording.adapters.ListAudio;
import com.example.soundrecording.dataBase.ConstantsDb;
import com.example.soundrecording.dataBase.ManagerDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewActivity extends AppCompatActivity {

    private ManagerDb manager;
    private TextView filePath;
    private TextView timeAdded;
    private TextView audioName;
    private Chronometer chronometer;
    private SeekBar playBar;
    private FloatingActionButton playButton;
    private ListAudio item;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        init();
        getMyIntent();
    }

    private void getMyIntent() {
        Intent intent = getIntent();
        if (intent != null){
            item = (ListAudio) intent.getSerializableExtra(ConstantsDb.LIST_ITEM_INTENT);

           filePath.setText(item.getFilePath());
           timeAdded.setText(item.getAddedTime());
           audioName.setText(item.getRecName());
        }
    }

    private void init(){
        manager = new ManagerDb(this);
        filePath = findViewById(R.id.filePath);
        timeAdded = findViewById(R.id.timeAdded);
        audioName = findViewById(R.id.audioName);
        chronometer = findViewById(R.id.chSoundLength);
        playBar = findViewById(R.id.playBar);
        playButton = findViewById(R.id.playButton);
    }

    public void onClickPlay(View view){
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(item.getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

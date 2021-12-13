package com.example.soundrecording;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soundrecording.adapters.ListAudio;
import com.example.soundrecording.dataBase.ConstantsDb;
import com.example.soundrecording.dataBase.ManagerDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class ViewActivity extends AppCompatActivity {

    private ManagerDb manager;
    private TextView filePath;
    private TextView timeAdded;
    private TextView audioName;
    private Chronometer chronometer;
    private SeekBar seekBar;
    private FloatingActionButton playButton;
    private ListAudio item;
    private MediaPlayer mediaPlayer;
    private Runnable runnable;
    private int recLength;
    private Handler handler;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);
        init();
        getMyIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
        }
        manager.closeDb(); // ЗАКРЫТЬ БАЗУ ДАННЫХ
    }

    public void onClickPlay(View view){
        playMusic();
    }


    private void getMyIntent() {
        Intent intent = getIntent();
        if (intent != null){
            item = (ListAudio) intent.getSerializableExtra(ConstantsDb.LIST_ITEM_INTENT);
           filePath.setText(item.getFilePath());
           timeAdded.setText(item.getAddedTime());
           audioName.setText(item.getRecName());
           recLength = Integer.parseInt(item.getRecLength());
            setTimeChronometer();
        }
    }

    private void setTimeChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime() + 1000 * recLength);
    }

    private void init(){
        handler = new Handler();
        manager = new ManagerDb(this);
        filePath = findViewById(R.id.filePath);
        timeAdded = findViewById(R.id.timeAdded);
        audioName = findViewById(R.id.audioName);
        chronometer = findViewById(R.id.chSoundLength);
        chronometer.setCountDown(true);
        seekBar = findViewById(R.id.playBar);
        playButton = findViewById(R.id.playButton);

        mediaPlayer = new MediaPlayer();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }
    private void playMusic(){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(item.getFilePath());

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                setTimeChronometer();
                chronometer.start();
                mediaPlayer.start();
                updateSeekBar();
            }
        });


    }
    private void updateSeekBar(){
        int currPos = mediaPlayer.getCurrentPosition();
        seekBar.setProgress(currPos);
        runnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
            if( chronometer.getText().toString().equalsIgnoreCase("00:00")){
                chronometer.stop();
            }
        }
    };
    handler.postDelayed(runnable, 1000);
    }
}

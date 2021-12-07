package com.example.soundrecording;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.soundrecording.adapters.ListAudio;
import com.example.soundrecording.adapters.MainAdapter;
import com.example.soundrecording.dataBase.ManagerDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ManagerDb manager;
    private RecyclerView recyclerView;
    private FloatingActionButton buttonRec;
    private ListAudio listAudio;
    private MainAdapter mainAdapter;

    public void init(){
        manager = new ManagerDb(this);
     //   buttonRec = findViewById(R.id.recButton);
        recyclerView = findViewById(R.id.rcView);
        mainAdapter = new MainAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        manager.openDb();
//        mainAdapter.updateAdapter(manager.getFromDb(""));
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.closeDb(); // ЗАКРЫТЬ БАЗУ ДАННЫХ
    }
}
package com.example.step12gameview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //게임 시작 버튼을 눌렀을 때 GameActivity로 이동하도록 한다.
        Button startBtn=findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });
        /*
        * GameActivity 에서 사용하기 위해 없앰 *

        //SoundManager 객체 생성하고
        SoundManager manager=new SoundManager(this);

        //효과음 로딩하기 (메인 액티비티가 실행되면 (onCreate가 호출되면) 로딩됨)
        manager.addSound(1, R.raw.laser1);
        manager.addSound(2, R.raw.shoot1);
        manager.addSound(3, R.raw.birddie);

        Button playBtn=findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() { //사운드 재생 버튼을 누르면 여기가 실행됨.
            @Override
            public void onClick(View v) {
                manager.playSound(1); //1번 사운드 재생 / 2번 사운드 재생하고 싶으면 2 전달.
            }
        });
        */
    }
}
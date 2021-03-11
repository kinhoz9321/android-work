package com.example.step21service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //버튼의 참조값 얻어와서 리스너 등록하기
        Button startBtn=findViewById(R.id.startBtn);
        Button stopBtn=findViewById(R.id.stopBtn);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startBtn:
                //MyService 를 활성화 시키기 위한 객체 / 서비스를 시작시키려면 Intent 가 필요하다.
                Intent intent1=new Intent(this, MyService.class);
                //액티비티의 메소드를 이용해서 서비스 시작 시키기
                startService(intent1);

                break;
            case R.id.stopBtn:
                //MyService 를 활성화 시키기 위한 객체 / 서비스를 시작시키려면 Intent 가 필요하다.
                Intent intent2=new Intent(this, MyService.class);
                //액티비티의 메소드를 이용해서 서비스 종료 시키기
                stopService(intent2);

                break;
        }
    }
}
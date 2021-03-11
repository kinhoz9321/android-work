package com.example.step21musicservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //서비스의 참조값을 저장할 필드
    MusicService service;
    //서비스에 연결되었는지 여부
    boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn=findViewById(R.id.startBtn);
        Button stopBtn=findViewById(R.id.stopBtn);
        Button pauseBtn=findViewById(R.id.pauseBtn);
        Button playBtn=findViewById(R.id.playBtn);
        startBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        playBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.startBtn:
                //MyService 를 활성화 시키기 위한 객체 / 서비스를 시작시키려면 Intent 가 필요하다.
                Intent intent1=new Intent(this, MusicService.class);
                //액티비티의 메소드를 이용해서 서비스 시작 시키기
                startService(intent1);

                break;
            case R.id.stopBtn:
                if(isConnected){
                    //서비스 바인딩 해제
                    unbindService(sConn);
                    isConnected=false;
                }

                //MyService 를 활성화 시키기 위한 객체 / 서비스를 시작시키려면 Intent 가 필요하다.
                Intent intent2=new Intent(this, MusicService.class);
                //액티비티의 메소드를 이용해서 서비스 종료 시키기
                stopService(intent2);

                break;
            case R.id.pauseBtn://일시정지 버튼을 눌렀을 때
                service.pauseMusic();

                break;
            case R.id.playBtn://재생 버튼을 눌렀을 때
                service.playMusic();

                break;
        }
    }
    //서비스에 연결한다.
    @Override
    protected void onStart() {
        super.onStart();
        //MusicService 에 연결할 인텐트 객체
        Intent intent=new Intent(this, MusicService.class);
        //액티비티의 bindService() 메소드를 이용해서 연결한다.
        //만일 서비스가 시작이 되지 않았으면 서비스 객체를 생성해서
        //시작할 준비만 된 서비스에 바인딩이 된다.
        bindService(intent, sConn, Context.BIND_AUTO_CREATE);
    }
    //서비스연결을 해제 한다.
    @Override
    protected void onStop() {
        if(isConnected) {
            //서비스 바인딩 해제
            unbindService(sConn);
            isConnected = false;
        }
        super.onStop();
    }

    //서비스 연결 객체를 필드로 선언한다
    // ServiceConnection 추상클래스 type
    ServiceConnection sConn=new ServiceConnection() {
        //서비스에 연결되었을때 호출되는 메소드
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {//연결요청을 하면 실행순서가 들어오는 곳
            //IBinder 객체를 원래 type 으로 casting
            MusicService.LocalBinder lBinder=(MusicService.LocalBinder)binder;

            //MusicService 의 참조값을 필드에 저장
            service=lBinder.getService();
            //연결되었다고 표시
            isConnected=true;
            //액티비티의 참조값을 전달하기
            lBinder.setActivity(MainActivity.this);
            Log.e("MainActivity", "connected!");

            Log.e("MainActivity", "service:"+service);
        }

        //서비스와 연결 해제 되었을때 호출되는 메소드
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //연결 해제 되었다고 표시
            isConnected=false;

        }
    };
}
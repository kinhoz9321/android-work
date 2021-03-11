package com.example.step21musicservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
    //필드
    MediaPlayer player;
    //필드로 바인더 객체를 가지고 있게 한다.
    final IBinder binder=new LocalBinder();
    //필드로 MainActivify 의 참조값을 저장하기 위해
    MainActivity activity;

    public MusicService() {
        Log.e("MusicService", "MusicService()");
    }

    //액티비티가 서비스에 연결하면 호출되는 메소드
    @Override
    public IBinder onBind(Intent intent) {
        //바인터 객체를 리턴해준다.
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MusicService", "onCreate()");
        //res/raw/mp3music 음악을 로딩해서 준비하기
        player=MediaPlayer.create(this, R.raw.jrabbitmusic);
    }

    //서비스가 시작될 때 호출되는 메소드
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //재생
        player.start();
        //운영체제가 강제로 종료시켜도 다시 시작 되지 않도록
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //중지 및 자원 해제
        player.stop();
        player.release();
        //종료 되기 전에 작업은 super.onDestroy() 호출하기 전에 한다.
        super.onDestroy();
    }

    //만일 특정 액티비티에서 아래의 메소드를 호출하고 싶다면?
    public void pauseMusic(){
        player.pause();
    }

    public void playMusic(){
        player.start();
    }

    //Binder 클래스를 상속 받아서 LocalBinder 클래스를 정의한다.
    public class LocalBinder extends Binder{
        //서비스 객체의 참조값을 리턴해주는 메소드
        public MusicService getService(){
            return MusicService.this;
        }
        //MainActivity 의 참조값을 전달받는 메소드
        public void setActivity(MainActivity activity){
            //인자로 전달받은 액티비티의 참조값을
            //MusicService의 필드에 저장하기
            MusicService.this.activity=activity;
        }
    }
}
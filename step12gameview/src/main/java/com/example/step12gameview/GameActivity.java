package com.example.step12gameview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    /* 필드 */
    //사운드 매니저 객체
    SoundManager sManager;
    //사운드의 종류별로 상수 정의하기 (다른 곳에서 접근할 수 있도록 public static final 로 만들어주기)
    //애초에 맵 정의할 때 키값을 Stirng으로 받게 할 수도 있지만 안드로이드는 상수로 정의하는 게 일반적이다.
    public static final int SOUND_LAZER=1;
    public static final int SOUND_SHOOT=2;
    public static final int SOUND_BIRDDIE=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GameView 객체를 생성해서
        GameView view=new GameView(this); //this = GameActivity 의 참조값 전달
        //화면을 GameView 로 모두 채운다.
        setContentView(view);
        //사운드 매니저 객체를 생성해서 필드에 저장한다.
        sManager=new SoundManager(this);
        //사운드 매니저 객체를 GameView 객체에 넣어준다.
        view.setsManager(sManager);
    }

    //다시 재시작될 때 onStart 메소드 효과음 로딩
    @Override
    protected void onStart() {
        super.onStart();
        //효과음 미리 로딩하기
        sManager.addSound(SOUND_LAZER, R.raw.laser1);
        sManager.addSound(SOUND_SHOOT, R.raw.shoot1);
        sManager.addSound(SOUND_BIRDDIE, R.raw.birddie);
    }

    //게임 액티비티가 멈출때 자원해제 하기 위해 onStop 메소드 사용
   @Override
    protected void onStop() {
        super.onStop();
        //자원 해제
        sManager.release();
    }
}
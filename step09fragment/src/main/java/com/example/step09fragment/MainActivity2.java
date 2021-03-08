package com.example.step09fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity2 extends AppCompatActivity implements GuraFragment.GuraFragmentListener
    , View.OnClickListener {//모든 Activity는 FragmentActivity를 상속받음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //레이아웃 xml 문서를 전개해서 화면 구성
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2); //setContentView (화면을 구성)
        Button resetBtn=findViewById(R.id.resetBtn); //findViewById (UI의 참조값 얻어오기) / 제일 중요한 메소드 2가지
        resetBtn.setOnClickListener(this);//implements / 리스너 등록
    }

    //프래그먼트 액티비티에서 호출하는 메소드라고 가정
    //GuraFragment 에서 특정 시점에 호출하는 메소드 (프래그먼트를 클릭하면 메인액티비티에 글씨 출력)
    public void wow(String msg){
        TextView textConsole=findViewById(R.id.textConsole);
        textConsole.setText(msg); //내용 전달
    }

    @Override
    public void onClick(View v) {
        //액티비티의 getSupportFragmentManager() 메소드를 이용해서 FragmentManager 객체의 참조값을 얻어온다.
        FragmentManager fm=getSupportFragmentManager();
        //FragmentManager 객체의 메소드를 이용해서 Fragment 객체의 참조값 얻어오기
        Fragment f=fm.findFragmentById(R.id.guraFragment);
        //원래 type으로 casting 하기
        GuraFragment gf=(GuraFragment)f;
        gf.reset();

        //GuraFragment 객체의 참조값을 얻어오는 작업을 1줄로 줄이면 이렇게 된다. (자기가 관리하는 프레그먼트니까 원래 타입으로 써도 된다.)
        GuraFragment gf2=(GuraFragment)getSupportFragmentManager().findFragmentById(R.id.guraFragment);

    }
}
/*
    Fragment가 자신이 관리하는 프로그램을 액티비티에 전달할 일이 있다. 프래그먼트의 재활용성?
    어떻게 할까?
    Fragment와 Activity 사이의 소통 익히기
    존나 어려움
    프래그먼트에 인터페이스가 정의되어 있는 경우

    *프래그먼트에서 액티비티에다 정보를 전달하는 방법*
    프래그먼트에 인터페이스를 정의해두고
    액티비티에서 프래그먼트의 인터페이스를 구현한다

    *자주 쓰는 메소드 3가지 기억하기*
    setContentView
    findViewById
    getSupportFragmentManager

    안드로이드 잘하면 자바 중급... (복습이나 행...ㅠ)
 */
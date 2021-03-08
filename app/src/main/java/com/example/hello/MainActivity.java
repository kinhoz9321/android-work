package com.example.hello;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//app을 실행했을 때 처음 사용자를 대면하는 MainActivity
public class MainActivity extends AppCompatActivity {

    //Activity 가 활성화 될 때 onCreate() 메소드가 호출된다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main);
        // Activity 가 활성화 되는 시점에 원하는 작업이 있으면 여기에 코딩한다.
    }

    /*
        Activity_main.xml 에 정의된 버튼을 눌렀을 때 호출되는 메소드 정의하기
        메소드의 인자로 view type 을 전달받도록 만들어야 한다.
     */
    public void sendClicked(View v){
        //콘솔창에 문자열 출력하기
        //Log 클래스 사용. tag:, msg: 는 작성하지 않는다. 자동으로 나옴.
        Log.i("sendClicked()", "전송 버튼을 눌렀네?");
    }

    public void send2Clicked(View v){
        //잠깐 떴다가 사라지는 토스트 메세지 띄우기 (밑에 알림처럼 띄워짐)
        Toast.makeText(this, "전송 버튼을 눌렀네?", Toast.LENGTH_LONG).show();
    }

    public void deleteClicked(View v){
        Toast.makeText(this, "삭제 버튼을 눌렀네?", Toast.LENGTH_LONG).show();
    }

    public void delete2Clicked(View v){
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("삭제 합니다")
                .setPositiveButton("확인", null)
                .create()
                .show();
    }


}
package com.example.step13broadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //필드
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //방송 수신자 객체를 생성해서 동작이 가능하도록 등록하기 (이것까지 해야 동작)
        MyReceiver mr=new MyReceiver();
        registerReceiver(mr, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        //TextView의 참조값을 필드에 저장
        textView=findViewById(R.id.textView);
    }
    //방송 수신자 객체에서 호출할 메소드
    public void setMessage(String msg){
        textView.setText(msg);
    }
}
/*
    안드로이드 4대 컴포넌트
    1. Activity
    -레이아웃 xml 문서를 전개해서 화면 구성 (컨트롤러 역할)
    2. BroadcastReceiver
    -폰 안에 일어나는 이벤트(방송)들을 감시(수신)해서 작업을 하고 싶을 때 사용
    3. Service
    -UI없이 백그라운드에서 활동. ex)mp3
    4. ContentProvider
    -컨텐츠 공급자(특정 어플리케이션은 이렇게 될 수 있다.)
 */
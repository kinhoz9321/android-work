package com.example.step06listener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
// listener 를 직접 implements (많이 쓰이는 패턴) 액티비티 자체가 리스너가 된 패턴
public class MainActivity3 extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //id 가 sendBtn 인 Button 의 참조값 얻어오기 (아이디, 지역변수/필드명을 똑같이하면 편하다.)
        Button sendBtn=findViewById(R.id.sendBtn);

        //view 클래스 안에 정의된 onClickListener 인터페이스 타입 / View.OnClickListener 지칭. 클래스 안에 인터페이스를 정의할 수 있다.
        //전송 버튼에 클릭 리스너 등록하기
        //xml 문서에 onClick 속성을 쓰지 않고도 동작하게 만듦. (중요)
        sendBtn.setOnClickListener(this);
    }

    // implements로 강제 구현된 메소드
    @Override
    public void onClick(View v) {
        Toast.makeText(this, "전송 합니다. 3", Toast.LENGTH_SHORT).show();
    }
}
package com.example.step06listener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //id 가 sendBtn 인 Button 의 참조값 얻어오기 (아이디, 지역변수/필드명을 똑같이하면 편하다.)
        Button sendBtn=findViewById(R.id.sendBtn);

        //익명의 local inner class 를 이용해서 view.OnClickListener type 의 참조값 얻어내기
        View.OnClickListener listener=new View.OnClickListener(){
            //이 리스너 객체를 등록한 UI에 클릭 이벤트가 발생하면 호출되는 메소드 (하고싶은 동작이 있으면 여기에 코딩하기)
            @Override
            public void onClick(View v) {//view class 게임화면 같은 걸 view를 상속받아서 만든다? / 모든 ui의 부모 / view v 에는 이벤트가 일어난 요소의 참조값이 전달된다. (눌러진 버튼의 참조값)
                //v.해서 참조값을 사용한다.
                //바깥 클래스의 이름을 반드시 명시해주어야 한다.
                Toast.makeText(MainActivity.this, "전송합니다.", Toast.LENGTH_LONG).show();
            }
        };

        //view 클래스 안에 정의된 onClickListener 인터페이스 타입 / View.OnClickListener 지칭. 클래스 안에 인터페이스를 정의할 수 있다.
        //전송 버튼에 클릭 리스너 등록하기
        //xml 문서에 onClick 속성을 쓰지 않고도 동작하게 만듦. (중요)
        sendBtn.setOnClickListener(listener);
    }
}
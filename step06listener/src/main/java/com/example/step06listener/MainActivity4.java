package com.example.step06listener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// listener 를 직접 implements (많이 쓰이는 패턴) alt+enter 추상메소드 implements
public class MainActivity4 extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //id 가 sendBtn 인 Button 의 참조값 얻어오기 (아이디, 지역변수/필드명을 똑같이하면 편하다.)
        Button sendBtn=findViewById(R.id.sendBtn);//이 주민번호를 갖고있는 요소의 번호(참조값)를 가지고 와라!
        //수정, 삭제 버튼의 참조값 얻어오기
        Button updateBtn=findViewById(R.id.updateBtn);
        Button deleteBtn=findViewById(R.id.deleteBtn);
        //view 클래스 안에 정의된 onClickListener 인터페이스 타입 / View.OnClickListener 지칭. 클래스 안에 인터페이스를 정의할 수 있다.
        //전송 버튼에 클릭 리스너 등록하기
        //xml 문서에 onClick 속성을 쓰지 않고도 동작하게 만듦. (중요)
        sendBtn.setOnClickListener(this);
        //수정, 삭제 버튼에도 동일한 리스너 등록하기
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        sendBtn.setOnLongClickListener(this);
    }

    /* implements로 강제 구현된 메소드
    @Override
    public void onClick(View v) {
        Toast.makeText(this, "나는 네가 뭘 눌렀는지 알고 있다. 전송, 수정, 삭제 중에 하나겠지!", Toast.LENGTH_SHORT).show();
    }
    */
    /*
        리스너로 등록한 UI에 클릭 이벤트가 발생하면 호출되는 메소드
        인자로 전달된 View v 에는 클릭 이벤트가 일어난 UI(Button) 의 참조값이 들어있다.
     */
    @Override
    public void onClick(View v) {//전송버튼의 참조값, 수정버튼의 참조값, 삭제버튼의 참조값 각각 다른 참조값이 부모타입 v에 전달됨.
        //int id=v.getId();
        //이벤트가 일어난 UI 에 부여된 아이디값을 읽어와서 분기 한다.
        /*
        if(v.getId()==R.id.sendBtn){ 너 주민번호가 뭐야! == 주민번호가 sendBtn과 같나 비교해보세용
            Toast.makeText(this, "전송 합니다.", Toast.LENGTH_SHORT).show();
        }else if(v.getId()==R.id.updateBtn){
            Toast.makeText(this, "수정 합니다.", Toast.LENGTH_SHORT).show();
        }else if(v.getId()==R.id.deleteBtn){
            Toast.makeText(this, "삭제 합니다.", Toast.LENGTH_SHORT).show();
        }
        */
        //if~else if문 대신에 switch 문도 가능하다. 구조가 복잡해보이면 switch 문을 섞어주는게 좋다.
        switch (v.getId()){//id에 부여된 값이 있다면
            case R.id.sendBtn:
                Toast.makeText(this, "전송 합니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.updateBtn:
                Toast.makeText(this, "수정 합니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.deleteBtn:
                Toast.makeText(this, "삭제 합니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //전송버튼을 오랫동안 누르고 있으면 실행순서가 이리로 온다.
    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this, " 고만 좀 눌러라", Toast.LENGTH_SHORT).show();
        return false;
    }
}
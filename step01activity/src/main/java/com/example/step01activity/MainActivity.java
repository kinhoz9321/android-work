package com.example.step01activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

//app 을 실행했을 때 처음 사용자를 대면하는 MainActivity
public class MainActivity extends AppCompatActivity {

    //액티비티가 활성화 될 때 onCreate() 메소드가 호출된다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/activity_main.xml (레이아웃 xml) 문서를 전개해서 화면 구성하기 (new ConstraintLayout, new Button 해서 객체를 생성한다는 의미)
        setContentView(R.layout.activity_main);
        // xml 문서를 전개한다는 것은 실제로 텍스트 문서를 해석해서 화면에 ui를 배치했다 라고 생각하기
        //액티비티가 활성화 되는 시점에 원하는 작업이 있으면 여기에 코딩한다.
    }

    /*
        activity_main.xml 에 정의된 버튼을 눌렀을 때 호출되는 메소드 정의하기
        메소드의 인자로 view type 을 전달받도록 만들어야 한다.
        view = 모든 ui 의 부모타입
        ex) ConstraintLayout, Button / xml 문서에서 ctrl 누르고 클릭! class 확인해보기
        View v=new Button(); 가능 / 부모타입으로 받은 것.
     */
    public void sendClicked(View v) {
        //콘솔창에 문자열 출력하기
        //Log 클래스 사용. tag:, msg: 는 작성하지 않는다. 자동으로 나옴.
        Log.i("sendClicked()", "전송 버튼을 눌렀네?");
        //잠깐 떴다가 사라지는 토스트 메세지(밑에 알림처럼 띄워짐) 띄우기 / context: this (부모타입) 자주나오고 중요 / CharSequence = 문자열을 넣어달라
        Toast.makeText(this, "전송 버튼을 눌렀네?", Toast.LENGTH_LONG).show();
    }

    public void deleteClicked(View v) {
        Toast.makeText(this, "삭제 버튼을 눌렀네?", Toast.LENGTH_LONG).show();
    }

    public void tDelete(View v) {
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("삭제 합니다")
                .setPositiveButton("확인", null)
                .create()
                .show();

        /*위의 코드를 여러줄로 작성하면 아래와 같다.
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("알림");
        builder.setMessage("삭제 합니다.");
        builder.setPositiveButton("확인", null);
        AlertDialog dialog=builder.create();
        dialog.show();
        */
    }
}
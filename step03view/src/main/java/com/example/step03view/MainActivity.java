package com.example.step03view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //액티비티가 활성화 될 때 호출되는 메소드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        /*
            레이아웃 리소스 아이디를 전달하면 자동으로 UI 객체들이 생성되고 (new)
            생성된 UI 가 화면을 구성하게 된다.
            이 예제에서는 ConstraintLayout, EditText, Button, TextView 객체가 생성이 되어서
            화면 구성을 하게 된다.
         */
        setContentView(R.layout.activity_main); //제일 중요한 메소드! 상속받았기 때문에 setContentview 를 호출할 수 있다.
        EditText e1=new EditText(this); //EditText 직접 생성 - 참조값은 e1에 들어있음.
    }

    //전송 버튼을 눌렀을 때 호출되는 메소드 (view = 모든 ui의 부모타입)
    public void sendClicked(View v){
        //1. EditText 에 입력한 문자열을 읽어온다. 중요. R클래스 이해하기.
        EditText a=findViewById(R.id.inputText);//id조차도 정수값으로 관리가 된다. 원래타입으로 받는다.(자동 캐스팅이 된다.)
        String msg=a.getText().toString();
        //2. Toast 에 읽어온 문자열을 띄운다. makeText = static method
        //액티비티의 메소드 안에서 Context type 이 필요하면 this 를 전달하면 된다. ***
        //context:this 인 이유 : 상속관계에 context가 있기 때문에 this 사용 가능
        //Toast.LENGTH_LONG 오래 띄울거면 1, 짧게 띄울거면 0
        Toast toast=Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

        //this 의 상속관계 살펴보기
        Object t1=this;
        MainActivity t2=this;
        AppCompatActivity t3=this;
        Context t4=this;

        //String 은 CharSequence를 implements(구현) 한 것
        String s1="abcd"; //(=CharSequence type)
        CharSequence s2="abcd"; //CharSequence 를 넣어달라고 할 땐 문자열을 넣어주면 된다.
        
        //3. textView 에 읽어온 문자열을 띄운다.
        TextView b=findViewById(R.id.textView);
        b.setText(msg);
        //4. EditText 에 입력된 문자열 삭제
        a.setText("");//빈문자열을 넣어서 send 누른 다음 입력한 문자를 삭제시킨다.
        
        /*
            안드로이드를 사용하다보면 다양한 옵션을 상수값으로 전달해야할 일이 많다.
            그럴 때 Toast.LENGTH_LONG / R.id.inputText / R.layout.activity_main
            이런 패턴을 잘 사용하도록 해야한다.
            대부분이 static final 상수로 이루어져있는 것 같다.
            정수로 관리되고 있기 때문인가?
            . 을 이용해 참조값을 사용
         */
    }
}
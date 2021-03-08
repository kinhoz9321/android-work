package com.example.step07example;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //필요한 필드 선언
    EditText editText;
    List<String> names;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//필요한 참조값을 미리 준비해둔다.
        super.onCreate(savedInstanceState);
        //화면 구성을 한다. (제일 처음 할 일 *** 중요한 작업)
        setContentView(R.layout.activity_main);
        //필요한 UI 의 참조값을 id 에 부여된 id 를 이용해서 얻어오기 (화면 구성을 해야지만 생성할 수 있다.)
        listView=findViewById(R.id.listView);
        editText=findViewById(R.id.editText);
        Button addBtn=findViewById(R.id.addBtn);
        //모델생성
        names=new ArrayList<>();
        //아답타 ?
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        //아답타를 ListView에 연결
        listView.setAdapter(adapter);
        //버튼에 리스너 등록
        addBtn.setOnClickListener(this);
    }
    //버튼을 눌렀을 때 호출되는 메소드
    @Override
    public void onClick(View v) {
        //1. EditText 에 입력한 문자열을 읽어온다. (toString)
        String inputName=editText.getText().toString();
        //2. 모델에 데이터를 추가한다.
        names.add(inputName);
        //3. ListView 가 업데이트 될 수 있도록 아답타에 모델이 수정되었다고 알린다.
        adapter.notifyDataSetChanged();
        //4. EditText 에 입력한 문자열 삭제
        editText.setText("");
        //5. 방금 추가된 데이터를 볼 수 있도록 ListView에 해당 위치로 스크롤 시킨다.
        int length=names.size();
        listView.smoothScrollToPosition(length);
    }
}
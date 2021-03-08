package com.example.step17example;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements Util.RequestListener {
    MemberDto dto;
    EditText edit_name;
    EditText edit_addr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //MainActivity 에서 전달한 인텐트 객체의 참조값
        Intent intent=getIntent();
        //dto 라는 키값으로 담긴 Serializable type 객체(MemberDto 객체)의 참조값 얻어오기
        dto=(MemberDto)intent.getSerializableExtra("dto");//intent 타입에 Serializable type을 담는 것 = getSerializableExtra
        //dto 에 담긴 회원정보를 EditText 에 출력해보세요.
        EditText edit_num=findViewById(R.id.edit_num);
        edit_name=findViewById(R.id.edit_name);
        edit_addr=findViewById(R.id.edit_addr);
        edit_num.setText(Integer.toString(dto.getNum()));
        edit_name.setText(dto.getName());
        edit_addr.setText(dto.getAddr());

        Button updateBtn=findViewById(R.id.updateBtn);
        Button deleteBtn=findViewById(R.id.deleteBtn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //수정할 회원의 번호
                int num=dto.getNum();
                //수정할 회원의 이름
                String name=edit_name.getText().toString();
                //수정할 회원의 주소
                String addr=edit_addr.getText().toString();
                //웹서버에 요청할 url
                String url="http://192.168.0.4:8888/spring04/api/member/update.do";
                //요청 파라미터를 Map 에 담는다.
                Map<String, String> params=new HashMap<>();
                params.put("num", Integer.toString(num));
                params.put("name", name);
                params.put("addr", addr);
                //Util 을 이용해서 전송 / 1 = static final 상수로 정하면 더 좋다.
                Util.sendPostRequest(1, url, params, DetailActivity.this);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //삭제할 회원의 번호
                int num=dto.getNum();
                //웹서버에 요청할 url
                String url="http://192.168.0.4:8888/spring04/api/member/delete.do";
                //요청 파라미터를 Map 에 담는다.
                Map<String, String> params=new HashMap<>();
                params.put("num", Integer.toString(num));
                //Util 을 이용해서 전송 / sendPostRequest , sendGetRequest 상관없다.
                Util.sendPostRequest(2, url, params, DetailActivity.this);
            }
        });
    }

    //Util.RequestListener (onSuccess, onFail)

    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {
        int code=(int)result.get("code");
        if(code!=200){ //응답 코드가 200 이 아니면 실패이다.
            Toast.makeText(this, "작업실패", Toast.LENGTH_SHORT).show();
            return; //메소드를 여기서 종료
        }
        String msg=null;
        switch (requestId){
            case 1: //수정 성공
                msg=dto.getNum()+" 번 회원의 정보를 수정했습니다.";
                break;
            case 2: //삭제 성공
                msg=dto.getNum()+" 번 회원의 정보를 삭제했습니다.";
                break;
        }
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(msg)
                .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                        DetailActivity finish() 메소드를 호출해서
                        DetailActivity 가 종료 되도록 한다
                        결과적으로 onStop() 까지 호출되어서 비활성화된 상태로 대기중이던
                        MainActivity 가 onReStart() onStart() onResume() 이 호출되면서
                        다시 활성화가 된다.
                        따라서 onStart() 메소드 안에서 회원목록을 서버에 다시 요청하게 되기 때문에
                        업데이트 된 회원 목록을 볼 수 있는 것이다.
                         */
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onFail(int requestId, Map<String, Object> result) {
        //에러 메세지
        String msg=(String)result.get("data");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
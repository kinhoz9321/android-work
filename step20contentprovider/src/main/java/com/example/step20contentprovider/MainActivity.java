package com.example.step20contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText console, inputName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //버튼에 리스너 등록하기
        Button getBtn=findViewById(R.id.getBtn);
        getBtn.setOnClickListener(this);
        console=findViewById(R.id.console);
        inputName=findViewById(R.id.inputName);
    }

    @Override
    public void onClick(View v) {
        //연락처 정보 얻어오기 권한이 체크 되었는지 상수값 얻어오기
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        //만일 권한이 허용되지 않았다면
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            //권한을 허용하도록 유도한다.

            //권한이 필요한 목록을 배열에 담는다.
            String[] permissions={Manifest.permission.READ_CONTACTS};
            //배열을 전달해서 해당 권한을 부여하도록 요청한다.
            ActivityCompat.requestPermissions(this, permissions, 0);//요청의 아이디

            return;//메소드는 여기서 종료
        }
        //연락처 정보 얻어오기
        getContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0: // 0번 요청인 경우
                //권한을 부여 했다면
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //연락처 정보 얻어오기
                    getContacts();
                } else {//권한을 부여 하지 않았다면
                    Toast.makeText(this, "연락처 접근 권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
    //ContentProvider 에게서 ContentResolver 객체를 이용해서 연락처 정보를 얻어내는 메소드
    //ContentProvider 어떤 정보를 갖고있는 데이터베이스다. 라고 간주하기.
    //DataBase => table => column
    public void getContacts(){
        //입력한 검색어
        String keyword=inputName.getText().toString().trim();

        //ContentResolver 객체의 참조값 얻어오기
        ContentResolver resolver=getContentResolver();
        //연락처 정보를 지칭할 수 있는 uri 객체의 참조값 얻어오기
        Uri contactUri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //필요한 칼럼
        String[] columns={
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        };
        // where display_name = 'kim hongju'
        String where=ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" = '김구라' "; //" = ?" ;
        //ID 로 정렬하기
        // order by contact_id asc
        String order=ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" ASC";

        //String[] args={"김구라"};
        String[] args={"%"+keyword+"%"};

        //원하는 정보를 얻어낸다.
        Cursor cursor=resolver.query(contactUri, //table name
                columns, //column name
                where, //where
                args, //selection args
                order); //order by
        //반복문 돌면서 Cursor 에서 데이터 얻어내기
        while (cursor.moveToNext()){
            int id=(int)cursor.getLong(0);
            String phoneNumber=cursor.getString(1);
            String name=cursor.getString(2);
            //결과를 한줄의 문자열로 구성해서
            String result=id+" | "+phoneNumber+" | "+name;
            //EditText 에 출력하기
            console.append(result+"\n");
        }
    }
}
/*
매니페스트에서 허용을 시켜놔도 앱에 들어가보면 기본적으로 '거부'가 되어있음.
그래서 개발자 입장에서는 분기해서 권한 허용을 유도해야함.
 */
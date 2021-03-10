package com.example.step20callphone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    //필드
    EditText inputPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText inputPhone=findViewById(R.id.inputPhone);
        Button dialBtn=findViewById(R.id.dialBtn);
        dialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 전화번호
                String phoneNumber=inputPhone.getText().toString().trim();
                //전화를 걸겠다는 Intent 객체 생성하기
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_DIAL);//ACTION_CALL 앱이 죽어버림. 허가되지 않은 사용을 했기 때문.
                //전화번호를 Uri 로 얻어낸다.
                Uri uri=Uri.parse("tel:"+phoneNumber);
                //Intent 객체에 담는다.
                intent.setData(uri);
                //전화를 걸 수 있는 액티비티를 실행 시킨다.
                startActivity(intent);
            }
        });
        Button callBtn=findViewById(R.id.callBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전화를 걸기전에 전화걸기 허용을 했는지 확인
                //전화걸기 권한이 체크 되었는지 상수값 얻어오기
                int permissionCheck= ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE);
                //만일 권한이 허용 되지 않았다면
                if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                    //권한을 허용하도록 유도한다.

                    //권한이 필요한 목록을 배열에 담는다.
                    String[] permissions={Manifest.permission.CALL_PHONE};
                    //배열을 전달해서 해당 권한을 부여하도록 요청한다.
                    ActivityCompat.requestPermissions(MainActivity.this,
                            permissions,
                            0); //요청의 아이디
                    return; //메소드는 여기서 종료
                }
                //전화걸기
                call();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0: // 0번 요청인 경우
                //권한을 부여 했다면
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //전화 걸기
                    call();
                }else{//권한을 부여 하지 않았다면
                    Toast.makeText(this, "전화를 거는 권한이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //전화 거는 메소드
    public void call(){
        //입력한 전화번호
        String phoneNumber=inputPhone.getText().toString().trim();
        //전화를 걸겠다는 Intent 객체 생성하기
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //전화번호를 Uri 로 얻어낸다.
        Uri uri=Uri.parse("tel:"+phoneNumber);
        //Intent 객체에 담는다.
        intent.setData(uri);
        //전화를 걸수 있는 액티비티를 실행 시킨다.
        startActivity(intent);
    }
}
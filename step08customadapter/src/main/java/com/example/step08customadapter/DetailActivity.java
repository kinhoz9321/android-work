package com.example.step08customadapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //액티비티에 전달된 인텐트 객체의 참조값 얻어오기
        Intent intent=getIntent(); //처음 나온 것 액티비티간의 어떤 데이터를 주고 받을 때 intent를 활용하면 된다.
        //인텐트에 담긴 CountryDto 의 참조값 얻어오기
        CountryDto dto=(CountryDto)intent.getSerializableExtra("dto");//Serializable type으로 간주되기 때문에 CountryDto로 캐스팅 해주어야 함.
        //activity_detail.xml 을 전개했을 때 생성되는 UI의 참조값 얻어오기
        ImageView imageView=findViewById(R.id.imageView);
        TextView textView=findViewById(R.id.textView);
        Button confirmBtn=findViewById(R.id.confirmBtn);
        //ImageView, TextView 에 필요한 정보 출력하기
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getContent());
        //버튼에 리스너를 익명 클래스를 이용해서 등록하기
        confirmBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //액티비티 종료하기
                //DetailActivity.this.finish();//finish() 메소드
                finish();//프로그램적으로 백버튼을 누른거나 마찬가지다.
            }
        });
    }
}
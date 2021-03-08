package com.example.step10viewpager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.step10viewpager.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //res/layout/activity_main.xml 문서를 전개해서 화면 구성하기
        setContentView(R.layout.activity_main);

        List<String> newsList=new ArrayList<>();
        newsList.add("비대면 수업이 연장 되었어요.");
        newsList.add("아이패드를 주문했어요.");
        newsList.add("주식이 위기에요.");


        //핵심코드 ***
        //ViewPager에 Fragment 를 공급할 아답타 ctrl SectionsPagerAdapter 클릭
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(newsList, getSupportFragmentManager());
        //ViewPager 의 참조값 얻어와서 아답타 연결하기
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);


        //상단 tab의 참조값 얻어와서
        TabLayout tabs=findViewById(R.id.tabs);
        //상단 tab 에 ViewPager 연결하기
        tabs.setupWithViewPager(viewPager);
        //우하단에 떠있는 버튼의 참조값 얻어와서
        FloatingActionButton fab = findViewById(R.id.fab);
        //클릭 리스터 등록하기
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
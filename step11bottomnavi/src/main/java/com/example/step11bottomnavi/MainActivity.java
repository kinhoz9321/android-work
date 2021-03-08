package com.example.step11bottomnavi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //하단 네비게이션뷰의 참조값 얻어오기
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //하단 메뉴바 설정객체의 참조값 얻어오기
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        //네비게이션 컨트롤러 객체의 참조값 얻어오기
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);//R.id.nav_host_fragment 사용확인
        //네비게이션 컨트롤러와 하단 메뉴바가 동작하기 위한 초기화 작업
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
/*
    하나의 액티비티에 여러개의 프레그먼트 사용
 */
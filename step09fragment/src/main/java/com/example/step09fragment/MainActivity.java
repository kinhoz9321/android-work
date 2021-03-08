package com.example.step09fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {//Activity 는 컨트롤러 역할 / UI 하고 상호반응을 하는.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
/*
Fragment (많이 사용하니 익혀야함.)
- 하나의 조각으로 기능을 만들어놓고, 여러군데에서 가져다 사용.
- 나름의 layout.xml 파일을 가지고 있을 것이고, fragment는 이 레이아웃만 관리해주면 된다.
- activity의 하청. activity는 많은 코드가 감소되고, 재활용이 쉬워진다. (수정, 관리가 용이해짐.)
- 복잡성은 더하지만 유지 보수가 쉬워진다.
- activity 관리하에서 운영되는 minicontroller 라고 이해하자. 하청업체... (activity 없이 fragment 단독으로 운영은 안됨.)
 */
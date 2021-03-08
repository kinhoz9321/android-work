package com.example.step10viewpager.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/*
    [ ViewPager 에 Fragment 를 공급할 아답타 클래스 정의하기 ]

    - FragmentPagerAdapter 추상 클래스를 상속 받아서 만든다.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    List<String> news;
    //생성자
    public SectionsPagerAdapter(List<String> news, @NonNull FragmentManager fm) {
        super(fm);
        //각각의 프레그먼트에 출력할 모델을 가지고 있는 List 를 필드에 저장
        this.news=news;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) { //viewPager가 자동으로 getItem을 호출한다. (필요한 시점에) Adapter의 부모에서 호출.
        //position 에 해당하는 프레그먼트를 생성해서 (어떤 프레그먼트를 생성할지는 상황에 따라 다름)
        NewsFragment fragment=new NewsFragment();
        //프레그먼트에 전달할 꾸러미 객체 생성
        Bundle bundle=new Bundle();
        //꾸러미 객체에 msg 라는 키값으로 position 에 해당하는 내용을 담아서
        bundle.putString("msg", news.get(position));
        //꾸러미를 프레그먼트의 인자로 전달한다
        fragment.setArguments(bundle);
        //리턴해준다.
        return fragment;
    }
    //ViewPager 가 표시할 프레그먼트 페이지의 갯수 리턴
    @Override
    public int getCount() {
        return 3; //똑같은 프레그먼트로 구성된 페이지 3개
    }

    //position 에 해당하는 페이지의 제목을 리턴한다.
    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        //제목을 미리 준비해놓고
        String[] titles={"첫번째","두번째","세번째"};
        //String title=Integer.toString(position);
        //position 에 해당하는 제목을 리턴한다.
        return titles[position];
    }
}
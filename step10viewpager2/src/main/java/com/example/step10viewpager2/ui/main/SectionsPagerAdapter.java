package com.example.step10viewpager2.ui.main;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.step10viewpager2.CountryDto;

import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    //필드
    List<CountryDto> countries;

    //생성자
    public SectionsPagerAdapter(List<CountryDto> countries, FragmentManager fm) {
        super(fm);
        this.countries=countries;
    }

    @Override
    public Fragment getItem(int position) {
        //position 에 해당하는 CountryDto
        CountryDto dto=countries.get(position);
        //새로운 CountryFragment 객체의 참조값을 받아와서
        CountryFragment cf=CountryFragment.newInstance(dto); //new 하지않고 직접 받아와서 리턴해주겠다.
        //리턴해준다.
        return cf;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //position 에 해당되는 국가의 이름을 리턴해 준다.
        return countries.get(position).getName();
    }

    @Override
    public int getCount() {
        //List 의 방 사이즈를 리턴해준다.
        return countries.size();
    }
}
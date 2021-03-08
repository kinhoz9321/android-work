package com.example.step10viewpager.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.step10viewpager.R;

public class NewsFragment extends Fragment {
    String msg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 꾸러미 객체에 msg 라는 키값으로 담긴 문자열 얻어내기
        msg=getArguments().getString("msg");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // res/layout/fragment_news.xml 문서를 전개해서 View 를 만든다.
        View view=inflater.inflate(R.layout.fragment_news, container, false);
        // View 에 뉴스 출력
        TextView textView=view.findViewById(R.id.textView);
        textView.setText(msg);
        return view;
    }
}
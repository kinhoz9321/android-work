package com.example.step10viewpager2.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.step10viewpager2.CountryDto;
import com.example.step10viewpager2.R;


public class CountryFragment extends Fragment {
    CountryDto dto;
    /*
        CountryDto dto 를 필드로 만들지 않는 이유.
        - 객체마다 고유한 값(dto)를 가지고 있어야 한다.

        - static CountryDto dto; 를 만들면 하나만 만들어진다. (여러개가 필요한데)
        - static / non-static의 차이

        같은 클래스 안에 있지만 이 메소드들은 전혀 다른 메소드이기 때문 (static / non-static의 차이)
        public static CountryFragment newInstance(CountryDto dto)
        public void onCreate(Bundle savedInstanceState)

        프레그먼트의 고유한 Dto 를 만들어서 setter 메소드를 만들어서 전달(캡쳐 참고)

        static과 heap은 완전 별개
     */

    //자신의 객체 참조값을 생성해서 리턴해주는 static 메소드 (dao 생각해보기. 비슷.)
    public static CountryFragment newInstance(CountryDto dto) {//new 할 때 마다 객체 생성 전달된 dto 를 각각 다른걸로 사용해야 함.
        //this.dto=dto; static 메소드에서는 this가 아무의미가 없다 / static메소드에서는 static 메소드만 쓸 수가 있다.
        //프레그먼트를 생성해서
        CountryFragment fragment = new CountryFragment();
        //번들 객체에 Country 를 dto 라는 키값으로 담아서 (bundle 객체는 담아서 옮겨주는 역할. 여러개 담아서 옮길 수도 있다.)
        Bundle bundle=new Bundle(); //애초부터 존재 **
        bundle.putSerializable("dto", dto); //dto는 Serializable를 구현했기 때문에 가능.
        //프레그먼트의 인자로 전달하고
        fragment.setArguments(bundle);//프레그먼트가 활성화될 때 getArguments해서 읽어낼 수 있음.
        //프레그먼트를 리턴해준다.
        return fragment;
    }

    //static이 안붙은 것들은 new 했을때 참조값과 함께 사물함에 들어감.
    //프레그먼트가 활성화 될 때 가장 첫번째로 호출되는 메소드
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //무언가 초기화 작업을 하기에 적당한 위치이다.
        //1. 인자로 전달된 번들 객체의 참조값을 얻어와서
        Bundle bundle=getArguments();//객체가 생성되어야만 존재 **
        //2. 번들에 dto 라는 키값으로 담긴 CountryDto 객체를 얻어내서 필드에 저장
        dto=(CountryDto)bundle.getSerializable("dto");
    }
    //onCreate() 메소드가 호출된 이후에 호출되는 메소드
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //프레그먼트의 레이아웃 xml 을 전개해서 view 를 만들어서
        View view=inflater.inflate(R.layout.fragment_country, container, false);
        //View 안에서 필요한 UI 의 참조값을 얻어와서
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView textView=view.findViewById(R.id.textView);
        //이미지와 텍스트를 출력한다.
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getContent());
        //이미지와 텍스트가 출력된 View를 리턴해준다.
        return view;
    }
}
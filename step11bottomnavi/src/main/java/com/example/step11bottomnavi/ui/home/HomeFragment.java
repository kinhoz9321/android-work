package com.example.step11bottomnavi.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.step11bottomnavi.R;

//HomeFragment 에서 동작 정의
public class HomeFragment extends Fragment implements View.OnClickListener{

    TextView textView;
    EditText editText;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        //레이아웃 xml 을 전개해서 View 만들기
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //TextView 의 참조값 얻어오기
        textView = root.findViewById(R.id.textView);
        //EditText의 참조값
        editText=root.findViewById(R.id.editText);
        //Button 의 참조값
        Button saveBtn=root.findViewById(R.id.saveBtn);
        //버튼에 리스너 등록하기
        saveBtn.setOnClickListener(this);
    /*
        1. 뷰 모델 객체로 부터 LiveData 객체를 얻어와서
        2. 해당 객체의 observe() 메소드를 이용해서 Observer(관찰자) 객체를 등록한다.
        - 모델의 변화가 생기면 자동으로 onChanged() 메소드가 호출되고 변화된 새로운 데이터가 onChanged() 메소드의 인자로 전달된다.
     */
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //만든 뷰의 참조값 리턴하기
        return root;
    }

    @Override
    public void onClick(View v) {
        //입력한 내용을 읽어와서
        String msg=editText.getText().toString();
        //TextView에 출력
        //textView.setText(msg);
        homeViewModel.setText(msg);
    }
}
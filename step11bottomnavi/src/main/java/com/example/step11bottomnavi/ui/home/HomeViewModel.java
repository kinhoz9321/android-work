package com.example.step11bottomnavi.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/*
    Activity or Fragment 에서 사용하는 데이터를 따로 분리해서 관리 하기 위한 ViewModel

    1. ViewModel 클래스를 상속 받아서 만든다.
    2. 관리하고자 하는 데이터를 MutableLiveData<T> 의 generic 클래스로 지정을 해서 필드를 하나 정의한다.
    3. 해당 데이터를 Activitiy or Fragment 에서 사용할 때는 메소드를 통해서 LiveData 로 받아가서 사용한다.
 */
public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");//값을 넣어주거나 수정할 때
        //mText.getValue();
    }

    //LiveData 를 리턴해주는 메소드
    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String msg){
        //LiveData 의 setValue() 호출되면 모델이 변화 된다고 감지 된다.
        mText.setValue(msg);
    }
}
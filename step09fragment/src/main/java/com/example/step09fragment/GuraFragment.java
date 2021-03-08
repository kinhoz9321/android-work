package com.example.step09fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/*
    [ Fragment 만드는 방법 ]
    1. Fragment 클래스를 상속 받는다.
    2. 프레그먼트의 layout.xml 문서를 만든다.
    3. onCreateView() 메소드를 오버라이딩한다.

    - View는 아니지만 자체적으로 View를 가지고 있음.
 */
public class GuraFragment extends Fragment implements View.OnClickListener {


    //필요한 필드 정의하기
    TextView textView;
    //클릭 카운트를 셀 필드를 정의하기 0을 굳이 안넣어줘도 선언만해도 0이 들어감. ex)int count;
    int count=0;

    //UI에 사용될 준비작업
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //인자로 전달되는 레이아웃 전개자 객체를 이용해서 View를 만들어서
        View view=inflater.inflate(R.layout.fragment_gura, container); //1. R.layout.fragment_gura 자기만의 레이아웃을 갖고 있고
        //만든 View에서 TextView의 참조값을 얻어온다. view.findViewById() 없으면 null
        textView=view.findViewById(R.id.textView);
        textView.setOnClickListener(this); //2. 자기가 일을 처리하고 있다. (프레그먼트도 리스너 역할을 할 수 있다.)
        //리턴해준다.
        return view;
    }
    // 미니 컨트롤러 같은?

    //TextView를 클릭하면 호출되는 메소드
    @Override
    public void onClick(View v) {//이벤트가 일어난 바로 그 요소의 참조값이 View v로 들어옴.
        //v 에는 이벤트가 발생한 TextView의 참조값이 전달된다.
        //boolean result = textView == v; //디버깅 -> 디버거 16250으로 숫자(참조값(원래는 참조값이 간단하지는 않지만 구분할 수 있는 숫자 / 이 숫자가 같으면 참조값이 같다고 보면 된다.))이 같다는 걸 알 수 있음.
        /*
            v와 textView가 같은데 v.setText()가 불가능한 이유

            View v=new TextView(); //v는 부모타입. 사용설명서가 부족하다.
            Textview t=(TextView)v; //사용하려면 캐스팅해서 사용해야함. / View type을 TextView로 casting 해서
            t.setText("click 했네"); // setText() 메소드 호출하기
         */

        //필드의 값을 1 증가 시키기
        count++;
        //setText() 메소드 호출하기
        textView.setText(count+""); //이렇게 해도 된다. 1+"test"="1test" 연결연산자를 활용해 새로운 문자열을 만들어준 것.
        //계속해서 배웠던 것들이 조금씩 다른 모양으로 반복되서 나오고 있음. 복습의 중요성!
        //textView.setText(Integer.toString(count)); //정수를 문자열로 바꿔주기
        //""를 안써주면 화면이 꺼진다.

        /*
            setText에 int 를 전달하면 resid를 넣어야 한다.
            1이라는 리소스를 찾는데 없어서 앱이 죽어버린다.
            Resources NotFoundException : String resource ID #0x1 = 1이 없다.
         */

        /*
        이렇게 코딩하게 되면 누를때마다 count가 0으로 초기화된다.
        실행하면 한번만 동작함.
        int count=0;
        count++;
        TextView t=(TextView)v;
        t.setText(count+" 번 click 했네?");
         */
        //문자열을 리소스로 등록해놓고 가져다 쓸 수 있다 (예시) res>values>strings.xml
        //R.string.msg
        //textView.setText(R.string.msg); //setText(int resid);
        /*
        이렇게 하는 이유
        values-en이라는 폴더를 만들어서
        한국어와 영어를 둘다 사용가능하게 만듦.
        다국어 지원.
        언어를 영어로 맞춰놓으면 영어폴더를 띄우고
        디폴트로 맞춰놓으면 한글폴더로 띄움.
        폴더이름이 values 이기만 하면 되는건가?
         */

        //만일 count가 10의 배수라면 액티비티에 해당 정보를 알려보자 (여기서 액티비티는 현재 프래그먼트를 관리하는 액티비티)

        //프래그먼트를 관리하는 액티비티의 참조값을 부모 type으로 얻어오기 (메인액티비티의 참조값)
        FragmentActivity fa=getActivity();//프래그먼트에서 나를 관리하고 있는 액티비티의 참조값을 가져올 때 getActivity();
        //프래그먼트안에서 컨텍스트가 필요하면 fa를 넣어주면 된다. getActivity가 context니까

        //메인액티비티2타입으로 캐스팅 / 이런 코딩은 메인액티비티2에서만 쓸 수 있다. 다른 곳에서 구라프래그먼트를 쓸 수 없음.
        //액티비티가 GuraFragmentListener 가 맞는지 확인 ***
        if(fa instanceof GuraFragmentListener && count%10 == 0){//count를 10으로 나눈 나머지가 0과 같다면 = 10의 배수
            GuraFragmentListener ma2=(GuraFragmentListener)fa; //메인액티비티의 참조값을 메인액티비티2로 캐스팅할 수 없다?
            ma2.wow(count+" 입니다. 액티비티님!");  //나를 관리하는 메소드에 특정 메소드를 호출할 수 있다.
        }
        //프래그먼트는 재활용을 위해 쓰는건데 이렇게 메인액티비티2에 의지하면 의미가 없다?
        /*
        인터페이스... 프레그먼트를 여러 메소드에서 사용할 것이라면 인터페이스를 활용을 하라.
        의존관계를 느슨하게 하기 위해 스프링에서 사용하던 방법
        관리되는 입장에서는 원래타입으로 쓰면 ex) 메인액티비티2 에서만 쓸 수 있다. 그러니 인터페이스를 활용한다.
         */
    }
    //리셋하는 메소드 (액티비티가 필요할 때 이 메소드를 호출할 것)
    public void reset(){
        count=0;
        textView.setText("0");
    }

    //이 프래그먼트를 사용하는 액티비티가 구현해야 하는 메소드
    public interface GuraFragmentListener{
        public void wow(String msg);
    }
}
/*
    *Fragment 에서 반드시 오버라이드 해야하는 메소드*
    onCreateView

    * Fragment 에서 가장 중요한 메소드 *
    getActivity
    - 부모로부터 물려받은 메소드

 */
package com.example.step08customadapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
/*디바이스에서 intent를 던져준다. MainActivity로 들어오는 이유 Manifest.xml intent-filter 때문*/
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    List<CountryDto> countries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView=findViewById(R.id.listView);
        //아답타에 연결할 모델 객체 생성
        countries=new ArrayList<>();
        //샘플 데이터
        countries.add(new CountryDto(R.drawable.austria,
                "오스트리아", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.belgium,
                "벨기에", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.brazil,
                "브라질", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.france,
                "프랑스", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.germany,
                "독일", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.greece,
                "그리스", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.israel,
                "이스라엘", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.italy,
                "이탈리아", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.japan,
                "일본", "그지 같은 나라~"));
        countries.add(new CountryDto(R.drawable.korea,
                "대한민국", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.poland,
                "폴란드", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.spain,
                "스페인", "어쩌구.. 저쩌구.."));
        countries.add(new CountryDto(R.drawable.usa,
                "미국", "어쩌구.. 저쩌구.."));

        //ListView 에 연결할 아답타 객체
        CountryAdapter adapter=new CountryAdapter(this, R.layout.listview_cell, countries);
        //아답타를 ListView 에 연결하기
        listView.setAdapter(adapter);
        //셀을 클릭했을 때 리스너 등록
        listView.setOnItemClickListener(this);
    }
    /*
        position - cell index 전달
        id - index 전달
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //DetailActivity 로 이동 (listView 아무거나 클릭해도 Detail view로 넘어감) * 어떤 객체를 활성화 시키기 위해 반드시 필요한 객체 intent *
        Intent intent=new Intent(this, DetailActivity.class); //나의 앱의 어플리케이션은 명확하게 요청. 하지만 다른 앱의 어플리케이션은 명확하게 요청할 수 없다.
        //클릭한 아이템에 해당되는 CountryDto 얻어오기
        CountryDto dto=countries.get(position); //CountryDto 를 Serializable 로 바꿔줄 수도 있다.
        //이동할 액티비티에 전달할 데이터를 Intent 객체에 담을 수 있다. (CountryDto 를 Serializable implements를 해야 가능)
        intent.putExtra("dto", dto); //dto (* Serializable type *) list는 Serializable type 을 상속받지 못했기 때문에 담지 못한다.
        //ArrayList 는 가능. 자바에서 자주 쓰던 객체들은 대부분 Serializable type 을 상속 받음.
        //list 를 (인터페이스 타입이 아닌) ArrayList (원래 클래스) 로 바꾸면 전달 가능. Hashmap도 가능.
        startActivity(intent); //intent는 결국 액티비티를 실행시키는데 필요하다.
    }
}
/*
우리가 설계한 클래스를 리스트뷰가 알아서 쓰고 있다.
onCreate 호출되면
listView에 연결할 아답타 객체가 생성되고 연결됨.
intent에 담을 수 있는 데이터 타입이 정해져 있다. putExtra

* 안드로이드 4대 component (구성 요소) *
intent 는 밑의 3가지를 활성화 시킨다.
1. Activity - 레이아웃을 전개를 해서 사용자를 대면 (사용자와 상호 반응) / UI
2. Service - UI가 없는 component / 화면상에 보이지는 않지만 실행되고 있다.
ex)윈도우의 oracle / mp3플레이어 화면이 꺼져도 백그라운드 로 돌림.
3. BroadcastReceiver - 방송을 수신할 수 있는 수신자 객체

ContentResolver는
1. ContentProvider를 활성화 시킨다.

* BroadcastReceiver *
안드로이드는 플랫폼(platform) - 운영체제 / 디바이스 상에서 돌아감.
ex)android / window / linux / mac os ...
운영체제가 앱에게 방송 (broadcast)
wifi on ~
battery low ~
lte on ~
airplane mode on ~

앱이 BroadcastReceiver 를 통해 필요한 것만 방송을 듣는다.

intent에 사진찍고 싶어라고 하면 사진 앱을 찾아줌.
상호작용하는건가?
 */
package com.example.step07listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
        DialogInterface.OnClickListener {
    //여러 메소드에서 사용하도록 필드로 만들어주기 (여러 메소드에서 공유하는 자원)
    List<String> names;
    int position;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ListView 의 참조값 얻어오기
        ListView listView=findViewById(R.id.listView);
        //ListView 에 출력할 모델
        names=new ArrayList<>();
        names.add("김구라");
        names.add("해골");
        names.add("원숭이");
        for(int i=0; i<100; i++){
            names.add("주뎅이"+i);
        }
        //ListView 에 연결할 아답타 객체 / 아답타는 우리가 커스텀으로 만들수도 있고, 간단하게 만들어져 있는 것도 있다.
        //new ArrayAdapter<>(Context type, layout resource, 모델) 모델은  adapter에 adapter는 listview에 연결
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);//simple_list_item_1 문자열 한 줄 출력할 수 있는 text view
        /*
        R class가
        com.example.step07listview.R / 우리가 만들어낸 자원을 찾는 법 (같은 패키지 안에 있는 것은 패키지를 밝히지 않아도 되니까 이렇게 사용)
        android.R / 미리 준비된 자원을 찾는 법
        이렇게 2개 등록되어있음.
         */
        //ListView에 아답타 연결하기
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    //ListView 의 특정 cell 을 클릭하면 호출되는 메소드드
   @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //View 는 cell view 의 참조값, position 은 클릭한 아이템 인덱스
       //클릭한 셀에 사용된 데이터 얻어내기
       String name=names.get(position);
       Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    //ListView 의 셀을 오랫동안 클릭하면 호출되는 메소드
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //지역 변수 position 에 전달된 값을 필드 position 에 대입하기
        this.position=position;
        String name=names.get(position);

        //알림창 띄우기
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(name+" 을 삭제 하시겠습니까?")
                .setNegativeButton("아니요", null)
                /*
                .setPositiveButton("네", new DialogInterface.OnClickListener() {// 알림창에 "네" 버튼을 누르면 호출되는 메소드
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        names.remove(position);
                    }
                 */
                .setPositiveButton("네", this)
                .create()
                .show();

        return false;
    }

    // 알림창에 "네" 버튼을 누르면 호출되는 메소드
    @Override
    public void onClick(DialogInterface dialog, int which) {
        //필드에 저장된 값을 이용해서 모델에서 해당 인덱스를 삭제
        names.remove(position);
        //모델의 내용이 수정되었다고 아답타에 알리기
        //결과적으로 아답타가 ListView에 수정된 cell view를 다시 공급해서 ListView가 업데이트 된다.
        adapter.notifyDataSetChanged();
    }
    /*
        listView 는 많이 쓰이기도 하지만 안드로이드를 연습하기에 좋은 예제도 된다.
        클래스안에 인터페이스가 정의된 경우가 많다.
        인터페이스는 같지만 다른 클래스에 정의됐을 때도 있다. 그래서 맞는 걸 찾아서 적용해야한다.
        코드를 짜는데 오류를 냈다.
        처음에 출력된 빨간 메세지 확인하기.
     */
}
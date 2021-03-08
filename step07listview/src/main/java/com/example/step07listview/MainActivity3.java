package com.example.step07listview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

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
        //ListView 에 연결할 어댑터 설정
        //new ArrayAdapter<>(Context, Layout resource, 모델)
        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                names);
        //ListView 에 어댑터 연결하기
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }

    //ListView 의 특징 cell 을 클릭하면 호출되는 메소드
    @Override
    public void onItemClick(AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
        //View 는 cell view 의 참조값, position 은 클릭한 아이템 인덱스
        //클릭한 셀에 사용된 데이터 얻어내기
        String name=names.get(position);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent,
                                   View view,
                                   int position,
                                   long id) {
        //지역변수 position 에 전달된 값을 필드 position 에 대입하기
        this.position=position;
        String name=names.get(position);
        //알림창 띄우기
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage(name+"을 삭제하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        names.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        names.set(position, "삭제 되지 않았어요");
                        adapter.notifyDataSetChanged();
                    }
                })
                .create()
                .show();
        return false;
    }
}
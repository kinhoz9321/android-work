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

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,
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
                .setNegativeButton("아니요", this)
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

    // 알림창에 "네", "아니오" 버튼을 누르면 호출되는 메소드
    /*
         이 경우에는 아무거나 써도 상관이 없으나 if문 안에 if문이 또 있을 경우 모양이 별로 안좋으니
         switch 문을 사용하기.
     */
    @Override
    public void onClick(DialogInterface dialog, int which) {
        //Positive 버튼을 눌렀는지 Negative 버튼을 눌렀는지 알아내서 분기하기
        /*
        if(which == DialogInterface.BUTTON_POSITIVE){

        }else if(which == DialogInterface.BUTTON_NEGATIVE){

        }

        위와 같은 내용을 담고 있는 코딩이지만 가독성이 떨어진다.
        if(which == -1){ static final 상수가 상당히 많이 쓰인다. 정수타입.

        }else if(which == -2){

        }

        switch (which){//switch문은 정수에 한해 사용할 수 있다.
            case DialogInterface.BUTTON_POSITIVE:
                if(){

                }else{

                }
                break;
            case DialogInterface.BUTTON_NEGATIVE:

                break;
        }
        */
        //switch에서 판별할 변수는 정수 자료형만 사용할 수 있고, 실수 자료형(float, double)은 사용할 수 없습니다.
        //단, 문자 자료형(char)도 정수 자료형이므로 switch에서 사용할 수 있습니다.
        switch (which){//switch문은 정수에 한해 사용할 수 있다.
            case DialogInterface.BUTTON_POSITIVE:
                names.remove(position);
                adapter.notifyDataSetChanged();// 중복된 코드는 밖으로 빼도 된다.
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                names.set(position, "오잉 안지웠네?");// 내용을 수정하는 메소드
                adapter.notifyDataSetChanged();
                break;
        }
        //중복된 코드를 빼낼 위치
    }
}
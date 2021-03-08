package com.example.step15sqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    //필요한 필드 정의하기
    DBHelper helper;
    EditText inputText;
    TodoAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //new DBHelper(Context type, 생성될 DB 파일의 이름, 팩토리(사용할 일 없어서 null), 버전)
        //어떤 로직(없음)에 의해서 version 값이 증가되면 DBHelper 객체의 OnUpgrade() 메소드가 호출된다.(업그레이드는 안할 것)
        helper=new DBHelper(this, "MyDB.sqlite", null, 1);
        //UI 의 참조값을 얻어와서 기본 동작을 준비한다.
        inputText=findViewById(R.id.inputText);
        Button addBtn=findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        //todo 테이블에 있는 content를 select 해서 listview를 출력해보세요.

        //할일 목록 얻어오기
        List<TodoDto> list=new TodoDao(helper).getList();
        //ListView 에 연결할 아답타
        adapter=new TodoAdapter(this, R.layout.listview_cell, list);
        //ListView 에 아답타 연결하기
        listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //아이템 롱 클릭 리스너 등록
        listView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //1. 입력한 문자열을 읽어와서
        String msg=inputText.getText().toString();
        //2. TodoDto 객체에 담아서
        TodoDto dto=new TodoDto();
        dto.setContent(msg);
        //3. TodoDto 객체를 이용해서 저장한다.
        new TodoDao(helper).insert(dto);
        //4. 업데이트 된 할일 목록을 얻어와서
        List<TodoDto> list=new TodoDao(helper).getList();
        //5. 아답타에 넣어주고
        adapter.setList(list);
        //5. 모델의 내용이 바뀌었다고 아답타에 알려서 ListView를 업데이트 한다.
        adapter.notifyDataSetChanged();
        //7. 최근 추가된 내용이 보일 수 있도록
        listView.smoothScrollToPosition(adapter.getCount());

        Toast.makeText(this, "저장했습니다.", Toast.LENGTH_SHORT).show();
        inputText.setText("");
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //position 은 long 클릭한 인덱스이고, id는 해당 아이템의 primary key 이다.
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("삭제 하시겠습니까?")
                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TodoDao dao=new TodoDao(helper);
                        //long 을 int 로 casting
                        int num=(int)id;
                        //DB 에서 삭제
                        dao.delete(num);
                        //새로 목록을 얻어와서
                        List<TodoDto> list=dao.getList();
                        //아답타에 전달하고
                        adapter.setList(list);
                        //UI 업데이트 하기
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("아니요", null)
                .create()
                .show();
        return false;
    }
}
/*
안드로이드 가상기기에서 리스트 추가한다음
MyDB.sqlite 다운받기. 크롬 sqlite 매니저 에서 내가 입력한 내용 확인할 수 있다.
File - MyDB.sqlite 파일 열기
add - select 문으로 DB 불러오기 (select * from todo)
데이터 할일 목록으로 불러오기 만들어보기
*/
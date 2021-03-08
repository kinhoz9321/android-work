package com.example.step15sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TodoAdapter extends BaseAdapter {
    //필드
    Context context;
    int layoutRes;
    List<TodoDto> list;

    //생성자
    public TodoAdapter(Context context, int layoutRes, List<TodoDto> list){
        this.context=context;
        this.layoutRes=layoutRes;
        this.list=list;
    }

    //새로운 List 를 넣어주는 메소드
   public void setList(List<TodoDto> list) {
        this.list = list;//인자로 전달되는 list를 필드에 덮어쓰기 한다.
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        //번호가 primary key 이기 때문에 primary key 값을 리턴해주면 된다.
        return list.get(position).getNum();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){//만약 null이면
            convertView= LayoutInflater.from(context).inflate(layoutRes, parent, false);//직접 만든다.
        }
        //position 에 해당하는 TodoDto 객체의 참조값을 얻어와서
        TodoDto dto=list.get(position);
        //TextView 2개에 해당 정보를 출력해서 (null 이 아니면)
        TextView text_content=convertView.findViewById(R.id.text_content);
        TextView text_regdate=convertView.findViewById(R.id.text_regdate);
        text_content.setText(dto.getContent());
        text_regdate.setText(dto.getRegdate());
        //View를 리턴해준다.
        return convertView;
    }
}

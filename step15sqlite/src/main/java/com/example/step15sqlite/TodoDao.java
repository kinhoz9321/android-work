package com.example.step15sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TodoDao {
    //필드
    private DBHelper helper;

    //생성자
    public TodoDao(DBHelper helper){
        this.helper=helper;
    }

    public void insert(TodoDto dto){
        SQLiteDatabase db=helper.getWritableDatabase();
        // ? 에 바인딩할 인자를 Object[] 객체에 순서대로 넣어둔다. 바인딩할때 오브젝트의 갯수만큼 전달을 하면된다. 지금은 하나니까 한개.
        Object[] args={dto.getContent()};
        // datatime('now', 'localtime')은 오라클의 SYSDATE 와 비슷하다.
        String sql="INSERT INTO todo (content, regdate)" +
                " VALUES(?, datetime('now','localtime'))";//datetime() 현재시간을 문자열로 얻어냄.
        //SQL 문 실행하기
        db.execSQL(sql, args);
        db.close(); //close() 를 호출해야 실제 반영된다.
    }
    public void update(TodoDto dto){
        SQLiteDatabase db=helper.getWritableDatabase();
        // ? 에 바인딩할 인자를 Object[] 객체에 순서대로 넣어둔다. 바인딩할때 오브젝트의 갯수만큼 전달을 하면된다. 지금은 하나니까 한개.
        Object[] args={dto.getContent(), dto.getNum()};
        // datatime('now', 'localtime')은 오라클의 SYSDATE 와 비슷하다.
        String sql="UPDATE todo" +
                " SET content=?" +
                " WHERE num=?";//datetime() 현재시간을 문자열로 얻어냄.
        //SQL 문 실행하기
        db.execSQL(sql, args);
        db.close(); //close() 를 호출해야 실제 반영된다.
    }
    public void delete(int num){
        SQLiteDatabase db=helper.getWritableDatabase();
        // ? 에 바인딩할 인자를 Object[] 객체에 순서대로 넣어둔다. 바인딩할때 오브젝트의 갯수만큼 전달을 하면된다. 지금은 하나니까 한개.
        Object[] args={num};
        // datatime('now', 'localtime')은 오라클의 SYSDATE 와 비슷하다.
        String sql="DELETE FROM todo" +
                " WHERE num=?";//datetime() 현재시간을 문자열로 얻어냄.
        //SQL 문 실행하기
        db.execSQL(sql, args);
        db.close(); //close() 를 호출해야 실제 반영된다.
    }
    public TodoDto getData(int num){
        //select할때는 getReadableDatabase() 메소드를 호출한다.
        SQLiteDatabase db=helper.getReadableDatabase();
        //selection 인자는 string[] 에 담아서 전달한다.
        String[] args={Integer.toString(num)};
        String sql="SELECT content, regdate" + //0번째 인덱스 ,1번째 인덱스
                " FROM todo" +
                " WHERE num=?";
        //select 문의 수행결과는 Cursor 객체로 리턴된다. Cursor = resultset
        Cursor result=db.rawQuery(sql, args);
        //할일하나의 정보를 담을 TodoDto 객체 생성
        TodoDto dto=new TodoDto();
        dto.setNum(num);
        //select된 row가 있다면 해당 row로 커서를 한칸 내려서
        if(result.moveToNext()){//rs.next()
            //커서가 위치한 곳의 row에서 0 번째 칼럼에 있는 문자열 얻어오기
            String content=result.getString(0);
            //커서가 위치한 곳의 row에서 1 번째 칼럼에 있는 문자열 얻어오기
            String regdate=result.getString(1);
            //읽어온 결과를 TodoDto 객체에 담고
            dto.setContent(content);
            dto.setRegdate(regdate);
        }
        //TodoDto 객체의 참조값을 리턴해준다.
        return dto;
    }
    public List<TodoDto>getList(){
        //할일 목록을 담을 List
        List<TodoDto> list=new ArrayList<>();
        SQLiteDatabase db=helper.getReadableDatabase();
        /*
            SQLite DB 에서 날짜 format 만들기

            - strftime() 함수를 활용한다.
            year : %Y
            month : %m
            date : %d
            week of day 0 1 2 3 4 5 6 : %w
            hour : %H
            minute : %M
         */
        String sql="SELECT num, content," +
                " strftime(\"%Y년 %m월 %d일 %H시 %M분\", regdate)" +
                " FROM todo" +
                " ORDER BY num ASC";
        /*
        String sql="SELECT num,content," +
                "strftime('%Y년 %m월 %d일 ', regdate) " +
                "|| substr('일월화수목금토', strftime('%w', regdate)+1, 1) " + //요일까지 표시
                "|| strftime(' %H시 %M분 ', regdate) " +
                " FROM todo" +
                " ORDER BY num ASC";
         */
           /*
           SQLite DB 에서 날짜 format 만들기

           - strftime() 함수를 활용한다.
           year : %Y
           month : %m
           date : %d
           week of day 0 1 2 3 4 5 6 : %w
           hour : %H
           minute : %M

           substr('일월화수목금토', strftime('%w', regdate)+1, 1)
           substr('일요일월요일화요일수요일목요일금요일토요일', strftime('%w', regdate)*3+1, 3)

        String sql="SELECT num,content," +
                "strftime('%Y년 %m월 %d일 ', regdate) " +
                "|| substr('일요일월요일화요일수요일목요일금요일토요일', strftime('%w', regdate)*3+1, 3) " +
                "|| strftime(' %H시 %M분 ', regdate) " +
                " FROM todo" +
                " ORDER BY num ASC";
        */
        Cursor result=db.rawQuery(sql, null);

        while (result.moveToNext()){//반복문 돌면서 todoDto 객체 생성
            int num=result.getInt(0);
            String content=result.getString(1);
            String regdate=result.getString(2);
            TodoDto dto=new TodoDto(num, content, regdate);
            list.add(dto);
        }
        return list;
    }

}

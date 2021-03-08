package com.example.step15sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
    DB 생성 및 리셋을 도와주는 도우미 클래스 만들기
    - SQLiteOpenHelper 추상 클래스를 상속 받아서 만든다.
    - 생성자, 추상메소드 오버라이드
    - DB를 활용한 예제
 */
public class DBHelper extends SQLiteOpenHelper {
    //생성자 (호출)
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //추상메소드
    //App 에서 DB 를 처음 사용할 때 호출되는 메소드
    @Override
    public void onCreate(SQLiteDatabase db) {//안드로이드 운영체제에 대한 기본 지식이 없어도 된다. 메소드를 이용해서 사용.
        //사용할 테이블을 만들면 된다. (INTEGER = NUMBER , TEXT = VARCHAR2) / sqlite 에는 시퀀스가 없다. 있긴하지만 우리가 만들지 않고, 자동으로 만들어진다. AUTOINCREMENT 사용.
        //1.실행할 sql 문을 준비하고
        String sql="CREATE TABLE todo (num INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, regdate TEXT)";//날짜도 text 사용
        //2. SQLiteDataBase 객체를 이용해서 실행한다.
        db.execSQL(sql);
    }

    //DB를 리셋(업그레이드)할 때 호출되는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //업그레이드할 내용을 작성하면 된다.
        db.execSQL("DROP TABLE IF EXISTS todo"); //만일 테이블이 존재하면 삭제한다.
        //다시 만들어 질 수 있도록 onCreate() 메소드를 호출한다.
        onCreate(db);
    }
}

package com.example.step12gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameView_teacher extends View {
    //필요한 필드 정의하기
    Bitmap backImg;
    int width, height; //view 의 폭과 높이
    int back1Y;

    public GameView_teacher(Context context) {
        super(context);
    }

    public GameView_teacher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //초기화 메소드
    public void init(){
        //필요한 이미지 로딩해서 준비해 놓기
        Bitmap backImg=
                BitmapFactory.decodeResource(getResources(), R.drawable.backbg);
        //배경이미지를 view 의 크기에 맞게 조절해서 필드에 저장
        this.backImg=Bitmap
                .createScaledBitmap(backImg, width, height, false);
        //핸들러에 빈 메세지를 20/1000 초 이후에 보낸다.
        handler.sendEmptyMessageDelayed(0, 20);
    }
    //View 가 활성화 될때 최초 한번 호출되고 View 의 사이즈가 바뀌면 다시 호출된다.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //view 가 차지하고 있는 폭과 높이가 px 단뒤로 w, h 에 전달된다.
        width=w;
        height=h;
        //초기화 메소드 호출
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        back1Y += 3; //속도 조절 / back1Y++; 기본
        //배경 이미지 그리기
        canvas.drawBitmap(backImg, 0, back1Y, null);
    }
    //스레드를 대신할 핸들러 객체 생성하고 handleMessage 메소드 오버라이드
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //GameView 를 무효화 하고 다시 그려지게 한다.
            //결과적으로 onDraw() 메소드가 다시 호출됩니다.
            invalidate();
            //자신(Handler)에게 다시 20/1000 초 이후에 메세지를 보낸다.
            handler.sendEmptyMessageDelayed(0, 20); //핸들러 때문에 화면이 움직이게 된다.
        }
    };
}

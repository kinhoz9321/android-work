package com.example.step12customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
    //색상을 나타내는 상수값을 미리 int[] 에 준비하고
    int[] colors={Color.YELLOW, Color.LTGRAY, Color.BLACK};
    //인덱스로 사용할 필드
    int index;

    //생성자 1
    public MyView(Context context) {

        super(context);
    }

    //생성자2 (Layout.xml 문서에서 사용하면 아래의 생성자가 호출된다.)
    public MyView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    //View 가 차지하고 있는 화면에 그림 그리기
    @Override
    protected void onDraw(Canvas canvas){
        //Canvas 객체를 이용해서 원하는 작업을 한다.
        canvas.drawColor(colors[index]);

        /* 이미지를 원하는 위치에다 그리기 */
        //BitmapFactory 클래스의 static 메소드를 이용해서 이미지 로딩해서 Bitmap type 으로 만들기
        Bitmap image= BitmapFactory.decodeResource(getResources(), R.drawable.korea);
        //Bitmap 의 크기를 변환하기 (이미지 사이즈 조절)
        Bitmap scaledImage=Bitmap.createScaledBitmap(image, 10, 10, false);
        //Canvas 객체를 이용해서 이미지의 좌상단의 좌표를 지정하고 그린다.
        canvas.drawBitmap(image, 100,100, null);

        /* 글자를 원하는 위치에다 출력하기 */
        //글자를 출력하기 위한 paint 객체
        Paint textP=new Paint();
        textP.setColor(Color.BLUE);
        textP.setTextSize(100);
        //Canvas 객체를 이용해서 글자의 좌하단의 좌표를 지정하고 글니다.
        canvas.drawText("안녕", 100, 10, textP);

    }

    //View에 터치이벤트가 발생했을때 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event){
        //인덱스를 1 증가 시키고
        index++;
        //만일 없는 인덱스라면
        if(index==3){//3이되면 다시 0으로 바꾸기 때문에 0,1,2 이렇게 변함.
            index=0;//0으로 초기화 하기
        }
        //화면 갱신하기 (결과적으로 View 가 무효화되고 onDraw()가 다시 호출)
        invalidate();
        return super.onTouchEvent(event);
    }
}
/*
내일 게임 만들기
게임은 대부분
List / Dto / for / if
이렇게 구성되있다.
 */

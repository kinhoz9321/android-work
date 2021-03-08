package com.example.step12gameview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView_hongju extends View {
    //필요한 필드 정의하기
    Bitmap backImg;
    int width, height; //View의 폭과 높이
    int back1Y, back2Y;
    //드래곤의 이미지를 저장할 배열 (이미지의 숫자만큼 [] 의 숫자 설정)
    Bitmap[] dragonImgs=new Bitmap[4];
    //드래곤 이미지 인덱스
    int dragonIndex=0;
    //유닛(드래곤, 적기) 의 크기를 저장할 필드 (크기는 폭의 1/5)
    int unitSize;
    //드래곤의 좌표를 저장할 필드 (가운데 기준)
    int dragonX, dragonY;
    //onDraw() 메소드 호출 횟수를 저장할 필드
    int count=0;
    //Missile 객체를 저장할 List
    List<Missile> missList=new ArrayList<>();
    //미사일의 크기
    int missSize;
    //미사일의 이미지를 저장할 배열
    Bitmap[] missImgs=new Bitmap[3];
    //적기의 이미지를 저장할 배열
    Bitmap[] enemyImgs=new Bitmap[2];
    //Enemy 객체를 저장할 List
    List<Enemy> enemyList=new ArrayList<>();
    //적기의 x좌표를 저장할 배열
    int[] enemyX=new int[5];
    //랜덤한 숫자를 얻어낼 Random 객체
    Random ran=new Random();
    //드래곤이 죽었는지 여부
    boolean isDragonDie=false;
    //SoundManager 객체를 저장할 필드
    SoundManager sManager;
    //적기의 속도를 저장할 필드
    int speedEnemy;
    //미사일의 속도를 저장할 필드
    int speedMissile;

    public GameView_hongju(Context context) {
        super(context);
    }

    public GameView_hongju(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //SoundManager 객체를 필드에 저장하는 setter 메소드 만들기
    public void setsManager(SoundManager sManager) {
        this.sManager = sManager;
    }

    //초기화 메소드
    public void init() {
        //필요한 이미지 로딩해서 준비해놓기
        backImg = BitmapFactory.decodeResource(getResources(), R.drawable.backbg);
        //배경 이미지를 view의 크기에 맞게 조절해서 필드에 저장
        this.backImg = Bitmap.createScaledBitmap(backImg, width, height, false);
        //드래곤 이미지를 로딩해서 사이즈를 조절하고 배열에 저장한다.
        Bitmap dragonImg1=BitmapFactory.decodeResource(getResources(), R.drawable.unit1);
        Bitmap dragonImg2=BitmapFactory.decodeResource(getResources(), R.drawable.unit2);
        Bitmap dragonImg3=BitmapFactory.decodeResource(getResources(), R.drawable.unit3);
        dragonImg1=Bitmap.createScaledBitmap(dragonImg1, unitSize, unitSize, false);
        dragonImg2=Bitmap.createScaledBitmap(dragonImg2, unitSize, unitSize, false);
        dragonImg3=Bitmap.createScaledBitmap(dragonImg3, unitSize, unitSize, false);
        dragonImgs[0]=dragonImg1;
        dragonImgs[1]=dragonImg2;
        dragonImgs[2]=dragonImg3;
        dragonImgs[3]=dragonImg2;

        //추가 미사일의 이미지를 로딩
        Bitmap missImg1=BitmapFactory.decodeResource(getResources(), R.drawable.mi1);
        Bitmap missImg2=BitmapFactory.decodeResource(getResources(), R.drawable.mi2);
        Bitmap missImg3=BitmapFactory.decodeResource(getResources(), R.drawable.mi3);
        //미사일 이미지 크기 조절
        missImg1=Bitmap.createScaledBitmap(missImg1, missSize, missSize, false);
        missImg2=Bitmap.createScaledBitmap(missImg2, missSize, missSize, false);
        missImg3=Bitmap.createScaledBitmap(missImg3, missSize, missSize, false);
        //미사일 이미지를 배열에 넣어두기
        missImgs[0]=missImg1;
        missImgs[1]=missImg2;
        missImgs[2]=missImg3;
        //적기 이미지 로딩
        Bitmap enemyImg1=BitmapFactory.decodeResource(getResources(), R.drawable.gold1);
        Bitmap enemyImg2=BitmapFactory.decodeResource(getResources(), R.drawable.silver1);
        //적기 이미지 사이즈 조절
        enemyImg1=Bitmap.createScaledBitmap(enemyImg1, unitSize, unitSize, false);
        enemyImg2=Bitmap.createScaledBitmap(enemyImg2, unitSize, unitSize, false);
        //적기 이미지 배열에 저장
        enemyImgs[0]=enemyImg1;
        enemyImgs[1]=enemyImg2;
        //적기의 x 좌표를 배열에 저장한다. (아래의 코드 반복문 돌면서 넣기)
        for(int i=0; i<5; i++){
            enemyX[i]=i*unitSize+unitSize/2;
        }
        //enemyX={unitSize/2, unitSize+unitSize/2, 2*unitSize+unitSize/2, 3*unitSize+unitSize/2, 4*unitSize+unitSize/2};

        //배경 이미지의 초기 좌표
        back1Y = 0;
        back2Y = -height;

        //핸들러에 빈 메세지를 20/1000 초 이후에 보낸다. (처음 시작.)
        handler.sendEmptyMessageDelayed(0, 20);
    }

    //View가 활성화 될 때 최초 한번 호출되고 View의 사이즈가 바뀌면 다시 호출된다.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //view 가 차지하고 있는 폭과 높이가 px 단뒤로 w,h 에 전달된다.
        width = w;
        height = h;
        //unitSize 는 화면 폭의 1/5로 설정
        unitSize=w/5;
        //드래곤의 초기 좌표 부여 (가운데 기준)
        dragonX=w/2;
        dragonY=height-unitSize/2;

        //미사일의 크기
        missSize=unitSize/4;
        //적기의 속도 부여
        speedEnemy=h/100; //높이값을 20으로 나눈다. 숫자가 커질수록 적기가 느려짐
        //미사일의 속도 부여
        speedMissile=h/50;
        //초기화 메소드 호출
        init();
    }
    //초당 50번 호출되는 메소드
    @Override
    protected void onDraw(Canvas canvas) {
        /*
        if(isDragonDie){//만일 드래곤이 죽으면
            return;
        }
        */
        back1Y += 3;
        back2Y += 3;
        //배경 1의 좌표가 아래로 벗어나면
        if (back1Y >= height) {
            //배경1의 상단으로 다시 보낸다
            back1Y = -height;
            //배경2와 오차가 생기지 않게 하기위해 복원하기
            back2Y = 0;
        }
        if (back2Y >= height) {
            back2Y = -height;
            back1Y = 0;
        }
        //카운트를 증가 시키고
        count++;
        if(count%10 == 0){ //숫자를 설정하면 날개의 속도 설정 가능 / 숫자가 작아질수록 빨리 움직인다.
            //드래곤 애니메이션 효과를 주기 위해
            dragonIndex++;
            if(dragonIndex==4){//만일 없는 인덱스라면
                dragonIndex=0;//다시 처음으로
            }
        }
        //미사일 만드는 메소드
        makeMissile();
        //미사일 움직이는 메소드
        moveMissile();
        //미사일 체크(배열에서 제거할 미사일은 제거)
        checkMissile();
        //적기 관련 처리
        makeEnemy();
        moveEnemy();
        checkEnemy();
        //적기와 미사일의 충돌 검사
        checkStrike();
        //드래곤의 충돌 검사
        checkDie();

        //배경 이미지 그리기 (좌상단의 좌표가 변함이 없게 하려고 0 설정)
        canvas.drawBitmap(backImg, 0, back1Y, null);
        canvas.drawBitmap(backImg, 0, back2Y, null);
        //반복문 돌면서 적기 그리기
        for(Enemy tmp:enemyList){
            if(tmp.isFall){//추락하는 중일 때
                canvas.save();
                canvas.translate(tmp.x, tmp.y);
                canvas.rotate(tmp.angle);
                //적기를 원점에 그린다.
                canvas.drawBitmap(enemyImgs[tmp.type],
                        0-unitSize/2,
                        0-unitSize/2,
                        null);
                canvas.restore();
            }else{//추락하는 중이 아닐 때
                canvas.drawBitmap(enemyImgs[tmp.type], //0 또는 1 참조
                        tmp.x-unitSize/2, tmp.y-unitSize/2, null);
            }
        }

        //반복문 돌면서 미사일 그리기
        for(Missile tmp:missList){
            canvas.drawBitmap(missImgs[1], tmp.x-missSize/2, tmp.y-missSize/2, null);//미사일 이미지가 약간 오른쪽에서 나감
        }

        /*
        moveEnemy 부분. 중요. 이해하기
        캔버스를 아무것도 변화시키지 않은 정상 상태 저장
        save()(현재상태저장) -> 변화시키고 그린다음에 -> restore()(캔버스 save 상태로 원상복구) (패턴 알아두기) *** 브레스 맞은 적기 돌면서 떨어지게 만들기 발판작업.
         */
        /*
        //캔버스의 현재상태 저장 (캔버스 변화 전 상태 저장)
        canvas.save();

        //원점 자체를 드래곤의 위치로 이동
        canvas.translate(dragonX, dragonY);
        //회전 (좌표계 자체를 회전시킴) / 좌표계는 평행이동도 가능하고, 회전도 가능하다.
        canvas.rotate(45);
         */
        //드래곤 이미지 그리기 (숫자가 주기적으로 0,1,2 바뀌면 이미지가 변화된다.) 유닛사이즈를 8분의 1만큼 뺐다. 폭의 반이 8분의 1이기 때문.
        //dragonX-unitSize/2, dragonY-unitSize/2 -> 0-unitSize/2, 0-unitSize/2
        canvas.drawBitmap(dragonImgs[dragonIndex], dragonX-unitSize/2, dragonY-unitSize/2, null);
        /*
        //캔버스 복구(save 때의 상태로)
        canvas.restore();
         */

        /*
        위치를 지정하는 2가지 방법

        원점에서 좌상단의 위치를 지정해서 그리는 방법
        좌표계를 이동해서 원점 자체에 그리는 방법
        */
    }
    //드래곤과 적기의 충돌검사
    public void checkDie(){
        for(Enemy tmp:enemyList){
            //드래곤의 좌표와 적기 사이의 거리를 구해서 겹친다고 간주되면 충돌로 판정 (피타고라스의 정리)
            double powX=Math.pow(dragonX-tmp.x, 2);
            double powY=Math.pow(dragonY-tmp.y, 2);
            //드래곤과 적기 사이의 거리
            double distance=Math.sqrt(powX+powY);//Math.sqrt = 루트를 씌운 것 (제곱한 값에 루트를 씌운 것)
            if(distance < unitSize/2){ //겹치는 거리를 나타냄. 오른쪽의 값이 클수록(겹치는 공간의 허용이 축소된다.) 잘죽음.
                isDragonDie=true;
                //드래곤이 죽는 효과음 재생
                sManager.playSound(GameActivity.SOUND_BIRDDIE);
            }
        }
    }

    //새로운 쓰레드에서 UI를 건드는 건 금지되어 있다. 메인 쓰레드에서만 건드려야 된다.
    /*
    Thread t=new Thread(){
        @Override
        public void run() {
            while(true){
                try{
                    Thread.sleep(20);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                invalidate();
            }
        }
    };
     */
    //적기와 미사일의 충돌 검사하기
    public void checkStrike(){
        //반복문 돌면서 미사일 객체를 하나씩 참조해서
        for(int i=0; i<missList.size(); i++){
            Missile m=missList.get(i);
            //참조된 미사일 객체를 반복문 돌면서 모든 적기 객체와 충돌을 체크한다.
            for(int j=0; j<enemyList.size(); j++){
                Enemy e=enemyList.get(j);
                //i번째 미사일이 j번째 적기를 맞추었는지 여부 (모두다 true 여야해서 &&로 묶음)
                boolean isStrike=m.x > e.x - unitSize/2 &&
                                 m.x < e.x + unitSize/2 &&
                                 m.y > e.y - unitSize/2 &&
                                 m.y < e.y + unitSize/2;
                if(isStrike){
                    //적기 에너지를 줄이고
                    e.energy -= 50;
                    //미사일을 없앤다.
                    m.isDead=true; //checkMissile 할 때 사라진다.
                    //적기의 에너지가 0 이하면 적기도 제거 되도록
                    if(e.energy <= 0){
                        e.isDead=true;
                    }
                    //적기가 미사일에 맞았다는 효과음을 재생한다.
                    sManager.playSound(GameActivity.SOUND_SHOOT);
                }
            }
        }
    }

    //적기를 만드는 메소드 (불특정 시점에 5개씩 만듦.)
    public void makeEnemy(){
        //0~49 사이의 랜덤한 숫자를 발생 시켜서
        int ranNum=ran.nextInt(50);//적을 더 많이 나오게 하려면 숫자를 줄이기
        //우연히 20이 나오지 않으면 (우연히 20번이 나오면 적기 출현)
        if(ranNum!=20){
            //메소드를 여기서 종료한다.
            return;
        }
        //반복문 돌면서 적기 5개를 만들어서 배열에 누적 시키기
        for(int i=0; i<5; i++){
            //적기의 종류도 랜덤하게 0~1 사이의 랜덤한 수 얻어내기
            int type=ran.nextInt(2);
            if(type==0){
                Enemy e=new Enemy(enemyX[i], -unitSize/2, type, 50, unitSize);//반복문 돌면서 enemyX[i] 0,1,2,3,4 변화
                enemyList.add(e);
            }else if(type==1){
                Enemy e=new Enemy(enemyX[i], -unitSize/2, type, 100, unitSize);
                enemyList.add(e);
            }
        }
    }
    public void moveEnemy(){
        for(Enemy tmp:enemyList){
            //적기가 추락하는 상태라면
            if(tmp.isFall){
                //크기를 줄이고
                tmp.size -= 1;
                //회전값을 증가 시킨다.
                tmp.angle += 10;
                //만일 크기가 0보다 작아진다면
                if(tmp.size <= 0){
                    tmp.isDead=true;
                }
            }else{//추락하는 상태가 아니라면 움직이기
                tmp.y += speedEnemy;//적기가 움직이는 속도 / px단위면 고해상도에서는 미사일이 느려진다. / 상대적인 값을 사용해야함.
            }
            //적기가 화면 아래쪽으로 벗어나면 (원래는 10 / 필드에 설정한 speedEnemy로 바꿈)
            if(tmp.y >= height+unitSize/2){
                tmp.isDead=true;//배열에서 제거 될수 있도록 표시한다.
            }
        }
    }

    public void checkEnemy(){
        for(int i=enemyList.size()-1; i>=0; i--){
            Enemy tmp=enemyList.get(i);
            if(tmp.isDead){
                enemyList.remove(i);
            }
        }
    }
    //배열에서 제거할 미사일은 제거하는 메소드
    public void checkMissile(){
        //반복문을 배열의 마지막 방에서 부터 역순으로 돈다.
        for(int i=missList.size()-1; i>=0; i--){
            //i번째 미사일 객체를 얻어와서
            Missile tmp=missList.get(i);
            //만일 제거할 미사일 이라면
            if(tmp.isDead){
                //배열에서 i번째 아이템을 삭제한다.
                missList.remove(i);
            }
        }
    }
    //미사일 움직이는 메소드
    public void moveMissile(){
        for(Missile tmp:missList){
            //미사일의 y좌표를 감소 시켜서 앞으로 전진하게 하고
            tmp.y -= speedMissile; //속도를 빠르게 하려면 숫자 키우기 / 상대적인 값 사용하기
            //만일 화면을 벗어난 미사일이라면 제거하도록 표시
            if(tmp.y <= -missSize/2) {
                tmp.isDead = true;
            }
        }
    }
    //미사일 만드는 메소드
    public void makeMissile(){
        //미사일 만들어지는 횟수 조절 (1초에 미사일이 하나씩 만들어짐)
        if(count%5 != 0){//50번 호출될 때 한번만 만들어지도록 / 50단위로 만들어진다? / 5의 배수로 하면 겁나 빨리 미사일 나가고 많아짐.
            return;//메소드를 여기서 끝내라라
       }
        //미사일의 초기좌표는 드래곤의 위치이다.
        Missile m=new Missile(dragonX, dragonY);
        //미사일 객체를 List 에 저장하기 (배열에 집어넣기)
        missList.add(m);
        //미사일 발사되는 효과음 재생하기
        sManager.playSound(GameActivity.SOUND_LAZER);//1을 전달하는 것과 마찬가지 (상수값 정의)
    }

    //View에 터치 이벤트가 발생하면 호출되는 메소드 (return true 해주니까 마우스클릭한채로 용을 부드럽게 움직일 수 있게 되었다.)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        dragonX=(int)event.getX();

        return true;
    }

    //스레드를 대신할 핸들러 객체 생성하고 handleMessage 메소드 오버라이드
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {//20/1000초마다 호출. 계속 화면이 업데이트 된다. (게임은 화면이 계속 움직여야 한다. ***)
            //GameView 를 무효화하고 다시 그려지게 한다.
            //결과적으로 onDraw() 메소드가 다시 호출됩니다.
            invalidate();//20/1000초 마다 갱신되게. 1초에 화면이 50번 갱신된다. / onDraw 호출
            if(!isDragonDie){//드래곤이 죽지 않았을때만 UI 가 업데이트 되도록 설정
                //자신(Handler)에게 다시 20/1000 초 이후에 메세지를 보낸다.
                handler.sendEmptyMessageDelayed(0,20); //핸들러 때문에 화면이 움직이게 된다.
            }
        }
    };
}

/*
x=w/2-unitSize/2
y=height-unitSize
 */
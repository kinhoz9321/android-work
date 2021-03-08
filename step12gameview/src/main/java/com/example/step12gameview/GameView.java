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

public class GameView extends View {
    //필요한 필드 정의하기
    Bitmap backImg;
    int width, height; //view 의 폭과 높이
    int back1Y, back2Y;
    //드레곤의 이미지를 저장할 배열
    Bitmap[] dragonImgs=new Bitmap[4];
    //드레곤 이미지 인덱스
    int dragonIndex=0;
    //유닛(드레곤, 적기) 의 크기를 저장할 필드
    int unitSize;
    //드레곤의 좌표를 저장할 필드(가운데 기준)
    int dragonX, dragonY;
    //onDraw() 메소드 호출 횟수를 저장할 필드
    int count=0;
    //Missile 객체를 저장할 List
    List<Missile> missList=new ArrayList<>();
    //미사일의 크기
    int missSize;
    //미사일 이미지를 담을 배열
    Bitmap[] missImgs=new Bitmap[3];
    //적기 이미지를 저장할 배열 / 2차원 배열 (다중배열) 사용 / 다중배열을 사용하지 않으면 List<List<Bitmap>> 사용해야한다. .get(0).get(0) 이런식으로 참조해야한다.
    Bitmap[][] enemyImgs=new Bitmap[2][2];//적기 이미지가 한종류당 2개씩 있으니까 이렇게 담기. / 총 방이 4개
    /*
    만일 방의 크기가 바뀐다면 List<List<Bitmap>> 를 사용해야 한다. 왜지?
     */
    //Enemy 객체를 저장할 List
    List<Enemy> enemyList=new ArrayList<>();
    //적기의 x 좌표를 저장할 배열
    int[] enemyX=new int[5];
    //랜덤한 숫자를 얻어낼 Random 객체
    Random ran=new Random();
    //드레곤이 죽었는지 여부
    boolean isDragonDie=false;
    //SoundManager 객체를 저장할 필드
    SoundManager sManager;
    //적기의 속도를 저장할 필드
    int speedEnemy;
    //미사일의 속도를 저장할 필드
    int speedMissile;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //SoundManager 객체를 필드에 저장하는 setter 메소드
    public void setsManager(SoundManager sManager) {
        this.sManager = sManager;
    }

    //초기화 메소드
    public void init(){
        //필요한 이미지 로딩해서 준비해 놓기
        Bitmap backImg=
                BitmapFactory.decodeResource(getResources(), R.drawable.backbg);
        //배경이미지를 view 의 크기에 맞게 조절해서 필드에 저장
        this.backImg=Bitmap
                .createScaledBitmap(backImg, width, height, false);
        //드레곤 이미지를 로딩해서 사이즈를 조절하고 배열에 저장한다.
        Bitmap dragonImg1=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit1);
        Bitmap dragonImg2=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit2);
        Bitmap dragonImg3=
                BitmapFactory.decodeResource(getResources(), R.drawable.unit3);
        dragonImg1=Bitmap
                .createScaledBitmap(dragonImg1, unitSize, unitSize, false);
        dragonImg2=Bitmap
                .createScaledBitmap(dragonImg2, unitSize, unitSize, false);
        dragonImg3=Bitmap
                .createScaledBitmap(dragonImg3, unitSize, unitSize, false);
        dragonImgs[0]=dragonImg1;
        dragonImgs[1]=dragonImg2;
        dragonImgs[2]=dragonImg3;
        dragonImgs[3]=dragonImg2;
        //미사일 이미지 로딩
        Bitmap missImg1=BitmapFactory.decodeResource(getResources(),
                R.drawable.mi1);
        Bitmap missImg2=BitmapFactory.decodeResource(getResources(),
                R.drawable.mi2);
        Bitmap missImg3=BitmapFactory.decodeResource(getResources(),
                R.drawable.mi3);
        //미사일 이미지 크기 조절
        missImg1=Bitmap.createScaledBitmap(missImg1,
                missSize, missSize, false);
        missImg2=Bitmap.createScaledBitmap(missImg2,
                missSize, missSize, false);
        missImg3=Bitmap.createScaledBitmap(missImg3,
                missSize, missSize, false);
        //미사일 이미지를 배열에 넣어두기
        missImgs[0]=missImg1;
        missImgs[1]=missImg2;
        missImgs[2]=missImg3;
        //적기 이미지 로딩
        Bitmap enemyImg1=BitmapFactory
                .decodeResource(getResources(), R.drawable.silver1);
        Bitmap enemyImg2=BitmapFactory
                .decodeResource(getResources(), R.drawable.silver2);
        Bitmap enemyImg3=BitmapFactory
                .decodeResource(getResources(), R.drawable.gold1);
        Bitmap enemyImg4=BitmapFactory
                .decodeResource(getResources(), R.drawable.gold2);
        //적기 이미지 사이즈 조절
        enemyImg1=Bitmap.createScaledBitmap(enemyImg1,
                unitSize, unitSize, false);
        enemyImg2=Bitmap.createScaledBitmap(enemyImg2,
                unitSize, unitSize, false);
        enemyImg3=Bitmap.createScaledBitmap(enemyImg3,
                unitSize, unitSize, false);
        enemyImg4=Bitmap.createScaledBitmap(enemyImg4,
                unitSize, unitSize, false);
        //적기 이미지 배열에 저장
        //silver 0행에 2개 집어넣고 [type][imageIndex] [0번 타입][0번 인덱스] / imageIndex 를 0,1,0,1 바뀌게 한다?
        enemyImgs[0][0]=enemyImg1; //0행 0열 silver1
        enemyImgs[0][1]=enemyImg2; //0행 1열 silver2
        //gold 1행에 2개 집어넣음
        enemyImgs[1][0]=enemyImg3; //1행 0열 gold1
        enemyImgs[1][1]=enemyImg4; //1행 1열 gold2
        //적기의 x 좌표를 배열에 저장한다.
        for(int i=0; i<5; i++){
            enemyX[i] = i*unitSize + unitSize/2;
        }

        //배경이미지의 초기좌표
        back1Y=0;
        back2Y=-height;
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
        //unitSize 는 화면 폭의 1/5 로 설정
        unitSize=w/5;
        //드레곤의 초기 좌표 부여
        dragonX=w/2;
        dragonY=height-unitSize/2;
        //미사일의 크기
        missSize=unitSize/4;
        //적기의 속도 부여
        speedEnemy=h/100;
        //미사일의 속도 부여
        speedMissile=h/50;
        //초기화 메소드 호출
        init();
    }
    //초당 50번 호출되는 메소드
    @Override
    protected void onDraw(Canvas canvas) {

        back1Y += 3;
        back2Y += 3;
        //배경1의 좌표가 아래로 벋어나면
        if(back1Y >= height){
            //배경1의 상단으로 다시 보낸다.
            back1Y=-height;
            //배경2와 오차가 생기지 않게 하기위해 복원하기
            back2Y=0;
        }
        if(back2Y >= height){
            back2Y=-height;
            back1Y=0;
        }
        //카운트를 증가시키고
        count++;
        if(count%10 == 0){
            //드레곤 애니메이션 효과를 주기위해
            dragonIndex++;
            if(dragonIndex==4){//만일 없는 인덱스라면
                dragonIndex=0;//다시 처음으로
            }
        }
        //미사일 만들고
        makeMissile();
        //미사일 움직이고
        moveMissile();
        //미사일 체크(배열에서 제거할 미사일은 제거)
        checkMissile();
        //적기 관련 처리
        makeEnemy();
        moveEnemy();
        checkEnemy();
        enemyAnimation();

        //적기와 미사일의 충돌 검사
        checkStrike();
        //드레곤의 충돌검사
        checkDie();

        //배경 이미지 그리기
        canvas.drawBitmap(backImg, 0, back1Y, null);
        canvas.drawBitmap(backImg, 0, back2Y, null);
        //반복문 돌면서 적기 그리기
        for(Enemy tmp:enemyList){
            if(tmp.isFall){//추락하는 중일때
                //canvas 에 어떤 변화도 가하지 않은 초기 상태를 저장하고
                canvas.save();//변화 전 상태를 저장
                //적기가 죽은 위치로 평행이동
                canvas.translate(tmp.x, tmp.y);
                //그 위치에서 좌표게를 회전
                canvas.rotate(tmp.angle);//좌표계 자체를 회전
                //좀 더 줄어든 크기의 Bitmap 이미지를 얻어내서
                Bitmap scaled=Bitmap.createScaledBitmap(enemyImgs[tmp.type][tmp.imageIndex], tmp.size, tmp.size, false);
                // 적기를 원점에 그린다.
                canvas.drawBitmap(scaled, 0-tmp.size/2, 0-tmp.size/2, null);//0을 바꾸면 적기가 어긋나게 떨어진다.
                canvas.restore();//변화 전 저장상태 불러오기
            }else {//추락하는 중이 아닐때
                canvas.drawBitmap(enemyImgs[tmp.type][tmp.imageIndex], tmp.x-unitSize/2, tmp.y-unitSize/2, null);
            }
        }

        //반복문 돌면서 미사일 그리기
        for(Missile tmp:missList){
            canvas.drawBitmap(missImgs[1],
                    tmp.x-missSize/2, tmp.y-missSize/2, null);
        }

        //드레곤 이미지 그리기
        canvas.drawBitmap(dragonImgs[dragonIndex], dragonX-unitSize/2,
                dragonY-unitSize/2, null);

    }

    //적기 애니메이션
    public void enemyAnimation(){
        //날개가 너무 빨리 움직이지 않도록 속도를 조절한다.
        if(count%10 != 0){
            return;
        }
        //반복문 돌면서
        for(Enemy tmp:enemyList){
            //적기의 이미지 인덱스를 1증가 시킨다.
            tmp.imageIndex++;
            if(tmp.imageIndex==2){//만일 존재하지 않는 인덱스라면
                tmp.imageIndex=0;//다시 처음으로 설정
            }
        }
    }

    //드레곤과 적기의 충돌검사
    public void checkDie(){
        for(Enemy tmp:enemyList){
            //드레곤의 좌표와 적기 사이의 거리를 구해서 겹친다고 간주 되면 충돌로 판정
            double powX=Math.pow(dragonX-tmp.x, 2);
            double powY=Math.pow(dragonY-tmp.y, 2);
            //드레곤과 적기 사이의 거리
            double distance=Math.sqrt(powX+powY);
            if(distance < unitSize/2){
                isDragonDie=true;
                //드레곤이 죽는 효과음 재생
                sManager.playSound(GameActivity.SOUND_BIRDDIE);
            }
        }
    }

    //적기와 미사일의 충돌 검사하기
    public void checkStrike(){
        //반복문 돌면서 미사일 객체를 하나씩 참조해서
        for(int i=0; i<missList.size(); i++){
            Missile m=missList.get(i);
            //참조된 미사일 객체를 반복문 돌면서 모든 적기 객체와 충돌을 체크한다.
            for(int j=0; j<enemyList.size(); j++){
                Enemy e=enemyList.get(j);
                // i 번째 미사일이 j 번째 적기를 맞추었는지 여부
                boolean isStrike = m.x > e.x - unitSize/2 &&
                        m.x < e.x + unitSize/2 &&
                        m.y > e.y - unitSize/2 &&
                        m.y < e.y + unitSize/2;
                if(isStrike && !e.isFall){//!e.isFall 이걸 넣지 않으면 추락하는 애들도 미사일을 맞는다. 걔네는 맞을 필요 없지.
                    //적기 에너지를 줄이고
                    e.energy -= 50;
                    //미사일을 없앤다
                    m.isDead=true;
                    //적기의 에너지가 0 이하면 적기가 추락 하도록
                    if(e.energy <= 0){
                        e.isFall=true; //강제 true 변화. 떨어지는 애니메이션 효과. -> moveEnemy 참고
                    }
                    //적기가 미사일에 맞았다는 효과음을 재생한다.
                    sManager.playSound(GameActivity.SOUND_SHOOT);
                }
            }
        }
    }

    public void makeEnemy(){
        //0~49 사이의 렌덤한 숫자를 발생 시켜서
        int ranNum=ran.nextInt(50);
        //우연히 20이 나오지 않으면
        if(ranNum!=20){
            //메소드를 여기서 종료한다.
            return;
        }
        //반복문 돌면서 적기 5개를 만들어서 배열에 누적 시키기
        for(int i=0; i<5; i++){
            //적기의 종류도 랜덤하게 0~1 사이의 랜덤한 수 얻어내기
            int type=ran.nextInt(2);
            if(type==0){
                Enemy e=new Enemy(enemyX[i], -unitSize/2, type, 50, unitSize);//unitSize = size라는 필드에 저장됨.
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
            if(tmp.isFall){ //isFall = true
                //크기를 줄이고
                tmp.size -= 1;
                //회전값을 증가 시킨다. 10도 20도 30도... 증가
                tmp.angle += 10;
                //만일 크기가 0보다 작아진다면
                if(tmp.size <= 0){
                    //배열에서 제거 될 수 있도록 표시한다.
                    tmp.isDead=true;
                }
            }else{//추락하는 상태가 아니라면 움직이기
                tmp.y += speedEnemy;
            }
            //적기가 화면 아래쪽으로 벗어나면
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
            //i 번째 미사일 객체를 얻어와서
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
            //미사일의 y 좌표를 감소 시켜서 앞으로 전진하게 하고
            tmp.y -= speedMissile;
            //만일 화면을 벗어난 미사일 이라면 제거하도록 표시한다.
            if(tmp.y <= -missSize/2){
                tmp.isDead=true;
            }
        }
    }
    //미사일 만드는 메소드
    public void makeMissile(){
        //미사일 만들어지는 횟수 조절
        if(count%5 != 0){ //50번 호출될때 한번만 만들어지도록
            return; //메소드를 여기서 끝내라
        }
        //미사일의 초기좌표는 드레곤의 위치이다.
        Missile m=new Missile(dragonX, dragonY);
        //미사일 객체를 List 에 저장하기
        missList.add(m);
        //미사일 발사 되는 효과음 재생하기
        sManager.playSound(GameActivity.SOUND_LAZER);
    }

    //View 에 터치 이벤트가 발생하면 호출되는 메소드
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        dragonX=(int)event.getX();

        return true;
    }

    //스레드를 대신할 핸들러 객체 생성하고 handleMessage 메소드 오버라이드
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //GameView 를 무효화 하고 다시 그려지게 한다.
            //결과적으로 onDraw() 메소드가 다시 호출됩니다.
            invalidate();
            if (!isDragonDie) {
                //자신(Handler)에게 다시 20/1000 초 이후에 메세지를 보낸다.
                handler.sendEmptyMessageDelayed(0, 20);
            }
        }
        /*
        전형적인 슈팅게임의 로직
        이미지를 바꾸고, 각도를 바꾸면 다른 게임으로 만들수도 있다.
        반복문 / if문 / 배열 / 객체 참조 연습
         */
    };
}

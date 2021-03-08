package com.example.step12gameview;

public class Missile {
    //필드
    public int x;
    public int y;

    //화면에서 제거할지 여부 (적기에 맞으면 저게할지 안할지 불리언타입으로 관리)
    public boolean isDead=false; //초기값 false 로 설정

    //생성자(미사일의 초기 좌표를 생성자로 전달받아서 필드에 저장)
    public Missile(int x, int y){
        this.x=x;
        this.y=y;
    }
}

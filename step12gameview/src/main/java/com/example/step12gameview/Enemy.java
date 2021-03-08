package com.example.step12gameview;

public class Enemy {
    public int x, y;//적기의 좌표
    public int type;//적기의 type 0 or 1
    public boolean isDead; // 배열에서 제거할지 여부
    public int energy;//에너지
    public boolean isFall; //현재 추락하고 있는지 여부 / 선언만하면 false -> 에너지가 다 닳으면 강제로 true 로 바꿀 것
    public int angle; //회전각 / 적기는 고유한 회전량을 갖고 있어야 하기 때문에 추가 (총맞는시점이 다 다르니까)
    public int size; //크기 / 각각의 적기는 크기도 각자 달라야 하기 때문에 추가 / 추락하고 있는 상태가 되면 크기를 점점 줄인다. (애니메이션 효과)
    public int imageIndex; //적기의 이미지 인덱스 (애니메이션 효과를 주기위해)

    //생성자
    public Enemy(int x, int y, int type, int energy, int size){
        this.x = x;
        this.y = y;
        this.type = type;
        this.energy = energy;
        this.size = size;
    }
}
package com.example.step19_asynctask;

import android.util.Log;

public class Messenger {
    //가상으로 메세지를 보내는 static 메소드
    public static void sendMessage(String msg){
        Log.e("Messenger sendMessage()", "메세지 전송중");
        //메세지를 전송하는데 20초가 걸린다고 가정
        try{
            Thread.sleep(10000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        Log.e("Messenger sendMessage()", "메세지 전송 완료");
    }
}

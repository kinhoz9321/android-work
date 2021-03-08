package com.example.step12gameview;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

/*
    효과음을 필요한 시점에 재생하기 위한 클래스 설계
 */
public class SoundManager {//긴음악은 MediaPlayer 사용. SoundManager는 짧은 효과음만 가능.
    //사운드의 아이디값(정수값) 을 저장하기 위한 Map (키값도 정수, value값도 정수)
    Map<Integer, Integer> map=new HashMap<>();
    //SoundPool
    SoundPool pool;
    //Context
    Context context;
    //볼륨
    int streamVolume;

    //생성자
    public SoundManager(Context context){
        this.context=context;
        //maxStream 동시 반응 사운드 갯수 (3이면 3개 동시출력 으앜 소리 더 잘남ㅋㅋㅋ)
        pool=new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        //오디오 서비스 객체를 얻어와서
        AudioManager am=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
        //설정된 음악 볼륨값을 읽어와서 필드에 저장한다. / 음악볼륨을 얻어와서 streamVolume 을 재생할 때 사용한다. (무음가능)
        streamVolume=am.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /* 중요한 부분 */
    //재생할 사운드 등록하는 메소드 (key serId를 전달하면) / addSound(1(key), R.raw.laser1(resId))
    public void addSound(int key, int resId){
        //(resId 를 이용해서 사운드를 로딩하고) 아이디값을 리턴 받는다. / 사운드를 로딩하면 아이디값이 리턴된다.
        int soundId=pool.load(context, resId, 1);
        //(리턴 받은 아이디값을 인자로 전달된 키값으로 저장한다.) / 정수 키값으로 정수를 저장을 하겠다.
        map.put(key, soundId);
    }

    //사운드를 재생하는 메소드 (등록된 key값을 이용해서 사운드 재생) / 등록1번을 전달하고 싶으면 1, 등록2번을 전달하고 싶으면 2
    public void playSound(int key){
        //인자로 전달받은 키값을 이용해서 Map 에서 재생할 사운드의 아이디를 읽어온다.
        int soundId=map.get(key);
        //재생하기 Volume 1 = 최대볼륨으로 하겠다. / left/rightVolume:1 을 사용하는 게 아니라 읽어온 값을 사용하면 볼륨의 설정을 조절할 수 있다. (무음가능)
        //시스템볼륨값을 얻어오기
        pool.play(soundId, streamVolume, streamVolume, 1, 0, 1);
    }

    public void stopSound(int key){
        int soundId=map.get(key);
        pool.stop(soundId);
    }
    public void pauseSound(int key){
        int soundId=map.get(key);
        pool.pause(soundId);
    }
    public void resumeSound(int key){
        pool.resume(map.get(key));
    }

    //자원 해제하는 메소드
    public void release(){
        pool.release();
    }
}
/*
    사용법만 익혀서 재생하면 된다.
    외우는 것 아님.
 */
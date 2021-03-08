package com.example.step19_asynctask;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView console;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//onCreate 에서 시작되는게 Main Thread (UI Thread) 여기서는 시간이 오래 걸리는 불확실한 작업을 하면 안된다.
        super.onCreate(savedInstanceState);
        //화면 구성하기
        setContentView(R.layout.activity_main);
        //버튼의 참조값 얻어오기
        Button sendBtn=findViewById(R.id.sendBtn);
        //리스너 등록하기
        sendBtn.setOnClickListener(this);
        //TextView의 참조값 얻어오기
        console=findViewById(R.id.console);
        //버튼의 참조값 얻어오기
        Button counterBtn=findViewById(R.id.counterBtn);
        //리스너 등록하기
       counterBtn.setOnClickListener(new View.OnClickListener() {
            //익명 클래스로 직접 구현
            @Override
            public void onClick(View v) {//UI Thread
                CounterTask task=new CounterTask();
                task.execute("김구라", "해골", "원숭이");
            }
        });
    }

    @Override
    public void onClick(View v) {//UI Thread
        /*
        시간이 오래 걸리거나 혹은 실행 시간이 불확실한 작업은
        Main Thread (UI Thread) 에서 하면 안된다.
         */
        //비동기 작업 클래스를 이용해서 객체 생성 후
        SendTask task=new SendTask();
        //execute() 메소드를 호출하면 자동으로 새로운 스레드에서 작업을 하게 된다. ***doInBackground 생성
        task.execute("hello", "one", "two", "three");//1. hello / 여러개 전달 / execute() = start() 와 비슷하다.
        //아무것도 전달하지 않을거라면 Void type을 전달. Void.
        //Void 타입에 들어가보면 생성자만 있고 아무것도 없다. 빈 클래스라고 알기.
        /*
        <사용법 익히기>
        비동기 작업 클래스 객체를 생성해서 참조값을 얻어낸 다음에
        execute() 를 호출하게 되면 내부적으로 새로운 스레드 상에서 doInBackground 메소드를 호출하게 된다.
         */
    }

    /*
        * 비동기 작업을 도와줄 클래스 설계하기 *
        1. AsyncTask 추상 클래스를 상속 받는다.
        2. AsyncTask<파라미터 type, 진행중 type, 결과 type>
            에 맞게끔 Generic 클래스를 잘 정의한다.
        3. doInBackground() 메소드를 오버라이드 한다.
        4. 추가로 필요한 메소드가 있으면 추가로 오버라이드 한다.
     */
    //아무것도 전달하지 않을거면 Void = 비어있는 타입 / null 이 아니라 void 를 넣어준다.
    //<파라미터 type(doInBackground()), 진행중 type(onProgressUpdate()), 결과 type('String' doInBackground -> onPostExecute())>
    public class SendTask extends AsyncTask<String, Void, Void> {//AsyncTask 상속 *** 전달할 파라미터의 타입을 적어야 한다. 어떤 타입을 전달할지 생각을 해서 정해야 한다.
        //SendTask 객체의 excute() 메소드를 호출하면 호출되는 메소드 (시간이 오래걸리는 불확실한 작업을 이곳에서 한다.)
        @Override
        protected Void doInBackground(String... strings) {//2. hello 전달받고 / String... type (=배열)동적인 인자의 갯수를 받을 수 있다.
            //동적인 갯수의 인자 strings 는 string[] 이다.
            //배열의 0번방에 첫번째 인자가 들어 있다.
            String first=strings[0];
            //여기가 새로운 스레드라고 생각하면 된다.
            Messenger.sendMessage(first);//3. hello 전달
            //여기는 새로운 스레드이기 때문에 UI 에 관련된 작업은 제약이 따른다.
            //작업이 끝났을 때 UI 에 관련된 작업을 하고 싶으면
            //OnPostExecute() 메소드를 오버라이드 해서 그 안에서 작업하면 된다.
            return null;
        }
        //doInBackground() 메소드가 리턴하면 자동으로 호출되는 메소드
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //여기는 UI 스레드이기 때문에 UI 에 관련된 작업을 마음대로 할 수 있다.
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("작업성공")
                    .create()
                    .show();
        }
    }

    public class CounterTask extends AsyncTask<String, Integer, String>{
        //<파라미터 type(doInBackground()), 진행중 type(onProgressUpdate()), 결과 type('String' doInBackground -> onPostExecute())>

        //반드시 오버라이드 해야하는 추상메소드
        @Override
        protected String doInBackground(String... strings) {//String... String 배열로 생각하기
            //새로운 스레드 (UI 업데이트 불가능)
            String name1=strings[0]; //김구라
            String name2=strings[1]; //해골
            String name3=strings[2]; //원숭이
            //0~20 사이의 랜덤한 정수를 얻어내서
            int ranNum=new Random().nextInt(20)+1;
            int count=0;
            //랜덤한 숫자를 얻어낸만큼 반복문 돌기
            for(int i=0; i<ranNum; i++){
                count++;
                try {
                    //1초씩 시간 딜레이
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                //여기서 현재 카운트값을 발행하기 -> 메인스레드에서 ui관련 작업해라.
                //결과적으로 onProgressUpdate() 메소드가 호출된다.
                this.publishProgress(count);//프로그래머가 임의로 호출. 메인스레드로 전달하는 느낌. / 숫자가 계속 달라지는 이유.
            }
            String result=count+" 까지 숫자를 다 세었습니다.";
            //비동기 작업의 결과를 리턴해주기
            //리턴된 데이터는 onPostExecute() 메소드가 호출되면서 인자로 전달된다.
            return result; //업데이트 x 리턴 o
        }

        //doInBackground() 메소드가 호출되기 직전에 호출되는 메소드
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //UI  스레드

        }

        //doInBackground() 가 리턴된 직후 호출되는 메소드
        @Override
        protected void onPostExecute(String s) {//doInBackground 결과 전달
            super.onPostExecute(s);
            //UI  스레드 (UI 를 마음대로 업데이트 한다.)
            console.setText(s);
        }

        //publishProgress() 메소드를 호출할 때 마다 호출되는 메소드 (중간중간에 publish)
        @Override
        protected void onProgressUpdate(Integer... values) {//진행중 타입이 Integer 이기 때문에 Integer... 이다. / this.publishProgress(count); count 전달.
            super.onProgressUpdate(values);
            //UI  스레드 (UI 관련된 어떤 작업이든 제약없이 할 수 있다.)
            //인자로 전달된 정수값을 문자열로 바꿔서 TextView 에 출력
            console.setText(Integer.toString(values[0]));//1,2,3,4,5,... 값 전달

        }
    }



    /*
    @Override
    public void onClick(View v) { //Main Thread (UI Thread)
        //시간이 오래 걸리는 작업은 새로운 작업단위(스레드)에서 작업을 시키고
        //UI 스레드는 바로 리턴한다.
        new SendThread().start();

    }

    class SendThread extends Thread{
        @Override
        public void run(){
            //리턴되는데 20초 걸리는 메소드를 여기서 호출한다면? / 20초 안에 다른 작업을 하려고 하면 앱이 죽어버림.
            //시간이 오래걸리거나 불확실한 작업들은 메인 스레드(UI 스레드)에서 절대로 하면 안된다. (새로운 스레드에서 해줘야함)
            Messenger.sendMessage("hello");
            Toast.makeText(MainActivity.this, "전송완료", Toast.LENGTH_SHORT).show();//UI를 건드리는 것은 오직 UI 스레드에서만 할 수 있다?
            //console.setText("전송완료");
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("전송완료")
                    .create().show();
        }
    }
     */

    /*
    AsyncTask = 안드로이드를 한다면 무조건 알아둬야함. 안쓸일은 거의 없다.
     */
}
package com.example.step17httprequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Util.RequestListener{
    EditText console;
    /*
        웹에서 json 문자열 받아와서 안드로이드에서 파싱해서 사용하는 법

        안드로이드에서 get방식 요청해서 json 문자열을 받아와서 파싱 후 ui update

        앱이 보여주는 문자열들은 웹이 응답하는 데이터이다.

        안드로이드 앱에서 서버에 문자열을 전송하는 방법. 응답되는 문자열을 가공하는 방법을 배움.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console=findViewById(R.id.console);
        Button requestBtn=findViewById(R.id.requestBtn);
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.0.4:8888/AndroidServer/info.jsp";
                Util.sendGetRequest(1, url, null, MainActivity.this);
            }
        });

        //요청하기 2
        Button requestBtn2=findViewById(R.id.requestBtn2);
        requestBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.0.4:8888/AndroidServer/info2.jsp";
                Util.sendGetRequest(2, url, null, MainActivity.this);
            }
        });

        //요청하기 3
        Button requestBtn3=findViewById(R.id.requestBtn3);
        requestBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://192.168.0.4:8888/AndroidServer/info3.jsp";
                Util.sendGetRequest(3, url, null, MainActivity.this);
            }
        });

        //이클립스 콘솔에 안드로이드에서 전송한 메세지가 출력된다. 웹브라우저의 폼 전송과 비슷한 기능을 한 것.
        /*
            서버 입장에서는 안드로이드에서 보낸건지 웹에서 보낸건지 모른다. 그냥 get방식 전송을 한 것일뿐?
         */
        Button sendBtn=findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 문자열 얻어오기
                EditText inputMsg=findViewById(R.id.inputMsg);
                String msg=inputMsg.getText().toString();
                //전송할 문자열을 Map에 msg 라는 키값으로 담는다.
                Map<String, String> map=new HashMap<>();
                map.put("msg", msg);

                String url="http://192.168.0.4:8888/AndroidServer/send.jsp";
                Util.sendGetRequest(4, url, map, MainActivity.this);
            }
        });

    }

    //응답되는 문자열을 가공하는 방법
    //결과가 성공이면 onSuccess
    @Override
    public void onSuccess(int requestId, Map<String, Object> result) {//requestId로 요청버튼 1을 눌렀는지 2를 눌렀는지 구분한다.
        //응답 코드값
        int code=(int)result.get("code");
        //응답된 문자열
        String data=(String)result.get("data");//이클립스의 문자열 <!doctype> 이 이곳에 담긴다.
        //요청의 아이디값을 이용해서 분기한다.
        switch (requestId){
            case 1:
                //추가 (이클립스 info.jsp 연동)
                /*
                    JSONObject : { }
                    JSONArray : [ ]
                 */
                try {
                    // data 는 {} 형식의 문자열이기 때문에 JSONObject 객체 생성하면서
                    // 생성자의 인자로 data 를 전달한다. date=JSON 문자열
                    JSONObject obj=new JSONObject(data);
                    //"num" 이라는 키값으로 저장된 int 얻어내기
                    int num=obj.getInt("num");
                    //"name" 이라는 키값으로 저장된 String 얻어내기
                    String name=obj.getString("name");
                    //"isMan" 이라는 키값으로 저장된 boolean 얻어내기
                    boolean isMan=obj.getBoolean("isMan");
                    console.setText("번호:"+num+" 이름:"+name+" 남자여부:"+isMan);//웹서버가 응답한 데이터 가공해서 사용

                } catch (JSONException e) {// data가 JSON 형식이 아니라면 예외가 발생한다.
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    //data는 [] 형식이므로 JSONArray 객체를 생성하면서
                    //생성자의 인자로 data를 전달한다.
                    JSONArray arr=new JSONArray(data);
                    //일단 지우고
                    console.setText("");
                    //JSONArray 의 사이즈 만큼 반복문을 돌면서
                    for(int i=0; i<arr.length(); i++){
                        //문자열을 순서대로 참조한다.
                        String tmp=arr.getString(i);
                        //참조된 문자열을 출력하기
                        console.append(tmp+"\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    //data는 [{},{},{}] 형식의 문자열이다. (JSONArray 안에 JSONObject 가 있는 구조)
                    //생성자의 인자로 data를 전달한다.
                    JSONArray arr=new JSONArray(data);
                    //일단 지우고
                    console.setText("");

                    //JSONArray 의 사이즈 만큼 반복문을 돌면서
                    for(int i=0; i<arr.length(); i++){
                        // data 는 {} 형식의 문자열이기 때문에 JSONObject 객체 생성하면
                        // 생성자의 인자로 data 를 전달한다.
                        JSONObject obj=arr.getJSONObject(i);
                        int num=obj.getInt("num");
                        String name=obj.getString("name");
                        boolean isMan=obj.getBoolean("isMan");
                        console.append("번호:"+num+" 이름:"+name+" 남자인지여부:"+isMan+"\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                //data는 HTML 형식임으로 그냥 EditText에 출력해보기
                console.setText(data);
                break;
        }

        //추가 (이클립스 info.jsp 연동)
        /*
            JSONObject : { }
            JSONArray : [ ]
         */
        try {
            // data 는 {} 형식의 문자열이기 때문에 JSONObject 객체 생성하면서
            // 생성자의 인자로 data 를 전달한다.
            JSONObject obj=new JSONObject(data);
            //"num" 이라는 키값으로 저장된 int 얻어내기
            int num=obj.getInt("num");
            //"name" 이라는 키값으로 저장된 String 얻어내기
            String name=obj.getString("name");
            //"isMan" 이라는 키값으로 저장된 boolean 얻어내기
            boolean isMan=obj.getBoolean("isMan");
            console.setText("번호:"+num+" 이름:"+name+" 남자여부:"+isMan);//웹서버가 응답한 데이터 가공해서 사용

        } catch (JSONException e) {// data가 JSON 형식이 아니라면 예외가 발생한다.
            e.printStackTrace();
        } 
        
        /*
        json 문자열 추출
        obj.getInt();
        obj.getString();
        obj.getBoolean();
         */
        
        //EditText에 출력해보기
        //console.setText(data);
    }

    //결과가 실패면 onFail
    @Override
    public void onFail(int requestId, Map<String, Object> result) {
        //예외 메세지 읽어오기
        String data=(String)result.get("data");
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
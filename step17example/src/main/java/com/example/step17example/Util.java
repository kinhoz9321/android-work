package com.example.step17example;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Util {
    public static interface RequestListener{//RequestListener 인터페이스 정의 (클래스안에 인터페이스를 정의할 수 있다.)
        public void onSuccess(int requestId, Map<String, Object> result);
        public void onFail(int requestId, Map<String, Object> result);
    }//인터페이스는 메소드의 모양을 강제하는 효과가 있다. (구현 클래스의 메소드를 강제한다.)
    /*
        1. 사용할때 RequestListener 인터페이스 Type 을 전달한다.
        2. 결과는 RequestListener 객체에 전달된다.
        3. Map<String,Object>  에서 응답 코드는
            "code" 라는 키값으로 200, 404, 500, -1 중에 하나가 리턴되고
             -1 이 리턴되면 Exception 발생으로 실패이다. onFail() 호출
     */
    //static 메소드 (인자로 4개 전달 / Util.sendGetRequest() 호출)
    public static void sendGetRequest(int requestId, String requestUrl, Map<String,String> params, RequestListener listener){//1 listener
        RequestTask task=new RequestTask();
        task.setRequestId(requestId);
        task.setRequestUrl(requestUrl);
        task.setListener(listener);//2. listener
        task.execute(params);
    }

    private static class RequestTask extends AsyncTask<Map<String,String>, Void, Map<String,Object>> {
        private int requestId;
        private String requestUrl;
        private RequestListener listener;//메인액티비티의 참조값이 들어오는 곳 (listener 의 최종 목적지) 4. listener

        public void setListener(RequestListener listener) {//3. listener
            this.listener = listener;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }
        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }
        @Override
        protected Map<String, Object> doInBackground(Map<String, String>... params) {
            String requestUrl=this.requestUrl;
            Map<String, String> param=params[0];
            if(param!=null){//요청 파리미터가 존재 한다면
                //서버에 전송할 데이터를 문자열로 구성하기
                StringBuffer buffer=new StringBuffer();
                Set<String> keySet=param.keySet();
                Iterator<String> it=keySet.iterator();
                boolean isFirst=true;
                //반복문 돌면서 map 에 담긴 모든 요소를 전송할수 있도록 구성한다.
                while(it.hasNext()){
                    String key=it.next();
                    String arg=null;
                    //파라미터가 한글일 경우 깨지지 않도록 하기 위해.
                    String encodedValue=null;
                    try {
                        encodedValue= URLEncoder.encode(param.get(key), "utf-8");
                    } catch (UnsupportedEncodingException e) {}
                    if(isFirst){
                        arg="?"+key+"="+encodedValue;
                        isFirst=false;
                    }else{
                        arg="&"+key+"="+encodedValue;
                    }
                    buffer.append(arg);
                }
                String data=buffer.toString();
                requestUrl +=data;
            }
            //서버가 http 요청에 대해서 응답하는 문자열을 누적할 객체
            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            //결과값을 담을 Map Type 객체
            Map<String,Object> map=new HashMap<String,Object>();
            try{
                //URL 객체 생성
                URL url=new URL(requestUrl);
                //HttpURLConnection 객체의 참조값 얻어오기
                conn=(HttpURLConnection)url.openConnection();
                if(conn!=null){//연결이 되었다면
                    conn.setConnectTimeout(20000); //응답을 기다리는 최대 대기 시간
                    conn.setRequestMethod("GET");//Default 설정
                    conn.setUseCaches(false);//케쉬 사용 여부
                    //응답 코드를 읽어온다.
                    int responseCode=conn.getResponseCode();
                    //Map 객체에 응답 코드를 담는다.
                    map.put("code", responseCode);
                    if(responseCode==200){//정상 응답이라면...
                        //서버가 출력하는 문자열을 읽어오기 위한 객체
                        isr=new InputStreamReader(conn.getInputStream());
                        br=new BufferedReader(isr);
                        //반복문 돌면서 읽어오기
                        while(true){
                            //한줄씩 읽어들인다.
                            String line=br.readLine();
                            //더이상 읽어올 문자열이 없으면 반복문 탈출
                            if(line==null)break;
                            //읽어온 문자열 누적 시키기
                            builder.append(line);
                        }
                        //출력받은 문자열 전체 얻어내기
                        String str=builder.toString();
                        //아래 코드는 수행 불가
                        //console.setText(str);
                        //Map 객체에 결과 문자열을 담는다.
                        map.put("data", str);
                    }
                }
            }catch(Exception e){//예외가 발생하면
                //에러 정보를 담는다.
                map.put("code",-1); //데이터
                map.put("data", e.getMessage()); //에러메세지
            }finally {
                try{
                    if(isr!=null)isr.close();
                    if(br!=null)br.close();
                    if(conn!=null)conn.disconnect();
                }catch(Exception e){}
            }
            //결과를 담고 있는 Map 객체를 리턴해준다.
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            int code=(int)map.get("code");
            if(code!=-1){//성공이라면
                listener.onSuccess(requestId, map);
            }else{//실패 (예외발생)
                listener.onFail(requestId, map);
            }
        }
    }
    //POST 방식 REQUEST / static 메소드
    public static void sendPostRequest(int requestId, String requestUrl, Map<String, String> params, RequestListener listener){
        PostRequestTask task=new PostRequestTask();
        task.setRequestId(requestId);
        task.setRequestUrl(requestUrl);
        task.setListener(listener);
        task.execute(params);
    }

    public static class PostRequestTask extends AsyncTask<Map<String, String>, Void, Map<String, Object>>{
        private int requestId;
        private String requestUrl;
        private RequestListener listener;

        public void setListener(RequestListener listener) {
            this.listener = listener;
        }
        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }
        public void setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
        }
        @Override
        protected Map<String, Object> doInBackground(Map<String, String>... params) {
            Map<String, String> param=params[0];
            String queryString="";
            if(param!=null){//요청 파리미터가 존재 한다면
                //서버에 전송할 데이터를 문자열로 구성하기
                StringBuffer buffer=new StringBuffer();
                Set<String> keySet=param.keySet();
                Iterator<String> it=keySet.iterator();
                boolean isFirst=true;
                //반복문 돌면서 map 에 담긴 모든 요소를 전송할수 있도록 구성한다.
                while(it.hasNext()){
                    String key=it.next();
                    String arg=null;
                    if(isFirst){
                        arg=key+"="+param.get(key);
                        isFirst=false;
                    }else{
                        arg="&"+key+"="+param.get(key);
                    }
                    buffer.append(arg);
                }
                queryString=buffer.toString();
            }
            //서버가 http 요청에 대해서 응답하는 문자열을 누적할 객체
            StringBuilder builder=new StringBuilder();
            HttpURLConnection conn=null;
            InputStreamReader isr=null;
            BufferedReader br=null;
            PrintWriter pw=null;
            //결과값을 담을 Map Type 객체
            Map<String,Object> map=new HashMap<String,Object>();
            try{
                //URL 객체 생성
                URL url=new URL(requestUrl);
                //HttpURLConnection 객체의 참조값 얻어오기
                conn=(HttpURLConnection)url.openConnection();
                if(conn!=null){//연결이 되었다면
                    conn.setConnectTimeout(20000); //응답을 기다리는 최대 대기 시간
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);//케쉬 사용 여부
                    //전송하는 데이터에 맞게 값 설정하기
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //폼전송과 동일
                    //출력할 스트림 객체 얻어오기
                    OutputStreamWriter osw=
                            new OutputStreamWriter(conn.getOutputStream());
                    //문자열을 바로 출력하기 위해 osw 객체를 PrintWriter 객체로 감싼다
                    pw=new PrintWriter(osw);
                    //서버로 출력하기
                    pw.write(queryString);
                    pw.flush();

                    //응답 코드를 읽어온다.
                    int responseCode=conn.getResponseCode();
                    //Map 객체에 응답 코드를 담는다.
                    map.put("code", responseCode);
                    if(responseCode==200){//정상 응답이라면...
                        //서버가 출력하는 문자열을 읽어오기 위한 객체
                        isr=new InputStreamReader(conn.getInputStream());
                        br=new BufferedReader(isr);
                        //반복문 돌면서 읽어오기
                        while(true){
                            //한줄씩 읽어들인다.
                            String line=br.readLine();
                            //더이상 읽어올 문자열이 없으면 반복문 탈출
                            if(line==null)break;
                            //읽어온 문자열 누적 시키기
                            builder.append(line);
                        }
                        //출력받은 문자열 전체 얻어내기
                        String str=builder.toString();
                        //아래 코드는 수행 불가
                        //console.setText(str);
                        //Map 객체에 결과 문자열을 담는다.
                        map.put("data", str);
                    }
                }
            }catch(Exception e){//예외가 발생하면
                //에러 정보를 담는다.
                map.put("code",-1);
                map.put("data", e.getMessage());
            }finally {
                try{
                    if(pw!=null)pw.close();
                    if(isr!=null)isr.close();
                    if(br!=null)br.close();
                    if(conn!=null)conn.disconnect();
                }catch(Exception e){}
            }
            //결과를 담고 있는 Map 객체를 리턴해준다.
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);
            int code=(int)map.get("code");
            if(code!=-1){//성공이라면
                listener.onSuccess(requestId, map);
            }else{//실패 (예외발생)
                listener.onFail(requestId, map);
            }
        }

    }
    //키보드 숨기는 메소드
    public static void hideKeyboard(Activity activity){

        InputMethodManager iManager=(InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus()==null)return;
        iManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    //메소드의 인자로 전달되는 View 객체의 포커스 해제하는 메소드
    public static void releaseFocus(View view) {
        ViewParent parent = view.getParent();
        ViewGroup group = null;
        View child = null;
        while (parent != null) {
            if (parent instanceof ViewGroup) {
                group = (ViewGroup) parent;
                for (int i = 0; i < group.getChildCount(); i++) {
                    child = group.getChildAt(i);
                    if(child != view && child.isFocusable())
                        child.requestFocus();
                }
            }
            parent = parent.getParent();
        }
    }
}

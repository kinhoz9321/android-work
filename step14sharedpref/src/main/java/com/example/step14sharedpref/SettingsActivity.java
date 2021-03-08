package com.example.step14sharedpref;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

/*
    메뉴버튼에서 settings 버튼을 눌렀을 때 settingsActivity로 이동
 */
public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        //up button 을 눌렀을 때 어떤 액티비티를 활성화 시킬지는
        //AndroidManifest.xml 에 Activity의 meta-data 설정을 해야 한다.
        //액션바에 up button 만들기
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Preference 리스너 등록
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);//getDefaultSharedPreferences settings_activity.xml 로 간다?
        pref.registerOnSharedPreferenceChangeListener(this);
        //액티비티가 활성화 되는 시점에 저장된 값을 읽어오고 싶으면 여기서 작업한다.
        String signature=pref.getString("signature", "");
        String reply=pref.getString("reply", "");
        boolean sync=pref.getBoolean("sync", false);
    }

    //onStop() 메소드에서 Preference 리스너 등록 해제
    @Override
    protected void onStop() {
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(this);
        pref.registerOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    //사용자가 설정 정보를 바꾸었을 때 호출되는 메소드
    @Override
    public void onSharedPreferenceChanged(SharedPreferences pref, String key) {
        //바뀐 설정의 key값이 전달된다. (예를들어 setting 의 서명 정보를 바꿨을 때 여기가 호출된다.)
        if(key.equals("signature")){
            //바뀐 signature 값을 읽어와보기 (설정의 서명 바꾸면 토스트가 뜬다. 호출확인)
            String signature=pref.getString(key, "");
            Toast.makeText(this,signature,Toast.LENGTH_SHORT).show();
        }else if(key.equals("reply")){//설정 정보가 토스트로 아래에 뜬다.
            String reply=pref.getString(key, "");
            Toast.makeText(this, reply, Toast.LENGTH_SHORT).show();
        }else if(key.equals("sync")){
            boolean sync=pref.getBoolean("sync", false);
            Toast.makeText(this, "동기화 여부:"+sync, Toast.LENGTH_SHORT).show();
        }
    }

    //settings 화면을 구성할 프레그먼트 (수정할 일은 없음)
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            //res/xml/root_preferences.xml 문서를 전개해서 프레그먼트 구성하기
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}
/*
getDefaultSharedPreferences - settingsActivity가 기본구성을 할 때 사용
getSharedPreferences - 직접 사용할 때 사용
두개의 차이점 알아보기.
디폴트로 만들어서 쓸 것이냐, 직접 만들어서 쓸 것이냐 의 차이
기능은 비슷한 것 같다.
 */
package com.example.step08customadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/*
    ListView 에 연결할 아답타 클래스 정의하기 ***
    메인액티비티에서 listView에 연결할 아답타 객체가 생성되고 연결됨.
    -BaseAdapter 추상 클래스를 상속 받아서 만든다. (표준에 맞춰서 만든 것)

 */
public class CountryAdapter extends BaseAdapter {
    //필드
    Context context;
    int layoutRes;
    List<CountryDto> list;

    //생성자
    public CountryAdapter(Context context, int layoutRes, List<CountryDto> list){
        //생성자의 인자로 전달된 값을 필드에 저장한다.
        this.context=context;
        this.layoutRes=layoutRes;
        this.list=list;
    }

    //모델의 아이템 갯수를 리턴한다.
    @Override
    public int getCount() {

        //return list.size()+10;//어딘가에서 null pointException 발생 13개가 지나가고 나면 꺼진다. (존재하지 않기 때문)
        //return 0; //아무것도 안나옴.
        //return 3; //3개 나옴
        return list.size();
    }
    //인자로 전달된 position 인덱스에 해당하는 아이템 리턴
    @Override
    public Object getItem(int position) {

        return list.get(position);
    }
    //인자로 전달된 position 인덱스에 해당하는 아이템의 아이디(PK(primary key))가
    //있으면 리턴, 없으면 그냥 position을 리턴
    @Override
    public long getItemId(int position) {

        return position;
    }
    //인자로 전달된 position 에 해당하는 cell view 를 만들어서 리턴하거나 이미 만들어진 cell view 의 내용만 만들어서 리턴해준다.
    //메소드가 어떤 동작을 할 것인지는 우리가 결정
    @Override
    public View getView(int position, /*View convertView*/ View view, ViewGroup parent) {//필요없는 셀뷰 지나간 셀뷰의 참조값이 view로 간다. null이 아니다.
        Log.d("CountryAdapter", "getView() 호출됨 position"+position);//e=error
        //1. res/layout/listview_cell.xml 문서를 전개해서 View 객체를 만든다.

        if(view == null){//null이면 새로 만들어서 내용을 return
            Log.e("CountryAdapter","view가 null 이여서 cell view 를 새로 만듭니다.");
            //레이아웃 전개자(inflater) 객체의 참조값 얻어오기
            LayoutInflater inflater=LayoutInflater.from(this.context);//context는 생성자의 첫번째 인자로 전달될 예정
            //listview_cell.xml 문서를 전개해서 새로운 View를 만든다.
            view=inflater.inflate(R.layout.listview_cell, parent, false);
        }

        //2. position 에 해당하는 CountryDto 객체의 참조값을 얻어온다.
        CountryDto dto=list.get(position);
        //3. 만든 View 객체 안에 있는 ImageView, TextView 의 참조값을 얻어온다.
        ImageView imageView=view.findViewById(R.id.imageView);
        TextView textView=view.findViewById(R.id.textView);
        //4. ImageView, TextView 에 정보를 출력한다.
        imageView.setImageResource(dto.getResId());
        textView.setText(dto.getName());
        //5. View 객체를 리턴해준다.
        return view;
    }
}
/*
Log 클래스를 사용하여 logcat에 표시되는 로그 메시지를 만들 수 있습니다.
일반적으로 다음과 같은 로그 메서드를 사용해야 하며,
우선순위가 가장 높은 것부터(또는 상세 수준이 가장 낮은 것부터) 순서대로 나열되어 있습니다.

Log.e(String, String) (오류)
Log.w(String, String) (경고)
Log.i(String, String) (정보)
Log.d(String, String) (디버그)
Log.v(String, String) (상세)

cell view는 보이는 부분에 대해 처음에 만들어지고, 그 뒤는
listview가 알아서 재활용을 한다.
E/CountryAdapter: view가 null 이여서 cell view 를 새로 만듭니다. 가 몇번 뜨는지 보기.

디테일 액티비티 만들기.
셀을 클릭하면 이동할 수 있게.

실행하면 CountryAdapter의 getView() 메소드는 왜 자동으로 실행되는건가여?
따로 코딩안해줘도 자동적으로 된다.
 */
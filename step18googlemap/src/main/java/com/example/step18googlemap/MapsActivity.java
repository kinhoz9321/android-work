package com.example.step18googlemap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);//구글맵과 LinearLayout 조합
        // id 가 map 인 프레그먼트의 참조값을 얻어와서 사용할 준비하기 (자동완성되어있던 것)
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditText input_lat=findViewById(R.id.input_lat);
        EditText input_lon=findViewById(R.id.input_lon);
        Button moveBtn=findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 문자열 .trim() 불필요한 공백 제거
                String strLat=input_lat.getText().toString().trim();
                String strLon=input_lon.getText().toString().trim();

                //LatLng location=new LatLng(lat, lon); 사용 위해 try 바깥으로 빼냄
                double lat=0;
                double lon=0;

                try {
                    //double type 숫자로 변환
                    lat=Double.parseDouble(strLat);
                    lon=Double.parseDouble(strLon);

                }catch(NumberFormatException ne){//잘못된 숫자 형식이면
                    Toast.makeText(MapsActivity.this, "숫자형식으로 입력하세요", Toast.LENGTH_SHORT).show();
                    return; //잘못된 형식으로 썼다면 메소드를 여기서 끝내야하기 때문에 return 시킨다.
                }
                //이동할 목적지 위도경도 객체
                LatLng location=new LatLng(lat, lon);
                //카메라 업데이트 객체를 얻어내서
                CameraUpdate cu= CameraUpdateFactory.newLatLngZoom(location, 18);
                //카메라 이동하기 위한 객체를 이용해서 실제로 애니메이션 효과와 함께 이동하기
                mMap.moveCamera(cu);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //GoogleMap 이 준비 완료되면 호출되는 메소드 (LatLng / MarKerOption / CameraUpdate / mMap) 4개의 객체를 이용해서 구글맵 사용.
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 카메라가 비출 위치 (위도, 경도)
        // 포항집 : 36.010460856627226, 129.33180748794118 렛트엘엔지

        // 위도 경도 객체에 위치를 표시할 위도 경도를 생성자의 인자로 전달한다.
        LatLng location = new LatLng(36.010460856627226, 129.33180748794118);
        // 지도 상에 표시할 마커의 옵션 객체
        MarkerOptions option=new MarkerOptions();
        option.position(location);
        option.title("우리 집");
        //res/drawable/korea 이미지를 로딩해서 Drawable 객체 얻어내기
        Drawable icon=getResources().getDrawable(R.drawable.korea);
        //Drawable 을 원하는 크기로 만들어서
        BitmapDescriptor bit=getMarkerIconFromDrawable(icon, 50, 50);
        //마커 옵션에 아이콘으로 지정하기
        option.icon(bit);
        //지도상에 마커 표시하기
        mMap.addMarker(option);
        //원하는 위치로 카메라 이동하기 위한 객체
        CameraUpdate cu= CameraUpdateFactory.newLatLngZoom(location, 1);
        //카메라 이동하기 위한 객체를 이용해서 실제로 애니메이션 효과와 함께 이동하기
        //mMap.animateCamera(cu);
        mMap.moveCamera(cu);//애니메이션 없이 바로 이동하기
    }

    //인자로 전달한 Drawable 객체를 이용해서 BitmapDescriptor 객체를 리턴해주는 메소드
    public BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable, int width, int height) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
/*
위도 경도 값을 받아와서 반복문 돌면서 처리를 하면
누구네집 보여주기
뭐 이런 카테고리를 만들어서 DB에 저장된 위도 경도를 불러와서
보여줄 수 있다. 아무튼.. 그렇다고 합니다. 어렵겠당...ㅠ_ㅠ

구글맵을 배운다는 것은 4개의 객체 사용법을 익히는 것.
개발자가 원하는 지도를 만들 수 있다. 멋지군용...

마커의 이미지도 커스터마이징할수있다. 넘 귀엽당...
 */
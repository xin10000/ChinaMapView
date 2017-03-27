package com.noteam.chinamapview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int hasProjectColor = 0xff40B6F0;
    private int selectdColor = 0xffEB5C47;
    float scale;
    float mapWidth = 1450;
    float mapHeight = 1200;
    int screenWidth;
     RelativeLayout rlMap;
     TextView tvData ;
     HVScaleScrollView scaleScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChinaMapView chinaMapView = (ChinaMapView) findViewById(R.id.mapView);
        rlMap = (RelativeLayout) findViewById(R.id.rl_map);
        tvData = (TextView) findViewById(R.id.tv_data);


         scaleScrollView = (HVScaleScrollView) findViewById(R.id.scrollView);
        chinaMapView.setPaintColor(ChinaMapView.Area.XinJiang, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.GanSu, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.SiChuan, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.GuiZhou, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.GuangDong, hasProjectColor, true);
        chinaMapView.setSelectdColor(selectdColor);
        chinaMapView.setOnProvinceDoubleClickListener(new ChinaMapView.OnProvinceDoubleClickListener() {
            @Override
            public void onProvinceDoubleClick() {
                scaleAndScroll();
            }
        });
        
        chinaMapView.setOnProvinceSelectedListener(new ChinaMapView.OnProvinceSelectedListener() {
            @Override
            public void onProvinceSelected(ChinaMapView.Area pArea, boolean repeatClick) {
//                Log.e(TAG, "onProvinceSelected: " + pArea.name + "  " + pArea.value);
                if (repeatClick) {
//                    scaleAndScroll();
                    return;
                }

                tvData.setText("名称：" + pArea.name + "   值：" + pArea.value);
            }
        });
        screenWidth = ScreenUtils.getScreenWidth(this);
        scale = screenWidth / mapWidth;
   

        scaleScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scaleAndScroll();

            }
        },800);

    }

    private void scaleAndScroll() {
        rlMap.setScaleX(scale);
        rlMap.setScaleY(scale);
        scaleScrollView.smoothScrollTo((rlMap.getWidth() - screenWidth) / 2, (int) (rlMap.getHeight() - (scale * mapWidth)) / 2-200);
    }
}

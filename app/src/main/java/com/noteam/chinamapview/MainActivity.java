package com.noteam.chinamapview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int hasProjectColor = 0xff40B6F0;
    private int selectdColor = 0xffEB5C47;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChinaMapView chinaMapView=(ChinaMapView)findViewById(R.id.mapView);
        
        
        HVScaleScrollView scaleScrollView=(HVScaleScrollView)findViewById(R.id.scrollView);
        chinaMapView.setPaintColor(ChinaMapView.Area.XinJiang, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.GanSu, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.SiChuan, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.GuiZhou, hasProjectColor, true);
        chinaMapView.setPaintColor(ChinaMapView.Area.GuangDong, hasProjectColor, true);
        chinaMapView.setSelectdColor(selectdColor);
        chinaMapView.setOnProvinceSelectedListener(new ChinaMapView.OnProvinceSelectedListener() {
            @Override
            public void onProvinceSelected(ChinaMapView.Area pArea,boolean doubleClick) {
                Log.e(TAG, "onProvinceSelected: " + pArea.name + "  " + pArea.value);
//                if (doubleClick) {
//                    getScaleMap();
//                    hvScrollView.smoothScrollTo((rlMap.getWidth() - screenMetrics.x) / 2, (int) (rlMap.getHeight() - (scaleMap * height)) / 2 - DisplayUtil.dip2px(getActivity(), 85));
//                    return;
//                }

//                tvProjectOverview.setText(pArea.name + projectOverview);
            }
        });

//        scaleScrollView.smoothScrollTo((rlMap.getWidth() - screenMetrics.x) / 2, (int) (rlMap.getHeight() - (scaleMap * height)) / 2 - DisplayUtil.dip2px(getActivity(), 85));
        
    }
}

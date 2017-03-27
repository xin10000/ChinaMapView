# ChinaMapView
用svg地图的路径制作中国地图，各个省份可点击，设置颜色，设置省份名大小以及位置

![Map](map.gif)

1. 在布局里引用，设定大小为原svg路径长和宽的倍数，目前是两倍:
```java
                 <com.noteam.chinamapview.ChinaMapView
                   android:id="@+id/mapView"
                   android:layout_width="1450px"
                   android:layout_height="1200px"
                   android:layout_centerInParent="true"
                  />
    
```


2. 在代码里设置:
```java
               chinaMapView.setPaintColor(ChinaMapView.Area.XinJiang, hasProjectColor, true);
               chinaMapView.setSelectdColor(selectdColor);
               chinaMapView.setOnProvinceDoubleClickListener(new ChinaMapView.OnProvinceDoubleClickListener() {
                   @Override
                   public void onProvinceDoubleClick() {

                   }
               });
               
               chinaMapView.setOnProvinceSelectedListener(new ChinaMapView.OnProvinceSelectedListener() {
                   @Override
                   public void onProvinceSelected(ChinaMapView.Area pArea, boolean repeatClick) {

                   }
               });
             
    
```

3. 为了支持手势缩放和各方向移动，加入了一个横竖都可以滑动，同时可以放大缩小的ScrollView，
用此控件包裹chinaMapView,设置合适的宽高，最终效果如上图
```java
      <com.noteam.chinamapview.HVScaleScrollView
        android:id="@+id/scrollView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center">

        <RelativeLayout
            android:id="@+id/rl_map"
            android:layout_width="3000px"
            android:layout_height="3000px"
          >

            <com.noteam.chinamapview.ChinaMapView
                android:id="@+id/mapView"
                android:layout_width="1450px"
                android:layout_height="1200px"
                android:layout_centerInParent="true"
               />
        </RelativeLayout>

    </com.noteam.chinamapview.HVScaleScrollView>
    
```

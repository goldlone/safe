package cn.goldlone.safe.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import cn.goldlone.safe.Configs;
import cn.goldlone.safe.R;

/**
 * 足迹异常监测
 * Created by CN on 2018/4/3.
 */

public class PathActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口
    //原有BDLocationListener接口暂时同步保留。具体介绍请参考后文中的说明
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;
    private Toolbar toolbar;
    private Switch aSwitch;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_path);

        sharedPreferences = getSharedPreferences(Configs.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Configs.isMonitor = sharedPreferences.getBoolean(Configs.SHARED_MONITOR, false);

        initToolbar();
        initView();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        mMapView = (TextureMapView) findViewById(R.id.mapView);
        aSwitch = (Switch) findViewById(R.id.sw_path_monitor);
        aSwitch.setChecked(Configs.isMonitor);
        if(Configs.isMonitor)
            aSwitch.setText("关闭异常轨迹监测");
        else
            aSwitch.setText("开启异常轨迹监测");


        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        initLocationOptions();

        aSwitch.setOnCheckedChangeListener(this);
    }

    /**
     * 初始化定位选项
     */
    private void initLocationOptions() {
        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        // 注册监听函数
        mLocationClient.registerLocationListener(myListener);


        LocationClientOption option = new LocationClientOption();
        // 可选，设置定位模式，默认高精度
        // LocationMode.Hight_Accuracy：高精度；
        // LocationMode. Battery_Saving：低功耗；
        // LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        // 可选，设置返回经纬度坐标类型，默认gcj02
        // gcj02：国测局坐标；
        // bd09ll：百度经纬度坐标；
        // bd09：百度墨卡托坐标；
        // 海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标
        option.setCoorType("bd09ll");

        // 可选，设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(3000);

        // 可选，设置是否使用gps，默认false
        // 使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        // 可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        // 可选，定位SDK内部是一个service，并放到了独立进程。
        // 设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        // 可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        // 可选，7.2版本新增能力
        // 如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
        option.setWifiCacheTimeOut(5*60*1000);

        // 可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);

        // mLocationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        // 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

        // mLocationClient为第二步初始化过的LocationClient对象
        // 调用LocationClient的start()方法，便可发起定位请求
        mLocationClient.start();
    }

    /**
     * 监听Switch的变化
     * @param compoundButton
     * @param isChecked
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()){
            case R.id.sw_path_monitor:
                editor.putBoolean(Configs.SHARED_MONITOR, isChecked);
                editor.commit();
                if(isChecked){
                    Toast.makeText(this, "开启异常监测", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "关闭异常监测", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 位置监听内部类
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location){
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获+取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
            float radius = location.getRadius();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = location.getLocType();

            switch (errorCode) {
                case 61:
                    Log.e("定位结果", "GPS定位结果，GPS定位成功");
                    break;
                case 66:
                    Log.e("定位结果", "离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果");
                    break;
                case 161:
                    Log.e("定位结果", "网络定位结果，网络定位成功");
                    break;
                case 67:
                    Log.e("定位失败", "请检查网络连接");
                    return;
                default:
                    Log.e("定位结果", "errorCode = "+errorCode);
                    break;
            }
            Log.e("定位结果", latitude+" : "+longitude+" -> "+radius);


            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(latitude)
                    .longitude(longitude).build();
            // 设置定位数据
            if (mBaiduMap != null) {
                mBaiduMap.setMyLocationData(locData);
                LatLng centerPoint = new LatLng(latitude, longitude);
                //定义地图状态
                MapStatus mMapStatus = new MapStatus.Builder()
                        .zoom(17)
                        .target(centerPoint)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mBaiduMap.setMapStatus(mMapStatusUpdate);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient.isStarted())
            mLocationClient.stop();
    }
}

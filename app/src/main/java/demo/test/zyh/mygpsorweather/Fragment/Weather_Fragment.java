package demo.test.zyh.mygpsorweather.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import demo.test.zyh.mygpsorweather.R;
import demo.test.zyh.mygpsorweather.Weather.ListViewAdapter;
import demo.test.zyh.mygpsorweather.Weather.MyJsonParser;
import demo.test.zyh.mygpsorweather.Weather.WeatherDate;

/**
 * 作者：zyh
 * 时间：2019/5/20 0020 08:31
 * 类名: Weather_Fragment
 * 联系方式：QQ：1360097662
 */
public class Weather_Fragment extends Fragment {

    private View view;
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    private List<WeatherDate> arrayList;
    private double latitude = 0.0;
    private double longitude = 0.0;

    private AMapLocationClient locationClientSingle = null;
    private TextView tv_longitude;
    private TextView tv_latitude;
    private TextView tv_country;
    private TextView tv_address;
    private TextView tv_province;
    private TextView tv_city;
    private TextView tv_district;
    private TextView tv_F5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        initView();
        initEvents();
        return view;
    }

    private void initView() {
        listView = view.findViewById(R.id.listview);
        tv_address = view.findViewById(R.id.tv_jiedao);
        tv_city = view.findViewById(R.id.tv_shi);
        tv_country = view.findViewById(R.id.tv_guojia);
        tv_district = view.findViewById(R.id.tv_qu);
        tv_longitude = view.findViewById(R.id.tv_jingdu);
        tv_latitude = view.findViewById(R.id.tv_weidu);
        tv_province = view.findViewById(R.id.tv_sheng);
        tv_F5 = view.findViewById(R.id.tv_F5);
        tv_F5.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_F5.getPaint().setAntiAlias(true);//抗锯齿
    }

    private void initEvents() {
        StartThread();
        startSingleLocation();
        tv_F5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartThread();
                startSingleLocation();
            }
        });
    }


    /**
     * 单次客户端的定位监听
     */
    AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (getLocationStr(location)) {
                Toast.makeText(getContext(), "定位成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 启动单次客户端定位
     */
    void startSingleLocation() {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(getContext().getApplicationContext());
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }


    //开线程获取网络json信息
    public void StartThread() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //城市需要用拼音
                    String city = "xinxiang";
                    String path = "https://api.seniverse.com/v3/weather/daily" +
                            ".json?key=nhb2i0uhbzc4rce4&location=" + city +
                            "&language=zh-Hans&unit=c" +
                            "&start=0&days=5";
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        //如果请求成功 获取网络上的数据
                        InputStream in = conn.getInputStream();
                        String str = "";
                        int len = -1;
                        byte[] buffer = new byte[1024];
                        while ((len = in.read(buffer)) > 0) {
                            //从buffer第0位取len的长度后拼接成字符串
                            str += new String(buffer, 0, len);
                        }
                        //解析json
                        arrayList = MyJsonParser.getWeather(str);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Log.d("测试", arrayList.get(0).getTime());
                                listViewAdapter = new ListViewAdapter(getContext(), arrayList);
                                listView.setAdapter(listViewAdapter);
                            }
                        });
                    } else {
                        Log.d("测试", "非200");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("测试", "错误" + e);
                }
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    public boolean getLocationStr(AMapLocation location) {
        if (null == location) {
            return false;
        }
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            tv_longitude.setText(location.getLongitude() + "°");
            tv_latitude.setText(location.getLatitude() + "°");
            tv_country.setText(location.getCountry());
            tv_address.setText(location.getAddress());
            tv_province.setText(location.getProvince());
            tv_city.setText(location.getCity());
            tv_district.setText(location.getDistrict());
            return true;

        } else {
            return false;
        }
    }
}

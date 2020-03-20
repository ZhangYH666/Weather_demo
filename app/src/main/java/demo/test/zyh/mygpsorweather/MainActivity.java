package demo.test.zyh.mygpsorweather;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import demo.test.zyh.mygpsorweather.Fragment.GPS_Fragment;
import demo.test.zyh.mygpsorweather.Fragment.Weather_Fragment;

public class MainActivity extends AppCompatActivity {

    List<String> mTablayoutList;
    List<Fragment> mFragment;
    private ViewPager vp;
    private TabLayout tabLayout;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉表头
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //沉浸状态栏
        setStatusBarFullTransparent();

        initView();
        initEvents();

    }

    /**
     * 全透状态栏
     */
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {//21表示5.0
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= 19) {//19表示4.4
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //虚拟键盘也透明
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }


    private void initEvents() {
        //给ViewPager创建适配器，将mTablayoutList和mFragment添加进ViewPager中
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mFragment.get(i);
            }

            @Override
            public int getCount() {
                return mFragment.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTablayoutList.get(position);
            }
        });
        //将ViewPager和TabLayout关联起来
        tabLayout.setupWithViewPager(vp);
    }

    //初始化
    private void initView() {
        vp = findViewById(R.id.vp);
        tabLayout = findViewById(R.id.tablayout);
        //mTablayoutList添加显示的字符串
        mTablayoutList = new ArrayList<>();
        mTablayoutList.add("天气");
        mTablayoutList.add("GPS");
        //mFragment 添加Fragment
        mFragment = new ArrayList<>();
        mFragment.add(new Weather_Fragment());
        mFragment.add(new GPS_Fragment());

    }
}

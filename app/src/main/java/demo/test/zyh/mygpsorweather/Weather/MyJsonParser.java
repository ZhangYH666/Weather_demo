package demo.test.zyh.mygpsorweather.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 作者：zyh
 * 时间：2019/5/20 0020 14:43
 * 类名: MyJsonParser
 * 联系方式：QQ：1360097662
 */
public class MyJsonParser {

    public static ArrayList<WeatherDate> getWeather(String str) throws Exception {
        ArrayList<WeatherDate> arrayList = new ArrayList<>();
        //获取json对象
        JSONObject json = new JSONObject(str);
        JSONArray results = json.getJSONArray("results");
        //获取城市
        JSONObject location = (JSONObject) results.get(0);
        String city = location.getJSONObject("location").getString("name");
        //获取daily 数组
        JSONArray daily = location.getJSONArray("daily");
        for (int i = 0; i < daily.length(); i++) {
            //初始化WeatherDate对象
            WeatherDate weatherDate = new WeatherDate();
            //添加城市
            weatherDate.setCity(city);
            JSONObject day = (JSONObject) daily.get(i);
            //获取最高温度
            String high = day.getString("high");
            weatherDate.setHigh(high);
            //获取最低温度
            String low = day.getString("low");
            weatherDate.setLow(low);
            //获取文字描述(白天)
            String text_day = day.getString("text_day");
            weatherDate.setWeather(text_day);
            //获取风力
            String wind_scale = day.getString("wind_scale");
            weatherDate.setFeng(wind_scale);
            //获取 天气的时间
            String date = day.getString("date");
            weatherDate.setTime(date);
            arrayList.add(weatherDate);
        }
        return arrayList;
    }

}

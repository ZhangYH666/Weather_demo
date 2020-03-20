package demo.test.zyh.mygpsorweather.Weather;

/**
 * 作者：zyh
 * 时间：2019/5/20 0020 13:57
 * 类名: WeatherDate
 * 联系方式：QQ：1360097662
 */
public class WeatherDate {
    private String Weather;
    private String feng;
    private String high;
    private String low;
    private String city;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getFeng() {
        return feng;
    }

    public void setFeng(String feng) {
        this.feng = feng;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }
}

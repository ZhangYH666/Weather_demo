package demo.test.zyh.mygpsorweather.Weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import demo.test.zyh.mygpsorweather.R;

/**
 * 作者：zyh
 * 时间：2019/5/20 0020 13:57
 * 类名: ListViewAdapter
 * 联系方式：QQ：1360097662
 */
public class ListViewAdapter extends BaseAdapter {

    private List<WeatherDate> list;
    private Context context;

    public ListViewAdapter(Context context, List<WeatherDate> list) {
        this.context = context;
        this.list = list;
    }

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            viewHolder.tv_showWeather = convertView.findViewById(R.id.tv_showWeather);
            viewHolder.tv_time = convertView.findViewById(R.id.tv_showtime);
            viewHolder.tv_wendu = convertView.findViewById(R.id.tv_showWendu);
            viewHolder.tv_wind = convertView.findViewById(R.id.tv_showfeng);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_wind.setText(list.get(position).getFeng() + "级");
        viewHolder.tv_wendu.setText("高:" + list.get(position).getHigh() + "°C" + "  低:" + list.get
                (position).getLow() + "°C");
        viewHolder.tv_time.setText(Day(position));
        viewHolder.tv_showWeather.setText(list.get(position).getWeather());
        Log.d("测试  position", position + "");
        return convertView;
    }

    private String Day(int position) {
        String day = "null";
        switch (position) {
            case 0:
                day = "今天";
                break;
            case 1:
                day = "明天";
                break;
            case 2:
                day = "后天";
                break;

        }
        return day;
    }

    class ViewHolder {
        TextView tv_showWeather;
        TextView tv_time;
        TextView tv_wendu;
        TextView tv_wind;
    }
}

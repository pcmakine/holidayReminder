package eagleapp.com.holidaynotify.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import eagleapp.com.holidaynotify.R;


/**
 * Created by Pete on 15.11.2015.
 */
public class DayListAdapter<Day> extends BaseAdapter {
    private SortedSet<Day> days;

    public DayListAdapter(SortedSet<Day> days, Context context){
        this.days = days;
    }


  /*  public void addAll(Collection<Day> collection){
        if(days != null){
            days.addAll(collection);
        }
    }*/

    public void addAll(Collection<Day> collection){
        days.addAll(collection);
    }


    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Day getItem(int position) {
        return new ArrayList<>(days).get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) HolidayNotify.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        Day day = getItem(position);
        TextView view = (TextView) convertView.findViewById(R.id.day_name);
        view.setText(day.toString());

        return convertView;
    }


}

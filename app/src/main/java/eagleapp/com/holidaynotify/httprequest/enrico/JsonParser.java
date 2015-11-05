package eagleapp.com.holidaynotify.httprequest.enrico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import eagleapp.com.holidaynotify.dao.Day;

/**
 * Created by Pete on 4.11.2015.
 */
public class JsonParser {
    public static List<Day> parseJson(JSONArray jsonArray){
        List<Day> days = new ArrayList<>();

        if( jsonArray.length() > 0){
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    JSONObject jsonDate = jsonObject.getJSONObject("date");
                    int day = jsonDate.getInt("day");
                    int month = jsonDate.getInt("month");
                    int year = jsonDate.getInt("year");
                    String localName = jsonObject.getString("localName");
                    String englishName = jsonObject.getString("englishName");
                    Calendar c = Calendar.getInstance();
                    c.set(year, month-1, day);
                    days.add(new Day(c.getTime(), localName, englishName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
        return days;
    }
    public static List<Day> parseJson(String json){
        try {
            JSONArray array = new JSONArray(json);
            return parseJson(array);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}

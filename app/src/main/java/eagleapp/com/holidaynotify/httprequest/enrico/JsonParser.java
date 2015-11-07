package eagleapp.com.holidaynotify.httprequest.enrico;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eagleapp.com.holidaynotify.domain.Country;
import eagleapp.com.holidaynotify.domain.Day;

/**
 * Created by Pete on 4.11.2015.
 */
public class JsonParser {
    public static List<Day> parseJson(JSONArray jsonArray){
        List<Day> days = new ArrayList<>();
        if( jsonArray != null && jsonArray.length() > 0){
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Date date = jsonDateToDate(jsonObject.getJSONObject("date"));
                    String localName = jsonObject.getString("localName");
                    String englishName = jsonObject.getString("englishName");
                    days.add(new Day(date, localName, englishName));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return days;
    }

    public static List<Country> parseCountries(JSONArray jsonArray){
        List<Country> countries = new ArrayList<>();
        if( jsonArray != null && jsonArray.length() > 0){
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String fullName = jsonObject.getString("fullName");
                    String countryCode = jsonObject.getString("countryCode");
                    Date fromDate = jsonDateToDate(jsonObject.getJSONObject("fromDate"));
                    Date toDate = jsonDateToDate(jsonObject.getJSONObject("toDate"));

                    JSONArray regionArr = jsonObject.getJSONArray("regions");
                    List<String> regions = new ArrayList<>();
                    if( regionArr != null ){
                        for (int j = 0; j < regionArr.length(); j++){
                            regions.add( regionArr.get(j).toString() );
                        }
                    }
                    Country country = new Country(fullName, countryCode, fromDate, toDate, regions);
                    countries.add(country);
                    System.out.println("regions: " + country.getRegions());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return countries;
    }

    private static Date jsonDateToDate(JSONObject jsonDate){
        try {
            Calendar c = Calendar.getInstance();
            c.set(jsonDate.getInt("year"), jsonDate.getInt("month")-1, jsonDate.getInt("day"));
            return c.getTime();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Country> parseCountries(String json){
        JSONArray arr = jsonStringToJsonArray(json);
        if( arr == null){
            return null;
        }
        return parseCountries(arr);
    }
    public static List<Day> parseJson(String json){
        JSONArray arr = jsonStringToJsonArray(json);
        if( arr == null){
            return null;
        }
        return parseJson(arr);
    }

    private static JSONArray jsonStringToJsonArray(String json){
        try {
            JSONArray array = new JSONArray(json);
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


}

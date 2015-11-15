package eagleapp.com.holidaynotify.httprequest.enrico;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pete on 4.11.2015.
 */
public class EnricoParams {
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static class Actions {
        public static final String MONTH_HOLIDAYS = "getPublicHolidaysForMonth";
        public static final String YEAR_HOLIDAYS = "getPublicHolidaysForYear";
        public static final String DATE_RANGE_HOLIDAYS = "getPublicHolidaysForDateRange";
        public static final String IS_PUBLIC_HOLIDAY = "isPublicHoliday";
        public static final String SUPPORTED_COUNTRIES_LIST = "getSupportedCountries";
    }

    public static class Keys {
        public static final String ACTION = "action";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
        public static final String COUNTRY = "country";
        public static final String REGION = "region";
        public static final String FROM_DATE = "fromDate";
        public static final String TO_DATE = "toDate";
    }

    public static String findValue(String key, List<String> params){
        for (int i = 0; i < params.size(); i++){
            if(key.equals(params.get(i))){
                return params.get(i+1);
            }
        }
        return null;
    }

    public static Map buildParamsMapForYear(int year, String countryCode, String region){
        Map<String, String> params = new HashMap<>();
        params.put(EnricoParams.Keys.ACTION, EnricoParams.Actions.MONTH_HOLIDAYS);
        params.put(EnricoParams.Keys.YEAR, String.valueOf(year));
        params.put(EnricoParams.Keys.COUNTRY, countryCode);
        params.put(EnricoParams.Keys.REGION, region);
        return params;
    }
    public static String buildParamsString(Map<String, String> params){
        List<String> paramsList = new ArrayList<>();
        if(params != null){
            Iterator entries = params.entrySet().iterator();
            while(entries.hasNext()){
                Map.Entry<String, String> current = (Map.Entry<String, String>) entries.next();
                String key = current.getKey();
                String value = current.getValue();
                paramsList.add(key + "=" + value);
            }
        }
        System.out.println("paramsList: " + paramsList.toString());
        ArrayList<CharSequence> charTokens = new ArrayList<CharSequence>();
        return TextUtils.join("&", paramsList.toArray());
    }
}

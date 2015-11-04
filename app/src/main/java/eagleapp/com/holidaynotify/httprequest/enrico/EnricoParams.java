package eagleapp.com.holidaynotify.httprequest.enrico;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pete on 4.11.2015.
 */
public class EnricoParams {
    public String testField = "hei";
    public static class Actions {
        public static final String KEY = "action";
        public static class Values {
            public static final String MONTH_HOLIDAYS = "getPublicHolidaysForMonth";
        }
    }

    public static class Keys {
        public static final String ACTION = "action";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
        public static final String COUNTRY = "country";
        public static final String REGION = "region";
    }

    /**
     * Builds a map from the parameter list
     * @param params Must be in format key, value, key, value, etc.
     * @return returns an ordered hashmap with the parameters
     */
    public static LinkedHashMap<String, String> buildParamsMap(String... params){
        List<String> paramList = Arrays.asList(params);
        LinkedHashMap<String, String> ret = new LinkedHashMap<>();
        String actionValue = findValue(EnricoParams.Actions.KEY, paramList);
        ret.put(EnricoParams.Actions.KEY, actionValue);
        ret.put(EnricoParams.Keys.MONTH, findValue(EnricoParams.Keys.MONTH, paramList));
        ret.put(EnricoParams.Keys.YEAR, findValue(EnricoParams.Keys.YEAR, paramList));
        ret.put(EnricoParams.Keys.COUNTRY, findValue(EnricoParams.Keys.COUNTRY, paramList));
        ret.put(EnricoParams.Keys.REGION, findValue(EnricoParams.Keys.REGION, paramList));
        return ret;
    }

    public static String findValue(String key, List<String> params){
        for (int i = 0; i < params.size(); i++){
            if(key.equals(params.get(i))){
                return params.get(i+1);
            }
        }
        return null;
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

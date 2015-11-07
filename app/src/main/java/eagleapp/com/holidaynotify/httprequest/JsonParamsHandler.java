package eagleapp.com.holidaynotify.httprequest;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Pete on 7.11.2015.
 */
public class JsonParamsHandler {

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

    public static List<String> findTokensByKey(String baseUrl, String url, String separator, String key){
        url = url.substring(baseUrl.length());
        List<String> foundTokens = new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(url, separator);

        while(tokens.hasMoreTokens()){
            String token = tokens.nextToken();
            String[] parts = token.split("=");
            if( parts.length > 1 && parts[0].equals(key) ){
                foundTokens.add(parts[1]);
            }
        }
        return foundTokens;
    }
}

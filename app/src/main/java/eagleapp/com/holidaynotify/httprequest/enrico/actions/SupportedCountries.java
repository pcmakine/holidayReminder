package eagleapp.com.holidaynotify.httprequest.enrico.actions;

import java.util.HashMap;
import java.util.Map;

import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;

/**
 * Created by Pete on 7.11.2015.
 */
public class SupportedCountries extends EnricoAction{

    public SupportedCountries(){
        super();
        this.actionStr = EnricoParams.Actions.SUPPORTED_COUNTRIES_LIST;
    }

    @Override
    public Map<String, String> buildParamsMap() {
        Map<String, String> params = new HashMap<>();
        params.put(EnricoParams.Keys.ACTION, this.actionStr);
        return params;
    }
}

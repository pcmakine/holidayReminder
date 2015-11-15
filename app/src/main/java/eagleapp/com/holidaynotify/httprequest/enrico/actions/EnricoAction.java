package eagleapp.com.holidaynotify.httprequest.enrico.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eagleapp.com.holidaynotify.httprequest.enrico.EnricoParams;

/**
 * Created by Pete on 5.11.2015.
 */
public abstract class EnricoAction {
    protected String actionStr;
    protected List<String> requiredKeys;
    protected String countryCode;

    public EnricoAction(){
        this.requiredKeys = new ArrayList<>();
        requiredKeys.add(EnricoParams.Keys.ACTION);
    }

    public List validateParams(Map params){
        List errors = new ArrayList();
        for(String key: requiredKeys){
            if( !params.keySet().contains(key) ){
                errors.add("Key " + key + " missing from params");
            }
        }
        return errors;
    }
    public abstract Map<String, String> buildParamsMap();

    protected void addToParams(Map<String, String> params, String key, String value){
        if(value != null){
            params.put(key, value);
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getActionStr() {
        return actionStr;
    }

    public void setActionStr(String actionStr) {
        this.actionStr = actionStr;
    }

    public List<String> getRequiredKeys() {
        return requiredKeys;
    }

    public void setRequiredKeys(List<String> requiredKeys) {
        this.requiredKeys = requiredKeys;
    }
}

package eagleapp.com.holidaynotify.activity.preferences;

import android.content.Context;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eagleapp.com.holidaynotify.db.dao.CountryDao;
import eagleapp.com.holidaynotify.domain.Country;

/**
 * Created by Pete on 8.11.2015.
 */
public class DynamicListPreference extends ListPreference {
    public static final String TAG = DynamicListPreference.class.getName();
   /* public DynamicListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DynamicListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }*/

    public DynamicListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        List<Country> countries = CountryDao.getInstance().loadAll(context);
        List<String> entries = new ArrayList<>();
        List<String> entryValues = new ArrayList<>();

        for(Country country: countries){
            if(country != null && country.getCountryCode() != null){    //This should never happen but in case it does don't show the country as an option
                entries.add(country.toString());
                entryValues.add(country.getCountryCode());
            }else{
                Log.e(TAG, "country list has a country that is null or has a country code null");
            }
        }
        setEntries(entries.toArray(new CharSequence[entries.size()]));
        setEntryValues(entryValues.toArray(new CharSequence[entryValues.size()]));
        //setValueIndex(1);
    }

    public DynamicListPreference(Context context) {
        this(context, null);
    }
}

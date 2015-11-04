package eagleapp.com.holidaynotify.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pete on 4.11.2015.
 */
public class Day {
    private Date date;
    private String localName;
    private String englishName;

    public Day(Date date, String localName, String englishName){
        this.date = date;
        this.localName = localName;
        this.englishName = englishName;
    }

    public void setDate(Date date){
        this.date = date;
    }
    public void setLocalName(String localName){
        this.localName = localName;
    }
    public void setEnglishName(String englishName){
        this.englishName = englishName;
    }
    public Date getDate(){
        return this.date;
    }
    public String getLocalName(){
        return this.localName;
    }
    public String getEnglishName(){
        return this.englishName;
    }

    @Override
    public String toString(){
        String ret = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        ret = dateStr + ": " + englishName + " (" + localName + ")" ;
        return ret;
    }
}

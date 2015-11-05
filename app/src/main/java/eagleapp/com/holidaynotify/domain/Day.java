package eagleapp.com.holidaynotify.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pete on 4.11.2015.
 */
public class Day implements Comparable{
    private Date date;
    private String localName;
    private String englishName;
    private String notes;

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
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString(){
        String ret = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        ret = dateStr + ": " + englishName + " (" + localName + ")" ;
        return ret;
    }

    @Override
    public int compareTo(Object another) {
        Day day = (Day) another;
        if( this.date.before(day.getDate()) ){
            return -1;
        }else if( this.date.after(day.getDate()) ){
            return 1;
        }
        return 0;
    }
}

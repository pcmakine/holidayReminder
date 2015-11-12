package eagleapp.com.holidaynotify.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pete on 4.11.2015.
 */
public class Day implements Comparable{
    private Long id;
    private Date date;
    private String localName;
    private String englishName;
    private String notes;
    private Region region;

    public Day(Date date, String localName, String englishName, String notes){
        this(null, date, localName, englishName, notes, null);
    }

    public Day(Long id, Date date, String localName, String englishName, String notes, Region region){
        this.id = id;
        this.date = date;
        this.localName = localName;
        this.englishName = englishName;
        this.notes = notes;
        this.region = region;
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
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Region getRegion() {
        return region;
    }
    public void setRegion(Region region) {
        this.region = region;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        String ret = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = df.format(date);
        ret = dateStr + ": " + localName + " (" + englishName + ")" ;
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

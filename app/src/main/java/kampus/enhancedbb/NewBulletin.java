package kampus.enhancedbb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Esprit on 23.12.2015.
 */
public class NewBulletin {
    public String subject;
    public String text;
    public String startDate;
    public String endDate;
    public long[] profIDs;
    public long[] subdivIDs;
    public long userID;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long[] getProfIDs() {
        return profIDs;
    }

    public void setProfIDs(long[] profIDs) {
        this.profIDs = profIDs;
    }

    public long[] getSubdivIDs() {
        return subdivIDs;
    }

    public void setSubdivIDs(long[] subdivIDs) {
        this.subdivIDs = subdivIDs;
    }
}

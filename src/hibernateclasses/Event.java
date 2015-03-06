package hibernateclasses;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by Home on 02.03.15.
 */
@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue
    protected Long id;

    @Column(name = "login")
    protected String login;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    protected Date date;
    @Column(name = "time")
        protected String time;
    @Column(name = "text")
    protected String text;
    @Column(name = "remember")
    protected Boolean remember;
    @Column(name = "importance")
    Integer importance;

    protected Boolean synchr;


    protected Event() {}

    public Event(String login, Date date, String time, String text, Boolean remember, Integer importance) {
        this.login = login;
        this.date = date;
        this.time = time;
        this.text = text;
        this.remember = remember;
        this.importance = importance;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getRemember() {
        return remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }

    public Boolean getSynchr() {
        return synchr;
    }

    public void setSynchr(Boolean synchr) {
        this.synchr = synchr;
    }

    public Long getUserId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.id = userId;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

package application.modele;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;

    private boolean login;

    @JsonInclude
    @Transient
    private boolean isCurrentUser;

    public HistoryEntity(){}

    public HistoryEntity(String username, Date timeStamp, boolean login) {
        this.username = username;
        this.timeStamp = timeStamp;
        this.login = login;
        this.isCurrentUser = false;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public boolean isLogin() {
        return login;
    }

    public boolean isCurrentUser() {
        return isCurrentUser;
    }

    public void setCurrentUser(boolean currentUse) {
        isCurrentUser = currentUse;
    }
}

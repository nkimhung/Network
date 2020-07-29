package network.data.models;

import javax.persistence.*;

@Entity
@Table(name = "relationship")
public class Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userOneId;
    private int userTwoId;
    private int actionUserId;
    private Boolean isRead;


    @Transient
    private UserInfo userInfo;

    public Relationship() {
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserOneId(){
        return userOneId;
    }

    public void setUserOneId(int userOneId) {
        this.userOneId = userOneId;
    }

    public int getUserTwoId() {
        return userTwoId;
    }

    public void setUserTwoId(int userTwoId) {
        this.userTwoId = userTwoId;
    }

    public int getActionUserId() {
        return actionUserId;
    }

    public void setActionUserId(int actionUserId) {
        this.actionUserId = actionUserId;
    }
}

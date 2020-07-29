package network.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int post;
    private int user;
    private String content;
    private LocalDateTime timeCreated;
    private LocalDateTime timeUpdated;
    @Transient
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public void setTimeUpdated(LocalDateTime timeUpdated) {
        this.timeUpdated = timeUpdated;
    }
}

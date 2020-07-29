package network.data.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int user_id;
    private String cation;
    private String type;
    private String post_url;
    private LocalDateTime time_created;
    private LocalDateTime time_updated;
    @Transient
    private List<Like> likes;
    @Transient
    private  List<Comment> comments;
    @Transient
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCation() {
        return cation;
    }

    public void setCation(String cation) {
        this.cation = cation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public LocalDateTime getTime_created() {
        return time_created;
    }

    public void setTime_created(LocalDateTime time_created) {
        this.time_created = time_created;
    }

    public LocalDateTime getTime_updated() {
        return time_updated;
    }

    public void setTime_updated(LocalDateTime time_updated) {
        this.time_updated = time_updated;
    }
}

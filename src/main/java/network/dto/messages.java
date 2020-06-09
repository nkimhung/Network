package network.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class messages {
    private int _id;
    private String text;
    private LocalDateTime createdAt;
    private user user;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public user getUser() {
        return user;
    }

    public void setUser( user user) {
        this.user = user;
    }
}

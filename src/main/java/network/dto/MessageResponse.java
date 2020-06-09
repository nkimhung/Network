package network.dto;

import network.data.models.Message;

import java.util.List;

public class MessageResponse {
    List<messages> ListMessageFrom ;
    List<messages> ListMessageTo ;

    public List<messages> getListMessageFrom() {
        return ListMessageFrom;
    }

    public void setListMessageFrom(List<messages> listMessageFrom) {
        ListMessageFrom = listMessageFrom;
    }

    public List<messages> getListMessageTo() {
        return ListMessageTo;
    }

    public void setListMessageTo(List<messages> listMessageTo) {
        ListMessageTo = listMessageTo;
    }
}

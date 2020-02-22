package com.example.newapp.ui.main;



public class NoticeItem{

    private String title;
    private String body;
    private String imageUrl;

    public NoticeItem(String title, String body, String imageUrl) {
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

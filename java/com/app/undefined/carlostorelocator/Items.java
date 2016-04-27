package com.app.undefined.carlostorelocator;

/**
 * Created by Gabriele Nardi on 26/04/2016.
 */
public class Items {

    private String title;
    private String description;

    public Items(String title, String description){
        super();
        this.title = title;
        this.description = description;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

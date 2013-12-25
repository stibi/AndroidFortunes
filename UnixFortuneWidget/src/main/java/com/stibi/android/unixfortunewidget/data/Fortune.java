package com.stibi.android.unixfortunewidget.data;

public class Fortune {
    private final long id;
    private final String type;
    private final String text;

    public Fortune(long id, String type, String text) {
        this.id = id;
        this.type = type;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}

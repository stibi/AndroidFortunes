package com.stibi.android.unixfortunewidget.data;

/**
 * TODO taky by to chtelo nejaky lepsi nazev - ha, idea je tu, co treba kolekce?
 */

public class FortuneCollection {

    // TODO popsat na co je id a jak se vytvori
    private final String id;
    private final String name;
    private final String language;

    public FortuneCollection(String name, String language) {
        this.id = setupIdForCollection();
        this.name = name;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    private String setupIdForCollection() {
        // TODO - odstranit mezery a special znaky ze jmena
        String fortuneCollectionId = "TODO" + name;
        return fortuneCollectionId;
    }
}

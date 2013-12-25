package com.stibi.android.unixfortunewidget.api;

import com.stibi.android.unixfortunewidget.data.Fortune;
import com.stibi.android.unixfortunewidget.data.FortuneCollection;

/**
 * TODO tohle je ale hodne odporny nazev pro tridu :( Jeste nejaky *Manager mi tu chybi
 */

public interface IFortunesProvider {

    public Fortune getRandomFortune();

    public Fortune getRandomFortuneFromCollection(FortuneCollection fortuneCollection);

    public int allFortunesCount();

    public int allFortunesInCollectionCount(FortuneCollection fortuneCollection);
}

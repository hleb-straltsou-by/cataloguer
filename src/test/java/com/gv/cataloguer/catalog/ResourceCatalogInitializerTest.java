package com.gv.cataloguer.catalog;

import org.junit.Assert;
import org.junit.Test;

public class ResourceCatalogInitializerTest {
    @Test
    public void run() throws Exception {
        final int waitingTime = 2000;   // in millis
        Thread initializer = new Thread(new ResourceCatalogInitializer());
        initializer.start();
        Thread.sleep(waitingTime);
        Assert.assertNotNull(ResourceCatalog.getInstance().getCategory("music"));
        Assert.assertNotNull(ResourceCatalog.getInstance().getCategory("movies"));
        Assert.assertNotNull(ResourceCatalog.getInstance().getCategory("books"));
        Assert.assertNotNull(ResourceCatalog.getInstance().getCategory("documents"));
    }
}
package com.gv.cataloguer.catalog;

import com.gv.cataloguer.models.Reference;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class ResourceCatalogTest {

    @Test
    public void searchReferences() throws Exception {
        ResourceCatalog.getInstance().initializeCalatog();
        String category = "music";
        String pattern = "And";
        Thread.sleep(4000);
        List<Reference> foundReferences = ResourceCatalog.getInstance().searchReferences(category, pattern);
        Assert.assertNotNull(foundReferences);
        Assert.assertEquals(2, foundReferences.size());
        Assert.assertEquals("City_And_Colour_–_Comin_Home.mp3", foundReferences.get(0).getName());
        Assert.assertEquals("City And Colour – Comin' Home.mp3", foundReferences.get(1).getName());
    }

}
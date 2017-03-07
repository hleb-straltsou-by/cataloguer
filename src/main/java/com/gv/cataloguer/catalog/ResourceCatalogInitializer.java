package com.gv.cataloguer.catalog;

import com.gv.cataloguer.catalog.dao.ResourceDaoDatabase;

/**
 * Object of this class is represent thread that In parallel updates resource in local
 * catalog from remote catalog and set lists of resources references
 */
public class ResourceCatalogInitializer implements Runnable{

    /**
     * udates resource in local catalog from remote catalog and set lists of
     * resources references
     */
    public void run() {
        ResourceCatalog catalog = ResourceCatalog.getInstance();
        catalog.setMusic(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("music"));
        catalog.setMovies(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("movies"));
        catalog.setBooks(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("books"));
        catalog.setDocuments(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("documents"));
    }
}

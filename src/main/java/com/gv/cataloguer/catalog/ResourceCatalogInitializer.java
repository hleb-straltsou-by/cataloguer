package com.gv.cataloguer.catalog;

import com.gv.cataloguer.catalog.dao.ResourceDaoDatabase;

public class ResourceCatalogInitializer implements Runnable{
    public void run() {
        ResourceCatalog catalog = ResourceCatalog.getInstance();
        catalog.setMusic(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("music"));
        catalog.setMovies(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("movies"));
        catalog.setBooks(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("books"));
        catalog.setDocuments(ResourceDaoDatabase.getInstance().getAllResourcesFromCategory("documents"));
    }
}

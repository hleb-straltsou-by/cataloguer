package com.gv.cataloguer.catalog;

import com.gv.cataloguer.catalog.dao.ResourceDaoDatabase;
import com.gv.cataloguer.models.Reference;
import java.io.File;
import java.util.Collections;
import java.util.List;

public class ResourceCatalog {

    private static ResourceCatalog resourceCatalog = new ResourceCatalog();

    private List<Reference> music;
    private List<Reference> movies;
    private List<Reference> books;
    private List<Reference> documents;

    private ResourceCatalog(){}

    public static ResourceCatalog getInstance(){
        return resourceCatalog;
    }

    public void addResourceToCatalog(String category, File file){
        ResourceDaoDatabase.getInstance().addResourceToCategory(category, file);
    }

    public void updateResources(){

    }

    public void initializeCalatog(){
        Thread initializer = new Thread(new ResourceCatalogInitializer());
        initializer.start();
    }

    public List<Reference> getCategory(String category){
        switch (category){
            case "music":
                return music;
            case "movies":
                return movies;
            case "books":
                return books;
            case "documents":
                return documents;
            default:
                return Collections.EMPTY_LIST;
        }
    }

    public void setMusic(List<Reference> music) {
        this.music = music;
    }

    public void setMovies(List<Reference> movies) {
        this.movies = movies;
    }

    public void setBooks(List<Reference> books) {
        this.books = books;
    }

    public void setDocuments(List<Reference> documents) {
        this.documents = documents;
    }
}

package com.gv.cataloguer.catalog;

import com.gv.cataloguer.catalog.dao.ResourceDaoDatabase;
import com.gv.cataloguer.models.Reference;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class represents storage of references on resources from local catalog,
 * provides CRUD methods to manipulate data from local catalog and add updates
 * to remote data storage. Class is realized according Singleton pattern
 */
public class ResourceCatalog {

    /** single instance of class */
    private static ResourceCatalog resourceCatalog = new ResourceCatalog();

    /** list of references on music resources from local catalog */
    private List<Reference> music;

    /** list of references on movies resources from local catalog */
    private List<Reference> movies;

    /** list of references on books resources from local catalog */
    private List<Reference> books;

    /** list of references on documents resources from local catalog */
    private List<Reference> documents;

    /**
     * private constructor of class for implementing singleton pattern
     */
    private ResourceCatalog(){}

    /**
     * Returns single instance of class
     * @return UserDaoSingleton instance
     */
    public static ResourceCatalog getInstance(){
        return resourceCatalog;
    }

    /**
     * insert new resource to remote and local catalogs according category
     * @param category - name of category of files
     * @param file - resource which will be added to remote and local catalogs
     */
    public void addResourceToCatalog(String category, File file){
        ResourceDaoDatabase.getInstance().addResourceToCategory(category, file);
    }

    /**
     * searches references of resources according string pattern in corresponding
     * category
     * @param category - name of category of files
     * @param stringPattern - string pattern which will be used in searching references
     * @return list of matching references
     */
    public List<Reference> searchReferences(String category, String stringPattern){
        LinkedList<Reference> foundReferences = new LinkedList<>();
        List<Reference> categoryReferences = getCategory(category);
        Pattern regExpPattern = Pattern.compile(stringPattern);
        Matcher matcher;
        for(Reference ref : categoryReferences){
            matcher = regExpPattern.matcher(ref.getName());
            if(matcher.find()){
                foundReferences.addLast(ref);
            }
        }
        return foundReferences;
    }

    /**
     * creates thread object @see ResourceCatalogInitializer for updating local catalog and
     * references from remote data storage
     */
    public void updateCatalog(){
        Thread initializer = new Thread(new ResourceCatalogInitializer());
        initializer.start();
    }

    /**
     * returns list of references on resources from local catalog according category
     * @param category - name of category of files
     * @return eturns list of references @see Reference
     */
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

    /**
     * deletes resource from remote, local catalogs and from list of references
     * according reference object and category
     * @param category - name of category of files
     * @param ref - reference on deleting resource
     */
    public void deleteResourceFromCatalog(String category, Reference ref){
        ResourceDaoDatabase.getInstance().deleteResourceFromCategory(category, ref.getId());
        getCategory(category).remove(ref);
    }

    /**
     * sets new list of music references
     * @param music - new list of music references
     */
    public void setMusic(List<Reference> music) {
        this.music = music;
    }

    /**
     * sets new list of movies references
     * @param movies - new list of movies references
     */
    public void setMovies(List<Reference> movies) {
        this.movies = movies;
    }

    /**
     * sets new list of books references
     * @param books - new list of books references
     */
    public void setBooks(List<Reference> books) {
        this.books = books;
    }

    /**
     * sets new list of documents references
     * @param documents - new list of documents references
     */
    public void setDocuments(List<Reference> documents) {
        this.documents = documents;
    }
}

package com.gv.cataloguer.models;

import java.io.File;
import java.util.Date;

/**
 * realizes reference for file resources
 */
public class Reference {

    /** property - id of reference */
    private int id = 0;

    /** property - name of reference */
    private String name = "";

    /** property - size of resource */
    private long size = 0;

    /** property - last modification of resource in local catalog */
    private String lastModified;

    /** property - path to resource */
    private String pathToResource = "";

    public Reference(String name){
        this.name = name;
    }

    public Reference(int id, String name, long size, Date lastModified, String pathToResource){
        this.id = id;
        this.name = name;
        this.size = size;
        this.lastModified = lastModified.toString();
        this.pathToResource = pathToResource;
    }

    public File getResource(){
        return new File(pathToResource);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
}

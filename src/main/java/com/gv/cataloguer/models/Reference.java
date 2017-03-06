package com.gv.cataloguer.models;

import java.io.File;
import java.util.Date;

public class Reference {

    private int id = 0;

    private String name = "";

    private long size = 0;

    private Date lastModified = null;

    private String pathToResource = "";

    public Reference(String name){
        this.name = name;
    }

    public Reference(int id, String name, long size, Date lastModified, String pathToResource){
        this.id = id;
        this.name = name;
        this.size = size;
        this.lastModified = lastModified;
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

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getPathToResource() {
        return pathToResource;
    }

    public void setPathToResource(String pathToResource) {
        this.pathToResource = pathToResource;
    }
}

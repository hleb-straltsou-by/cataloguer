package com.gv.cataloguer.catalog.dao;

import com.gv.cataloguer.models.Reference;
import java.io.File;
import java.util.List;

/**
 * This interface provides methods for accessing remote data storage with
 * information about files
 */
public interface ResourceDao {

    /**
     * retrieves all files from remote data storage and saves them to local catalog according
     * category of files
     * @param category - name of category of files
     * @return  list of references on files in local catalog
     */
    List<Reference> getAllResourcesFromCategory(final String category);

    /**
     * retrieves file from remote data storage and saves it to local catalog according
     * category of files and id of file, that stored in remote data storage
     * @param category - name of category of files
     * @param id - unique id of file in remote data storage
     * @return reference on file in local catalog
     */
    Reference getResourceFromCategory(final String category, final int id);

    /**
     * inserts file to remote data storage and also saves it to local catalog
     * according category
     * @param category - name of category of files
     * @param file - resource that will be added to remote data storage
     */
    void addResourceToCategory(final String category, File file);

    /**
     * deletes resource according id from remote catalog
     * @param category - name of category of files
     * @param id unique id of file in remote data storage
     */
    void deleteResourceFromCategory(final String category, int id);
}

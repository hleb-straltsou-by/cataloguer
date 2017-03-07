package com.gv.cataloguer.catalog.dao;

import com.gv.cataloguer.models.Reference;
import java.io.File;
import java.util.List;

public interface ResourceDao {

    List<Reference> getAllResourcesFromCategory(final String category);

    Reference getResourceFromCategory(final String category, final int id);

    void addResourceToCategory(final String category, File file);

    List<Integer> updateCategory(final String category);

    void deleteResourceFromCategory(String category, int id);
}

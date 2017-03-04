package com.gv.cataloguer.catalog.dao;

import com.gv.cataloguer.models.Reference;
import org.junit.Test;
import java.io.File;
import java.util.List;

public class ResourceDaoDatabaseTest {

    @Test
    public void getResourceFromCategory() throws Exception {
        String category = "music";
        int id = 2;
        ResourceDao resourceDao = ResourceDaoDatabase.getInstance();
        Reference ref = resourceDao.getResourceFromCategory(category, id);
        System.out.println(ref.getId());
        System.out.println(ref.getPathToResource());
    }

    @Test
    public void getAllResourcesFromCategory() throws Exception {
        String category = "music";
        ResourceDao resourceDao = ResourceDaoDatabase.getInstance();
        List<Reference> references = resourceDao.getAllResourcesFromCategory(category);
        for(Reference ref : references){
            System.out.println(ref.getId());
            System.out.println(ref.getPathToResource());
        }
    }

    @Test
    public void addResourceToCategory() throws Exception {
        String category = "music";
        File file = new File("G:\\Archive\\music\\City_And_Colour_–_Comin_Home.mp3");
        ResourceDao resourceDao = ResourceDaoDatabase.getInstance();
        resourceDao.addResourceToCategory(category, file);
        file = new File("G:\\Archive\\music\\City And Colour – Comin' Home.mp3");
        resourceDao.addResourceToCategory(category, file);

        category = "movies";
        file = new File("G:\\Archive\\favorites\\forOurFirstYear.avi");
        resourceDao.addResourceToCategory(category, file);

        category = "books";
        file = new File("G:\\Archive\\Literature\\Java materials\\JAVA_Methods_Programming_v2_march2015.pdf");
        resourceDao.addResourceToCategory(category, file);

        category = "documents";
        file = new File("G:\\Archive\\Прочее\\Характеристика\\Streltsov_Gleb_characteristic_2_course.doc");
        resourceDao.addResourceToCategory(category, file);
    }

}
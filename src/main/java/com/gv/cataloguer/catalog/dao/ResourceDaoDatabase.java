package com.gv.cataloguer.catalog.dao;

import com.gv.cataloguer.catalog.ResourceCatalog;
import com.gv.cataloguer.database.settings.DatabaseConnectionManager;
import com.gv.cataloguer.logging.AppLogger;
import com.gv.cataloguer.models.Reference;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResourceDaoDatabase implements ResourceDao{

    private static final ResourceDaoDatabase INSTANCE = new ResourceDaoDatabase();

    private final static String PATH_TO_LOCAL_CATALOG = "G:\\Archive\\desktop\\Cataloguer\\local catalog\\";

    private ResourceDaoDatabase(){}

    public static ResourceDaoDatabase getInstance(){
        return INSTANCE;
    }

    public List<Reference> getAllResourcesFromCategory(String category) {
        Connection connection;
        List<Reference> references = null;
        FileOutputStream outputStream;
        InputStream inputStream;
        byte[] buffer = new byte[1024];
        try{
            connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + category);
            ResultSet rS = stmt.executeQuery();
            rS.last();
            int rowsCount = rS.getRow();
            references = new ArrayList<>(rowsCount);
            rS.beforeFirst();
            while(rS.next()){
                File file = new File(PATH_TO_LOCAL_CATALOG + category + "\\"
                        + rS.getString("name"));
                outputStream = new FileOutputStream(file);
                inputStream = rS.getBinaryStream("resource");
                while(inputStream.read(buffer) > 0){
                    outputStream.write(buffer);
                }
                references.add(new Reference(rS.getInt("id"), file.getName(), file.length(),
                        new Date(file.lastModified()), file.getAbsolutePath()));
            }
        }catch (SQLException e){
            AppLogger.getLogger().error(e.getMessage());
        }finally {
            return references;
        }
    }

    public Reference getResourceFromCategory(String category, int id) {
        Connection connection;
        Reference reference = null;
        FileOutputStream outputStream;
        InputStream inputStream;
        byte[] buffer = new byte[1024];
        try{
            connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM " + category + " WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rS = stmt.executeQuery();
            rS.next();
            File file = new File(PATH_TO_LOCAL_CATALOG + category + "\\"
                    + rS.getString("name"));
            outputStream = new FileOutputStream(file);
            inputStream = rS.getBinaryStream("resource");
            while(inputStream.read(buffer) > 0){
                outputStream.write(buffer);
            }
            reference = new Reference(rS.getInt("id"), file.getName(), file.length(),
                    new Date(file.lastModified()), file.getAbsolutePath());
        }catch (SQLException e){
            AppLogger.getLogger().error(e.getMessage());
        }finally {
            return reference;
        }

    }

    public void addResourceToCategory(String category, File file) {
        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.getDatabaseConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO " + category +
                    " (name, resource) values (?, ?)");
            FileInputStream inputStream = new FileInputStream(file);
            stmt.setString(1, file.getName());
            int length = (int) file.length();
            stmt.setBinaryStream(2, inputStream, length);
            stmt.executeUpdate();
            connection.commit();
            addResourceToLocalCatalog(category, file);
        } catch (FileNotFoundException | SQLException e) {
            AppLogger.getLogger().error(e.getMessage());
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    AppLogger.getLogger().error(ex.getMessage());
                }
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                AppLogger.getLogger().error(ex.getMessage());
            }
        }
    }

    private void addResourceToLocalCatalog(String category, File file){
        try{
            Connection connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM " + category
                    + " WHERE name = ?");
            stmt.setString(1, file.getName());
            ResultSet rS = stmt.executeQuery();
            rS.next();
            Reference ref = new Reference(rS.getInt("id"), file.getName(), file.length(),
                    new Date(file.lastModified()), file.getAbsolutePath());
            ResourceCatalog.getInstance().getCategory(category).add(ref);
        } catch (SQLException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }

    @Override
    public void deleteResourceFromCategory(String category, int id) {
        try{
            Connection connection = DatabaseConnectionManager.getDatabaseConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM " + category + " where id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e){
            AppLogger.getLogger().error(e.getMessage());
        }
    }
}
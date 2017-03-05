package com.gv.cataloguer.browsing;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class DesktopBrowser {

    private final static Desktop desktop = Desktop.getDesktop();

    private DesktopBrowser(){}

    public static void openFile(File file){
        try {
            desktop.open(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

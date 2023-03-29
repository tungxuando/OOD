package Xml;

import Presentation.Presentation;

import java.io.IOException;


/**
 * Xml.XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor implements LoadSaveXML {
    public XMLAccessor() {
    }
    @Override
    public void loadFile(Presentation presentation, String filename) throws IOException {
        LoadXMLFile loadXMLFile = new LoadXMLFile();
        loadXMLFile.loadFile(presentation, filename);
    }

    @Override
    public void saveFile(Presentation presentation, String filename) throws IOException {
        SaveXMLFile saveXMLFile = new SaveXMLFile();
        saveXMLFile.saveFile(presentation, filename);
    }
}

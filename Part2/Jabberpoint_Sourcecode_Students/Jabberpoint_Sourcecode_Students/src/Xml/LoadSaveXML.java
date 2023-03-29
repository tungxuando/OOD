package Xml;

import Presentation.Presentation;

import java.io.IOException;

public interface LoadSaveXML {
    void loadFile(Presentation presentation, String filename) throws IOException;

    void saveFile(Presentation presentation, String filename) throws IOException;
}
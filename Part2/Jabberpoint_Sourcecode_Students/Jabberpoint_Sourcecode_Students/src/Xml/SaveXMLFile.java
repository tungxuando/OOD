package Xml;

import Item.BitmapItem;
import Item.SlideItem;
import Item.TextItem;
import Presentation.Presentation;
import Slide.Slide;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public class SaveXMLFile {
    /**
     * Save a file
     *
     * @param presentation Presentation.Presentation to save
     * @param fileName     Name of the file
     * @throws IOException IO Exception
     *                     NOTE: Extracted saveXMLPresentation(),saveXMLSlide(),saveXMLItem(), rename filename to fileName
     */
    public void saveFile(Presentation presentation, String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");

        saveXMLPresentation(out, presentation);

        out.close();
    }

    private void saveXMLPresentation(PrintWriter out, Presentation presentation) {
        out.println("<presentation>");
        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");
        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++) {
            Slide slide = presentation.getSlide(slideNumber);
            saveXMLSlide(out, slide);
        }
        out.println("</presentation>");
    }

    private void saveXMLSlide(PrintWriter out, Slide slide) {
        out.println("<slide>");
        out.println("<title>" + slide.getTitle() + "</title>");
        Vector<SlideItem> slideItems = slide.getSlideItems();
        for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++) {
            SlideItem slideItem = (SlideItem) slideItems.elementAt(itemNumber);
            saveXMLItem(out, slideItem);
        }
        out.println("</slide>");
    }

    private void saveXMLItem(PrintWriter out, SlideItem slideItem) {
        out.print("<item kind=");
        if (slideItem instanceof TextItem) {
            out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
            out.print(((TextItem) slideItem).getText());
        } else {
            if (slideItem instanceof BitmapItem) {
                out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                out.print(((BitmapItem) slideItem).getName());
            } else {
                System.out.println("Ignoring " + slideItem);
            }
        }
        out.println("</item>");
    }
}

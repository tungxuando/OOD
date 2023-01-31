import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;


/**
 * XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor {

    /**
     * Default API to use.
     */
    protected static final String DEFAULT_API_TO_USE = "dom";

    /**
     * Names of xml tags of attributes
     */
    protected static final String SHOWTITLE = "showtitle", SLIDETITLE = "title", SLIDE = "slide", ITEM = "item", LEVEL = "level", KIND = "kind", TEXT = "text", IMAGE = "image";

    /**
     * Text of messages
     */
    protected static final String PCE = "Parser Configuration Exception", UNKNOWNTYPE = "Unknown Element type", NFE = "Number Format Exception";


    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    /**
     * Load a file
     *
     * @param presentation Presentation to add the file element
     * @param fileName     Name of the file
     *                     NOTE: Extracted loadElement(), rename filename to fileName
     */
    public void loadFile(Presentation presentation, String fileName) throws IOException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(fileName)); //Create a JDOM document
            Element doc = document.getDocumentElement();
            presentation.setTitle(getTitle(doc, SHOWTITLE));

            NodeList slides = doc.getElementsByTagName(SLIDE);

            loadElement(slides, presentation);

        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
    }

    /**
     * Load slide element fromm NodeList slides
     *
     * @param slides       List of slides
     * @param presentation Presentation
     */
    private void loadElement(NodeList slides, Presentation presentation) {
        Slide slide;
        for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
            Element xmlSlide = (Element) slides.item(slideNumber);

            slide = new Slide();
            slide.setTitle(getTitle(xmlSlide, SLIDETITLE));
            presentation.addSlide(slide);

            NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);
            for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
                Element item = (Element) slideItems.item(itemNumber);
                loadSlideItem(slide, item);
            }
        }
    }

    /**
     * Load slide item
     *
     * @param slide Slide
     * @param item  Item
     *              NOTE: Extracted getTextLevel(), rename leveltext to levelText, change multi level if to another else if
     */
    protected void loadSlideItem(Slide slide, Element item) {
        NamedNodeMap attributes = item.getAttributes();
        String levelText = attributes.getNamedItem(LEVEL).getTextContent();
        int level = getTextLevel(levelText);

        String type = attributes.getNamedItem(KIND).getTextContent();

        if (TEXT.equals(type)) {
            slide.addSlideItem(new TextItem(level, item.getTextContent()));
        } else if (IMAGE.equals(type)) {
            slide.addSlideItem(new BitmapItem(level, item.getTextContent()));
        } else {
            System.err.println(UNKNOWNTYPE);
        }
    }

    /**
     * Get text level
     *
     * @param levelText Level text
     * @return Level
     */
    private int getTextLevel(String levelText) {
        if (levelText != null) {
            try {
                return Integer.parseInt(levelText);
            } catch (NumberFormatException x) {
                System.err.println(NFE);
            }
        }
        return 1;
    }

    /**
     * Save a file
     * @param presentation Presentation to save
     * @param fileName Name of the file
     * @throws IOException IO Exception
     * NOTE: Extracted saveXMLPresentation(),saveXMLSlide(),saveXMLItem(), rename filename to fileName
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
            SlideItem slideItem = slideItems.elementAt(itemNumber);
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

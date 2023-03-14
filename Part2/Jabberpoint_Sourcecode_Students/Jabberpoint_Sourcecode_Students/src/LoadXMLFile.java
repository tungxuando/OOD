import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class LoadXMLFile extends XMLAccessor {
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
            presentation.setTitle(getTitle(doc, XMLTag.SHOWTITLE.getValue()));

            NodeList slides = doc.getElementsByTagName(XMLTag.SLIDE.getValue());

            loadElement(slides, presentation);

        } catch (IOException iox) {
            System.err.println(iox.toString());
        } catch (SAXException sax) {
            System.err.println(sax.getMessage());
        } catch (ParserConfigurationException pcx) {
            System.err.println(XMLException.parseError());
        }
    }

    private String getTitle(Element element, String tagName) {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    /**
     * Load slide element fromm NodeList slides
     *
     * @param slides       List of slides
     * @param presentation Presentation
     */
    private void loadElement(NodeList slides, Presentation presentation) {
        int slideNumber, itemNumber, max, maxItems;
        Slide slide;
        max = slides.getLength();

        for (slideNumber = 0; slideNumber < max; slideNumber++) {
            Element xmlSlide = (Element) slides.item(slideNumber);

            slide = new Slide();
            slide.setTitle(getTitle(xmlSlide, XMLTag.SLIDETITLE.getValue()));
            presentation.addSlide(slide);

            NodeList slideItems = xmlSlide.getElementsByTagName(XMLTag.ITEM.getValue());
            maxItems = slideItems.getLength();
            for (itemNumber = 0; itemNumber < maxItems; itemNumber++) {
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
        String levelText = attributes.getNamedItem(XMLTag.LEVEL.getValue()).getTextContent();
        int level = getTextLevel(levelText);

        String type = attributes.getNamedItem(XMLTag.KIND.getValue()).getTextContent();

        if (XMLTag.TEXT.getValue().equals(type)) {
            slide.addSlideItem(new TextItem(level, item.getTextContent()));
        } else if (XMLTag.IMAGE.equals(type)) {
            slide.addSlideItem(new BitmapItem(level, item.getTextContent()));
        } else {
            System.err.println(XMLException.unknownTypeError());
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
                System.err.println(XMLException.numberFormatError());
            }
        }
        return 1;
    }
}

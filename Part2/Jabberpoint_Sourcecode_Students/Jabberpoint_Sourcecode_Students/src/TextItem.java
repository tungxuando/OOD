import Style.Style;
import Style.StyleHelper;

import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
    private final String text; //NOTE: final

    private static final String EMPTYTEXT = "No Text Given";

    //A textitem of int level with text string
    public TextItem(int level, String string) {
        super(level);
        text = string;
    }

    //An empty textitem
    public TextItem() {
        this(0, EMPTYTEXT);
    }

    //Returns the text
    public String getText() {
        return text == null ? "" : text;
    }

    //Returns the AttributedString for the Item
    public AttributedString getAttributedString(Style style, float scale) {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
        return attrStr;
    }

    /**
     * Returns the bounding box of the text
     *
     * @param graphics the graphics context
     * @param observer the image observer
     * @param scale    the scale
     * @param myStyle  the style
     * @return the bounding box as Rectangle
     */
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle) {
        int xsize = 0, ysize = StyleHelper.getLeadingStyle(myStyle, scale);

        List<TextLayout> layouts = getLayouts(graphics, scale, myStyle);
        for (TextLayout layout : layouts) {
            Rectangle2D bounds = layout.getBounds();
            if (bounds.getWidth() > xsize) {
                xsize = (int) bounds.getWidth();
            }
            if (bounds.getHeight() > 0) {
                ysize += bounds.getHeight();
            }
            ysize += layout.getLeading() + layout.getDescent();
        }
        return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
    }

    /**
     * Draw textItem to screen, rearrange signature
     *
     * @param graphics the graphics context
     * @param observer the image observer
     * @param scale    the scale
     * @param style    the style
     * @param x        the x coordinate
     * @param y        the y coordinate
     */
    public void drawItem(Graphics graphics, ImageObserver observer, float scale, Style style, int x, int y) {
        if (text != null && text.length() != 0) {

            List<TextLayout> layouts = getLayouts(graphics, scale, style);
            Point pen = new Point(x + (int) (style.getIndent() * scale),
                    y + (int) (style.getLeading() * scale));
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.setColor(style.getColor());

            for (TextLayout layout : layouts) {
                pen.y += layout.getAscent();
                layout.draw(g2d, pen.x, pen.y);
                pen.y += layout.getDescent();
            }
        }
    }

    private List<TextLayout> getLayouts(Graphics graphics, float scale, Style style) {
        List<TextLayout> layouts = new ArrayList<TextLayout>();
        AttributedString attrStr = getAttributedString(style, scale);
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), ((Graphics2D) graphics).getFontRenderContext());
        float wrappingWidth = (Measurement.WIDTH.getSize() - style.getIndent()) * scale;

        while (measurer.getPosition() < getText().length()) {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }
        return layouts;
    }

    public String toString() {
        return new TextItem(getLevel(),getText()).toString();
    }
}

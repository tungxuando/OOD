package Slide;

import Enum.Measurement;
import Presentation.Presentation;

import javax.swing.*;
import java.awt.*;


/**
 * <p>Slide.SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {

    private static final long serialVersionUID = 227L;
    private static final Color BGCOLOR = Color.white, COLOR = Color.black; //NOTE: Combine 2 declarations into 1
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100, YPOS = 20; //NOTE: Combine 2 declarations into 1
    private final Font labelFont; //The font for labels //NOTE: Removes null
    private final JFrame frame; //NOTE: Removes null, make final
    private Slide slide; //The current slide
    private Presentation presentation; //The presentation //NOTE: Removes null

    public SlideViewerComponent(Presentation presentation, JFrame frame) {
        setBackground(BGCOLOR);
        this.presentation = presentation;
        labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
        this.frame = frame;
    }

    /**
     * Get default size of component.
     *
     * @return Dimension of component.
     */
    public Dimension getPreferredSize() {
        return new Dimension(Measurement.WIDTH.getSize(), Measurement.HEIGHT.getSize());
    }

    /**
     * Updates the contents from the slide
     *
     * @param presentation the presentation use to update the contents
     * @param data         the data to add to updated slide
     *                     NOTE: Reverse if condition to not null -> proceed
     */
    public void updateContent(Presentation presentation, Slide data) {
        if (data != null) {
            this.presentation = presentation;
            this.slide = data;
            repaint();
            frame.setTitle(presentation.getTitle());
        } else {
            repaint();
        }
    }

    /**
     * Draw the current slide.
     * NOTE: Extracted 3 methods (paintText(), paintBG(), getTextArea())
     */
    public void paintComponent(Graphics g) {
        paintBG(g, BGCOLOR);

        if (presentation.getSlideNumber() < 0 || slide == null) return;

        paintText(g, COLOR);
        slide.drawSlide(g, getTextArea(), this); //NOTE: replaced area with getTextArea() to prevent null
    }

    /**
     * Paint text on the slide.
     *
     * @param g     the graphics
     * @param color the color
     */
    private void paintText(Graphics g, Color color) {
        g.setFont(labelFont);
        g.setColor(color);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " + presentation.getSize(), XPOS, YPOS);
    }

    /**
     * Paint the background of the slide.
     *
     * @param g     the graphics
     * @param color the color
     */
    private void paintBG(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    /**
     * Get the area for the text.
     *
     * @return area
     */
    private Rectangle getTextArea() {
        return new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
    }
}

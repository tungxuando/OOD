import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * <p>A slide. This class has drawing functionality.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
    //public final static int WIDTH = 1200, HEIGHT = 800;
    protected String title; //The title is kept separately
    protected Vector<SlideItem> items; //The SlideItems are kept in a vector

    public Slide() {
        items = new Vector<SlideItem>();
    }

    //Add a SlideItem

	/**
	 * Rename method
	 * @param anItem the item to add
	 */
    public void addSlideItem(SlideItem anItem) {
        items.addElement(anItem);
    }

    //Return the title of a slide
    public String getTitle() {
        return title;
    }

    //Change the title of a slide
    public void setTitle(String newTitle) {
        title = newTitle;
    }

    //Create a TextItem out of a String and add the TextItem
	//NOTE: Rename method
    public void createTextItem(int level, String message) {
        addSlideItem(new TextItem(level, message));
    }

    //Returns the SlideItem
    public SlideItem getSlideItem(int number) {
        return (SlideItem) items.elementAt(number);
    }

    //Return all the SlideItems in a vector
    public Vector<SlideItem> getSlideItems() {
        return items;
    }

    //Returns the size of a slide
    public int getSize() {
        return items.size();
    }

	/**
	 * Draw the Slide.
	 * NOTE: Extracted getElements() to reduce how many times the fields repeats.
	 * @param graphics The graphics object to draw on.
	 * @param area The area to draw in.
	 * @param observer The observer
	 */
	public void drawSlide(Graphics graphics, Rectangle area, ImageObserver observer) {
        float scale = getScale(area);
        int y = area.y;
        //The title is treated separately
        SlideItem slideItem = new TextItem(0, getTitle());

        y += getElements(graphics, observer, y, area, new TextItem(0, getTitle()));

        for (int number = 0; number < getSize(); number++) {
            slideItem = getSlideItems().elementAt(number);
            y += getElements(graphics, observer, y, area, slideItem);
        }
    }

    //Returns the scale to draw a slide
    private float getScale(Rectangle area) {
        return Math.min(((float) area.width) / ((float) Measurement.WIDTH.getSize()), ((float) area.height) / ((float) Measurement.HEIGHT.getSize()));
    }

	private int getElements(Graphics graphics, ImageObserver observer, int y, Rectangle area, SlideItem slideItem ) {
		Style style = Styles.getStyle(slideItem.getLevel());
		slideItem.drawItem(graphics, observer, getScale(area), style, area.x, y);

		return slideItem.getBoundingBox(graphics, observer, getScale(area), style).height;
	}
}

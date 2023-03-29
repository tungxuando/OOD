package Item;

import Style.Style;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class SlideItem {
    private int level = 0; //The level of the Item.SlideItem

    public SlideItem(int lev) {
        level = lev;
    }

//	public Item.SlideItem() {
//		this(0);
//	}

    //Returns the level
    public int getLevel() {
        return level;
    }

    //Returns the bounding box
    public abstract Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style style);

    //Draws the item
    public abstract void drawItem(Graphics g, ImageObserver observer, float scale, Style style, int x, int y);
}

package Item;

import Style.Style;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;


/**
 * <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class BitmapItem extends SlideItem {
    protected static final String FILE = "File ";
    protected static final String NOTFOUND = " not found";
    private final String imageName; //NOTE: final
    private BufferedImage bufferedImage;


    //level indicates the item-level; name indicates the name of the file with the image
    public BitmapItem(int level, String name) {
        super(level);
        imageName = name;
        try {
            bufferedImage = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            System.err.println(FILE + imageName + NOTFOUND);
        }
    }

    //Returns the filename of the image
    public String getName() {
        return imageName;
    }

    //Returns the bounding box of the image
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style style) {
        return new Rectangle((int) (style.getIndent() * scale), 0,
                (int) (bufferedImage.getWidth(observer) * scale),
                ((int) (style.getLeading() * scale)) +
                        (int) (bufferedImage.getHeight(observer) * scale));
    }

    //Draws the image
    public void drawItem(Graphics graphics, ImageObserver observer, float scale, Style style, int x, int y) {
        int width = x + (int) (style.getIndent() * scale);
        int height = y + (int) (style.getLeading() * scale);
        graphics.drawImage(bufferedImage, width, height, (int) (bufferedImage.getWidth(observer) * scale),
                (int) (bufferedImage.getHeight(observer) * scale), observer);
    }

    public String toString() {
        return "Item.BitmapItem[" + getLevel() + "," + imageName + "]";
    }
}

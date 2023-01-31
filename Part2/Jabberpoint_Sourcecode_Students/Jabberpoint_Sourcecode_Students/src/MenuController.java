import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
    private MenuItem menuItem;

    private final Frame parent; //The frame, only used as parent for the Dialogs //NOTE: final
    private final Presentation presentation; //Commands are given to the presentation //NOTE: final

    public MenuController(Frame frame, Presentation pres) {
        parent = frame;
        presentation = pres;
        this.presentationMenu();
        this.navigationMenu();
        this.aboutPage();
    }

    //Creating a menu-item
    public MenuItem mkMenuItem(String name) {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }

    private void presentationMenu() {
        Menu viewMenu = new Menu(MenuField.FILE);

        add(viewMenu);
        this.openButton(viewMenu);
        this.clearButton(viewMenu);
        this.newButton(viewMenu);
        this.saveButton(viewMenu);
        this.exitButton(viewMenu);
    }

    /**
     * Saves the current presentation slide
     *
     * @param fileMenu the menu where the button is added
	 */
    private void saveButton(Menu fileMenu) {
        MenuItem menuItem;
        fileMenu.add(menuItem = mkMenuItem(MenuField.SAVE));
        menuItem.addActionListener(e -> {
                    Accessor xmlAccessor = new XMLAccessor();
                    try {
                        xmlAccessor.saveFile(presentation, MenuField.SAVEFILE);
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(parent, MenuField.IOEX + exc, MenuField.SAVEERR, JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    /**
     * Help menu and about button
     */
    private void aboutPage() {
        Menu helpMenu = new Menu(MenuField.HELP);
        MenuItem menuItem;
        helpMenu.add(menuItem = mkMenuItem(MenuField.ABOUT));
        menuItem.addActionListener(actionEvent -> AboutBox.show(parent));
        setHelpMenu(helpMenu);
    }

    /**
     * Clears the presentation
     *
     * @param menuItem the menu where the button is added
     */
    private void clearButton(MenuItem menuItem) {
        menuItem.addActionListener(actionEvent -> {
            presentation.clear();
            parent.repaint();
        });
    }

    /**
     * Wxit button
     *
     * @param fileMenu the menu where the button is added
     */
    private void exitButton(Menu fileMenu) {
        fileMenu.addSeparator();
        MenuItem menuItem;
        fileMenu.add(menuItem = mkMenuItem(MenuField.EXIT));
        menuItem.addActionListener(actionEvent -> presentation.exit(0));
    }

    /**
     * Opens the default presentation
     *
     * @param fileMenu the menu where the button is added
     */
    private void openButton(Menu fileMenu) {
        fileMenu.add(this.menuItem = mkMenuItem(MenuField.OPEN));
        menuItem.addActionListener(actionEvent -> {
            presentation.clear();
            Accessor xmlAccessor = new XMLAccessor();
            try {
                xmlAccessor.loadFile(presentation, MenuField.TESTFILE);
                presentation.setSlideNumber(0);
            } catch (IOException exc) {
                JOptionPane.showMessageDialog(parent, MenuField.IOEX + exc,
                        MenuField.LOADERR, JOptionPane.ERROR_MESSAGE);
            }
            parent.repaint();
        });
    }

    /**
     * Creating new slide
     *
     * @param fileMenu
     */
    private void newButton(Menu fileMenu) {
        MenuItem menuItem;
        fileMenu.add(menuItem = mkMenuItem(MenuField.NEW));
        menuItem.addActionListener(actionEvent -> {
            presentation.clear();
            parent.repaint();
        });
    }

    /**
     * Slide navigation
     */
    private void navigationMenu() {
        Menu viewMenu = new Menu(MenuField.VIEW);
        add(viewMenu);
        this.getNextSlide(viewMenu);
        this.getPrevSlide(viewMenu);
        this.goToSlide(viewMenu);
    }

    /**
     * Selecting next slide
     *
     * @param viewMenu the menu where the button is added
     */
    private void getNextSlide(Menu viewMenu) {
        MenuItem menuItem;
        viewMenu.add(menuItem = mkMenuItem(MenuField.NEXT));
        menuItem.addActionListener(actionEvent -> presentation.nextSlide());
    }

    /**
     * Selecting previous slide
     *
     * @param viewMenu the menu where the button is added
     */
    private void getPrevSlide(Menu viewMenu) {
        viewMenu.add(this.menuItem = mkMenuItem(MenuField.PREV));
        menuItem.addActionListener(actionEvent -> presentation.prevSlide());
        ;
    }

    /**
     * Selecting specific slide
     *
     * @param viewMenu the menu where the button is added
     */
    private void goToSlide(Menu viewMenu) {
        viewMenu.add(this.menuItem = mkMenuItem(MenuField.GOTO));
        this.menuItem.addActionListener(actionEvent -> {
            String pageNumberStr = JOptionPane.showInputDialog(MenuField.PAGENR);
            int pageNumber = Integer.parseInt(pageNumberStr);

            if(pageNumber > 0 && pageNumber <= presentation.getSize()){
                presentation.setSlideNumber(pageNumber - 1);
            }
            else {
                presentation.setSlideNumber(presentation.getSlideNumber());
            }
        });
    }
}
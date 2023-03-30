package Controller;

import Menu.FileMenu;
import Menu.HelpMenu;
import Menu.ViewMenu;
import Presentation.Presentation;
import Presentation.PresentationFrame;

import java.awt.*;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
    private PresentationFrame presentationFrame;
    private FileMenu fileMenu;
    private ViewMenu viewMenu;
    private HelpMenu helpMenu;

    public MenuController(Frame frame, Presentation pres) {
        this.presentationFrame = new PresentationFrame(frame, pres);

        fileMenu = new FileMenu(this.presentationFrame);
        viewMenu = new ViewMenu(this.presentationFrame);
        helpMenu = new HelpMenu(this.presentationFrame);
        add(fileMenu.makeFileMenu());
        add(viewMenu.makeViewMenu());
        add(helpMenu.makeAboutMenu());
    }

//    //Creating a menu-item
//    public MenuItem mkMenuItem(String name) {
//        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
//    }
//
//    private void presentationMenu() {
//        Menu viewMenu = new Menu(MenuField.FILE);
//
//        this.add(viewMenu);
//        this.openButton(viewMenu);
//        this.clearButton(viewMenu);
//        this.newButton(viewMenu);
//        this.saveButton(viewMenu);
//        this.exitButton(viewMenu);
//    }
//
//    /**
//     * Saves the current presentation slide
//     *
//     * @param fileMenu the menu where the button is added
//     */
//    private void saveButton(Menu fileMenu) {
//        MenuItem menuItem;
//        fileMenu.add(menuItem = mkMenuItem(MenuField.SAVE));
//        menuItem.addActionListener(e -> {
//                    SaveXMLFile saveXMLFile = new SaveXMLFile();
//                    try {
//                        saveXMLFile.saveFile(presentation, MenuField.SAVEFILE);
//                    } catch (IOException exc) {
//                        JOptionPane.showMessageDialog(parent, MenuField.IOEX + exc, MenuField.SAVEERR, JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//        );
//    }
//
//    /**
//     * Help menu and about button
//     */
//    private void aboutPage() {
//        Menu helpMenu = new Menu(MenuField.HELP);
//        MenuItem menuItem;
//        helpMenu.add(menuItem = mkMenuItem(MenuField.ABOUT));
//        menuItem.addActionListener(actionEvent -> AboutBox.showAbout(parent));
//        setHelpMenu(helpMenu);
//    }
//
//    /**
//     * Clears the presentation
//     *
//     * @param menuItem the menu where the button is added
//     */
//    private void clearButton(MenuItem menuItem) {
//        menuItem.addActionListener(actionEvent -> {
//            presentation.clear();
//            parent.repaint();
//        });
//    }
//
//    /**
//     * Wxit button
//     *
//     * @param fileMenu the menu where the button is added
//     */
//    private void exitButton(Menu fileMenu) {
//        fileMenu.addSeparator();
//        MenuItem menuItem;
//        fileMenu.add(menuItem = mkMenuItem(MenuField.EXIT));
//        menuItem.addActionListener(actionEvent -> presentation.exit(0));
//    }
//
//    /**
//     * Opens the default presentation
//     *
//     * @param fileMenu the menu where the button is added
//     */
//    private void openButton(Menu fileMenu) {
//        fileMenu.add(this.menuItem = mkMenuItem(MenuField.OPEN));
//        menuItem.addActionListener(actionEvent -> {
//            presentation.clear();
//            LoadXMLFile loadXMLFile = new LoadXMLFile();
//            try {
//                loadXMLFile.loadFile(presentation, MenuField.TESTFILE);
//                presentation.setSlideNumber(0);
//            } catch (IOException exc) {
//                JOptionPane.showMessageDialog(parent, MenuField.IOEX + exc,
//                        MenuField.LOADERR, JOptionPane.ERROR_MESSAGE);
//            }
//            parent.repaint();
//        });
//    }
//
//    /**
//     * Creating new slide
//     *
//     * @param fileMenu
//     */
//    private void newButton(Menu fileMenu) {
//        MenuItem menuItem;
//        fileMenu.add(menuItem = mkMenuItem(MenuField.NEW));
//        menuItem.addActionListener(actionEvent -> {
//            presentation.clear();
//            parent.repaint();
//        });
//    }
//
//    /**
//     * Slide.Slide navigation
//     */
//    private void navigationMenu() {
//        Menu viewMenu = new Menu(MenuField.VIEW);
//        add(viewMenu);
//        this.getNextSlide(viewMenu);
//        this.getPrevSlide(viewMenu);
//        this.goToSlide(viewMenu);
//    }
//
}
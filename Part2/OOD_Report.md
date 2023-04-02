# OOD Report

This report describes the problem and solution to Jabberpoint application.

## Details

Student name: Tung Do Xuan

Student number: 4679687

## Problem and Solution

### 1. MenuController

This class control the creation of menu items File, View, Help.

- Too many field

- Long method

- Bug that allow user to enter page number larger than presentation slide count.

- Does not have proper error handling

  **Solution**: Extract classes and methods, create MenuControllerException

This solution give each class FileMenu, HelpMenu, ViewMenu each own responsibility and methods are extracted with clear method name (Single Responsibility Principle - SOLID) and also eliminate the MenuController as a god class (Interface Segregation Principle). MakeMenuItemHelper is used in the 3 mentioned class so it is also extracted rather than having 3 duplicated method in 3 classes. Fixed goto bug. MenuControllerException handle error message. 

### 2. XmlAccessor, Demo Presentation

XMLAccessor class reads and writes XML files for the presentation. It reads title information from the presentation such as the show title and slide titles, and reads slide items such as text or images. It also writes title information and slide items to the XML files.

Both classes loads and saves xml.

- XML class has too many fields

- XML class has long methods

- XML class doesn't have proper error handling

  **Solution**: Extract XMLTag class, create interface for load and save xml(Interface Segregation Principle), create exception class, remove Accessor class

This solution give factory pattern to the interface allowing DemoPresentation and XMLAccessor (Open-Closed Principle - SOLID) to implement differently, extract methods to LoadXMLFile and SaveXMLFile let class do only one job (Single Responsibility Principle - SOLID).

#### 3. SlideViewerComponent

This class is a graphical component that displays slides. It sets up the background color, font, and other graphical elements, and then uses the draw() method of the Slide class to draw the slide. It also displays the slide number and the total number of slides in the presentation.

- Long methods

  **Solution**: Extract methods

This solution gives better maintability since each method does its own responsibility

#### 4. TextItem, Slide, SlideViewerComponent, SlideViewerFrame

These classes using same hand written value of width and height

**Solution**: Enum class Measurement

This solution let those classes access the measurement without creating duplicated field. Measurement can be changed in the future through one enum class.

#### 5. Style

It is not possible to create any additional styles without modifying the code.

**Solution**: Create StyleHelper class

This solution allows future changes to the style without modifying the base class

#### 6. Presentation, Slide

An interface can be beneficial to these two classes because they are both using the same methods, such as getTitle(), setTitle(), and getSize(). This would allow the two classes to share the same methods and have less code duplication. The interface would also provide a clean way of separating the two classes and providing a way to easily access the methods.

**Solution**: Create PresentationSlidePrep interface

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
/**
 * Starter code for Image Manipulation Array Assignment.
 * 
 * The class Processor contains all of the code to actually perform
 * transformation. The rest of the classes serve to support that
 * capability. This World allows the user to choose transformations
 * and open files.
 * 
 * Add to it and make it your own!
 * 
 * @author Jordan Cohen
 * @version November 2013
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "raptors.jpg";

    // Objects and Variables:
    private ImageHolder image;

    private TextButton blueButton;
    private TextButton hRevButton;
    private TextButton openFile;

    private TextButton vRevButton;
    private TextButton negButton;
    private TextButton greyButton;
    private TextButton warmButton;
    private TextButton blurButton;
    private TextButton pngButton;
    private TextButton coolButton;
    private TextButton greenButton;
    private TextButton rotateCWButton;
    private TextButton undoButton;

    private String fileName;

    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 

        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE);
        blueButton = new TextButton(" [ Blue-ify ] ");
        hRevButton = new TextButton(" [ Flip Horizontal ] ");
        openFile = new TextButton(" [ Open File: " + STARTING_FILE + " ] ");

        vRevButton = new TextButton (" [Flip Vertical ]");
        negButton = new TextButton (" [Negative] ");
        greyButton = new TextButton (" [Greyscale] ");
        warmButton = new TextButton (" [Warm] ");
        blurButton = new TextButton (" [Blur] ");
        pngButton = new TextButton (" [Save as PNG] ");
        coolButton = new TextButton (" [Cool] ");
        greenButton = new TextButton (" [Greenify] ");
        rotateCWButton = new TextButton (" [Rotate Clockwise] ");
        undoButton = new TextButton (" [Undo] ");

        // Add objects to the screen
        addObject (image, 300, 300);
        addObject (blueButton, 700, 200);
        addObject (hRevButton, 700, 250);

        addObject (vRevButton, 700, 300);
        addObject (negButton, 700, 350);
        addObject (greyButton, 700, 400);
        addObject (warmButton, 700, 450);
        addObject (blurButton, 700, 150);
        addObject (coolButton, 700, 500);
        addObject (rotateCWButton, 700, 150);
        addObject (undoButton, 525, 24);
        addObject (greenButton, 700, 550);

        addObject (openFile, 400, 24);
        addObject (pngButton, 250, 24);
        
        Processor.addImage(image.getImage().getAwtImage());
        
        prepare();
    }

    /**
     * Act() method just checks for mouse input... Not going to do anything else here
     */
    public void act ()
    {
        checkMouse();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            if (Greenfoot.mouseClicked(blueButton)){
                Processor.blueify(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(hRevButton)){
                Processor.flipHorizontal(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(openFile))
            {
                openFile ();
            }
            else if (Greenfoot.mouseClicked(vRevButton)){
                Processor.flipVertical(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(negButton)){
                Processor.negative(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(greyButton)){
                Processor.greyScale(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(warmButton)){
                Processor.warm(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(blurButton)){
                Processor.blur(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(pngButton)){
                Processor.savePNG(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(coolButton)){
                Processor.cool(image.getBufferedImage());
            }
            else if (Greenfoot.mouseClicked(rotateCWButton)){
                image.setImage(createGreenfootImageFromBI(Processor.rotate(image.getBufferedImage())));
            }
            else if (Greenfoot.mouseClicked(undoButton)){
                image.setImage(createGreenfootImageFromBI(Processor.undo()));
            }
            else if (Greenfoot.mouseClicked(greenButton)){
                Processor.greenify(image.getBufferedImage());
            }
        }
    }

    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        String fileName = JOptionPane.showInputDialog("Please input a value");

        // If the file opening operation is successful, update the text in the open file button
        if (image.openFile (fileName))
        {
            String display = " [ Open File: " + fileName + " ] ";
            openFile.update (display);
        }

    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     * 
     * @param newBi The BufferedImage to convert.
     * 
     * @return GreenfootImage   A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        return returnImage;
    }

}


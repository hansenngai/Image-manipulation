import java.awt.image.BufferedImage;
import greenfoot.GreenfootImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JOptionPane;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.awt.Graphics2D;

/**
 * Starter code for Processor - the class that processes images.
 * <p>
 * This class manipulated Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.
 * <p>
 * I have included two useful methods for dealing with bit-shift operators so
 * you don't have to. These methods are unpackPixel() and packagePixel() and do
 * exactly what they say - extract red, green, blue and alpha values out of an
 * int, and put the same four integers back into a special packed integer. 
 * <p>
 * Image maniupulation project with multiple features.
 * 
 * @author Hansen Ngai, Jordan Cohen 
 * @version April 2015
 */

public class Processor  
{
    static ArrayList<BufferedImage> imagesList = new ArrayList<BufferedImage>(); //array used for undos
    /**
     * An effect that can be used to blur an image. Found on internet.
     * 
     * @author http://www.jhlabs.com/ip/blurring.html
     * 
     * @param bi The BufferedImage (passed by reference) to change.
     */
    public static void blur (BufferedImage bi){
        addImage(bi);

        float[] matrix = {
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
            };

        BufferedImage biCopy = deepCopy(bi);
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, matrix) );
        op.filter(biCopy, bi);

    }

    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void blueify (BufferedImage bi)
    {
        addImage(bi);
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (blue <= 255)
                    blue += 2;
                if (red >= 50)
                    red--;
                if (green >= 50)
                    green--;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    /**
     * Changes to image to have negative color. Removes original r,g,b value from 255. 
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void negative (BufferedImage bi){
        addImage(bi);
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // takes away original rgb
                blue = 255 - blue;
                green = 255 - green;
                red = 255 - red;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    
    /**
     * Flips the image horizontally.
     *
     *Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * 
     * 
     */public static void flipHorizontal (BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        /**
         * INSERT TWO LOOPS HERE:
         * - FIRST LOOP MOVES DATA INTO A SECOND, TEMPORARY IMAGE WITH PIXELS FLIPPED
         *   HORIZONTALLY
         * - SECOND LOOP MOVES DATA BACK INTO ORIGINAL IMAGE
         * 
         * Note: Due to a limitation in Greenfoot, you can get the backing BufferedImage from
         *       a GreenfootImage, but you cannot set or create a GreenfootImage based on a 
         *       BufferedImage. So, you have to use a temporary BufferedImage (as declared for
         *       you, above) and then copy it, pixel by pixel, back to the original image.
         *       Changes to bi in this method will affect the GreenfootImage automatically.
         */ 
        // moves data into temp
        for (int i =0; i < xSize; i++){
            for (int j = 0; j< ySize; j++){
                newBi.setRGB(xSize-i-1, j, bi.getRGB(i,j)); //modifies x values
            }
        }

        //moves data back to original
        for (int i = 0; i <xSize; i++){
            for (int j = 0; j < ySize; j++){
                bi.setRGB(i,j, newBi.getRGB(i,j)); // 
            }
        }
    }

    /**
     * Flips the image vertically.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * 
     */public static void flipVertical (BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage newBi = new BufferedImage (xSize, ySize, 3);

        // moves data into temp
        for (int i =0; i < xSize; i++){
            for (int j = 0; j< ySize; j++){
                newBi.setRGB(i, ySize-j-1, bi.getRGB(i,j)); //modifies y values
            }
        }

        //moves data back to original
        for (int i = 0; i <xSize; i++){
            for (int j = 0; j < ySize; j++){
                bi.setRGB(i,j, newBi.getRGB(i,j));
            }
        } 
    }

    /**
     * Makes the image black & white (greyscale). 
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * 
     */public static void greyScale(BufferedImage bi)
    {
        addImage(bi);
        // Gets image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];                
                int same = (red + green + blue) /3;

                //grayer by setting all R G and B to same value
                if (blue <= 255)
                    blue = same;
                if (red <= 255)
                    red = same;
                if (green <= 255)
                    green = same;

                int newColour = packagePixel (red, green, blue, alpha);// packs pixels to be placed into the buffered image that was initially used.
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * "Warms" the picture. (Increases red value).
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * 
     */public static void warm (BufferedImage bi){
        addImage(bi);
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // increase red value 
                if (red + 20 < 255){
                    red += 10;
                }

                int newColour = packagePixel (red, green, blue, alpha);// packs pixels to be placed into the buffered image that was initially used.
                bi.setRGB (x, y, newColour);
            }
        }
    }

    
    /**
     * "Cools" the image. (Increases blue value)
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * 
     */public static void cool (BufferedImage bi){
        addImage(bi);
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // increase blue value 
                if (blue + 20 < 255){
                    blue += 10;
                }

                int newColour = packagePixel (red, green, blue, alpha);// packs pixels to be placed into the buffered image that was initially used.
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Turns the image greener.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * 
     */public static void greenify (BufferedImage bi){
        addImage(bi);
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // increase green value 
                if (green + 20 < 255){
                    green += 10;
                }

                int newColour = packagePixel (red, green, blue, alpha);// packs pixels to be placed into the buffered image that was initially used.
                bi.setRGB (x, y, newColour);
            }
        }
    }

    static BufferedImage previousImage;
    public static BufferedImage undo (){
        try{
            previousImage = imagesList.get(imagesList.size()-2);
            imagesList.remove(imagesList.size()-1);
        }
        catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog (null, "No more undos possible");
        } 
        return previousImage;
    }

    /**
     * Saves as a PNG file.
     * 
     */public static void savePNG (BufferedImage image){
        //Asks for user input
        String fileName = JOptionPane.showInputDialog("Input file name (include ___.png)");

        File name = new File (fileName);
        try{
            ImageIO.write(image, "png", name);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog (null, "Exception");
        }

        JOptionPane.showMessageDialog (null, "Saved picture");
    }
    
    /**
     * Rotates the image by swapping x and y.
     * 
     * Utilizes pixel packaging and unpackaging methods. 
     *   
     * @param bi    The BufferedImage (passed by reference) to change.
     * @return  BufferedImage Returns the BufferedImage of rotated form
     */ 
    public static BufferedImage rotate (BufferedImage bi)
    {
        //Adds image to the ArrayList prior to making changes.
        addImage(bi);
        
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (ySize, xSize, 3);
        
        //For loop used similar to in ArrayList. This allows for the swapping of pixels at certain locations with other pixels. Effectively rotating the 2-D Array.
        //This produces the results seen with button press. 
        for (int x = 0; x < xSize; x++)
        {
            for (int y = ySize-1; y >=0; y--)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];   
                int newColour = packagePixel (red, green, blue, alpha);// packs pixels to be placed into the buffered image that was initially used.
                newBi.setRGB (ySize-y-1,x, newColour); //swap x and y
            }
        }
        return newBi;
    }

    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }

    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     * @return  BufferedImage Returns the BufferedImage that is used for undoing.
     */
    public static BufferedImage deepCopy(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultip = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultip, null);
    }

    /**
     * Adds image to list for undo purposes.
     * 
     * @param bi    The BufferedImage (passed by reference) to add.
     * 
     */
    public static void addImage (BufferedImage bi){
        imagesList.add(deepCopy(bi));
    }
    
    /**
     * Takes in a BufferedImage and returns a GreenfootImage. By Mr. Cohen.
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

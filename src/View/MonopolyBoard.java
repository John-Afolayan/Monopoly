package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/*
 * The JPanel is an image of the Monopoly board that we will be overlaying the components over.
 */
class MonopolyBoard extends JPanel implements Serializable {
    private Image image;

    public MonopolyBoard() {
        this.setSize(950, 560);
        try {
            InputStream iS = this.getClass().getClassLoader().getResourceAsStream("Monopoly.png");
            BufferedImage loadedImage = ImageIO.read(iS);
            image = loadedImage.getScaledInstance(935, 430, Image.SCALE_AREA_AVERAGING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(image, 0, 0, this);
        graphics.drawRect(0,0,getWidth()-1, getHeight()-1);
    }
}

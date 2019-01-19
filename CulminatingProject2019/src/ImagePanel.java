import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
        setBackground(Color.PINK);
        setPreferredSize(new Dimension(img.getWidth(null), img.getHeight(null)));

        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}

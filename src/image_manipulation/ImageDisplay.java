package image_manipulation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageDisplay extends JPanel implements Runnable {
    
    private ImageLoaderTL iTL;
    
    public ImageDisplay(ImageLoaderTL iTL) {
	this.iTL = iTL;
    }
    
    @SuppressWarnings("unused")
    private ImageDisplay(){}
    
    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(iTL.getImage(), 0, 0, this);
    }

    @Override
    public void run() {
	JFrame f = new JFrame();
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setBounds(new Rectangle(0, 0, iTL.getImage().getWidth(), iTL.getImage().getHeight()));
	f.setMinimumSize(new Dimension(iTL.getImage().getWidth(), iTL.getImage().getHeight()));
	f.add(this);
	f.setAlwaysOnTop(true);
	f.setLocationRelativeTo(this);
	f.setVisible(true);
    }
    
}
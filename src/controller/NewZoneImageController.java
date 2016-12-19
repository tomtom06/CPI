package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.ImageModel;
import view.ImageView;

public class NewZoneImageController extends ImageController {

    private Rectangle selectedRect;
    private ArrayList<Rectangle> listeRectToEncrypt;

    private Point start;

    public NewZoneImageController(ImageModel model, ImageView view) {
	super(model, view);
	this.listeRectToEncrypt = new ArrayList<Rectangle>();
	this.start = new Point();
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
	Graphics g = this.model.getImage().createGraphics();
	//g.setColor(Color.BLUE);
	//g.drawRect((int) selectedRect.getX(), (int) selectedRect.getY(), (int) selectedRect.getWidth(),
		//(int) selectedRect.getHeight());
	g.setColor(Color.LIGHT_GRAY);
	g.fillRect((int) selectedRect.getX(), (int) selectedRect.getY(), (int) selectedRect.getWidth(),
		(int) selectedRect.getHeight());
	listeRectToEncrypt.add(selectedRect);
	System.out.println("Selected : " + listeRectToEncrypt.size());

    }

    @Override
    public void mouseMoved(MouseEvent me) {
	start = me.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
	Point end = me.getPoint();
	selectedRect = new Rectangle(start, new Dimension(end.x - start.x, end.y - start.y));
	repaint(this.model.getImage(), this.model.getImage());
	this.view.repaint();
	// System.out.println("Rectangle: " + selectedRect);
    }

    public void repaint(BufferedImage orig, BufferedImage copy) {
	Graphics2D g = copy.createGraphics();
	g.drawImage(orig, 0, 0, null);
	if (selectedRect != null) {
	    //g.setColor(Color.BLUE);
	    // System.out.println("repaint ...");
	    //g.draw(selectedRect);
	    g.setColor(Color.LIGHT_GRAY);
	    g.fill(selectedRect);
	}
    }

    @Override
    public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

    }

}

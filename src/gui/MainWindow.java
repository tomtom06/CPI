package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import controller.WindowController;
import model.ImageModel;
import view.ImageView;

public class MainWindow {

	private JFrame frame;
	private JPanel buttons;
	private JPanel imagePortal;
	private JToggleButton btnNewZones;
	private JToggleButton btnSelectZones;
	private JButton btnSelectAll;

	private ImageModel model;
	private ImageView view;
	private WindowController wcontroller;
	
	private String fileName, path;

	private final int xOffset = 25;
	private final int yOffset = 25;

	/**
	 * Create the application.
	 */
	public MainWindow(ImageModel model, ImageView view, WindowController wcontroller) {
		this.model = model;
		this.view = view;
		this.wcontroller = wcontroller;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void allowButtons() {
		btnNewZones.setEnabled(true);
		btnSelectZones.setEnabled(true);
		btnSelectAll.setEnabled(true);
	}


	/*
	 * Verifie les dimensions de l'image chargee dans la vue et adapte
	 * l'affichage
	 */
	private void checkBounds() {
		if(model.getImage() != null)
		{
			int original_width = model.getImage().getWidth();
			int original_height = model.getImage().getHeight();
			int bound_width = this.imagePortal.getWidth() - 2 * this.xOffset;
			int bound_height = this.imagePortal.getHeight() - 2 * this.yOffset;
			int new_width = original_width;
			int new_height = original_height;

			// first check if we need to scale width
			if (original_width > bound_width) {
				// scale width to fit
				new_width = bound_width;
				// scale height to maintain aspect ratio
				new_height = (new_width * original_height) / original_width;
			}

			// then check if we need to scale even with the new height
			if (new_height > bound_height) {
				// scale height to fit instead
				new_height = bound_height;
				// scale width to maintain aspect ratio
				new_width = (new_height * original_width) / original_height;
			}
			this.view.setBounds((int) ((double) this.imagePortal.getWidth() / 2 - (double) new_width / 2),
					(int) ((double) this.imagePortal.getHeight() / 2 - (double) new_height / 2), new_width,
					new_height);
			
			this.view.setRectangles(new ArrayList<Rectangle>());
		}
	}

	/**
	 * updateImageModel charge et affiche l'image passee en parametre
	 * @param file
	 * File : l'image a afficher
	 */
	private void updateImageModel(File file) {
		
		this.model.loadImage(file.getAbsolutePath());
		checkBounds();
		this.view.repaint();
	}

	/**
	 * ajoute les composants a la fenetre
	 */
	public void addContentsToPane() {
		
		Container pane = frame.getContentPane();
		pane.setLayout(new BorderLayout());
		this.imagePortal = new JPanel();
		this.imagePortal.add(this.view);
		pane.add(imagePortal, BorderLayout.CENTER);
		this.buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 6));
		
		JButton btnLoadImage = new JButton("Load Image");
		btnLoadImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser dialogue = new JFileChooser(new File("."));
				PrintWriter pw = null;
				File file;

				if (dialogue.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					file = dialogue.getSelectedFile();
					try {
						pw = new PrintWriter(new FileWriter(file.getPath(), true));
						allowButtons();
					} catch (IOException e) {
					}
					pw.close();
					
					// getting filePath and fileName and give it to Encription window
					path = file.getAbsolutePath();
					fileName = file.getName();
					updateImageModel(file);
				}
			}
		});
		buttons.add(btnLoadImage);

		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EncryptionWindow ew = new EncryptionWindow(view, fileName, path, model);
				view.prepareRectangles();
				ew.setVisible(true);
			}
		});
		buttons.add(btnEncrypt);

		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DecryptionWindow dw = new DecryptionWindow(view, fileName, path, model);
				dw.setVisible(true);
			}
		});
		buttons.add(btnDecrypt);

		// Button to create zones
		Icon createIcon = new ImageIcon("res/select.png");
		JToggleButton btnNewZone = new JToggleButton(createIcon);
		this.btnNewZones = btnNewZone;
		btnNewZone.setToolTipText("Create new zones");
		btnNewZone.setEnabled(false);
		buttons.add(btnNewZone);
		this.wcontroller.addComponent("newZone", btnNewZone);
		btnNewZone.addItemListener(this.wcontroller);

		JButton btnSelectAll = new JButton("Select All");
		this.btnSelectAll = btnSelectAll;
		btnSelectAll.setToolTipText("Select all image");
		btnSelectAll.setEnabled(false);
		buttons.add(btnSelectAll);
		btnSelectAll.addActionListener(new ActionListener() {
			//
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Rectangle> rectangle = new ArrayList<Rectangle>();
				rectangle.add(new Rectangle(0,0,model.getImage().getWidth(),model.getImage().getHeight()));
				view.setRectangles(rectangle);
				view.repaint();
			}
		});

		// Button to select zones
		Icon mousePointer = new ImageIcon("res/mouse.png");
		JToggleButton btnSelectZone = new JToggleButton(mousePointer);
		this.btnSelectZones = btnSelectZone;
		btnSelectZone.setToolTipText("Select zones");
		btnSelectZone.setEnabled(false);
		buttons.add(btnSelectZone);
		this.wcontroller.addComponent("selectZone", btnSelectZone);
		btnSelectZone.addItemListener(this.wcontroller);
		
		pane.add(buttons, BorderLayout.SOUTH);
		
	}

	/**
	 * lancement de la fenetre
	 */
	private void initialize() {

		setFrame(new JFrame("PIE - Partial Image Encryption"));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMaximumSize(dim);
		frame.setSize(dim);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addContentsToPane();
		frame.setResizable(true);
		frame.addComponentListener(new ComponentListener() {
		    public void componentResized(ComponentEvent e) {
		        checkBounds();
		        view.repaint();
		    }

			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
	}

	/**
	 * getFrame renvoie la fenetre
	 * @return
	 * JFrame : la fenetre renvoye
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * setFrame modifie la fenetre
	 * @param frame
	 * JFrame : la fenetre a utiliser
	 */
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}

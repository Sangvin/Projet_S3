package colorChooser;

import graphic.Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import objet.Objet3D;

/**
 * Classe permettant de creer un color chooser fonctionne avec ColorTable et ColorCreator
 * @author Alex Dalencourt
 * @author Yoan Lamaire
 */
public class MyColorChooser extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * titre du colorChooser
	 */
	private JLabel titre;
	/**
	 * permet l'aper�u de la couleur
	 */
	private JPanel color;
	/**
	 * titre de la partie rouge du colorCreator
	 */
	private JButton appliquer;
	/**
	 * contient une table avec 216 couleurs diff�rentes
	 */
	private ColorTable table;
	/**
	 * contient une module permettant de creer sa propre couleur
	 */
	private ColorCreator creator;
	/**
	 * Contient la frame principale
	 */
	private Frame f;
	/**
	 * Contient l'objet dont il faut changer la couleur
	 */
	private Objet3D object;
	/**
	 * Conserve la couleur d'origine
	 */
	private Color save;
	
	/**
	 * instancie les diff�rents �l�ments et enregistre la frame appelante
	 * @param i
	 */
	public MyColorChooser(Frame f){
		this.f = f;
		this.object = null;
		initComponent();
		initProperties();
	}
	
	public MyColorChooser(Frame f, Objet3D object){
		this.f = f;
		this.object = object;
		initComponent();
		initProperties();
	}
	
	/**
	 * inittialise les propri�t� de la frame
	 */
	private void initProperties(){
		this.setTitle("ColorChooser");
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		pack();
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()-this.getWidth()-10);
		int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/3-this.getHeight()/2); 
		this.setLocation(width,height);
		
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				if(object != null){
					object.setColor(save);
					f.getTablette().repaint();
				}
				else{
					f.setbackground(save);
				}
				f.setEnabled(true);
				dispose();
			}
		});
	}
	
	/**
	 * initialise et position les �l�ments
	 */
	private void initComponent(){
	  	titre = new JLabel();
    	titre.setFont(new Font("Comic Sans MS", 0, 24));
    	titre.setText("Choisi ta propre couleur");
    	
    	color = new JPanel();
    	if(this.object != null){
    		color.setBackground(this.object.getColor());
    		save = color.getBackground();
    	}
    	else{
    		color.setBackground(this.f.getbackground());
    		save = color.getBackground();
    	}
    	color.setPreferredSize(new Dimension(100,100));
    	color.setBorder(BorderFactory.createLineBorder(Color.black));

    	
        appliquer = new JButton("Appliquer");
        appliquer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.setEnabled(true);
				dispose();
			}
        });
        
        table = new ColorTable(this);
        creator = new ColorCreator(this);
        
        GridBagLayout bagLayout = new GridBagLayout();
        setLayout(bagLayout);
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.weighty = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NORTHEAST;
        bagLayout.setConstraints(titre, c);
        add(titre);
        
        c.gridwidth = 1;
        c.gridy = 1;
        bagLayout.setConstraints(color, c);
        add(color);
        
        c.gridx = 1;
        c.gridwidth = 1;
        bagLayout.setConstraints(creator, c);
        add(creator);
        
        c.gridwidth = 2;
        c.gridy = 2;
        c.gridx = 0;
        bagLayout.setConstraints(table, c);
        add(table);
        
        c.gridwidth = 2;
        c.gridy = 3;
        c.gridx = 0;
        bagLayout.setConstraints(appliquer, c);
        add(appliquer);
	}
	
	/**
	 * permet de r�cup�rer la couleur courante
	 * @return
	 */
	public Color getCurentColor(){
		return color.getBackground();
	}
	
	/**
	 * change la couleur actuelle temporairement
	 * @param color
	 */
	public void setColor(Color color) {
		this.color.setBackground(color);
		this.creator.actualiseSlider(color);
		if(this.object != null){
			this.object.setColor(color);
			this.f.getTablette().repaint();
		}
		else
			this.f.setbackground(color);
	}
}

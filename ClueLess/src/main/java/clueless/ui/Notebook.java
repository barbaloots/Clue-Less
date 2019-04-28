package clueless.ui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueless.gamelogic.Player;

/**
 * Class that controls players' notebooks
 * 
 * @author erinsmedley
 *
 */
public class Notebook {

	private Player player;
	private JFrame notebookFrame;
	private JButton notebookButton;
	
	/**
	 * Constructor
	 * 
	 * @param player
	 * 		Determines which player's notebook is being referenced
	 */
	public Notebook(JButton notebookButton, Player player) {
		this.notebookButton = notebookButton;
		this.player = player;
		
		init();
	}
	
	private void init() {
		notebookFrame = new JFrame();
		notebookFrame.setAlwaysOnTop(true);
		notebookFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				notebookButton.setEnabled(true);
			}
		});
		
		JPanel notebook = new JPanel();
		BoxLayout box = new BoxLayout(notebook, BoxLayout.Y_AXIS);
		notebook.setLayout(box);
		notebook.setPreferredSize(new Dimension(200, 700));
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	    int x = (int) rect.getMaxX() - notebookFrame.getWidth();
	    int y = 0;
	    notebookFrame.setLocation(x, y);
	    
		JLabel whoLabel = new JLabel("Who?");		
		JPanel who = new JPanel(new GridLayout(6, 1));
		JCheckBox green = new JCheckBox("Green");
		JCheckBox mustard = new JCheckBox("Mustard");
		JCheckBox peacock = new JCheckBox("Peacock");
		JCheckBox plum = new JCheckBox("Plum");
		JCheckBox scarlet = new JCheckBox("Scarlet");
		JCheckBox white = new JCheckBox("White");
		who.add(green);
		who.add(mustard);
		who.add(peacock);
		who.add(plum);
		who.add(scarlet);
		who.add(white);
		
		JLabel whatLabel = new JLabel("What?");
		JPanel what = new JPanel(new GridLayout(6, 1));
		JCheckBox candlestick = new JCheckBox("Candlestick");
		JCheckBox knife = new JCheckBox("Knife");
		JCheckBox leadpipe = new JCheckBox("Lead Pipe");
		JCheckBox revolver = new JCheckBox("Revolver");
		JCheckBox rope = new JCheckBox("Rope");
		JCheckBox wrench = new JCheckBox("Wrench");
		what.add(candlestick);
		what.add(knife);
		what.add(leadpipe);
		what.add(revolver);
		what.add(rope);
		what.add(wrench);
		
		JLabel whereLabel = new JLabel("Where?");
		JPanel where = new JPanel(new GridLayout(9, 1));
		JCheckBox study = new JCheckBox("Study");
		JCheckBox hall = new JCheckBox("Hall");
		JCheckBox lounge = new JCheckBox("Lounge");
		JCheckBox library = new JCheckBox("Library");
		JCheckBox billiard = new JCheckBox("Billiard Room");
		JCheckBox dining = new JCheckBox("Dining Room");
		JCheckBox conservatory = new JCheckBox("Conservatory");
		JCheckBox ballroom = new JCheckBox("Ballroom");
		JCheckBox kitchen = new JCheckBox("Kitchen");
		where.add(study);
		where.add(hall);
		where.add(lounge);
		where.add(library);
		where.add(billiard);
		where.add(dining);
		where.add(conservatory);
		where.add(ballroom);
		where.add(kitchen);
		
		notebook.add(whoLabel);
		notebook.add(who);
		notebook.add(whatLabel);
		notebook.add(what);
		notebook.add(whereLabel);
		notebook.add(where);
		
		notebookFrame.add(notebook);
	}
	
	protected void open(Player player) {
		notebookFrame.pack();
		notebookFrame.setVisible(true);
		notebookButton.setEnabled(false);
	}
}

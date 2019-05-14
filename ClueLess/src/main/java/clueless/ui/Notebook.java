package clueless.ui;

import clueless.gamelogic.*;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueless.gamelogic.CharacterEnum;
import clueless.gamelogic.CharacterName;
import clueless.gamelogic.Player;
import clueless.gamelogic.RoomName;
import clueless.gamelogic.WeaponType;

/**
 * Class that controls players' notebooks
 * 
 * @author erinsmedley
 *
 */
public class NotebookUI {

	private Player player;
	private JFrame notebookFrame;
	private JButton notebookButton;
	private clueless.gamelogic.Notebook playerNotes;   
        private HashMap<CharacterName, Boolean> tmp;
        private HashMap<WeaponType, Boolean> wtmp;
        private HashMap<RoomName, Boolean> rtmp;
	private static int xDim = 200;
        private static int yDim = 700;
	/**
	 * Constructor
	 * 
	 * @param player
	 * 		Determines which player's notebook is being referenced
	 */
	public NotebookUI(JButton notebookButton, Player player) {
		this.notebookButton = notebookButton;
		this.player = player;
		playerNotes = player.getNotebook();
		
		init();
	}
	
	/**
	 * initializes notebook frame
	 */
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
		notebook.setPreferredSize(new Dimension(xDim, yDim));
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
	    int x = (int) rect.getMaxX() - xDim;
	    int y = 0;
	    notebookFrame.setLocation(x, y);

	    
		JLabel whoLabel = new JLabel("Who?");		
		JPanel who = this.who();
		
		JLabel whatLabel = new JLabel("What?");
		JPanel what = this.what();
		
		JLabel whereLabel = new JLabel("Where?");
		JPanel where = this.where();
		
		notebook.add(whoLabel);
		notebook.add(who);
		notebook.add(whatLabel);
		notebook.add(what);
		notebook.add(whereLabel);
		notebook.add(where);
		
		notebookFrame.add(notebook);
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel who() {
		JPanel who = new JPanel(new GridLayout(6, 1));
		
		JCheckBox green = new JCheckBox("Green");
		green.setSelected(playerNotes.getCharacterSection().get(CharacterName.GREEN));
		green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmp = playerNotes.getCharacterSection();
				tmp.put(CharacterName.GREEN, green.isSelected());
				playerNotes.setCharacterSection(tmp);
			}
		});
		
		JCheckBox mustard = new JCheckBox("Mustard");
		mustard.setSelected(playerNotes.getCharacterSection().get(CharacterName.MUSTARD));
		mustard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmp = playerNotes.getCharacterSection();
				tmp.put(CharacterName.MUSTARD, mustard.isSelected());
				playerNotes.setCharacterSection(tmp);
			}
		});
		
		JCheckBox peacock = new JCheckBox("Peacock");
		peacock.setSelected(playerNotes.getCharacterSection().get(CharacterName.PEACOCK));
		peacock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmp = playerNotes.getCharacterSection();
				tmp.put(CharacterName.PEACOCK, peacock.isSelected());
				playerNotes.setCharacterSection(tmp);
			}
		});
		
		JCheckBox plum = new JCheckBox("Plum");
		plum.setSelected(playerNotes.getCharacterSection().get(CharacterName.PLUM));
		plum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmp = playerNotes.getCharacterSection();
				tmp.put(CharacterName.PLUM, plum.isSelected());
				playerNotes.setCharacterSection(tmp);
			}
		});
		
		JCheckBox scarlet = new JCheckBox("Scarlet");
		scarlet.setSelected(playerNotes.getCharacterSection().get(CharacterName.SCARLET));
		scarlet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmp = playerNotes.getCharacterSection();
				tmp.put(CharacterName.SCARLET, scarlet.isSelected());
				playerNotes.setCharacterSection(tmp);
			}
		});
		
		JCheckBox white = new JCheckBox("White");
		white.setSelected(playerNotes.getCharacterSection().get(CharacterName.WHITE));
		white.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmp = playerNotes.getCharacterSection();
				tmp.put(CharacterName.WHITE, white.isSelected());
				playerNotes.setCharacterSection(tmp);
			}
		});
		
		who.add(green);
		who.add(mustard);
		who.add(peacock);
		who.add(plum);
		who.add(scarlet);
		who.add(white);
		
		return who;
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel what() {
		JPanel what = new JPanel(new GridLayout(6, 1));
		
		JCheckBox candlestick = new JCheckBox("Candlestick");
		candlestick.setSelected(playerNotes.getWeaponSection().get(WeaponType.CANDLESTICK));
		candlestick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtmp = playerNotes.getWeaponSection();
				wtmp.put(WeaponType.CANDLESTICK, candlestick.isSelected());
				playerNotes.setWeaponSection(wtmp);
			}
		});
		
		JCheckBox knife = new JCheckBox("Knife");
		knife.setSelected(playerNotes.getWeaponSection().get(WeaponType.KNIFE));
		knife.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtmp = playerNotes.getWeaponSection();
				wtmp.put(WeaponType.KNIFE, knife.isSelected());
				playerNotes.setWeaponSection(wtmp);
			}
		});
		
		JCheckBox leadpipe = new JCheckBox("Lead Pipe");
		leadpipe.setSelected(playerNotes.getWeaponSection().get(WeaponType.LEADPIPE));
		leadpipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtmp = playerNotes.getWeaponSection();
				wtmp.put(WeaponType.LEADPIPE, leadpipe.isSelected());
				playerNotes.setWeaponSection(wtmp);
			}
		});
		
		JCheckBox revolver = new JCheckBox("Revolver");
		revolver.setSelected(playerNotes.getWeaponSection().get(WeaponType.REVOLVER));
		revolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtmp = playerNotes.getWeaponSection();
				wtmp.put(WeaponType.REVOLVER, revolver.isSelected());
				playerNotes.setWeaponSection(wtmp);
			}
		});
		
		JCheckBox rope = new JCheckBox("Rope");
		rope.setSelected(playerNotes.getWeaponSection().get(WeaponType.ROPE));
		rope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtmp = playerNotes.getWeaponSection();
				wtmp.put(WeaponType.ROPE, rope.isSelected());
				playerNotes.setWeaponSection(wtmp);
			}
		});
		
		JCheckBox wrench = new JCheckBox("Wrench");
		wrench.setSelected(playerNotes.getWeaponSection().get(WeaponType.WRENCH));
		wrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wtmp = playerNotes.getWeaponSection();
				wtmp.put(WeaponType.WRENCH, wrench.isSelected());
				playerNotes.setWeaponSection(wtmp);
			}
		});
		
		what.add(candlestick);
		what.add(knife);
		what.add(leadpipe);
		what.add(revolver);
		what.add(rope);
		what.add(wrench);
		
		return what;
	}
	
	/**
	 * 
	 * @return
	 */
	private JPanel where() {
		JPanel where = new JPanel(new GridLayout(9, 1));
		
		JCheckBox study = new JCheckBox("Study");
		study.setSelected(playerNotes.getRoomSection().get(RoomName.STUDY));
		study.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.STUDY, study.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox hall = new JCheckBox("Hall");
		hall.setSelected(playerNotes.getRoomSection().get(RoomName.HALL));
		hall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.HALL, hall.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox lounge = new JCheckBox("Lounge");
		lounge.setSelected(playerNotes.getRoomSection().get(RoomName.LOUNGE));
		lounge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.LOUNGE, lounge.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox library = new JCheckBox("Library");
		library.setSelected(playerNotes.getRoomSection().get(RoomName.LIBRARY));
		library.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.LIBRARY, library.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox billiard = new JCheckBox("Billiard Room");
		billiard.setSelected(playerNotes.getRoomSection().get(RoomName.BILLIARDROOM));
		billiard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.BILLIARDROOM, billiard.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox dining = new JCheckBox("Dining Room");
		dining.setSelected(playerNotes.getRoomSection().get(RoomName.DININGROOM));
		dining.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.DININGROOM, dining.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox conservatory = new JCheckBox("Conservatory");
		conservatory.setSelected(playerNotes.getRoomSection().get(RoomName.CONSERVATORY));
		conservatory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.CONSERVATORY, conservatory.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox ballroom = new JCheckBox("Ballroom");
		ballroom.setSelected(playerNotes.getRoomSection().get(RoomName.BALLROOM));
		ballroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.BALLROOM, ballroom.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		JCheckBox kitchen = new JCheckBox("Kitchen");
		kitchen.setSelected(playerNotes.getRoomSection().get(RoomName.KITCHEN));
		kitchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rtmp = playerNotes.getRoomSection();
				rtmp.put(RoomName.KITCHEN, kitchen.isSelected());
				playerNotes.setRoomSection(rtmp);
			}
		});
		
		where.add(study);
		where.add(hall);
		where.add(lounge);
		where.add(library);
		where.add(billiard);
		where.add(dining);
		where.add(conservatory);
		where.add(ballroom);
		where.add(kitchen);
		
		return where;
	}
	
	/**
	 * 
	 * @param player
	 */
	protected void open(Player player) {	
		notebookFrame.pack();
		notebookFrame.setVisible(true);
		notebookButton.setEnabled(false);
	}
}

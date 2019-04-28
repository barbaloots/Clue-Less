package clueless.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clueless.gamelogic.Game;
import clueless.gamelogic.Player;
import clueless.gamelogic.TurnEnforcement;

/**
 * Class for general/main board UI and user interfacing
 * 
 * @author erinsmedley
 *
 */
public class Board {

	private Image backgroundImage;
	private Notebook notebook;
	private Player player;
	private Game game;
	
	/**
	 * Constructor
	 */
	public Board(Game game, Player player) {
		this.game = game;
		this.player = player;
		
		init();
	}
	
	private void init() {
		JFrame board = new JFrame();
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setExtendedState(JFrame.MAXIMIZED_BOTH);
		board.setTitle("Bar-Ba-Loots Clue-Less");


		
		try {
			backgroundImage = ImageIO.read(new File("ClueLess/src/main/Resources/ClueBoard.png"));
			JPanel panel = new JPanel(new BorderLayout()) {
		
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage, 0, 0, board.getWidth(), board.getHeight(), null);
				}
		
			};

			panel.setLayout(new FlowLayout());
			
			JButton handButton = new JButton("SHOW HAND");
			handButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new PlayerCardWindow(handButton, player);
				}
			});
			
			JButton notebookButton = new JButton("SHOW NOTEBOOK");
			notebookButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (notebook == null) {
						notebook = new Notebook(notebookButton, player);
						notebook.open(player);
					}
					else {
						notebook.open(player);
					}
				}
			});
			
			JButton endTurnButton = new JButton("END TURN");
			endTurnButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					TurnEnforcement.turnMade();
					endTurnButton.setEnabled(false);
				}
			});
			
			JButton suggestButton = new JButton("MAKE SUGGESTION");
			suggestButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SuggestAccuseParentWindow(board, false);
				}
			});
			
			JButton accuseButton = new JButton("MAKE ACCUSATION");
			accuseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SuggestAccuseParentWindow(board, true);
				}
			});
			
			panel.add(handButton);
			panel.add(notebookButton);
			panel.add(endTurnButton);
			panel.add(suggestButton);
			panel.add(accuseButton);
			
			board.add(panel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		board.pack();
		board.setVisible(true);
	}
}

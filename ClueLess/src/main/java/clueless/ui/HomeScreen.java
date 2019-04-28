package clueless.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clueless.gamelogic.Player;
import clueless.gamelogic.locationenums.LocationEnum;
import clueless.gamelogic.CharacterName;
import clueless.gamelogic.Game;

/**
 * Class for home screen UI
 * 
 * @author erinsmedley
 *
 */
public class HomeScreen extends JPanel {

	private Image backgroundImage;
	private Player player;
	private Game game;
	
	/**
	 * Constructor.
	 */
	public HomeScreen() {
		init();
	}
	
	/**
	 * Initializes home screen
	 * 
	 * @throws IOException 
	 */
	private void init() {
		JFrame home = new JFrame();
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		home.setExtendedState(JFrame.MAXIMIZED_BOTH);
		home.setTitle("Bar-Ba-Loots Clue-Less");


		
		try {
			backgroundImage = ImageIO.read(new File("ClueLess/src/main/Resources/CluePoster.png"));
			JPanel panel = new JPanel(new BorderLayout()) {
		
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage, 0, 0, home.getWidth(), home.getHeight(), null);
				}
		
			};
			
			BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
			panel.setLayout(box);
			panel.setBorder(new EmptyBorder(new Insets(250, 600, 200, 600)));
			
			JButton newGameButton = new JButton("NEW GAME") {
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(300, 50);
				}
			};
			newGameButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					home.setVisible(false);

					//TODO
					game = new Game(6);
					player = game.getPlayers().get(0);
					new Board(game, player);
				}
			});
			
			JButton continueGameButton = new JButton("CONTINUE GAME") {
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(300, 50);
				}
			};
			continueGameButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					home.setVisible(false);
					
					
				}
			});
			continueGameButton.setEnabled(false);
			
			JButton userProfileButton = new JButton("USER PROFILE") {
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(300, 50);
				}
			};
			JButton helpButton = new JButton("HELP/TUTORIAL") {
				@Override
				public Dimension getPreferredSize() {
					return new Dimension(300, 50);
				}
			};
			
			panel.add(newGameButton);
			panel.add(Box.createHorizontalStrut(1));
			panel.add(continueGameButton);
			panel.add(Box.createHorizontalStrut(1));
			panel.add(userProfileButton);
			panel.add(Box.createHorizontalStrut(1));
			panel.add(helpButton);
			
			home.add(panel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		home.pack();
		home.setVisible(true);
	}
}


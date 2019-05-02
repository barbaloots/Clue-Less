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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueless.gamelogic.Game;
import clueless.gamelogic.Player;
import clueless.gamelogic.TurnEnforcement;
import clueless.gamelogic.locationenums.LocationEnum;

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
	
	private JFrame board;
	private JPanel panel;
	
	private Image greenPiece;
	private Image mustardPiece;
	private Image peacockPiece;
	private Image plumPiece;
	private Image scarletPiece;
	private Image whitePiece;
	
	/**
	 * Constructor
	 */
	public Board(Game game, Player player) {
		this.game = game;
		this.player = player;
		
		init();
		initGamePieces();
	}
	
	private void init() {
		board = new JFrame();
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setExtendedState(JFrame.MAXIMIZED_BOTH);
		board.setTitle("Bar-Ba-Loots Clue-Less");


		
		try {
			backgroundImage = ImageIO.read(new File("ClueLess/src/main/Resources/ClueBoard.png"));
			panel = new JPanel(new BorderLayout()) {
		
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage, 0, 0, board.getWidth(), board.getHeight(), null);
				}
		
			};

			JPanel buttonsPanel = new JPanel(new FlowLayout());
			
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
			
			buttonsPanel.add(handButton);
			buttonsPanel.add(notebookButton);
			buttonsPanel.add(endTurnButton);
			buttonsPanel.add(suggestButton);
			buttonsPanel.add(accuseButton);
			
			panel.add(buttonsPanel, BorderLayout.NORTH);
			
			board.add(panel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		board.pack();
		board.setVisible(true);
	}
	
	private void initGamePieces() {
		try {
			greenPiece = ImageIO.read(new File("ClueLess/src/main/Resources/GamePieces/GreenPiece.png"));
			mustardPiece = ImageIO.read(new File("ClueLess/src/main/Resources/GamePieces/MustardPiece.png"));
			peacockPiece = ImageIO.read(new File("ClueLess/src/main/Resources/GamePieces/PeacockPiece.png"));			
			plumPiece = ImageIO.read(new File("ClueLess/src/main/Resources/GamePieces/PlumPiece.png"));
			scarletPiece = ImageIO.read(new File("ClueLess/src/main/Resources/GamePieces/ScarletPiece.png"));	
			whitePiece = ImageIO.read(new File("ClueLess/src/main/Resources/GamePieces/WhitePiece.png"));
			
			JPanel piecesPanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {
					//g.drawImage(greenPiece, LocationEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getX(), LocationEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getY(), null);
					//g.drawImage(mustardPiece, LocationEnum.LOUNGETODININGROOMHALLWAY.getLocation().getX(), LocationEnum.LOUNGETODININGROOMHALLWAY.getLocation().getY(), null);
					//g.drawImage(peacockPiece, LocationEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getX(), LocationEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getY(), null);
					//g.drawImage(plumPiece, LocationEnum.STUDYTOLIBRARYHALLWAY.getLocation().getX(), LocationEnum.STUDYTOLIBRARYHALLWAY.getLocation().getY(), null);
					//g.drawImage(scarletPiece, LocationEnum.HALLTOLOUNGEHALLWAY.getLocation().getX(), LocationEnum.HALLTOLOUNGEHALLWAY.getLocation().getY(), null);
					//g.drawImage(whitePiece, LocationEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getX(), LocationEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getY(), null);
					
					g.drawImage(greenPiece, 566, 630, null);
					g.drawImage(mustardPiece, 940, 230, null);
					g.drawImage(peacockPiece, 437, 490, null);
					g.drawImage(plumPiece, 437, 220, null);
					g.drawImage(scarletPiece, 815, 90, null);
					g.drawImage(whitePiece, 815, 630, null);
				}
			};
			
			panel.add(piecesPanel);
			
			panel.revalidate();
			panel.repaint();
			board.revalidate();
			
		} catch (IOException e) {
			//TODO Audo-generated catch block
			e.printStackTrace();
		}
	}
}

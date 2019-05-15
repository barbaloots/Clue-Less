package clueless.ui;


import java.awt.BorderLayout;


import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import clueless.gamelogic.CustomOutputStream;
import clueless.gamelogic.Game;
import clueless.gamelogic.Player;
import clueless.gamelogic.TurnEnforcement;
import clueless.gamelogic.locationenums.LocationEnum;
import clueless.gamelogic.locationenums.MovementEnum;

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
	private PrintWriter clientOut;
	
	/**
	 * Constructor
	 */
	public Board(Game game, Player player, PrintWriter clientOut) {
		this.game = game;
		this.player = player;
		this.clientOut = clientOut;
		init();
		initGamePieces();
	}
	
	private void init() {
		board = new JFrame();
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setExtendedState(JFrame.MAXIMIZED_BOTH);
		board.setTitle("Bar-Ba-Loots Clue-Less");
		//board.setResizable(false);


		
		try {
			backgroundImage = ImageIO.read(new File("src/main/Resources/ClueBoard.png"));
			panel = new JPanel(new BorderLayout()) {
		
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage, 0, 0, board.getWidth(), board.getHeight(), null);
				}
		
			};
			
			panel.setPreferredSize(new Dimension(1424,777));

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
					clientOut.println("Done");
					//TurnEnforcement.turnMade();
					//endTurnButton.setEnabled(false);
				}
			});
			
			JButton suggestButton = new JButton("MAKE SUGGESTION");
			suggestButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SuggestAccuseParentWindow(board, false, clientOut);
				}
			});
			
			JButton accuseButton = new JButton("MAKE ACCUSATION");
			accuseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new SuggestAccuseParentWindow(board, true, clientOut);
				}
			});
			
			JButton move = new JButton("MOVE");
			move.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new MoveWindow(board, clientOut, player);
				}
			});
			
			buttonsPanel.add(handButton);
			buttonsPanel.add(notebookButton);
			buttonsPanel.add(endTurnButton);
			buttonsPanel.add(suggestButton);
			buttonsPanel.add(accuseButton);
			buttonsPanel.add(move);
			
			panel.add(buttonsPanel, BorderLayout.NORTH);
			
			//JScrollPane logPanel = new JScrollPane();
			//logPanel.setPreferredSize(new Dimension(300, 300));
			JTextArea logTextArea = new JTextArea(30, 20);
			logTextArea.setEditable(false);
			
			PrintStream printStream = new PrintStream(new CustomOutputStream(logTextArea));
			//System.setOut(printStream);
			//System.setErr(printStream);
					
			//logPanel.add(new JScrollPane(logTextArea));
			panel.add(new JScrollPane(logTextArea), BorderLayout.WEST);
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
			greenPiece = ImageIO.read(new File("src/main/Resources/GamePieces/GreenPiece.png"));
			mustardPiece = ImageIO.read(new File("src/main/Resources/GamePieces/MustardPiece.png"));
			peacockPiece = ImageIO.read(new File("src/main/Resources/GamePieces/PeacockPiece.png"));			
			plumPiece = ImageIO.read(new File("src/main/Resources/GamePieces/PlumPiece.png"));
			scarletPiece = ImageIO.read(new File("src/main/Resources/GamePieces/ScarletPiece.png"));	
			whitePiece = ImageIO.read(new File("src/main/Resources/GamePieces/WhitePiece.png"));
			
			String playerAbbreviation = player.getAbbreviation();
			
			/*switch(playerAbbreviation)
			{
			    case "PP": g.drawImage(plumPiece, MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getX(), MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getY(), null);
			    break;
			    case "CM": g.drawImage(mustardPiece, MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getX(), MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getY(), null);
			    break;
			    case "MS": 
			    	
			}*/
			
			JPanel piecesPanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(greenPiece, MovementEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getX(), MovementEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getY(), null);
					g.drawImage(mustardPiece, MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getX(), MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getY(), null);
					g.drawImage(peacockPiece, MovementEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getX(), MovementEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getY(), null);
					g.drawImage(plumPiece, MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getX(), MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getY(), null);
					g.drawImage(scarletPiece, MovementEnum.HALLTOLOUNGEHALLWAY.getLocation().getX(), MovementEnum.HALLTOLOUNGEHALLWAY.getLocation().getY(), null);
					g.drawImage(whitePiece, MovementEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getX(), MovementEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getY(), null);					
				}
			};
			/*
			JPanel greenPiecePanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(greenPiece, MovementEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getX(), MovementEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getY(), null);
				
				}
			};
			
			//panel.add(greenPiecePanel);
			board.add(greenPiecePanel);
			
			JPanel mustardPiecePanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {

					g.drawImage(mustardPiece, MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getX(), MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getY(), null);
				
				}
			};
			
			//panel.add(mustardPiecePanel);
			board.add(mustardPiecePanel);
			
			JPanel peacockPiecePanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {

					g.drawImage(peacockPiece, MovementEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getX(), MovementEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getY(), null);
				
				}
			};
			
			//panel.add(peacockPiecePanel);
			board.add(peacockPiecePanel);
			
			JPanel plumPiecePanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {

					g.drawImage(plumPiece, MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getX(), MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getY(), null);
				
				}
			};
			
			//panel.add(plumPiecePanel);
			board.add(plumPiecePanel);
			
			JPanel scarletPiecePanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {

					g.drawImage(scarletPiece, MovementEnum.HALLTOLOUNGEHALLWAY.getLocation().getX(), MovementEnum.HALLTOLOUNGEHALLWAY.getLocation().getY(), null);
				
				}
			};
			
			//panel.add(scarletPiecePanel);
			board.add(scarletPiecePanel);
			
			JPanel whitePiecePanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {

					g.drawImage(whitePiece, MovementEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getX(), MovementEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getY(), null);					
				}
			};
			
			//panel.add(whitePiecePanel);
			board.add(whitePiecePanel);
			*/
			
			panel.add(piecesPanel);
			panel.revalidate();
			panel.repaint();
			board.revalidate();
			
		} catch (IOException e) {
			//TODO Audo-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateGamePieces(String movement) {
		try {
			greenPiece = ImageIO.read(new File("src/main/Resources/GamePieces/GreenPiece.png"));
			mustardPiece = ImageIO.read(new File("src/main/Resources/GamePieces/MustardPiece.png"));
			peacockPiece = ImageIO.read(new File("src/main/Resources/GamePieces/PeacockPiece.png"));			
			plumPiece = ImageIO.read(new File("src/main/Resources/GamePieces/PlumPiece.png"));
			scarletPiece = ImageIO.read(new File("src/main/Resources/GamePieces/ScarletPiece.png"));	
			whitePiece = ImageIO.read(new File("src/main/Resources/GamePieces/WhitePiece.png"));
			
			JPanel piecesPanel = new JPanel(new BorderLayout()) {
				@Override
				public void paintComponent(Graphics g) {
					g.drawImage(greenPiece, MovementEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getX(), MovementEnum.CONSERVATORYTOBALLROOMHALLWAY.getLocation().getY(), null);
					g.drawImage(mustardPiece, MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getX(), MovementEnum.LOUNGETODININGROOMHALLWAY.getLocation().getY(), null);
					g.drawImage(peacockPiece, MovementEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getX(), MovementEnum.LIBRARYTOCONSERVATORYHALLWAY.getLocation().getY(), null);
					g.drawImage(plumPiece, MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getX(), MovementEnum.STUDYTOLIBRARYHALLWAY.getLocation().getY(), null);
					g.drawImage(scarletPiece, MovementEnum.HALLTOLOUNGEHALLWAY.getLocation().getX(), MovementEnum.HALLTOLOUNGEHALLWAY.getLocation().getY(), null);
					g.drawImage(whitePiece, MovementEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getX(), MovementEnum.BALLROOMTOKITCHENHALLWAY.getLocation().getY(), null);					
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
	
	public void openDisproveWindow()
	{
		new ProveSuggestionWindow(board, player, clientOut);
	}
}

package clueless.ui;

import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clueless.gamelogic.Player;

/**
 * Class for players' three cards window UI
 * 
 * @author erinsmedley
 *
 */
public class PlayerCardWindow {

	/**
	 * Constructor.
	 * 
	 * @param player
	 * 		determines which player's card window is being referenced
	 */
	public PlayerCardWindow(JButton handButton, Player player) {
		String card1 = player.getCurrentHand().get(0).getCardName();
		String card2 = player.getCurrentHand().get(1).getCardName();
		String card3 = player.getCurrentHand().get(2).getCardName();
		
		JFrame handFrame = new JFrame();
		handFrame.setAlwaysOnTop(true);
		handFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				handButton.setEnabled(true);
			}
		});
		JPanel hand = new JPanel();
		BoxLayout box = new BoxLayout(hand, BoxLayout.Y_AXIS);
		hand.setLayout(box);
		hand.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));

		JLabel label1 = new JLabel(new ImageIcon("src/main/Resources/Cards/" + card1 + ".png"));
		JLabel label2 = new JLabel(new ImageIcon("src/main/Resources/Cards/" + card2 + ".png"));
		JLabel label3 = new JLabel(new ImageIcon("src/main/Resources/Cards/" + card3 + ".png"));
		
		hand.add(label1);
		hand.add(label2);
		hand.add(label3);
		
		handFrame.add(hand);
		handFrame.pack();
		handFrame.setVisible(true);
		handButton.setEnabled(false);
	}
}

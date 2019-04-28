package clueless.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Parent window for the suggestion and accusation windows.
 * 
 * @author erinsmedley
 *
 */
public class SuggestAccuseParentWindow {

	/**
	 * Constructor for launching the suggestion or accusation windows.
	 * 
	 * @param accuse
	 * 		true if the player is making an accusation and false if player is making a suggestion
	 */
	public SuggestAccuseParentWindow(JFrame parent, Boolean accuse) {
		JDialog window = new JDialog(parent, "Make a" + (accuse ? "n Accusation" : " Suggestion"), true);
		window.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(box);
		panel.setPreferredSize(new Dimension(600, 250));
		
		JLabel whoLabel = new JLabel("Who?");		
		JPanel who = new JPanel(new GridLayout(1, 6));
		JRadioButton green = new JRadioButton("Green");
		JRadioButton mustard = new JRadioButton("Mustard");
		JRadioButton peacock = new JRadioButton("Peacock");
		JRadioButton plum = new JRadioButton("Plum");
		JRadioButton scarlet = new JRadioButton("Scarlet");
		JRadioButton white = new JRadioButton("White");
		who.add(green);
		who.add(mustard);
		who.add(peacock);
		who.add(plum);
		who.add(scarlet);
		who.add(white);
		
		JLabel whatLabel = new JLabel("What?");
		JPanel what = new JPanel(new GridLayout(1, 6));
		JRadioButton candlestick = new JRadioButton("Candlestick");
		JRadioButton knife = new JRadioButton("Knife");
		JRadioButton leadpipe = new JRadioButton("Lead Pipe");
		JRadioButton revolver = new JRadioButton("Revolver");
		JRadioButton rope = new JRadioButton("Rope");
		JRadioButton wrench = new JRadioButton("Wrench");
		what.add(candlestick);
		what.add(knife);
		what.add(leadpipe);
		what.add(revolver);
		what.add(rope);
		what.add(wrench);
		
		JLabel whereLabel = new JLabel("Where?");
		JPanel where = new JPanel(new GridLayout(3, 3));
		JRadioButton study = new JRadioButton("Study");
		JRadioButton hall = new JRadioButton("Hall");
		JRadioButton lounge = new JRadioButton("Lounge");
		JRadioButton library = new JRadioButton("Library");
		JRadioButton billiard = new JRadioButton("Billiard Room");
		JRadioButton dining = new JRadioButton("Dining Room");
		JRadioButton conservatory = new JRadioButton("Conservatory");
		JRadioButton ballroom = new JRadioButton("Ballroom");
		JRadioButton kitchen = new JRadioButton("Kitchen");
		where.add(study);
		where.add(hall);
		where.add(lounge);
		where.add(library);
		where.add(billiard);
		where.add(dining);
		where.add(conservatory);
		where.add(ballroom);
		where.add(kitchen);
		
		JButton submit = new JButton(accuse ? "Accuse" : "Suggest");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		});
		
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(submit);
		buttons.add(cancel);
		
		panel.add(whoLabel);
		panel.add(who);
		panel.add(whatLabel);
		panel.add(what);
		panel.add(whereLabel);
		panel.add(where);
		panel.add(buttons);
		
		window.add(panel);
		
		window.pack();
		window.setVisible(true);
	}
}

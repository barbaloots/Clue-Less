package clueless.ui;

import java.awt.Dimension;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import clueless.gamelogic.Accusation;
import clueless.gamelogic.RoomName;
import clueless.gamelogic.Suggestion;
import clueless.gamelogic.WeaponType;
import clueless.gamelogic.CharacterName;
import java.io.*;

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
	public SuggestAccuseParentWindow(JFrame parent, Boolean accuse, PrintWriter clientOut) {
		JDialog window = new JDialog(parent, "Make a" + (accuse ? "n Accusation" : " Suggestion"), true);
		window.setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		BoxLayout box = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(box);
		panel.setPreferredSize(new Dimension(600, 250));
		
		JLabel whoLabel = new JLabel("Who?");		
		JPanel who = new JPanel(new GridLayout(1, 6));
		ButtonGroup whoGroup = new ButtonGroup();
		JRadioButton green = new JRadioButton("Green");
		JRadioButton mustard = new JRadioButton("Mustard");
		JRadioButton peacock = new JRadioButton("Peacock");
		JRadioButton plum = new JRadioButton("Plum");
		JRadioButton scarlet = new JRadioButton("Scarlet");
		JRadioButton white = new JRadioButton("White");
		whoGroup.add(green);
		whoGroup.add(mustard);
		whoGroup.add(peacock);
		whoGroup.add(plum);
		whoGroup.add(scarlet);
		whoGroup.add(white);
		who.add(green);
		who.add(mustard);
		who.add(peacock);
		who.add(plum);
		who.add(scarlet);
		who.add(white);
		
		JLabel whatLabel = new JLabel("What?");
		JPanel what = new JPanel(new GridLayout(1, 6));
		ButtonGroup whatGroup = new ButtonGroup();
		JRadioButton candlestick = new JRadioButton("Candlestick");
		JRadioButton knife = new JRadioButton("Knife");
		JRadioButton leadpipe = new JRadioButton("Lead Pipe");
		JRadioButton revolver = new JRadioButton("Revolver");
		JRadioButton rope = new JRadioButton("Rope");
		JRadioButton wrench = new JRadioButton("Wrench");
		whatGroup.add(candlestick);
		whatGroup.add(knife);
		whatGroup.add(leadpipe);
		whatGroup.add(revolver);
		whatGroup.add(rope);
		whatGroup.add(wrench);
		what.add(candlestick);
		what.add(knife);
		what.add(leadpipe);
		what.add(revolver);
		what.add(rope);
		what.add(wrench);
		
		JLabel whereLabel = new JLabel("Where?");
		JPanel where = new JPanel(new GridLayout(3, 3));
		ButtonGroup whereGroup = new ButtonGroup();
		JRadioButton study = new JRadioButton("Study");
		JRadioButton hall = new JRadioButton("Hall");
		JRadioButton lounge = new JRadioButton("Lounge");
		JRadioButton library = new JRadioButton("Library");
		JRadioButton billiard = new JRadioButton("Billiard Room");
		JRadioButton dining = new JRadioButton("Dining Room");
		JRadioButton conservatory = new JRadioButton("Conservatory");
		JRadioButton ballroom = new JRadioButton("Ballroom");
		JRadioButton kitchen = new JRadioButton("Kitchen");
		whereGroup.add(study);
		whereGroup.add(hall);
		whereGroup.add(lounge);
		whereGroup.add(library);
		whereGroup.add(billiard);
		whereGroup.add(dining);
		whereGroup.add(conservatory);
		whereGroup.add(ballroom);
		whereGroup.add(kitchen);
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
				//String suggest = "AS";
				Suggestion suggestion;
				Accusation accusation;
				String who = "";
				for(AbstractButton button : Collections.list(whoGroup.getElements())) {
					if(button.isSelected()) {
						who = button.getText();
						break;
					}
				}
				
				String what = "";
				for(AbstractButton button : Collections.list(whatGroup.getElements())) {
					if(button.isSelected()) {
						what = button.getText();
						if(what.equalsIgnoreCase("Lead pipe"))
						{
							what = "leadpipe";
						}
						break;
					}
				}
				
				String where = "";
				for(AbstractButton button : Collections.list(whereGroup.getElements())) {
					if(button.isSelected()) {
						where = button.getText();
						break;
					}
				}
				
				if(accuse) {
					accusation = new Accusation(WeaponType.valueOf(what.toUpperCase()), RoomName.valueOf(where.toUpperCase()), CharacterName.valueOf(who.toUpperCase()));
					clientOut.println(accusation.toString());
				} else {
					suggestion = new Suggestion(WeaponType.valueOf(what.toUpperCase()), RoomName.valueOf(where.toUpperCase()), CharacterName.valueOf(who.toUpperCase()));
					clientOut.println(suggestion.toString());
					
				}
				
				JOptionPane.showMessageDialog(window, "You are " + (accuse ? "accusing " : "suggesting ") + who + " with the " + what + " in the " + where + ".");
				//suggest = suggest + "_" + who + "_" + what + "_" + where;
				window.dispose();

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

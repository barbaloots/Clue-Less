package clueless.ui;

import java.awt.Color;
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
import clueless.gamelogic.Player;
import java.io.*;



/**
 * Class for players' suggestion proof window UI
 * 
 * @author erinsmedley
 *
 */
public class ProveSuggestionWindow {

	public ProveSuggestionWindow(Player player, PrintWriter clientOut) 
	{
		JDialog window = new JDialog(parent, "Disprove the suggestion", true);
		window.setLocationRelativeTo(null);
		JPanel panel = new JPanel(new GridLayout(1,3));
		JLabel label = new JLabel("Please select a card");
		label.setForeground(Color.BLACK);
		panel.setPreferredSize(new Dimension(400, 400));
		ButtonGroup cardsInHand = new ButtonGroup();
		String firstCard = player.getCurrentHand().get(0).toString();
		String secondCard = player.getCurrentHand().get(1).toString();
		String thirdCard = player.getCurrentHand().get(2).toString();
		JRadioButton first = new JRadioButton(firstCard);
		JRadioButton second = new JRadioButton(secondCard);
		JRadioButton third = new JRadioButton(thirdCard);
		cardsInHand.add(first);
		cardsInHand.add(second);
		cardsInHand.add(third);
		panel.add(label);
		panel.add(first);
		panel.add(second);
		panel.add(third);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String selection = "";
				for(AbstractButton button : Collections.list(cardsInHand.getElements())) {
					if(button.isSelected()) {
						selection = button.getText();
						break;
					}
				}
				
				clientOut.println(selection);
				window.dispose();
			}
		}
	    );
		
		window.add(panel);
		window.pack();
		window.setVisible(true);
		
		
		
		
	}
}

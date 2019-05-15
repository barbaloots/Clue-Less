package clueless.ui;

import java.awt.Dimension;


import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.border.EmptyBorder;

import clueless.gamelogic.Accusation;
import clueless.gamelogic.RoomName;
import clueless.gamelogic.Suggestion;
import clueless.gamelogic.WeaponType;
import clueless.gamelogic.CharacterName;
import clueless.gamelogic.Player;
import clueless.gamelogic.Movement;
import clueless.gamelogic.Location;
import java.io.*;

public class MoveWindow 
{
	public MoveWindow(JFrame parent, PrintWriter clientOut, Player currentPlayer)
	{
		JDialog window = new JDialog(parent, "Choose a direction or click 'Secret Passage'", true);
		window.setLocationRelativeTo(null);
		
		//JPanel panel = new JPanel();
		JPanel buttons = new JPanel();
		BoxLayout box = new BoxLayout(buttons, BoxLayout.X_AXIS);
		buttons.setLayout(box);
		buttons.setPreferredSize(new Dimension(600, 250));
		
		JButton left = new JButton("Left");
		left.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int currentX = currentPlayer.getLocation().getX();
				int currentY = currentPlayer.getLocation().getY();
				
				int desiredX = currentX;
				int desiredY = currentY - 1;
				
				Movement movement = new Movement(new Location(desiredX, desiredY));
				clientOut.println(movement.toString());
				
				window.dispose();
				
			}
		}
		);
	
		JButton right = new JButton("Right");
		right.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int currentX = currentPlayer.getLocation().getX();
				int currentY = currentPlayer.getLocation().getY();
				
				int desiredX = currentX;
				int desiredY = currentY + 1;
				
				Movement movement = new Movement(new Location(desiredX, desiredY));
				clientOut.println(movement.toString());
				
				window.dispose();
				
			}
		}
		);
		
		JButton up = new JButton("Up");
		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int currentX = currentPlayer.getLocation().getX();
				int currentY = currentPlayer.getLocation().getY();
				
				int desiredX = currentX - 1;
				int desiredY = currentY;
				
				Movement movement = new Movement(new Location(desiredX, desiredY));
				clientOut.println(movement.toString());
				
				window.dispose();
				
			}
		}
		);
		
		JButton down = new JButton("Down");
		down.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int currentX = currentPlayer.getLocation().getX();
				int currentY = currentPlayer.getLocation().getY();
				
				int desiredX = currentX + 1;
				int desiredY = currentY;
				
				Movement movement = new Movement(new Location(desiredX, desiredY));
				clientOut.println(movement.toString());
				
				window.dispose();
				
			}
		}
		);
		
		buttons.setBorder(new EmptyBorder(new Insets(50,50,50,50)));
		buttons.add(left);
		buttons.add(right);
		buttons.add(up);
		buttons.add(down);
		window.add(buttons);
		window.pack();
		window.setVisible(true);
	}
}

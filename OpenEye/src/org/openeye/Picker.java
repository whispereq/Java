package org.openeye;
import java.awt.Color;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Picker {
	public static void assignPicker(JMenuBar jb, final JTextArea t) {
		JMenu m = new JMenu();
		m.setFont(OpenEye.menufont);
		m.setText("FontColors");
		m.setForeground(Color.blue);
		jb.add(m);
		JMenuItem white = new JMenuItem("White");
		white.setFont(OpenEye.menufont);
		JMenuItem black = new JMenuItem("Black");
		black.setFont(OpenEye.menufont);
		white.setBackground(Color.white);
		black.setBackground(Color.black);
		black.setForeground(Color.white);
		m.add(white);
		m.addSeparator();
		m.add(black);
		class white implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				t.setForeground(Color.WHITE);
			}
		}
		white.addActionListener(new white());
		
		class black implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				t.setForeground(Color.BLACK);
			}
		}
		black.addActionListener(new black());
	}
}

package org.openeye;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

public class OpenEye {
	public static JTextArea t = new JTextArea();
	public static BufferedWriter out = null;
	public static String savePos = null;
	public static Font bigF = new Font("Arial", Font.PLAIN, 40);
	public static Font mediumF = new Font("Arial", Font.PLAIN, 30);
	public static Font smallF = new Font("Arial", Font.PLAIN, 18);
	public static Font menufont = new Font("Arial", Font.PLAIN, 30);
	public OpenEye(String title) {
		// Frame Definition
		final JFrame f = new JFrame();
		f.setSize(1000, 800);
		f.setTitle(title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(new BorderLayout());
		f.getContentPane().setBackground(Color.DARK_GRAY);
	
		// Components
		final JMenuBar jb = new JMenuBar();
		final JMenu file = new JMenu();
		final JMenu customization = new JMenu();
		final JMenuItem newFile = new JMenuItem();
		final JMenuItem close = new JMenuItem();
		final JMenuItem save = new JMenuItem();
		final JMenuItem dark = new JMenuItem();
		final JMenuItem light = new JMenuItem();
		
		// Setting up
		t.addMouseListener(new SyntaxHighlighter());
		t.setBackground(Color.gray);
		t.setVisible(true);
		t.setFont(new Font("Arial", Font.PLAIN, 70));
		jb.setVisible(true);
		jb.setBackground(Color.DARK_GRAY);
		file.setText("File");
		file.setForeground(Color.WHITE);
		file.setFont(menufont);
		save.setText("Save File As");
		save.setFont(menufont);
		save.setBackground(Color.GRAY);
		save.setForeground(Color.white);
		customization.setText("Layouts");
		customization.setForeground(Color.white);
		customization.setFont(menufont);
		newFile.setText("New File ..");
		newFile.setBackground(Color.GRAY);
		newFile.setFont(menufont);
		newFile.setForeground(Color.white);
		close.setText("Close OpenEye");
		close.setFont(menufont);
		close.setForeground(Color.white);
		close.setBackground(Color.gray);
		light.setText("Light Composition");
		light.setFont(menufont);
		dark.setText("Dark Composition");
		dark.setFont(menufont);
		
		// Including
		f.add(jb, BorderLayout.NORTH);
		jb.add(file);
		jb.add(customization);
		file.add(newFile);
		file.addSeparator();
		file.add(close);
		file.addSeparator();
		file.add(save);
		customization.add(light);
		customization.addSeparator();
		customization.add(dark);
		customization.addSeparator();
		f.add(t, BorderLayout.CENTER);
		
		// Listeners
		class newF implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure, you want to create a new file without saving the current content?");
				if (i == 1) {
					// NO
					System.out.println("[ACTION/NEW] Deined by user.");
				} else if (i == 2){
					// CANCEL
					System.out.println("[ACTION/NEW] Cancelled by user.");
				} else {
					// YES
					System.out.println("[ACTION/NEW] Approved by user.");
					new OpenEye("OpenEye Editor - SubFrame Parent: F");
				}
			}
		}
		newFile.addActionListener(new newF());
		
		class close implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit OPENEYE without saving?");
				if (i == 1) {
					// NO
					System.out.println("[ACTION/CLOSE] Deined by user.");
				} else if (i == 2){
					// CANCEL
					System.out.println("[ACTION/CLOSE] Cancelled by user.");
				} else {
					// YES
					System.out.println("[ACTION/CLOSE] Approved by user.");
					System.exit(0);
				}
			}
		}
		close.addActionListener(new close());
		
		class save implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (savePos == null) {
						JFileChooser s = new JFileChooser();
						int i = s.showSaveDialog(null);
						if (i == JFileChooser.APPROVE_OPTION) {
							try {
								out = new BufferedWriter(new FileWriter(s.getSelectedFile().getPath(), true));
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							try {
									out.write(t.getText());
									System.out.println("[ACTION/SAVE] Approved");
									JOptionPane.showMessageDialog(null, "Saved to: "+s.getSelectedFile().getPath());
									savePos = s.getSelectedFile().getPath().toString();
									f.setTitle("OpenEye - "+save);
									out.close();
									out = new BufferedWriter(new FileWriter(new File(savePos)));
									System.out.println("[ACTION/SAVE] Approved (read from savePos = "+savePos+")");
									JOptionPane.showMessageDialog(null, "Saved to latest location.");
									f.setTitle("OpenEye - "+save);
									out.close();
						} catch (IOException e1) {
							e1.printStackTrace();
							}
						} else {
							System.out.println("[ACTION/SAVE] Deined by an User");
						}
					}
				
				}
			}
		save.addActionListener(new save());
		
		class Window implements WindowListener {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int i = JOptionPane.showConfirmDialog(null , "Do you want to save file before exiting?");
				if (i == 1) {
					// NO
					System.exit(0);
				} else if (i == 2){
					// CANCEL
					System.out.println("[ACTION/CLOSE] Cancelled by user.");
				} else {
					// YES
					JFileChooser s = new JFileChooser();
					int in = s.showSaveDialog(null);
					if (in == JFileChooser.APPROVE_OPTION) {
						BufferedWriter out = null;
						try {
							out = new BufferedWriter(new FileWriter(s.getSelectedFile().getPath()));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							out.write(t.getText());
							System.out.println("[ACTION/SAVE] Approved");
							JOptionPane.showMessageDialog(null, "Saved to: "+s.getSelectedFile().getPath());
							out.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						System.out.println("[ACTION/SAVE] Deined");
					}
					System.exit(0);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
		f.addWindowListener(new Window());
		
		class DarkLayout implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				close.setForeground(Color.white);
				close.setBackground(Color.gray);
				save.setBackground(Color.GRAY);
				save.setForeground(Color.white);
				t.setBackground(Color.gray);
				t.setForeground(Color.white);
				jb.setBackground(Color.DARK_GRAY);
				newFile.setBackground(Color.gray);
				newFile.setForeground(Color.white);
				file.setBackground(Color.gray);
				file.setForeground(Color.white);
				customization.setBackground(Color.gray);
				customization.setForeground(Color.white);
				light.setBackground(Color.gray);
				light.setForeground(Color.white);
				dark.setBackground(Color.GRAY);
				dark.setForeground(Color.white);
			}
		}
		dark.addActionListener(new DarkLayout());
		
		class LightLayout implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				close.setForeground(Color.black);
				close.setBackground(Color.white);
				save.setBackground(Color.white);
				save.setForeground(Color.black);
				t.setBackground(Color.WHITE);
				t.setForeground(Color.BLACK);
				jb.setBackground(Color.white);
				jb.setForeground(Color.black);
				newFile.setBackground(Color.white);
				newFile.setForeground(Color.BLACK);
				file.setBackground(Color.white);
				file.setForeground(Color.black);
				customization.setBackground(Color.white);
				customization.setForeground(Color.BLACK);
				light.setBackground(Color.white);
				light.setForeground(Color.black);
				dark.setBackground(Color.white);
				dark.setForeground(Color.black);
			}
		}
		light.addActionListener(new LightLayout());
		
		Picker.assignPicker(jb, t);
		
		f.setVisible(true);
		}
	}
}

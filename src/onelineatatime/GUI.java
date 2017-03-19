package onelineatatime;

import javax.swing.*;

/**
 * GUI Provides the main JFrame for the graphical application.
 * Includes method to change which JPanel is currently shown in the main frame
 * Note that this is mostly a skeleton and code for what's normally shown on screen is in the other GUI classes.
 * 
 * @author Max Körlinge
 * @see	GUIStartPage
 */
public class GUI extends JFrame{

	public GUI() {
		
		setTitle("One Line at a Time");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new GUIStartPage(this));
		pack();
		setVisible(true);
	}
	
	/**
	 * Changes which JPanel is currently displayed on screen.
	 * @param newPanel	a JPanel that fills the GUI JFrame (completely), normally one of the other GUIclasses.
	 * @see	GUIInputMode
	 * @see GUIReaderMode
	 */
	public void changePanel(JPanel newPanel) {
		getContentPane().removeAll();
		add(newPanel);
		validate();
		repaint();
	}
}
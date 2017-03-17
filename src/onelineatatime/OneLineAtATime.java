package onelineatatime;

import javax.swing.*;

/* Starts program */
public class OneLineAtATime {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUI gui = new GUI();
			}
		});

	}
}

/*
 * TODO - Now delimiter (. or ?) isnt shown in the lines output - Implement the
 * GOTO button (now does nothing) - Implement switching lines with keyboard
 * arrows
 */
package onelineatatime;

import javax.swing.*;

/**
 * One Line at a Time is a small program which lets you read long texts one line at a time.
 * @author Max Körlinge
 * @see GUI
 */
public class OneLineAtATime {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});

	}
}
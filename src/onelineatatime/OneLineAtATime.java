package onelineatatime;
import javax.swing.SwingUtilities;

/* Starts program */
public class OneLineAtATime {

	public static void main(String[] args) {

		GUI gui = new GUI();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gui.guiInputMode();
			}
		});
	}

}

/* TODO
		- Now delimiter (. or ?) isnt shown in the lines output
		- Implement the GOTO button (now does nothing)
		- Implement switching lines with keyboard arrows
*/
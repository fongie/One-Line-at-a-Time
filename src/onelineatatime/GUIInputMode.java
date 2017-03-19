package onelineatatime;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Provides the JPanel which is displayed when we need to input text to enter text insert mode.
 * @author Max Körlinge
 * @see	GUI
 *
 */
public class GUIInputMode extends JPanel {
	
	private JTextArea textField; // text field to paste in at init
	private JButton startBtn;
	
	/**
	 * Constructor
	 * @param mainWindow Needed to display itself on main frame
	 */
	public GUIInputMode(GUI mainWindow) {
		
		setBackground(Constants.readerBg);
		setLayout(new BorderLayout());
		setPreferredSize(Constants.mainWindowsize);
		// setup the text field for text input
		textField = new JTextArea("Insert text to be read here, then click Start.", 50, 150); // in practice sets window size
		textField.setEditable(true);
		textField.setLineWrap(true);
		textField.setWrapStyleWord(true);

		// setup button to start reading
		startBtn = new JButton("Start Reading");
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.changePanel(new GUIReaderMode(textField.getText()));
			}
		});

		// add panels to container and window
		add(textField, BorderLayout.CENTER);
		add(startBtn, BorderLayout.SOUTH);
	}
}

package onelineatatime;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

/**
 * Provides the JPanel where the text is read one line at a time, using buttons or arrow keys.
 * @author Max Körlinge
 * @see GUI
 *
 */
public class GUIReaderMode extends JPanel {

	// essential non-aestethical objects
	private TextReader reader;
	private PDFHandler pdf;
	private int mode;
	private ActionListener keyListener;
	protected String RIGHT_ARROW = "RIGHT";
	protected String LEFT_ARROW = "LEFT";

	// jcomponents in panel
	private JLabel textLine;
	private JButton prevBtn;
	private JButton nextBtn;
	private JPanel southPanel; // for movement buttons
	private JPanel northPanel; // for other buttons

	/**
	 * Constructor for PDF MODE, where text is fetched from a PDF.
	 * The pdf text is fed into a TextReader object, which handles dividing and working with the text.
	 * @param pdf	An instance of PDFHandler class with a valid pdf file to read from
	 * @see PDFHandler
	 * @see TextReader
	 */
	public GUIReaderMode(PDFHandler pdf) {
		mode = 0;
		this.pdf = pdf;
		this.reader = new TextReader(this.pdf.nextPage());
		this.keyListener = new ArrowKeyListener();
		setupPanel();
		setFocusable(true);
		setPreferredSize(Constants.mainWindowsize);

		registerKeyboardAction(keyListener, RIGHT_ARROW, KeyStroke.getKeyStroke(RIGHT_ARROW),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(keyListener, LEFT_ARROW, KeyStroke.getKeyStroke(LEFT_ARROW),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	/**
	 * Constructor for TEXT INPUT MODE, where text is manually copied and pasted into the program.
	 * The text is fed into a TextReader object, which handles dividing and working with the text.
	 * @param inputtext	The text to be read one line at a time, as a String object.
	 * @see TextReader
	 */
	public GUIReaderMode(String inputtext) {
		mode = 1;
		this.reader = new TextReader(inputtext);
		this.keyListener = new ArrowKeyListener();
		setupPanel();
		setFocusable(true);
		setPreferredSize(Constants.mainWindowsize);

		registerKeyboardAction(keyListener, RIGHT_ARROW, KeyStroke.getKeyStroke(RIGHT_ARROW),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
		registerKeyboardAction(keyListener, LEFT_ARROW, KeyStroke.getKeyStroke(LEFT_ARROW),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	/**
	 * Sets up the design of the JPanel in the same way for the different constructors.
	 * Change here to change the design of the reader view
	 * @see GUIReaderMode(String inputtext)
	 * @see GUIReaderMode(PDFHandler pdf)
	 */
	private void setupPanel() {
		setBackground(Constants.readerBg);
		setLayout(new BorderLayout());

		// create JLabel that displays current line
		textLine = new JLabel(Constants.lineCSS + "Use arrow keys or buttons to read forward and backward</p></html>");
		textLine.setHorizontalAlignment(SwingConstants.CENTER);
		add(textLine, BorderLayout.CENTER);

		// create button panels and add to container
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		southPanel.setBackground(Constants.readerBg);
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		northPanel.setBackground(Constants.readerBg);
		add(southPanel, BorderLayout.SOUTH);
		add(northPanel, BorderLayout.NORTH);

		// create buttons
		prevBtn = new JButton("prev <-");
		prevBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		prevBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPrevLine();
			}
		});
		nextBtn = new JButton("next ->");
		nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showNextLine();
			}
		});
		southPanel.add(prevBtn);
		southPanel.add(nextBtn);
	}

	/**
	 * Shows the next textline on the screen
	 */
	private void showNextLine() {
		try {
			textLine.setText(Constants.lineCSS + reader.getNextLine() + Constants.endLineCSS);
		} catch (NoNextLineException ex) {
			// if pdf mode get next pdf page, else its finished
			if (mode == 0) {
				reader = new TextReader(pdf.nextPage());
			} else {
				textLine.setText(Constants.lineCSS + "FINISHED" + Constants.endLineCSS);
			}
		}
	}

	/**
	 * Shows the previous textline on the screen
	 */
	private void showPrevLine() {
		textLine.setText(Constants.lineCSS + reader.getPreviousLine() + Constants.endLineCSS);
	}

	/**
	 * The keylistener for the arrow keys
	 */
	private class ArrowKeyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			String cmd = (String) ae.getActionCommand();
			if (cmd.equals(RIGHT_ARROW)) {
				showNextLine();
			} else if (cmd.equals(LEFT_ARROW)) {
				showPrevLine();
			}
		}
	}
}

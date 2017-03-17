package onelineatatime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* This is the GUI and effectively main part of the program */

public class GUI {

	/*
	 * FOR START AND INPUT SCREEN
	 */
	private JFrame window; // mainframe + main container
	private JPanel container;
	private JTextArea textField; // text field to paste in at init
	private JButton startBtn;

	/*
	 * FOR READER MODE
	 */
	// reader object that ships the text lines
	private TextReader reader;
	private JLabel textLine;
	private JButton prevBtn;
	private JButton nextBtn;
	private JButton goToLineBtn;
	private JPanel southPanel; // for movement buttons
	private JPanel northPanel; // for other buttons

	public GUI() {
		// main window and container
		window = new JFrame();
		// window.setSize(new Dimension(1200,800));
		window.setTitle("One Line at a Time");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		container = new JPanel(new BorderLayout());

		JButton pdfmodeBtn = new JButton("PDF Mode");
		JButton inputmodeBtn = new JButton("Text Mode");
		pdfmodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PDFHandler pdf = new PDFHandler("C:\\Users\\Max\\Desktop\\Programmering\\Java\\OneLineAtATime\\pdf\\test.pdf", 5, 10);
				startReading(pdf.nextPage());
			}
		});
		inputmodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiInputMode();
			}
		});
		
		container.add(pdfmodeBtn, BorderLayout.CENTER);
		container.add(inputmodeBtn,BorderLayout.SOUTH);
		window.add(container);
		window.pack();
		window.setVisible(true);;
	}
	

	public void guiInputMode() {
		container.removeAll();
		// setup the text field for text input
		textField = new JTextArea("Insert text to be read here, then click Start.", 50, 150); // in practice sets window size
		textField.setEditable(true);
		textField.setLineWrap(true);
		textField.setWrapStyleWord(true);

		// setup button to start reading
		startBtn = new JButton("Start Reading");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startReading(textField.getText());
			}
		});

		// add panels to container and window
		container.add(textField, BorderLayout.CENTER);
		container.add(startBtn, BorderLayout.SOUTH);
		window.add(container);
		window.pack();
		window.setVisible(true);
	}

	private void guiReaderMode() {

		// set bgcolor and remove old objects
		container.setBackground(Constants.readerBg);
		// container.remove(textField);
		// container.remove(startBtn);
		container.removeAll();

		// create JLabel that displays current line
		textLine = new JLabel(Constants.lineCSS + "Use arrow keys or buttons to read forward and backward</p></html>");
		textLine.setHorizontalAlignment(SwingConstants.CENTER);
		container.add(textLine, BorderLayout.CENTER);

		// create button panels and add to container
		southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		southPanel.setBackground(Constants.readerBg);
		northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
		northPanel.setBackground(Constants.readerBg);
		container.add(southPanel, BorderLayout.SOUTH);
		container.add(northPanel, BorderLayout.NORTH);

		// create buttons
		prevBtn = new JButton("prev <-");
		prevBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textLine.setText(Constants.lineCSS + reader.getPreviousLine() + Constants.endLineCSS);
			}
		});

		nextBtn = new JButton("next ->");
		nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textLine.setText(Constants.lineCSS + reader.getNextLine() + Constants.endLineCSS);
			}
		});

		goToLineBtn = new JButton("Go to");
		goToLineBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

		southPanel.add(prevBtn);
		southPanel.add(nextBtn);
		northPanel.add(goToLineBtn);

		window.pack();
		// update gui
		window.setVisible(true);
	}

	private void startReading(String inputText) {
		// create reader object to feed lines from
		reader = new TextReader(inputText);
		// put GUI in read mode
		guiReaderMode();
	}
}

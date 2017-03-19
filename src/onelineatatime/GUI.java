package onelineatatime;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/* This is the GUI and effectively main part of the program */

public class GUI {

	//0 for pdf 1 for input
	private int mode;
	/*
	 * FOR START AND INPUT SCREEN
	 */
	private JFrame window; // mainframe + main container
	private JPanel container;
	private JTextArea textField; // text field to paste in at init
	private JButton startBtn;

	/*
	 * FOR PDF MODE
	 */
	private int pageStart = 0;
	private PDFHandler pdf;
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
		container.setPreferredSize(new Dimension(600,600));

		// start window where you choose pdf more or text mode
		JPanel btnpanel = new JPanel();
		JButton pdfmodeBtn = new JButton("PDF Mode");
		JButton inputmodeBtn = new JButton("Text Mode");
		pdfmodeBtn.setPreferredSize(Constants.startbtnsize);
		inputmodeBtn.setPreferredSize(Constants.startbtnsize);
		
		
		// layout to choose pdf and which page to start at
		JPanel pdfPageChoser = new JPanel();
		pdfPageChoser.setLayout(new BoxLayout(pdfPageChoser, BoxLayout.PAGE_AXIS));
		pdfPageChoser.setPreferredSize(Constants.startbtnsize);
		JPanel pagestartwrapper = new JPanel();
		pagestartwrapper.setPreferredSize(new Dimension(Constants.startbtnsize.width, Constants.startbtnsize.height/2));
		JLabel pdftext = new JLabel();
		pdftext.setText("Pagenr Start:");
		JTextArea pdfpage = new JTextArea("1");
		pdfpage.setEditable(true);
		pagestartwrapper.setLayout(new BoxLayout(pagestartwrapper, BoxLayout.LINE_AXIS));
		pagestartwrapper.add(pdftext);
		pagestartwrapper.add(pdfpage);

		JButton openfileBtn = new JButton("Open PDF");
		pdfpage.setPreferredSize(new Dimension(Constants.startbtnsize.width/2, Constants.startbtnsize.height/2));
		openfileBtn.setPreferredSize(new Dimension(Constants.startbtnsize.width, Constants.startbtnsize.height/2));
		pdfPageChoser.add(pagestartwrapper);
		pdfPageChoser.add(openfileBtn);
		
		pdfmodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			btnpanel.remove(pdfmodeBtn);
			btnpanel.add(pdfPageChoser);
			window.setVisible(true);
		}
		});
		// add btn actionlisteners. pdf one opens an "open file" dialogue
		openfileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				pageStart = Integer.parseInt(pdfpage.getText()) - 1;
				} catch (NumberFormatException err) {
					pdfpage.setText("Use Valid Nr");
					return;
				}
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(fc);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					pdf = new PDFHandler(file, pageStart);
					mode = 0;
					startReading(pdf.nextPage());
				} else {
					pdfpage.setText("Interrupted/Failed");
					System.out.println("Failed to choose pdf from menu");
				}
			}
		});
		inputmodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guiInputMode();
			}
		});
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.PAGE_AXIS));
		wrapper.add(Box.createVerticalStrut(250));
		btnpanel.add(inputmodeBtn);
		btnpanel.add(pdfmodeBtn);
		wrapper.add(btnpanel, BorderLayout.CENTER);
		wrapper.add(Box.createVerticalStrut(250));
		container.add(wrapper);
		
		//final add and show gui
		window.add(container);
		window.pack();
		window.setVisible(true);;
	}
	

	public void guiInputMode() {
		mode = 1;
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
		container.setPreferredSize(new Dimension(1200,800));

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
				showPrevLine();
			}
		});

		nextBtn = new JButton("next ->");
		nextBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					showNextLine();
			}
		});

		goToLineBtn = new JButton("Go to");
		goToLineBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

		southPanel.add(prevBtn);
		southPanel.add(nextBtn);
		northPanel.add(goToLineBtn);

		//keylistener to use arrow keys
		container.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) { return; }
			
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_LEFT:
					System.out.println("PRESS!");
					showPrevLine();
					break;
				case KeyEvent.VK_RIGHT:
					showNextLine();
				}
			}
			
			public void keyReleased(KeyEvent e) {return;}
		});

		container.setFocusable(true);
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
	
	/* For reader mode, goes to next line
	 */
	private void showNextLine() {
		try {
			textLine.setText(Constants.lineCSS + reader.getNextLine() + Constants.endLineCSS);
		} catch (NoNextLineException ex) {
			//if pdf mode get next pdf page, else its finished
			if(mode == 0) {
				reader = new TextReader(pdf.nextPage());
			} else {
				textLine.setText(Constants.lineCSS + "FINISHED" + Constants.endLineCSS);
			}
		}
	}
	private void showPrevLine() {
		textLine.setText(Constants.lineCSS + reader.getPreviousLine() + Constants.endLineCSS);
	}
	
}

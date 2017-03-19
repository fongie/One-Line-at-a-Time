package onelineatatime;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Provides the initial startpage to the main GUI frame, where you choose PDF or Text insert mode.
 * @author Max Körlinge
 * @see GUI
 *
 */
public class GUIStartPage extends JPanel {
	
	// attributes and objects
	private int pageStart;
	
	//Jcomponents
	private JPanel btnpanel;
	private JButton pdfmodeBtn;
	private JButton inputmodeBtn;
	
	private JPanel openpdfPane;
	private JLabel pdfLabel;
	private JTextField startpageInput;
	private JButton openfileBtn;

	/**
	 * Constructs a JPanel with two main buttons, to choose PDF or Text Insert mode.
	 * @param mainWindow	Takes the main frame (GUI) as parameter, to be able to call its changePanel method. Without this, this panel will not be displayed in the main frame.
	 */
	public GUIStartPage(GUI mainWindow) {
		setPreferredSize(Constants.mainWindowsize);
		
		/* Main buttons and their panels layout
		 * 
		 */
		btnpanel = new JPanel();
		pdfmodeBtn = new JButton("PDF Mode");
		inputmodeBtn = new JButton("Text Mode");
		pdfmodeBtn.setPreferredSize(Constants.startBtnsize);
		inputmodeBtn.setPreferredSize(Constants.startBtnsize);
		inputmodeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.changePanel(new GUIInputMode(mainWindow));
			}
		});
		pdfmodeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnpanel.remove(pdfmodeBtn);
				btnpanel.add(openpdfPane);
				btnpanel.validate();
				btnpanel.repaint();
			}
		});
		btnpanel.add(inputmodeBtn);
		btnpanel.add(pdfmodeBtn);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		int space = Constants.mainWindowsize.height/2 - Constants.startBtnsize.height/2;
		add(Box.createVerticalStrut(space));
		add(btnpanel, BorderLayout.CENTER);

		/* PDF open/page start panel, appears when you click PDF Mode button
		 * 
		 */
		openpdfPane = new JPanel();
		openpdfPane.setLayout(new BorderLayout());
		openpdfPane.setPreferredSize(Constants.startBtnsize);

		pdfLabel = new JLabel();
		pdfLabel.setText("Start reading on page:");
		pdfLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		startpageInput = new JTextField("1");
		startpageInput.setEditable(true);
		startpageInput.setHorizontalAlignment(SwingConstants.CENTER);
		startpageInput.setPreferredSize(new Dimension(Constants.startBtnsize.width / 2, Constants.startBtnsize.height / 2));

		//opens a file chooser dialogue, only if you input a valid number in startpageInput
		openfileBtn = new JButton("Open PDF");
		openfileBtn.setPreferredSize(new Dimension(Constants.startBtnsize.width, Constants.startBtnsize.height / 2));
		openfileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					pageStart = Integer.parseInt(startpageInput.getText()) - 1;
				} catch (NumberFormatException err) {
					startpageInput.setText("Use Valid Nr");
					return;
				}
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(fc);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					mainWindow.changePanel(new GUIReaderMode(new PDFHandler(file, pageStart)));
				} else {
					startpageInput.setText("Interrupted/Failed");
					System.out.println("Failed to choose pdf from menu");
				}
			}
		});
		openpdfPane.add(pdfLabel, BorderLayout.NORTH);
		openpdfPane.add(startpageInput, BorderLayout.CENTER);
		openpdfPane.add(openfileBtn, BorderLayout.SOUTH);
		/* end open PDF panel
		 * 
		 */
	}
}
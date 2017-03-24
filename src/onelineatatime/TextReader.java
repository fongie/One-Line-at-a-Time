package onelineatatime;
/* This class handles the text splitting and sending the text lines to GUI */

/**
 * Handles splitting and processing the text, and returning them as lines to be read one at a time.
 * @author Max Körlinge
 *
 */
public class TextReader {

	private String text;
	private String[] lines;
	private int totalLines = 0;
	private int lineCounter = 0;
	private boolean lastReturnedPrevious = false;

	/**
	 * Constructor, splits the text on instantiating the object.
	 * @param textInput	The text to be processed.
	 */
	public TextReader(String textInput) {
		text = textInput;

		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '.' || text.charAt(i) == '?')
				totalLines++;
		}

		lines = text.split("(\\.)|(\\?)"); // should get us an array with lines
											// split by . or ?
	}

	/**
	 * @return	The next line in the text.
	 * @throws NoNextLineException	When there are no more lines to send, this exception is thrown.
	 * @see NoNextLineException
	 */
	public String getNextLine() throws NoNextLineException {
		if (lastReturnedPrevious) {
			lineCounter++;
			lastReturnedPrevious = false;
		}
		if (lineCounter == totalLines) {
			throw new NoNextLineException();
		} else {
			return lines[lineCounter++] + ". ";
		}
	}

	/**
	 * @return The previous line in the text.
	 */
	public String getPreviousLine() {
		if (lineCounter < 1) {
			return Constants.noPreviousLine;
		}
			lastReturnedPrevious = true;
			lineCounter--;
			return lines[--lineCounter] + ". ";
	}
}
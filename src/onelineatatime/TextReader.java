package onelineatatime;
/* This class handles the text splitting and sending the text lines to GUI */

public class Reader {

	private String text;
	private String[] lines;
	private int totalLines = 0;
	private int lineCounter = 0;
	
	private String endText = "FINISHED";
	
	public Reader(String textInput) {
		text = textInput;
		
		
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '.' || text.charAt(i) == '?')
				totalLines++;
		}
		
		lines = text.split("(\\.)|(\\?)");	//should get us an array with lines split by . or ?
	}
	
	public String getNextLine() {
		if (lineCounter == totalLines)
			return endText;
		
		return lines[lineCounter++];
	}
	
	public String getPreviousLine() {
		if (!(lineCounter > 0))			// if you press prev line while at line 0, nothing happens
			return lines[lineCounter];
		return lines[--lineCounter];
	}
}
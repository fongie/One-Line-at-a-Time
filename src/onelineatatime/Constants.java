package onelineatatime;

import java.awt.Color;
import java.awt.Dimension;

/** Allows to easily change parts of the design and layout
 *  This class is never instantiated, it is only used for static constants.
 * 
 */
public final class Constants {

	//for GUIStartPage
	public static final Dimension mainWindowsize = new Dimension(1200,800);
	public static final Dimension startBtnsize = new Dimension(200,100);

	//for GUIReaderMode
	public static final Color readerBg = Color.BLACK;
	public static final String textColor = "#A0A0A0";
	public static final String lineCSS = "<html><body style='width: 800px;'><p style='color: " + textColor
			+ ";text-align:center; font-size:32px;'>";
	public static final String endLineCSS = "</p></body></html>";
	public static final int minLineLength = 70;
	public static final String noPreviousLine = "ASDLKJERJKFKJKERJKWKWQKJEK";

}

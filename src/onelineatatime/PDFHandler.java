package onelineatatime;

import java.util.*;
import java.io.*;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.*;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class PDFHandler {
	
	private File pdf;
	private PDDocument pdd;
	private PDFTextStripper pdfreader;
	private Splitter splitter;
	private List<PDDocument> pdf_pages;
	private Iterator<PDDocument> page_iterator;

	/* The constructor takes a filename, page start, page end. It uses Apache PDFBox to load the pdf,
	 * split it into pages, we put the pages in a list and only continue with the subset of pages that we want,
	 * and create an iterator.
	 */
	public PDFHandler(File pdf, int page_start) {
		try {
			this.pdf = pdf;
			this.pdd = PDDocument.load(pdf);
			this.pdfreader = new PDFTextStripper();
			this.splitter = new Splitter();
			this.pdf_pages = splitter.split(pdd);
			this.page_iterator = pdf_pages.listIterator(page_start);

		} catch(InvalidPasswordException e) {
			System.out.println("PDF is encrypted?");
			e.printStackTrace();

		} catch(IOException e) {
			System.out.println("File not found");
			e.printStackTrace();

		} catch(IndexOutOfBoundsException e) {
			System.out.println("PDF page indexes out of bounds");
			e.printStackTrace();
		}
	}
	
	public String nextPage() {
		try { 
			if (!page_iterator.hasNext())
				return "FINISHED.";

			return pdfreader.getText(page_iterator.next());
		} catch(IOException e) {
			return "IOException trying to fetch next PDF page";
		}
	}
}

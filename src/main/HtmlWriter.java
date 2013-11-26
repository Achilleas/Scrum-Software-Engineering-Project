package main;
/*import java.io.*;
import java.util.*;*/

/**
 * ---------------------------------------------------------------------------------------
 * This class helps to generate the recommendation pages.
 * ---------------------------------------------------------------------------------------
 * @author Qiao Kang
 * ---------------------------------------------------------------------------------------
 * @version 1.1
 * ---------------------------------------------------------------------------------------
 */

public class HtmlWriter {
	private String content;
    /**
     * Set title for html file
     * @param title
     */
	public HtmlWriter(String title) {
		content = "<!DOCTYPE html>" + "<html>" + "<head>"
				+ "<meta charset=\"UTF-8\" />" + "<title>" + title + "</title>";
	}
    /**
     * Start to write the body of the file
     */
	public void closeHead() {
		content += "</head><body>";
	}
    /**
     * Add a new line to html content
     * @param elements
     */
	public void append(String elements) {
		content += elements;
	}
    /**
     * close the html file with tags
     */
	public void closeHtml() {
		content += "</body>" + "</html>";
	}
    /**
     * Get the content of this writer
     * @return content
     */
	public String getContent() {
		return content;
	}
	/**
	 * Static CSS contents
	 * @return CSS contents
	 */
	public static String getCSS(){
		return "<style type=\"text/css\">"
		      +".Header {color:#71C6E2}"
		      +".Recommended {}"
		      + "table,th,td {border: 1px solid black;}"
		      +"</style>" 
		      +"<link rel=\"stylesheet\" type=\"text/css\" href=\"../static/Style.css\" />";
	}
	/**
	 * Get a piece predefined and static CSS segment for javascript.
	 * @return An javascript segment.
	 */
	public static String getJavaScript(){
		return "<script type=\"text/javascript\">"
	          +"function ChangeStyle() {"
	          +"    var headerElements = document.getElementsByClassName(\"Header\");"
	          +"    var RecommendedElements = document.getElementsByClassName(\"Recommended\");"
	          +"    for (var i = 0; i < RecommendedElements.length; i++) {"
	          +"        RecommendedElements[i].style.fontWeight = \"Bold\";"
	          +"        RecommendedElements[i].style.color = \"blue\";"
	          +"    }"
	          +"    for (var i = 0; i < headerElements.length; i++) {"
	          +"        headerElements[i].style.color = \"red\";"
	          +"    }"
	          +"}"
		      +"</script>";
	}
	/**
	 * Change spaces in a String to %20 to pass html5 validation
	 * @param value
	 * @return escaped space for html
	 */
	public static String escapeSpace(String value){
		for(int i=0;i<value.length();i++){
			if(value.charAt(i)==' '){
				value=value.substring(0,i)+"%20"+value.substring(i+1);
				i+=2;
			}
		}
		return value;
	}
}

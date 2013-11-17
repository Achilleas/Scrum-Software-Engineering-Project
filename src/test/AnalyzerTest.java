package test;

import java.util.ArrayList;

import main.Analyzer;
import main.HtmlWriter;
import main.Investor;

import org.junit.Test;

public class AnalyzerTest {
	String firstName = "Qiao";
	String surname = "Kang";
	ArrayList<String> companiesInvested;
	ArrayList<String> companiesInterested;
	Investor user;
	String password="123";
	@Test
	public void normalTest(){
		Analyzer analyzer = new Analyzer( ",");
		analyzer.analyze();
		HtmlWriter html = new HtmlWriter("Analysis");
		html.append(HtmlWriter.getCSS());
		html.append(HtmlWriter.getJavaScript());
		html.closeHead();
		html.append(analyzer.report(new Investor(),Analyzer.HIGH_GRANULARITY));
		html.append("<button type=\"button\" onclick=\"ChangeStyle();\">Highlight</button>");
		html.closeHtml();
		System.out.println(html.getContent());
	}
}

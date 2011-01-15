package basicnlp;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HtmlWordExtraction {

	Set<String> allWords = new HashSet<String>();
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		File input = new File("Advertising.htm");
		try {
			
/*			Document doc = Jsoup.connect("http://example.com/").get();
			String title = doc.title();
			Document doc = Jsoup.connect("http://example.com")
			  .data("query", "Java")
			  .userAgent("Mozilla")
			  .cookie("auth", "token")
			  .timeout(3000)
			  .post();*/
			
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			String title = doc.title();
			String text = doc.body().text();

			System.out.println(title);
			System.out.println(text);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

}

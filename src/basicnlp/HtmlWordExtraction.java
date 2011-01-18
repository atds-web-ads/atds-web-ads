package basicnlp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class HtmlWordExtraction {
	
	public static Set<String> DEFAULT_STOPWORDS = loadDefaultStopWords("default_stopwords.txt");
	public static Set<String> stopwords = new HashSet<String>();

	public static Map<String, Integer> wordFrequency = new HashMap<String, Integer>(); // overall frequency
		

	private static Set<String> loadDefaultStopWords(String filename) {
		Set<String> stopwords = new HashSet<String>();

		try {
			BufferedReader br;
			br = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while ((line = br.readLine()) != null) {
				stopwords.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stopwords;
	}

	static Map<String, Integer> processFile(File input){

		Map<String, Integer> wordFrequencyInFile = new HashMap<String, Integer>();		
		
		try {

			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");

			String text = doc.body().text().toLowerCase();

		
			// StanfordCoreNLP object			
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit"); 
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

			Annotation document = new Annotation(text);
			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			for (CoreMap sentence : sentences) {			
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
					String word = token.get(TextAnnotation.class);					

					if (Character.isLetter(word.charAt(0))) {
						if(wordFrequencyInFile.containsKey(word))
							wordFrequencyInFile.put(word, wordFrequencyInFile.get(word) + 1);
						else
							wordFrequencyInFile.put(word, 1);					
					}						
				}
			}		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return wordFrequencyInFile;
	}

	public static void processDataset(File folder, int frequencyThreshold){
		
		List<Map<String, Integer>> allFiles = new ArrayList<Map<String,Integer>>();
		
		File[] files = folder.listFiles();
		
		for(int i=0;i<files.length;i++)			
			allFiles.add(processFile(files[i]));
		
		
		for (int i = 0; i < allFiles.size(); i++) {
			
			Map<String, Integer> crt = allFiles.get(i);
			
			Iterator<Map.Entry<String, Integer>> it = crt.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, Integer> crtEntry = it.next();

				Integer value = wordFrequency.get(crtEntry.getKey());

				if (value != null) {
					wordFrequency.put(crtEntry.getKey(), value + crtEntry.getValue());
				} else
					wordFrequency.put(crtEntry.getKey(), crtEntry.getValue());
			}

		}		
		
		// select stopwords
		Iterator<Map.Entry<String, Integer>> it = wordFrequency.entrySet().iterator();		

		List<Map.Entry<String, Integer>> wordFrequencyAsList = new ArrayList<Map.Entry<String,Integer>>();
		
		while(it.hasNext()){
			Map.Entry<String, Integer> crtEntry = it.next();
			
			if(crtEntry.getValue() > frequencyThreshold)
				stopwords.add(crtEntry.getKey());
			
			wordFrequencyAsList.add(crtEntry);	
		}	
				
		// Order by frequency (DECREASING)
		Collections.sort(wordFrequencyAsList, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		stopwords.addAll(DEFAULT_STOPWORDS);
	//	System.out.println(wordFrequencyAsList);
	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File folder = new File("karacrawl");
		
		processDataset(folder, 10);
		
		System.out.println(wordFrequency);
		
		for (String st : stopwords)
			wordFrequency.remove(st);
		
		System.out.println(wordFrequency);
	}
}

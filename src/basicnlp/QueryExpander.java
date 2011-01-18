package basicnlp;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class QueryExpander {

	public static List<String> expand(String query) {
		
		List<String> expandedQueryTerms = new ArrayList<String>();
		List<String> queryTerms = new ArrayList<String>();
		
		String wnhome = "data";
		String path = wnhome + File.separator + "dict";

		try {
			URL url = new URL("file", null, path);
			IDictionary dict = new Dictionary(url);
			dict.open();

			String qTerms[] = query.split(" ");
		
			for (String q : qTerms)
				queryTerms.add(q);

			expandedQueryTerms.addAll(queryTerms);
			
			// eliminateStopWords(queryTerms);

			// find synsets
			for (String term : queryTerms) {

				IIndexWord idxWord = dict.getIndexWord("default", POS.NOUN);
				if (dict.getIndexWord(term, POS.NOUN) != null) {
					idxWord = dict.getIndexWord(term, POS.NOUN);
				}

				if (dict.getIndexWord(term, POS.ADJECTIVE) != null) {
					idxWord = dict.getIndexWord(term, POS.ADJECTIVE);
				}

				if (dict.getIndexWord(term, POS.ADVERB) != null) {
					idxWord = dict.getIndexWord(term, POS.ADVERB);
				}
				if (dict.getIndexWord(term, POS.VERB) != null) {
					idxWord = dict.getIndexWord(term, POS.VERB);
				}
				
				IWordID wordID = idxWord.getWordIDs().get(0);
				IWord word = dict.getWord(wordID);

				ISynset synset = word.getSynset();
				
				List<ISynsetID> relatedWords = synset.getRelatedSynsets(Pointer.SIMILAR_TO);
				
				List<IWord> words;

				for(ISynsetID sid: relatedWords){
					words = dict.getSynset(sid).getWords();
					for(Iterator<IWord> i = words.iterator(); i.hasNext();){
						expandedQueryTerms.add(i.next().getLemma());						
					}
					
				}
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return expandedQueryTerms;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// example
		String query = "beautifully coloured bicycle";
		List<String> expandedQueryTerms = QueryExpander.expand(query);
		
		System.out.println(expandedQueryTerms);

	}
}

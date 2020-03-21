package spellchecker;

import static sbcc.Core.*;

import java.io.*;
import java.util.regex.*;

public class BasicSpellChecker implements SpellChecker {

	private BasicDictionary dictionary = new BasicDictionary();
	private StringBuilder document = new StringBuilder();
	private int restartIndex = 0;

	@Override
	public void importDictionary(String filename) throws Exception {
		if (dictionary.getRoot() != null) {
			dictionary.clear();
		}
		dictionary.importFile(filename);
	}


	@Override
	public void loadDictionary(String filename) throws Exception {
		dictionary.clear();
		dictionary.load(filename);
	}


	@Override
	public void saveDictionary(String filename) throws Exception {
		dictionary.save(filename);
	}


	@Override
	public void loadDocument(String filename) throws Exception {
		document.append(readFile(filename));
	}


	@Override
	public void saveDocument(String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.append(document);
		writer.close();
	}


	@Override
	public String getText() {
		return document.toString();
	}


	@Override
	public String[] spellCheck(boolean continueFromPrevious) {
		// TODO Auto-generated method stub
		var suggestions = new String[2];
		String[] result = new String[4];
		var p = Pattern.compile("\\b[\\w']+\\b");
		var m = p.matcher(document);
		restartIndex = (continueFromPrevious) ? restartIndex : 0;
		while (m.find(restartIndex)) {
			var word = m.group();
			suggestions = dictionary.find(word);
			restartIndex = m.end();
			if (suggestions != null) {
				result[0] = word;
				result[1] = Integer.toString(m.start());
				result[2] = suggestions[0];
				result[3] = suggestions[1];
				break;
			}
		}
		return (result[0] == null) ? null : result;
	}


	@Override
	public void addWordToDictionary(String word) {
		dictionary.add(word);
	}


	@Override
	public void replaceText(int startIndex, int endIndex, String replacementText) {
		document.replace(startIndex, endIndex, replacementText);
		restartIndex = startIndex + replacementText.length();
	}

}

package spellchecker;

import static sbcc.Core.*;

import java.io.*;

public class BasicSpellChecker implements SpellChecker {

	private BasicDictionary bd = new BasicDictionary();
	private StringBuilder document = new StringBuilder();

	@Override
	public void importDictionary(String filename) throws Exception {
		if (bd.getRoot() != null) {
			bd.clear();
		}
		bd.importFile(filename);
	}


	@Override
	public void loadDictionary(String filename) throws Exception {
		bd.clear();
		bd.load(filename);
	}


	@Override
	public void saveDictionary(String filename) throws Exception {
		bd.save(filename);
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
		return null;
	}


	@Override
	public void addWordToDictionary(String word) {
		bd.add(word);
	}


	@Override
	public void replaceText(int startIndex, int endIndex, String replacementText) {
		// TODO Auto-generated method stub

	}

}

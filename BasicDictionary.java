package spellchecker;

import static sbcc.Core.*;
import static java.lang.Math.*;
import static java.lang.System.*;

import java.io.*;
import java.util.*;

public class BasicDictionary implements Dictionary {
	private BinaryTree tree = new BinaryTree();

	@Override
	public void importFile(String filename) throws Exception {
		if (tree.getRoot() != null) {
			tree.clear();
		}
		List<String> words = readFileAsLines(filename);
		recursiveImportHelper(words, 0, words.size() - 1);
	}


	private void recursiveImportHelper(List<String> list, int start, int end) {
		if (start > end) {
			return;
		}
		int middle = (int) round((start + end) / 2.0);
		add(list.get(middle));
		recursiveImportHelper(list, start, middle - 1);
		recursiveImportHelper(list, middle + 1, end);
	}


	@Override
	public void load(String filename) throws Exception {
		tree.clear();
		List<String> words = readFileAsLines(filename);
		for (var word : words) {
			add(word);
		}
	}


	@Override
	public void save(String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		saveHelper(writer, tree.getRoot());
		writer.close();
	}


	private void saveHelper(BufferedWriter writer, BinaryTreeNode node) throws IOException {
		if (node == null) {
			return;
		}
		writer.append((node.value + lineSeparator()));
		saveHelper(writer, node.left);
		saveHelper(writer, node.right);
	}


	@Override
	public String[] find(String word) {

		BinaryTreeNode node = tree.getRoot();
		// if tree does not exist, return null
		if (node == null) {
			return null;
		}
		Stack<BinaryTreeNode> lesser = new Stack();
		Stack<BinaryTreeNode> greater = new Stack();
		var suggestions = new String[2];
		int distance;
		while (true) {
			distance = word.compareToIgnoreCase(node.value);
			// if a match is found, return null
			if (distance == 0) {
				return null;
			}
			if (distance < 0) {
				greater.add(node);
				if (isLeftLeaf(node)) {
					break;
				}
				node = node.left;
			} else {
				lesser.add(node);
				if (isRightLeaf(node)) {
					break;
				}
				node = node.right;
			}
		}
		suggestions[0] = (lesser.isEmpty()) ? "" : lesser.pop().value;
		suggestions[1] = (greater.isEmpty()) ? "" : greater.pop().value;

		return suggestions;
	}


	private boolean isRightLeaf(BinaryTreeNode node) {
		return node.right == null;
	}


	private boolean isLeftLeaf(BinaryTreeNode node) {
		return node.left == null;
	}


	@Override
	public void add(String word) {
		tree.add(word.trim());
	}


	@Override
	public BinaryTreeNode getRoot() {
		return tree.getRoot();
	}


	@Override
	public int getCount() {
		return tree.getCount();
	}


	public void clear() {
		tree.clear();
	}

}

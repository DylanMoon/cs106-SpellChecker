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
		} // if start == end, no need to calculate a root index, just add it.
		var root = (start == end) ? start : start + getRoot(end - start + 1);
		add(list.get(root));
		recursiveImportHelper(list, start, root - 1);
		recursiveImportHelper(list, root + 1, end);
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


	private int getRoot(int nodes) {
		var index = 0;
		var completeRows = nCompleteRows(nodes);
		var orphans = leftOverNodes(nodes);
		for (int i = 0; i < completeRows - 1; i++) {// runs n * 2 + 1 a complete number of rows -1 times
			index = (index * 2) + 1;
		}
		if (orphans == 0) {// if building a full tree return the root node
			return index;
		}
		// if the number of orphan nodes is greater or equal to the half of the final
		// row complete one more cycle of n * 2 + 1
		// if the number of orphan nodes is less than half of the final row offset the
		// root index by the number of orphans
		return ((((int) pow(2, completeRows)) / 2) <= orphans) ? (index * 2) + 1 : index + orphans;
	}


	private int nCompleteRows(int nodes) {
		var rows = 0;
		while (nodes >= pow(2, rows) - 1) {
			rows++;
		}
		return rows - 1;
	}


	private int leftOverNodes(int nodes) {
		if (nodes == 0) {
			return 0;
		}
		var level = levels(nodes);
		return (((int) pow(2, level) - 1) == nodes) ? 0 : (nodes - ((int) pow(2, level - 1) - 1));
	}


	private int levels(int x) {
		if (x == 0) {
			return 0;
		}
		return (int) (floor(log10(x) / log10(2))) + 1;
	}

}

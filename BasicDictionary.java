package spellchecker;

import static sbcc.Core.*;

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
		recursiveAdd(words, 0, words.size() - 1);
	}


	private void recursiveAdd(List<String> list, int start, int end) {
		if (start > end) {
			return;
		}
		int middle = ((start + end) / 2);
		add(list.get(middle));
		recursiveAdd(list, start, middle - 1);
		recursiveAdd(list, middle + 1, end);
	}


	@Override
	public void load(String filename) throws Exception {
		tree.clear();
		List<String> words = readFileAsLines(filename);
		for (var word : words) {
			add(word.trim());
		}
	}


	@Override
	public void save(String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		BinaryTreeNode node = tree.getRoot();
		Stack<BinaryTreeNode> stack = new Stack();
		while (node != null) {
			writer.append(node.value + "\n");
			if (node.right != null) {
				stack.add(node.right);
			}
			if (node.left != null) {
				node = node.left;
				continue;
			}
			if (!stack.isEmpty()) {
				node = stack.pop();
				continue;
			}
			node = null;
		}
		writer.close();
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
		int distance = word.compareToIgnoreCase(node.value);
		// int prevDistance = distance;
		String[] suggestions = new String[2];
		while (true) {// figure out a better way to do this
			// if a match is found, return null
			if (distance == 0) {
				return null;
			}
			if (distance < 0) {
				if (isLeftLeaf(node)) {
					greater.add(node);
					break;
				}
				greater.add(node);
				node = node.left;
				// prevDistance = distance;
				distance = word.compareToIgnoreCase(node.value);
				continue;

			} else {
				if (isRightLeaf(node)) {
					lesser.add(node);
					break;
				}
				lesser.add(node);
				node = node.right;
				// prevDistance = distance;
				distance = word.compareToIgnoreCase(node.value);
				continue;
			}
		}
		if (!lesser.isEmpty()) {
			suggestions[0] = lesser.pop().value;
		}

		if (!greater.isEmpty()) {
			suggestions[1] = greater.pop().value;
		}
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
		BinaryTreeNode newNode = new BinaryTreeNode(word);
		tree.add(newNode);
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

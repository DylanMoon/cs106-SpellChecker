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
		/*
		 * TODO get distance. If zero return null. if negative check if leaf, if not
		 * traverse left if leaf return current node and parent if positive check if
		 * leaf, if not traverse right if leaf return current node and parent
		 * 
		 */
		// BinaryTreeNode node = tree.getRoot();
		// // if tree does not exist, return null
		// if (node == null) {
		// return null;
		// }
		// Stack<BinaryTreeNode> stack = new Stack();
		// int distance = word.compareToIgnoreCase(node.value);
		// while (distance != 0) {// distance < previous distance
		//
		// // gets lexicographical distance between search word and current node
		// distance = word.compareToIgnoreCase(node.value);
		//
		// // if the word matches return null
		// if (distance == 0) {
		// return null;
		// }
		//
		// if (distance < 0) {
		// if (node.left != null) {
		// stack.push(node);
		// node = node.left;
		// }
		// } else {
		// if (node.right != null) {
		// stack.push(node);
		// node = node.right;
		// }
		// }
		// }
		return null;
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

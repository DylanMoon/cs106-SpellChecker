package spellchecker;

import static sbcc.Core.*;

import java.io.*;
import java.util.*;

public class BasicDictionary implements Dictionary {
	private BinaryTree tree = new BinaryTree();

	@Override
	public void importFile(String filename) throws Exception {
		ArrayList<String> words = (ArrayList<String>) readFileAsLines(filename);
		// ArrayList<String> wordsInOrder = new ArrayList();
		// recursiveAdd(words, wordsInOrder, 0, words.size() - 1);
		Collections.shuffle(words);
		for (var word : words) {
			add(word.trim());
		}
	}


	private static void recursiveAdd(ArrayList<String> from_list, ArrayList<String> to_list, int start, int end) {
		if (end - start == 0) {
			to_list.add(from_list.get(start));
			return;
		}
		if (end - start == 1) {
			to_list.add(from_list.get(end));
			to_list.add(from_list.get(start));
			return;
		}
		int index = (start + end) / 2;

		to_list.add(from_list.get(index));

		recursiveAdd(from_list, to_list, index + 1, end);
		recursiveAdd(from_list, to_list, start, index - 1);
	}


	@Override
	public void load(String filename) throws Exception {
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
		return tree.find(word);
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

}

package spellchecker;

import static sbcc.Core.*;

public class BinaryTree {

	private BinaryTreeNode root = null;
	private int count = 0;

	public void add(String word) {
		if (root == null) {
			root = new BinaryTreeNode(word);
			count++;
			return;
		} else {
			BinaryTreeNode node = root;
			var compare = word.compareToIgnoreCase(node.value);
			while (compare != 0) {
				if (compare < 0 && node.left == null) {
					node.left = new BinaryTreeNode(word);
					count++;
					return;
				} else if (compare > 0 && node.right == null) {
					node.right = new BinaryTreeNode(word);
					count++;
					return;
				}
				node = (compare < 0) ? node.left : node.right;
				compare = word.compareToIgnoreCase(node.value);
			}
		}
	}


	public void clear() {
		root = null;
		count = 0;
	}


	public int getCount() {
		return count;
	}


	public BinaryTreeNode getRoot() {
		return root;
	}

}

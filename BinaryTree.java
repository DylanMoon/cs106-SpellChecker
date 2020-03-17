package spellchecker;

import static sbcc.Core.*;

public class BinaryTree {

	private BinaryTreeNode root = null;
	private int count = 0;

	public void add(BinaryTreeNode newNode) {
		if (root == null) {
			root = newNode;
			count++;
			return;
		} else {
			BinaryTreeNode node = root;
			int compare = 0;
			while (true) {
				compare = node.value.compareToIgnoreCase(newNode.value);
				if (compare == 0) {
					return;
				}
				if (compare > 0 && node.left == null) {
					node.left = newNode;
					count++;
					return;
				}
				if (compare < 0 && node.right == null) {
					node.right = newNode;
					count++;
					return;
				}
				node = (compare > 0) ? node.left : node.right;
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

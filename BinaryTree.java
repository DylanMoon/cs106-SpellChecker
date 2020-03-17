package spellchecker;

public class BinaryTree {

	private BinaryTreeNode root = null;
	private int count = 0;

	public void add(BinaryTreeNode newNode) {
		if (root == null) {
			root = newNode;
			count++;
			return;
		} else {
			BinaryTreeNode curr = root;
			int compare = 0;
			while (true) {
				compare = curr.value.compareTo(newNode.value);
				if (compare == 0) {
					return;
				}
				if (compare > 0 && curr.left == null) {
					curr.left = newNode;
					count++;
					return;
				}
				if (compare < 0 && curr.right == null) {
					curr.right = newNode;
					count++;
					return;
				}
				curr = (compare > 0) ? curr.left : curr.right;
			}
		}
	}


	public String[] find(String word) {
		BinaryTreeNode curr = root;

		// TODO read documentation in dictionary.java
		return null;
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

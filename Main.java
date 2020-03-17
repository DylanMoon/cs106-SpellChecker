package spellchecker;

import static sbcc.Core.*;

import java.util.*;

import spellchecker.Dictionary;

import static java.lang.Math.*;
import static java.lang.System.*;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * 
 * Dylan Moon CS106-62502
 *
 */
public class Main {

	public static void main(String[] args) {

		BasicDictionary dictionary = new BasicDictionary();
		var start = nanoTime();
		try {
			dictionary.importFile("full_dictionary.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		var diff = (nanoTime() - start) / 1.0e6;
		println("build took: " + diff + "ms");

		start = nanoTime();
		try {
			dictionary.load("my_test.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		diff = (nanoTime() - start) / 1.0e6;
		println("load took: " + diff + "ms");
		//
		// try {
		// dictionary.save("my_test.txt");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		BinaryTreeNode node = dictionary.getRoot();

		println("\nwords in tree:" + dictionary.getCount());

		println("\ntree depth: " + getTreeDepth(node));

		// println("root node = " + dictionary.getRoot().value);
		// println("root's left node = " + dictionary.getRoot().left.value);
		// println("root's right node = " + dictionary.getRoot().right.value);

		// node = dictionary.getRoot();

		// printTreeLevelOrder(node);
	}


	private static void printTreeInOrder(BinaryTreeNode node) {
		if (node == null) {
			return;
		}
		printTreeInOrder(node.left);
		print(node.value + ", ");
		printTreeInOrder(node.right);
	}


	private static void printTreeLevelOrder(BinaryTreeNode node) {
		if (node == null) {
			return;
		}
		print(node.value + ", ");
		printTreeLevelOrder(node.left);
		printTreeLevelOrder(node.right);
	}


	private static int getTreeDepth(BinaryTreeNode node) {
		if (node == null)
			return 0;
		else
			return 1 + max(getTreeDepth(node.left), getTreeDepth(node.right));
	}
}

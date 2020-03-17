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

		BasicDictionary bd = new BasicDictionary();
		var start = nanoTime();
		try {
			bd.importFile("full_dictionary.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		var diff = (nanoTime() - start) / 1.0e6;
		println("import took: " + diff + "ms");
		println("\nwords in tree 2 :" + bd.getCount());

		BinaryTreeNode curr2 = bd.getRoot();
		println("tree depth: " + getTreeDepth(curr2));

	}


	private static int getTreeDepth(BinaryTreeNode node) {
		if (node == null)
			return 0;
		else
			return 1 + max(getTreeDepth(node.left), getTreeDepth(node.right));
	}
}

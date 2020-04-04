package spellchecker;

import static sbcc.Core.*;

import java.io.*;
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
	static final int NUM_NODES = 175169;

	public static void main(String[] args) throws Exception {
		var lastL = 0;
		printf("%10s%15s\n", "Nodes", "Levels");
		for (int i = 1; i <= NUM_NODES; i = i * 2) {
			var level = levels(i);
			if (level > lastL) {
				lastL = level;
				printf("%10s%14s\n", i - 1, level - 1);
			}
		}
		var lastRoot = 0;
		for (int i = 0; i <= NUM_NODES; i++) {
			var root = getRoot(i);
			if (root > lastRoot) {
				lastRoot = root;
				println("Root index for a tree with " + i + " nodes is: " + root);
			}
		}
		println("Wrong root for a tree with " + NUM_NODES + " nodes is: " + ((int) ceil(NUM_NODES / 2)));

	}


	public static int getRoot(int nodes) {
		var index = 0;
		var completeRows = nCompleteRows(nodes);
		var level = levels(nodes);
		var orphans = leftOverNodes(nodes);
		for (int i = 0; i < completeRows - 1; i++) {// need to loop complete rows -1
			index = (index * 2) + 1;
		}
		if (orphans == 0) {
			return index;
		}
		return ((((int) pow(2, completeRows)) / 2) <= orphans) ? (index * 2) + 1 : index + orphans;
	}


	public static int nCompleteRows(int nodes) {
		var rows = 0;
		while (nodes >= pow(2, rows) - 1) {
			rows++;
		}
		return rows - 1;
	}


	public static int getTreeDepth(BinaryTreeNode node) {
		if (node == null)
			return 0;
		else
			return 1 + max(getTreeDepth(node.left), getTreeDepth(node.right));
	}


	public static int leftOverNodes(int nodes) {
		if (nodes == 0) {
			return 0;
		} else {
			var level = levels(nodes);
			return (((int) pow(2, level) - 1) == nodes) ? 0 : (nodes - ((int) pow(2, level - 1) - 1));
		}
	}


	public static int levels(int x) {
		if (x == 0) {
			return 0;
		}
		return (int) (floor(log10(x) / log10(2))) + 1;
	}
}

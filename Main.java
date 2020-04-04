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

	public static void main(String[] args) throws Exception {

		BasicDictionary bd = new BasicDictionary();
		bd.importFile("my_test_alpha.txt");

	}
}

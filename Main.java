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

		var dictionary = new BasicDictionary();

		try {
			dictionary.importFile("small_dictionary.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		var result = dictionary.find("elements");

		print(result[0] + " " + result[1]);

	}
}

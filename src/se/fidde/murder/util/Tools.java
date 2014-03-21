package se.fidde.murder.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;

import javax.swing.JOptionPane;

/**
 * Tools class
 * 
 * @author fidde
 * 
 */
public class Tools {

	private static Random rand;

	/**
	 * Generates number 1 - 6
	 * 
	 * @return int
	 * @throws ArrayIndexOutOfBoundsException
	 *             if number > 6
	 */
	public static int rollT6() throws ArrayIndexOutOfBoundsException {
		rand = new Random();
		int[] numbers = { 1, 2, 3, 4, 5, 6 };
		int result = rand.nextInt(6);

		try {
			return numbers[result];

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("result > 5");
		}

	}

	/**
	 * Checks default locale and returns sv or en
	 * 
	 * @return String
	 */
	public static String getLanguage() {
		String lang = Locale.getDefault().getLanguage();
		if (lang.contains("sv") || lang.contains("SV"))
			return "sv";

		return "en";
	}

	/**
	 * Converts ListView to string list
	 * 
	 * @param listview
	 *            ListView
	 * @return list of strings
	 */
	public static List<String> toList(ListView<String> listview) {
		List<String> result = new ArrayList<>();

		for (String name : listview.getItems())
			result.add(name);

		return result;
	}

	/**
	 * Displays file error
	 * 
	 * @param type
	 *            type of file error, saving or loading
	 */
	public static void printFileError() {
		JOptionPane.showMessageDialog(
				null,
				ResourceBundle.getBundle(
						"se.fidde.murder.model.lang." + getLanguage())
						.getObject("ErrorMessage"),
				ResourceBundle
						.getBundle(
								"se.fidde.murder.model.lang." + getLanguage())
						.getObject("Error").toString(),
				JOptionPane.ERROR_MESSAGE);
	}

}

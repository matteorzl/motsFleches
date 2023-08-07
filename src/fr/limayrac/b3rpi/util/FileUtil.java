/**
 * "FileUtil" class provides functionality for reading words from a French word list file and
 * searching for words that start with a specified letter and meet certain criteria in a crossword puzzle grid.
 *
 * The class loads a list of French words from the specified file and stores them in an internal data structure.
 * It then provides a method "printMotsDebut" to search for words starting with a given letter and meeting certain
 * conditions (e.g., length less than "e") in a specified direction (DOWN, RIGHT, DOWNRIGHT, RIGHTDOWN) in the crossword grid.
 *
 * The class is designed to be used in conjunction with the "fr.limayrac.b3rpi.util.Start" class, where the crossword puzzle grid is displayed,
 * and the user interacts with the grid to add words and their definitions.
 *
 * @version 1.0
 * @since 2023-08-07
 * @author ARZEL Mattéo
 */

package fr.limayrac.b3rpi.util;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A utility class to handle file operations related to the crossword puzzle game.
 */
public class FileUtil {

	private static final String FILE_PATH = "/fr/limayrac/b3rpi/util/liste_francais.txt";
	public ArrayList<String> words;
	private ArrayList<String> wordList = null;
	private JButton[][] gridButtons;

	/**
	 * Constructor for FileUtil. Initializes the word list by reading from the file.
	 */
	public FileUtil() {
		super();
		wordList = new ArrayList<>();
		openFile();
	}

	/**
	 * Sets the grid buttons for the crossword puzzle.
	 *
	 * @param gridButtons The 2D array of JButtons representing the crossword puzzle grid.
	 */
	public void setGridButtons(JButton[][] gridButtons) {
		this.gridButtons = gridButtons;
	}

	/**
	 * Opens the word list file and populates the wordList ArrayList.
	 */
	private void openFile() {
		try {
			InputStream inputStream = FileUtil.class.getResourceAsStream(FILE_PATH);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				wordList.add(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Prints the words that start with the specified letter and fit the given length and direction.
	 * The method filters words that match the criteria and displays them on the grid panel.
	 *
	 * @param d         The starting letter of the word.
	 * @param e         The maximum length of the word.
	 * @param x         The row index of the starting position on the grid.
	 * @param y         The column index of the starting position on the grid.
	 * @param direction The chosen direction (DOWN, RIGHT, DOWNRIGHT, RIGHTDOWN).
	 * @param rows      The number of rows in the crossword grid.
	 * @param columns   The number of columns in the crossword grid.
	 * @param gridPanels The 2D array of JPanels representing the crossword puzzle grid.
	 * @param letters   The 2D array of JLabels representing the letters in the grid.
	 */
	public void printWordsStartingWith(String d, Integer e, int x, int y, String direction, int rows, int columns,
									   JPanel[][] gridPanels, JLabel[][] letters) {
		System.out.printf("Displaying words starting with %s%n", d);
		words = new ArrayList<>();
		ArrayList<String> wordsFiltered = new ArrayList<>();

		int down = x + 1;
		int right = y + 1;
		int rightdown = x;
		int downright = y;
		int count = 0;

		while (down < rows && rightdown < rows && right < columns && downright < columns &&
				down >= 0 && rightdown >= 0 && right >= 0 && downright >= 0) {

			if (direction.equals("BAS")) {
				String buttonText = letters[down][downright].getText();
				if (!buttonText.isEmpty()) {
					char letter = buttonText.charAt(0);
					if (!words.isEmpty()) {
						for (String word : words) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
							if (!wordsFiltered.isEmpty()) {
								words.clear();
							}
						}
					} else {
						for (String word : wordList) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
					}
				}
			}

			if (direction.equals("DROITE-BAS")) {
				String buttonText = letters[down][downright].getText();
				if (!buttonText.isEmpty()) {
					char letter = buttonText.charAt(0);
					if (!words.isEmpty()) {
						for (String word : words) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
						if (!wordsFiltered.isEmpty()) {
							words.clear();
						}
					} else {
						for (String word : wordList) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
					}
				}
			}

			if (direction.equals("DROITE")) {
				String buttonText = letters[rightdown][right].getText();
				if (!buttonText.isEmpty()) {
					char letter = buttonText.charAt(0);
					if (!words.isEmpty()) {
						for (String word : words) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
						if (!wordsFiltered.isEmpty()) {
							words.clear();
						}
					} else {
						for (String word : wordList) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
					}
				}
			}

			if (direction.equals("BAS-DROITE")) {
				String buttonText = letters[rightdown][right].getText();
				if (!buttonText.isEmpty()) {
					char letter = buttonText.charAt(0);
					if (!words.isEmpty()) {
						for (String word : words) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
						if (!wordsFiltered.isEmpty()) {
							words.clear();
						}
					} else {
						for (String word : wordList) {
							if (word.startsWith(d) && word.length() < e && word.length() > count && word.charAt(count) == letter) {
								wordsFiltered.add(word);
								System.out.println(word);
							}
						}
					}
				}
			}

			if (!wordsFiltered.isEmpty()) {
				words.addAll(wordsFiltered);
				wordsFiltered.clear();
			}

			// Move in the specified direction
			if (direction.equals("BAS")) {
				down++;
				count++;
			}
			if (direction.equals("BAS-DROITE")) {
				downright++;
				count++;
			}
			if (direction.equals("DROITE")) {
				right++;
				count++;
			}
			if (direction.equals("DROITE-BAS")) {
				rightdown++;
				count++;
			}
		}
		words = wordsFiltered;
		if (words.isEmpty()) {
			// If no words are found in the specified direction, display words starting with the selected letter
			for (String word : wordList) {
				if (word.startsWith(d) && word.length() <= e) {
					words.add(word);
					System.out.println(word);
				}
			}
		}
	}
}

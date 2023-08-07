/***
 * A graphical user interface for a crossword puzzle game.
 * Allows the user to create a crossword puzzle grid by specifying the dimensions (rows and columns),
 * and then interactively add words with their definitions to the grid.
 * The crossword puzzle can be displayed and edited by the user through mouse interactions.
 * Words can be added in different directions (DOWN, RIGHT, DOWNRIGHT, RIGHTDOWN) and can have single or double definitions.
 * The crossword puzzle and its definitions are saved to and loaded from a file using the "Fichier" class.
 * The class also includes a main method to start the crossword puzzle game.
 * @since: 2023-08-09
 * @version: 1.0
 * @author: ARZEL Mattéo
 **/

package fr.limayrac.b3rpi.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import java.awt.Font;

public class Start extends JFrame {

	FileUtil f = new FileUtil();
	private CustomDialog customDialog;
	private JPanel[][] gridPanels;
	private JLabel[][] letter;
	private Font arrowFont;

	private int rows;
	private int columns;
	int size = 0;
	private static final int CELL_SIZE = 70;

	public Start() {
		customDialog = new CustomDialog(this, new ArrayList<>());

		// Initialize the arrowFont
		arrowFont = new Font(Font.SANS_SERIF, Font.PLAIN, 30);

		setTitle("Création de Mots fléchés");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getGridDimensions();
		initializeGrid();

		// Set GridLayout for the main panel
		setLayout(new GridLayout(rows, columns));
		pack();

		// Set the frame to fullscreen mode
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Prompts the user to enter the dimensions (rows and columns) for the crossword puzzle grid.
	 * The method will re-prompt until valid numeric values between 1 and 10 are provided.
	 */
	private void getGridDimensions() {
		JTextField rowsField = new JTextField();
		JTextField columnsField = new JTextField();
		Object[] message = {
				"Saisissez le nombre de lignes (1 à 10)", rowsField,
				"Saisissez le nombre de colonnes (1 à 10)", columnsField
		};
		int option = JOptionPane.showConfirmDialog(null, message, "Taille de la grille", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			try {
				rows = Integer.parseInt(rowsField.getText());
				columns = Integer.parseInt(columnsField.getText());
				if (rows <= 0 || columns <= 0 || rows > 10 || columns > 10) {
					JOptionPane.showMessageDialog(null, "Dimensions de grille non valides. Veuillez entrer des valeurs entre 1 et 10.", "Erreur :/", JOptionPane.ERROR_MESSAGE);
					getGridDimensions(); // Re-prompt for input
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Entrée invalide. Veuillez saisir des valeurs numériques.", "Erreur :/", JOptionPane.ERROR_MESSAGE);
				getGridDimensions(); // Re-prompt for input
			}
		} else {
			System.exit(0); // User clicked Cancel or closed the window
		}
	}

	/**
	 * Initializes the crossword puzzle grid by creating the required panels and labels.
	 */
	private void initializeGrid() {
		gridPanels = new JPanel[rows][columns];
		letter = new JLabel[rows][columns];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {
				gridPanels[x][y] = new JPanel();
				gridPanels[x][y].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
				gridPanels[x][y].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				letter[x][y] = new JLabel(" ");
				gridPanels[x][y].add(letter[x][y]);
				gridPanels[x][y].addMouseListener(new PanelMouseListener(x, y));
				add(gridPanels[x][y]);
			}
		}
	}

	/**
	 * A MouseAdapter to handle mouse events on the crossword puzzle grid panels.
	 */
	private class PanelMouseListener extends MouseAdapter {
		private final int x;
		private final int y;

		public PanelMouseListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * Prompts the user to select a direction for adding a word and returns the chosen direction.
		 * The size of the word is also calculated based on the selected direction.
		 *
		 * @return The chosen direction as a String (DOWN, RIGHT, DOWNRIGHT, RIGHTDOWN)
		 */
		public String direction() {
			String[] optionsToChoose = {"BAS", "DROITE", "BAS-DROITE", "DROITE-BAS"};
			int right = y;
			int down = x;
			size = 0;
			String getDir = (String) JOptionPane.showInputDialog(
					null,
					"Dans quelle direction le mot doit-il être affiché ?",
					"Sélection du sens",
					JOptionPane.QUESTION_MESSAGE,
					null,
					optionsToChoose,
					optionsToChoose[0]);
			if (Objects.equals(getDir, optionsToChoose[0])) {
				while (down < rows - 1) {
					size++;
					down++;
				}
			}
			if (Objects.equals(getDir, optionsToChoose[2])) {
				while (right < rows) {
					size++;
					right++;
				}
			}
			if (Objects.equals(getDir, optionsToChoose[1])) {
				while (right < columns - 1) {
					size++;
					right++;
				}
			}
			if (Objects.equals(getDir, optionsToChoose[3])) {
				while (down < columns) {
					size++;
					down++;
				}
			}
			return getDir;
		}

		/**
		 * Prompts the user to select the type of definition (Single or Double) and add the respective definition(s).
		 *
		 * @return The selected definition type as a String (Simple or Double)
		 */
		public String definition() {
			String[] optionsToChoose = {"Définition Simple", "Définition Double"};
			String definitionType = (String) JOptionPane.showInputDialog(
					null,
					"Quel type de définition souhaitez-vous ajouter ?",
					"Type de définition",
					JOptionPane.QUESTION_MESSAGE,
					null,
					optionsToChoose,
					optionsToChoose[0]);

			if (Objects.equals(definitionType, optionsToChoose[0])) {
				// Simple definition
				// Ask for the definition and display it on the grid panel
				String definition = JOptionPane.showInputDialog("Saississez la définition");
				JLabel def = new JLabel(definition);
				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
				JPanel defPanel = new JPanel();
				defPanel.setBackground(Color.YELLOW);
				defPanel.add(def);
				mainPanel.add(defPanel);

				gridPanels[x][y].removeAll();
				gridPanels[x][y].setLayout(new BorderLayout());
				gridPanels[x][y].add(mainPanel);
				gridPanels[x][y].setEnabled(false); // Make the panel non-clickable

			} else if (Objects.equals(definitionType, optionsToChoose[1])) {
				// Double definition
				String definition1 = JOptionPane.showInputDialog("Saisissez la première définition");
				String definition2 = JOptionPane.showInputDialog("Saisissez la deuxième définition");
				JLabel def1 = new JLabel(definition1);
				JLabel def2 = new JLabel(definition2);

				JPanel mainPanel = new JPanel();
				mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

				JPanel topPanel = new JPanel();
				topPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE / 2));
				topPanel.setBackground(Color.YELLOW);
				topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				topPanel.add(def1);

				JPanel botPanel = new JPanel();
				botPanel.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE / 2));
				botPanel.setBackground(Color.YELLOW);
				botPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				botPanel.add(def2);

				gridPanels[x][y].removeAll();

				if (Objects.equals(direction(), "BAS") || Objects.equals(direction(), "BAS-DROITE")) {
					// Swap the order of topPanel and botPanel for "BAS" or "BAS-DROITE" direction
					mainPanel.add(botPanel); // Add botPanel first
					mainPanel.add(topPanel); // Add topPanel next
				} else {
					mainPanel.add(topPanel); // Add topPanel first
					mainPanel.add(botPanel); // Add botPanel next
				}

				gridPanels[x][y].setLayout(new BorderLayout()); // Use BorderLayout to ensure proper placement
				gridPanels[x][y].add(mainPanel, BorderLayout.CENTER);
				gridPanels[x][y].setEnabled(false);
			}
			return definitionType;
		}

		/**
		 * Adds a word to the crossword puzzle grid based on user input for starting letter and direction.
		 * The word is retrieved from a file using the "Fichier" class.
		 */
		public void displayWord() {
			String input = JOptionPane.showInputDialog("Entrez une lettre :");
			String direction = direction();
			f.printWordsStartingWith(input, size, x, y, direction, rows, columns, gridPanels, letter);

			// Update the list of words in the custom dialog box
			DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(f.words.toArray(new String[0]));
			customDialog.comboBox.setModel(comboBoxModel);

			customDialog.setVisible(true);
			String selectedValue = customDialog.getSelectedWord();
			char[] lettres = selectedValue.toCharArray();
			int down = x + 1;
			int right = y + 1;
			int downright = y;
			int rightdown = x;
			Font enlargedFont = new Font(letter[down][y].getFont().getName(), Font.BOLD, 20);

			boolean isFirstLetter = true; // Flag to keep track of the first letter

			if (direction.equals("BAS")) {
				for (char c : lettres) {
					if (isFirstLetter) {
						JLabel arrowLabel = new JLabel("\u2193"); // Unicode for DOWN ARROW symbol
						arrowLabel.setFont(arrowFont);
						gridPanels[down][y].add(arrowLabel,letter[down][y]);
						isFirstLetter = false; // Set the flag to false after adding the arrow
					}

					letter[down][y].setText(String.valueOf(c));
					letter[down][y].setFont(enlargedFont); // Set the enlarged font
					gridPanels[down][y].add(letter[down][y]);
					down++;
				}
			}

			if (direction.equals("BAS-DROITE")) {
				for (char c : lettres) {
					if (isFirstLetter) {
						JLabel arrowLabel = new JLabel("\u21b3"); // Unicode for DOWNRIGHT ARROW symbol
						arrowLabel.setFont(arrowFont);
						gridPanels[down][downright].add(arrowLabel,letter[down][downright]);
						isFirstLetter = false; // Set the flag to false after adding the arrow
					}

					letter[down][downright].setText(String.valueOf(c));
					letter[down][downright].setFont(enlargedFont); // Set the enlarged font
					gridPanels[down][downright].add(letter[down][downright]);
					downright++;
				}
			}

			if (direction.equals("DROITE")) {
				for (char c : lettres) {
					if (isFirstLetter) {
						JLabel arrowLabel = new JLabel("\u2192"); // Unicode for RIGHT ARROW symbol
						arrowLabel.setFont(arrowFont);
						gridPanels[x][right].add(arrowLabel,letter[x][right]);
						isFirstLetter = false; // Set the flag to false after adding the arrow
					}

					letter[x][right].setText(String.valueOf(c));
					letter[x][right].setFont(enlargedFont); // Set the enlarged font
					gridPanels[x][right].add(letter[x][right]);
					right++;
				}
			}

			if (direction.equals("DROITE-BAS")) {
				for (char c : lettres) {
					if (isFirstLetter) {
						JLabel arrowLabel = new JLabel("\u21b4"); // Unicode for RIGHTDOWN ARROW symbol
						arrowLabel.setFont(arrowFont);
						gridPanels[rightdown][right].add(arrowLabel,letter[rightdown][right]);
						isFirstLetter = false; // Set the flag to false after adding the arrow
					}

					letter[rightdown][right].setText(String.valueOf(c));
					letter[rightdown][right].setVerticalAlignment(SwingConstants.CENTER); // Center the letter vertically
					letter[rightdown][right].setFont(enlargedFont); // Set the enlarged font
					gridPanels[rightdown][right].add(letter[rightdown][right]);
					rightdown++;
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			String def = definition();
			if (Objects.equals(def, "Définition Double")) {
				displayWord();
				displayWord();
			} else {
				displayWord();
			}
		}
	}

	/**
	 * The main method to start the crossword puzzle game.
	 *
	 * @param args The command-line arguments (not used).
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(Start::new);
	}
}
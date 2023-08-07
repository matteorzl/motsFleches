import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Grid implements ActionListener {
    JFrame frame = new JFrame("Grille");
    JTextField widthField;
    JTextField heightField;
    JButton generateButton;
    JPanel gridPanel;
    JComboBox<String> wordComboBox; // New JComboBox for displaying matching words
    List<String> dictionary;
    JPanel[][] cells; // Array to store the cells of the grid

    public Grid() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel widthLabel = new JLabel("Largeur de la grille:");
        JLabel heightLabel = new JLabel("Hauteur de la grille:");
        this.widthField = new JTextField(10);
        this.heightField = new JTextField(10);
        this.generateButton = new JButton("Générer");
        this.generateButton.addActionListener(this);
        JPanel controlPanel = new JPanel();
        controlPanel.add(widthLabel);
        controlPanel.add(this.widthField);
        controlPanel.add(heightLabel);
        controlPanel.add(this.heightField);
        controlPanel.add(this.generateButton);

        // Create the JComboBox and add it to the control panel
        wordComboBox = new JComboBox<>();
        controlPanel.add(wordComboBox);

        // Create the Select button and add it to the control panel
        JButton selectButton = new JButton("Sélectionner");
        controlPanel.add(selectButton);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replaceSelectedWord((String) wordComboBox.getSelectedItem());
            }
        });

        this.gridPanel = new JPanel();
        this.gridPanel.setLayout(new GridLayout());
        this.frame.add(controlPanel, BorderLayout.NORTH);
        this.frame.add(this.gridPanel, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setVisible(true);

        loadDictionary();
    }

    private void loadDictionary() {
        dictionary = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("../motsFleches/src/liste_francais.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                dictionary.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.generateButton) {
            String widthText = this.widthField.getText();
            String heightText = this.heightField.getText();
            int width = Integer.parseInt(widthText);
            int height = Integer.parseInt(heightText);
            if (width > 11) {
                width = 11;
                this.widthField.setText("11");
            }

            if (height > 11) {
                height = 11;
                this.heightField.setText("11");
            }

            this.generateGrid(width, height);
        }
    }

    private void resetCellPanel(JPanel cellPanel) {
        JTextField textField = (JTextField) cellPanel.getComponent(0);
        JButton definitionButton = (JButton) cellPanel.getComponent(1);

        textField.setText("");
        textField.setEnabled(true);
        textField.setBackground(Color.WHITE); // Reset the background color of the text field
        definitionButton.setText("Définition");
        definitionButton.setEnabled(true);
    }

    private void divideCellVertically(int row, int col) {
        // Check if cell is already divided
        if (cells[row][col].getComponentCount() > 1) {
            return;
        }

        // Create two new sub-cells using GridLayout
        JPanel subCell1 = createCell(row, col);
        JPanel subCell2 = createCell(row, col);
        cells[row][col].setLayout(new GridLayout(2, 1));
        cells[row][col].removeAll();
        cells[row][col].add(subCell1);
        cells[row][col].add(subCell2);
        cells[row][col].revalidate();
    }


    private void replaceSelectedWord(String selectedWord) {
        if (selectedWord != null) {
            JTextField selectedTextField = getFocusedTextField();
            if (selectedTextField != null) {
                selectedTextField.setText(selectedWord);
                JPanel cellPanel = (JPanel) selectedTextField.getParent();
                cellPanel.setBackground(Color.GRAY); // Set the background to gray
                resetCellPanel(cellPanel);

                // Check if the cell is divided and if yes, update the sub-cells with the selected word
                int row = getRow(cellPanel);
                int col = getCol(cellPanel);
                if (row != -1 && col != -1 && cells[row][col].getComponentCount() > 1) {
                    JPanel subCell1 = (JPanel) cells[row][col].getComponent(0);
                    JPanel subCell2 = (JPanel) cells[row][col].getComponent(1);

                    JTextField topTextField = (JTextField) subCell1.getComponent(0);
                    JTextField bottomTextField = (JTextField) subCell2.getComponent(0);

                    if (selectedTextField == topTextField) {
                        bottomTextField.setText(selectedWord);
                    } else if (selectedTextField == bottomTextField) {
                        topTextField.setText(selectedWord);
                    }
                }
            }
        }
    }

    private JTextField getFocusedTextField() {
        for (java.awt.Component comp : gridPanel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel cellPanel = (JPanel) comp;
                for (java.awt.Component subComp : cellPanel.getComponents()) {
                    if (subComp instanceof JTextField && subComp.isFocusOwner()) {
                        return (JTextField) subComp;
                    }
                }
            }
        }
        return null;
    }

    private JPanel createCell(int row, int col) {
        JPanel cell = new JPanel(new BorderLayout());
        JTextField textField = new JTextField("");
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Add a DocumentListener to each text field
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateDropdownList(textField.getText().toLowerCase());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateDropdownList(textField.getText().toLowerCase());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateDropdownList(textField.getText().toLowerCase());
            }
        });

        final JButton definitionButton = new JButton("Définition");
        definitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (definitionButton.getText().equals("Définition")) {
                    cell.setBackground(Color.GRAY);
                    definitionButton.setText("Double");
                } else if (definitionButton.getText().equals("Double")) {
                    divideCellVertically(row, col);
                }
            }
        });

        cell.setBackground(Color.WHITE);
        cell.add(textField, BorderLayout.CENTER);
        cell.add(definitionButton, BorderLayout.SOUTH);

        return cell;
    }

    private int getRow(JPanel cell) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == cell) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int getCol(JPanel cell) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == cell) {
                    return j;
                }
            }
        }
        return -1;
    }

    private void generateGrid(int width, int height) {
        this.gridPanel.removeAll();
        this.gridPanel.setLayout(new GridLayout(height, width));

        cells = new JPanel[height][width];

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final JPanel cellPanel = createCell(i, j);

                cells[i][j] = cellPanel;
                this.gridPanel.add(cellPanel);
            }
        }

        this.frame.revalidate();
        this.frame.repaint();
    }

    private void updateDropdownList(String enteredText) {
        List<String> matchingWords = new ArrayList<>();
        for (String word : dictionary) {
            if (word.toLowerCase().startsWith(enteredText)) {
                matchingWords.add(word);
            }
        }

        // Update the JComboBox with matching words
        wordComboBox.removeAllItems();
        for (String word : matchingWords) {
            wordComboBox.addItem(word);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Grid grid = new Grid();
                // Set the application to full-screen mode
                grid.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }
}

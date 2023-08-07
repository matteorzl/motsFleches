import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OtherGrid extends JFrame {
    private int rows;
    private int cols;
    private JPanel[][] cells;
    private JPanel gridPanel;
    private JTextField heightField;
    private JTextField widthField;

    public OtherGrid() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GridLayout Example");

        // Create fields for height and width
        heightField = new JTextField(5);
        widthField = new JTextField(5);

        // Create "Generate" button
        JButton generateButton = new JButton("Générer");
        generateButton.addActionListener(e -> generateGrid());

        // Create a panel for input fields and button
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Hauteur :"));
        inputPanel.add(heightField);
        inputPanel.add(new JLabel("Largeur :"));
        inputPanel.add(widthField);
        inputPanel.add(generateButton);

        // Set default grid size
        rows = 4;
        cols = 4;
        createGrid(rows, cols);

        // Use BorderLayout to organize the components
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.SOUTH);
        add(new JScrollPane(gridPanel), BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private JPanel createCell() {
        JPanel cell = new JPanel(new BorderLayout());
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cell.setBackground(Color.WHITE);
        return cell;
    }

    private void createGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        if (gridPanel != null) {
            // Remove existing gridPanel if it exists
            remove(gridPanel);
        }

        gridPanel = new JPanel(new GridLayout(rows, cols));
        cells = new JPanel[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = createCell();
                cells[i][j].addMouseListener(new CellClickListener(i, j));
                gridPanel.add(cells[i][j]);
            }
        }

        add(new JScrollPane(gridPanel), BorderLayout.CENTER);
        revalidate();
    }

    private void generateGrid() {
        try {
            int height = Integer.parseInt(heightField.getText());
            int width = Integer.parseInt(widthField.getText());

            if (height > 0 && width > 0) {
                createGrid(height, width);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez saisir des valeurs positives pour la hauteur et la largeur.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir des valeurs numériques valides pour la hauteur et la largeur.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class CellClickListener extends MouseAdapter {
        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                divideCellHorizontally(row, col);
            }
        }
    }

    private void divideCellHorizontally(int row, int col) {
        // Check if cell is already divided
        if (cells[row][col].getComponentCount() > 1) {
            return;
        }

        // Create two new sub-cells using GridLayout
        JPanel subCell1 = createCell();
        JPanel subCell2 = createCell();
        cells[row][col].setLayout(new GridLayout(2, 1));
        cells[row][col].removeAll(); // Remove the existing cell content
        cells[row][col].add(subCell1);
        cells[row][col].add(subCell2);
        cells[row][col].revalidate();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(OtherGrid::new);
    }
}


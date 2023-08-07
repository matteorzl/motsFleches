/**
 * A custom class to display a word selection dialog box.
 * This class extends JDialog and uses a JComboBox to display the proposed words.
 *
 * The dialog box allows the user to select a word from a given list of words.
 * The selected word can be retrieved using the getSelectedWord() method.
 *
 * @version 1.0
 * @since 2023-08-07
 * @author ARZEL Mattéo
 */

package fr.limayrac.b3rpi.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomDialog extends JDialog {

    /**
     * The JComboBox used to display the proposed words.
     */
    public JComboBox<String> comboBox;

    /**
     * The list of proposed words.
     */
    private ArrayList<String> words;

    /**
     * Constructor of the CustomDialog class.
     *
     * @param parent The parent window of the dialog box.
     * @param words  The list of proposed words.
     */
    public CustomDialog(Frame parent, ArrayList<String> words) {
        super(parent, "Words", true);
        this.words = words;
        initComponents();
    }

    /**
     * Initializes the components of the dialog box.
     * Creates the JComboBox with the proposed words and the close button.
     */
    private void initComponents() {
        comboBox = new JComboBox<>(words.toArray(new String[0]));

        JButton closeButton = new JButton("OK");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLayout(new BorderLayout());
        add(comboBox, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Retrieves the word selected by the user.
     *
     * @return The word selected in the JComboBox.
     */
    public String getSelectedWord() {
        return (String) comboBox.getSelectedItem();
    }
}

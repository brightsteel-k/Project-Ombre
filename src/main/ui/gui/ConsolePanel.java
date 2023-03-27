package ui.gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConsolePanel extends JPanel implements KeyListener {

    private final JTextArea textOut;
    private final JScrollPane textOutPane;
    private final JTextField textIn;
    private Game game;
    private boolean isWaitingForEnter = false;

    public ConsolePanel() {
        setLayout(new BorderLayout());
        EmptyBorder outerBorder = new EmptyBorder(new Insets(20, 20, 20, 0));
        LineBorder innerBorder = new LineBorder(Colours.getColour("main_border"));
        setBorder(new CompoundBorder(outerBorder, innerBorder));

        textIn = configureTextIn();
        add(textIn, BorderLayout.SOUTH);
        textOut = configureTextOut();
        textOutPane = configureScrollableArea(textOut);
        add(textOutPane);

        setBackground(Colours.getColour("main_panel"));
    }

    private JTextField configureTextIn() {
        JTextField textField = new JTextField();
        textField.setForeground(Colours.getColour("text_normal"));
        textField.addKeyListener(this);
        textField.setFocusable(true);

        return textField;
    }

    private JTextArea configureTextOut() {
        JTextArea textArea = new JTextArea(1, 1);
        textArea.setEditable(false);
        textArea.setBackground(Colours.getColour("console_background"));
        textArea.setForeground(Colours.getColour("text_normal"));
        textArea.setLineWrap(true);
        return textArea;
    }

    private JScrollPane configureScrollableArea(JTextArea textArea) {
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollableTextArea;
    }

    public void inputEnterPressed() {
        game.printNextLine();
    }

    public void printLine(String text, boolean waitForResponse) {
        textOut.append(text + "\n\n");
        setInputActive(false);
        setWaitingForEnter(waitForResponse);

        textOutPane.revalidate();
        textOutPane.updateUI();
        int bottom = textOutPane.getVerticalScrollBar().getMaximum();
        textOutPane.getVerticalScrollBar().setValue(bottom);
    }

    public void setWaitingForEnter(boolean waiting) {
        isWaitingForEnter = waiting;
        if (waiting) {
            textIn.setText("[Press Enter to Continue]");
            textIn.requestFocus();
        } else {
            textIn.setText("");
        }
    }

    public void setInputActive(boolean value) {
        textIn.setEditable(value);
        if (value) {
            textIn.setForeground(Colours.getColour("text_normal"));
            textIn.setBackground(Colours.getColour("input_background_active"));
        } else {
            textIn.setBackground(Colours.getColour("text_inactive"));
            textIn.setBackground(Colours.getColour("input_background_inactive"));
        }
    }

    public ConsolePanel setGame(Game game) {
        this.game = game;
        return this;
    }

    private void flashInvalidInput() {

    }

    public void inputLine(String input) {
        if (!game.executeUserInput(input)) {
            flashInvalidInput();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused!
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println(textOutPane.getVerticalScrollBar().getValue());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (isWaitingForEnter) {
                inputEnterPressed();
            } else {
                inputLine(textIn.getText());
            }
        }
    }
}

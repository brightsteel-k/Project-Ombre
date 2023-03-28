package ui.gui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Prints story text to a scrollable viewport, receives user input text
public class ConsolePanel extends JPanel implements KeyListener {

    private final JTextArea textOut;
    private final JScrollPane textOutPane;
    private final JTextField textIn;
    private Game game;
    private boolean isWaitingForEnter = false;
    private boolean isPaused = false;

    // EFFECTS: ConsolePanel configures its layout, border, background, output text panel, and input text panel, and
    //          adds the two panels to itself.
    public ConsolePanel() {
        setLayout(new BorderLayout());
        EmptyBorder outerBorder = new EmptyBorder(new Insets(20, 20, 20, 0));
        LineBorder innerBorder = new LineBorder(Colours.getColour("main_border"));
        setBorder(new CompoundBorder(outerBorder, innerBorder));
        setBackground(Colours.getColour("main_panel"));

        textIn = configureTextIn();
        add(textIn, BorderLayout.SOUTH);
        textOut = configureTextOut();
        textOutPane = configureScrollableArea(textOut);
        add(textOutPane);
    }

    // EFFECTS: configures and returns a text field with the appropriate colour and listeners for the input text field
    private JTextField configureTextIn() {
        JTextField textField = new JTextField();
        textField.setForeground(Colours.getColour("text_normal"));
        textField.addKeyListener(this);
        textField.setFocusable(true);

        return textField;
    }

    // EFFECTS: configures and returns a text area with the appropriate setup for the output text area
    private JTextArea configureTextOut() {
        JTextArea textArea = new JTextArea(1, 1);
        textArea.setEditable(false);
        textArea.setBackground(Colours.getColour("console_background"));
        textArea.setForeground(Colours.getColour("text_normal"));
        textArea.setLineWrap(true);
        return textArea;
    }

    // EFFECTS: configures and returns a scroll panel through which to view the given output text area
    private JScrollPane configureScrollableArea(JTextArea textArea) {
        JScrollPane scrollableTextArea = new JScrollPane(textArea);
        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollableTextArea;
    }

    // MODIFIES: game, this
    // EFFECTS: tells this object's game instance to print the next line of the story's current scene.
    public void inputEnterPressed() {
        game.printNextLine();
    }

    // MODIFIES: this
    // EFFECTS: prints given text to the output text area, scrolls down to bottom, disables input, sets console panel
    //          to waiting for response iff waitForResponse == true.
    public void printLine(String text, boolean waitForResponse) {
        textOut.append(text + "\n\n");
        setInputActive(false);
        setWaitingForEnter(waitForResponse);

        textOutPane.revalidate();
        textOutPane.updateUI();
        int bottom = textOutPane.getVerticalScrollBar().getMaximum();
        textOutPane.getVerticalScrollBar().setValue(bottom);
    }

    // MODIFIES: this
    // EFFECTS: changes appearance of input bar to alert user that they can press enter to continue if waiting == true,
    //          reverts aforementioned changes if not.
    public void setWaitingForEnter(boolean waiting) {
        isWaitingForEnter = waiting;
        if (waiting) {
            textIn.setText("[Press Enter to Continue]");
            textIn.requestFocus();
        } else {
            textIn.setText("");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets input text field to editable iff value, changes its appearance appropriately
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

    // MODIFIES: this
    // EFFECTS: if value == true, sets isPaused to true and disables input. Else, sets isPaused to false and
    //          reverts the input text field's state to what it was previously.
    public void setPaused(boolean value) {
        isPaused = value;
        if (value) {
            setInputActive(false);
            textIn.setText("");
        } else if (isWaitingForEnter) {
            setWaitingForEnter(true);
        } else {
            setInputActive(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets this object's corresponding game to the given game
    public ConsolePanel setGame(Game game) {
        this.game = game;
        return this;
    }

    // REQUIRES: textIn.getText().length() > 0
    // MODIFIES: this
    // EFFECTS: uses an instance of Colours to linearly interpolates between normal and invalid text colours for the
    //          input text field for less than a second.
    private void flashInvalidInput() {
        Colours colours = new Colours();
        colours.flashColour(textIn, "text_normal", "text_invalid");
    }

    // REQUIRES: input.length() > 0
    // MODIFIES: this, game
    // EFFECTS: sends user input to game to be properly parsed and executed; makes the text flash red if invalid
    public void inputLine(String input) {
        if (!game.executeUserInput(input)) {
            flashInvalidInput();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Unused
    }

    // MODIFIES: this, game
    // EFFECTS: if console panel is paused, do nothing. Otherwise, when enter is pressed, two things may happen:
    //          1) if console panel is waiting for the user to press enter, it calls inputEnterPressed().
    //          2) else, if the input text field is not empty, passes the input on to inputLine().
    @Override
    public void keyReleased(KeyEvent e) {
        if (isPaused) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (isWaitingForEnter) {
                inputEnterPressed();
            } else if (textIn.getText().length() > 0) {
                inputLine(textIn.getText());
            }
        }
    }
}

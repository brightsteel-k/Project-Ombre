package ui.gui;

import model.SaveSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Panel that holds the "save" and "view spells" buttons
public class SidebarPanel extends JPanel {

    private MainWindow mainWindow;
    private Game game;
    private SaveButton saveButton;
    private SpellsButton spellsButton;
    private RestartButton restartButton;

    // EFFECTS: SidebarPanel has a box layout, empty border, coloured background, save button, and spells button
    public SidebarPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        setBackground(Colours.getColour("sidebar_panel"));

        saveButton = new SaveButton(this);
        spellsButton = new SpellsButton(this);
        restartButton = new RestartButton(this);

        add(saveButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(spellsButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(restartButton);
    }

    // MODIFIES: this
    // EFFECTS: sets this object's corresponding game instance to the given game
    public SidebarPanel setGame(Game game) {
        this.game = game;
        return this;
    }

    // MODIFIES: game, device disk
    // EFFECTS: tells this object's game instance to save the current state of the game
    public void saveGame() {
        game.saveGameState();
    }

    // MODIFIES: this object's spells button, this object's main window, this object's corresponding game instance
    // EFFECTS: resets spells button and tells the main window to reset its components and game
    public void restartGame() {
        spellsButton.reset();
        mainWindow.restartGame();
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}

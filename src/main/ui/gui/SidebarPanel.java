package ui.gui;

import model.SaveSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Panel that holds the "save" and "view spells" buttons
public class SidebarPanel extends JPanel {

    private MainWindow mainWindow;
    private Game game;

    // EFFECTS: SidebarPanel has a box layout, empty border, coloured background, save button, and spells button
    public SidebarPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        setBackground(Colours.getColour("sidebar_panel"));

        add(new SaveButton(this));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new SpellsButton(this));
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

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}

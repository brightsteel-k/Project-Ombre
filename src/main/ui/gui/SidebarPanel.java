package ui.gui;

import model.SaveSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarPanel extends JPanel {

    private MainWindow mainWindow;
    private Game game;

    public SidebarPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        setBackground(Colours.getColour("sidebar_panel"));

        add(new SaveButton(this));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new SpellsButton(this));
    }

    public SidebarPanel setGame(Game game) {
        this.game = game;
        return this;
    }

    public void saveGame() {
        game.saveGameState();
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }
}

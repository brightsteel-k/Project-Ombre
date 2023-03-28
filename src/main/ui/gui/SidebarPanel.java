package ui.gui;

import model.SaveSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarPanel extends JPanel {

    private Game game;

    public SidebarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));

        add(new SaveButton(this));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new SpellsButton(this));
        setBackground(Colours.getColour("sidebar_panel"));
    }

    public SidebarPanel setGame(Game game) {
        this.game = game;
        return this;
    }

    public void saveGame() {
        game.saveGameState();
    }
}

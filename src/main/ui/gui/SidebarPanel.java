package ui.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SidebarPanel extends JPanel {

    public SidebarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));

        add(new SaveButton());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new SpellsButton());
        setBackground(Colours.getColour("sidebar_panel"));
    }
}

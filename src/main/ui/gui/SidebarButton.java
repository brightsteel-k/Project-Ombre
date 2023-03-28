package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Abstract button that will appear on the sidebar of the game
public abstract class SidebarButton extends JButton implements ActionListener {

    protected SidebarPanel sidebar;

    // EFFECTS: SidebarButton has a set size, alignment, coloured background, action to be implemented, and
    //          corresponding sidebar panel to which it belongs.
    public SidebarButton(SidebarPanel sidebar) {
        this.sidebar = sidebar;
        MainWindow.setComponentSize(this, 50, 50);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setBackground(Colours.getColour("sidebar_button"));
        addActionListener(this);
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}

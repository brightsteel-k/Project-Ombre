package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SidebarButton extends JButton implements ActionListener {

    protected SidebarPanel sidebar;

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

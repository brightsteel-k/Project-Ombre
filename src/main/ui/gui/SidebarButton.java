package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class SidebarButton extends JButton implements ActionListener {

    protected SidebarPanel sidebar;

    public SidebarButton(SidebarPanel sidebar) {
        this.sidebar = sidebar;
        Dimension size = new Dimension(40, 40);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        addActionListener(this);
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}

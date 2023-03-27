package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class SidebarButton extends JButton implements ActionListener {

    public SidebarButton() {
        Dimension size = new Dimension(40, 40);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}

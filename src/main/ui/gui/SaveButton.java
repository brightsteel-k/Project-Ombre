package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SaveButton extends SidebarButton {

    public SaveButton(SidebarPanel sidebar) {
        super(sidebar);
        setIcon(new ImageIcon("data/icons/save_icon.png", "Floppy disk save icon"));
        setToolTipText("Save game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sidebar.saveGame();
    }
}

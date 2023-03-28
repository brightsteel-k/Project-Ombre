package ui.gui;

import java.awt.*;
import java.awt.event.ActionEvent;

public class SaveButton extends SidebarButton {

    public SaveButton(SidebarPanel sidebar) {
        super(sidebar);
        setBackground(new Color(0xA455C9));
        setText("S");
        setToolTipText("Save game");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sidebar.saveGame();
    }
}

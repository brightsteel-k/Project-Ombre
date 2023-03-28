package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

// A sidebar button that allows user to save their progress
public class SaveButton extends SidebarButton {

    // EFFECTS: SaveButton has a descriptive icon and tooltip, and a corresponding sidebar panel to which it belongs
    public SaveButton(SidebarPanel sidebar) {
        super(sidebar);
        setIcon(new ImageIcon("data/icons/save_icon.png", "Floppy disk save icon"));
        setToolTipText("Save game");
    }

    // MODIFIES: sidebar, device disk
    // EFFECTS: calls savegame() on its sidebar panel
    @Override
    public void actionPerformed(ActionEvent e) {
        sidebar.saveGame();
    }
}

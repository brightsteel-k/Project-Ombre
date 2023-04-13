package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

// A sidebar button that allows user to clear their progress
public class RestartButton extends SidebarButton {

    // EFFECTS: RestartButton has a descriptive icon and tooltip, and a corresponding sidebar panel to which it belongs
    public RestartButton(SidebarPanel sidebar) {
        super(sidebar);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setIcon(new ImageIcon("data/icons/restart_icon.png", "Hazard restart icon"));
        setToolTipText("Restart game");
    }

    // MODIFIES: sidebar
    // EFFECTS: displays confirmation message, calls restartgame() on its sidebar panel if user confirms
    @Override
    public void actionPerformed(ActionEvent e) {
        int i = JOptionPane.showConfirmDialog(this, "Are you sure you would like to restart?",
                "Restart game?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            sidebar.restartGame();
        }
    }
}

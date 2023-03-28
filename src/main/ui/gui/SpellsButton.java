package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

// A sidebar button that allows user to view their known spells
public class SpellsButton extends SidebarButton {

    private boolean selected = false;

    // EFFECTS: SpellsButton has a descriptive icon and tooltip, and a corresponding sidebar panel to which it belongs
    public SpellsButton(SidebarPanel sidebar) {
        super(sidebar);
        setIcon(new ImageIcon("data/icons/spells_icon.png", "Fireball spells icon"));
        setToolTipText("View spell list");
    }

    // MODIFIES: sidebar
    // EFFECTS: if selected, becomes unselected and sets this object's sidebar's main window's spell panel to inactive.
    //          else, becomes selected and sets this object's sidebar's main window's spell panel to active.
    @Override
    public void actionPerformed(ActionEvent e) {
        selected = !selected;
        sidebar.getMainWindow().setSpellsPanelActive(selected);
    }
}

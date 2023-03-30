package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

// A sidebar button that allows user to view their known spells
public class SpellsButton extends SidebarButton {

    private boolean selected = false;
    private ImageIcon iconOn;
    private ImageIcon iconOff;

    // EFFECTS: SpellsButton has a descriptive icon and tooltip, and a corresponding sidebar panel to which it belongs
    public SpellsButton(SidebarPanel sidebar) {
        super(sidebar);
        iconOn = new ImageIcon("data/icons/spells_selected_icon.png", "Fireball spells icon");
        iconOff = new ImageIcon("data/icons/spells_unselected_icon.png", "Fireball spells icon");
        setSelectionIcon();
        setToolTipText("View spell list");
    }

    // MODIFIES: this
    // EFFECTS: sets itself to unselected
    public void reset() {
        selected = false;
        setSelectionIcon();
    }

    // MODIFIES: this
    // EFFECTS: changes button icon to reflect selection state: iconOn if selected, iconOff else
    private void setSelectionIcon() {
        setIcon(selected ? iconOn : iconOff);
    }

    // MODIFIES: sidebar
    // EFFECTS: if selected, becomes unselected and sets this object's sidebar's main window's spell panel to inactive.
    //          else, becomes selected and sets this object's sidebar's main window's spell panel to active.
    @Override
    public void actionPerformed(ActionEvent e) {
        selected = !selected;
        setSelectionIcon();
        sidebar.getMainWindow().setSpellsPanelActive(selected);
    }
}

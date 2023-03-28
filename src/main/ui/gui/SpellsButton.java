package ui.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SpellsButton extends SidebarButton {

    private boolean selected = false;

    public SpellsButton(SidebarPanel sidebar) {
        super(sidebar);
        setIcon(new ImageIcon("data/icons/spells_icon.png", "Fireball spells icon"));
        setToolTipText("Spell list");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        selected = !selected;
        sidebar.getMainWindow().setSpellsPanelActive(selected);
    }
}

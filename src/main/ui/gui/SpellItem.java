package ui.gui;

import model.storyobjects.Spell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// An item in the list of spells displayed by the "view spells" panel
public class SpellItem extends JButton implements ActionListener {
    private SpellsPanel spellsPanel;
    private int index;

    // EFFECTS: SpellItem has a set colour, size, and action, and the given name, index, and spells panel to which it
    //          belongs. Index refers to the position of a corresponding spell in a list of spells in the given spells
    //          panel.
    public SpellItem(String spellName, int index, SpellsPanel spellsPanel) {
        super(spellName);
        this.spellsPanel = spellsPanel;
        this.index = index;
        MainWindow.setComponentSize(this, 360, 30);
        setBackground(Colours.getColour("spells_panel_button"));
        setForeground(Colours.getColour("text_normal"));
        addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS: displays information about the corresponding spell through the spellsPanel
    @Override
    public void actionPerformed(ActionEvent e) {
        spellsPanel.selectSpell(index);
    }
}

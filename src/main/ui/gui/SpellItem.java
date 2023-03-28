package ui.gui;

import model.storyobjects.Spell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpellItem extends JButton implements ActionListener {
    private SpellsPanel spellsPanel;
    private int index;

    public SpellItem(String spellName, int index, SpellsPanel spellsPanel) {
        super(spellName);
        this.spellsPanel = spellsPanel;
        this.index = index;
        MainWindow.setComponentSize(this, 360, 30);
        setBackground(Colours.getColour("spells_panel_button"));
        setForeground(Colours.getColour("text_normal"));
        addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        spellsPanel.selectSpell(index);
    }
}

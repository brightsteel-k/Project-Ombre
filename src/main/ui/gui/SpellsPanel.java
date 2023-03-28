package ui.gui;

import model.storyobjects.Spell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SpellsPanel extends JPanel {

    private JTextArea spellTitle;
    private JTextArea spellDescription;
    private JPanel bottomPanel;
    private List<Spell> spellList = new ArrayList<>();

    public SpellsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Colours.getColour("main_border")));
        setBackground(Colours.getColour("spells_panel_background"));
        setAlignmentX(RIGHT_ALIGNMENT);

        spellTitle = configureSpellTitle();
        spellDescription = configureSpellDescription();
        add(configureTopPanel(spellTitle, spellDescription));
        add(configureBottomPanel());
    }

    private JTextArea configureSpellTitle() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        MainWindow.setComponentSize(textArea, 400, 50);
        return textArea;
    }

    private JTextArea configureSpellDescription() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        MainWindow.setComponentSize(textArea, 400, 250);
        return textArea;
    }

    private JPanel configureTopPanel(JTextArea title, JTextArea description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        MainWindow.setComponentSize(panel, 400, 250);
        panel.add(title);
        panel.add(description);
        title.setEditable(false);
        description.setEditable(false);
        description.setLineWrap(true);
        panel.setBackground(Colours.getColour("spells_panel_description"));
        title.setBackground(Colours.getColour("spells_panel_description"));
        description.setBackground(Colours.getColour("spells_panel_description"));
        title.setForeground(Colours.getColour("text_silk"));
        description.setForeground(Colours.getColour("text_normal"));
        return panel;
    }

    private JScrollPane configureBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(Colours.getColour("spells_panel_background"));
        bottomPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 40)));
        JScrollPane scrollPane = new JScrollPane(bottomPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBackground(Colours.getColour("spells_panel_background"));
        return scrollPane;
    }

    public void registerSpells(Spell[] spells) {
        clearSpellList();
        for (int i = 0; i < spells.length; i++) {
            bottomPanel.add(new SpellItem(spells[i].getName(), i, this));
            add(Box.createRigidArea(new Dimension(0, 10)));
        }
        spellList.addAll(List.of(spells));
        revalidate();
        repaint();
    }

    public void selectSpell(int index) {
        if (index >= spellList.size()) {
            return;
        }
        Spell spell = spellList.get(index);
        spellTitle.setForeground(Colours.getColour("spell_" + spell.getSchool()));
        spellTitle.setText(spell.getName().toUpperCase());
        String contents = printSpellType(spell.getSchool()) + "\n" + printSpellDamage(spell.getDamage()) + "\n\n"
                + spell.getDescription();
        spellDescription.setText(contents);
    }

    public void clearSpellList() {
        bottomPanel.removeAll();
    }

    public String printSpellType(String school) {
        return "TYPE: " + school.substring(0, 1).toUpperCase() + school.substring(1) + " Magic.";
    }

    public String printSpellDamage(float damage) {
        return "DAMAGE: " + damage;
    }
}

package ui.gui;

import model.storyobjects.Spell;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

// Panel that displays the spells that the player knows
public class SpellsPanel extends JPanel {

    private JTextArea spellTitle;
    private JTextArea spellDescription;
    private JPanel bottomPanel;
    private JScrollPane bottomScrollPane;
    private List<Spell> spellList = new ArrayList<>();

    // EFFECTS: SpellsPanel has a box layout, line border, coloured background, spell title text area, and spell
    //          description text area.
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

    // EFFECTS: configures and returns a text area with the appropriate style and size for spell titles
    private JTextArea configureSpellTitle() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.BOLD, 18));
        textArea.setBackground(Colours.getColour("spells_panel_description"));
        textArea.setForeground(Colours.getColour("text_silk"));
        MainWindow.setComponentSize(textArea, 400, 50);
        textArea.setEditable(false);
        return textArea;
    }

    // EFFECTS: configures and returns a text area with the appropriate style and size for spell descriptions
    private JTextArea configureSpellDescription() {
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        textArea.setBackground(Colours.getColour("spells_panel_description"));
        textArea.setForeground(Colours.getColour("text_normal"));
        MainWindow.setComponentSize(textArea, 400, 250);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        return textArea;
    }

    // EFFECTS: configures and returns a panel with a box layout, empty border, spell title, and spell description
    private JPanel configureTopPanel(JTextArea title, JTextArea description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
        MainWindow.setComponentSize(panel, 400, 250);
        panel.add(title);
        panel.add(description);
        panel.setBackground(Colours.getColour("spells_panel_description"));
        return panel;
    }

    // EFFECTS: configures and returns a panel and scroll pane with a box layout, empty border, coloured background,
    //          and vertical scrollbar.
    private JScrollPane configureBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 40)));
        bottomPanel.setBackground(Colours.getColour("spells_panel_background"));
        bottomScrollPane = new JScrollPane(bottomPanel);
        bottomScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bottomScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        bottomScrollPane.setBackground(Colours.getColour("spells_panel_background"));
        return bottomScrollPane;
    }

    // REQUIRES: spells.length > 0
    // MODIFIES: this
    // EFFECTS: reloads line of spell buttons, displays one for each spell in the given array of spells and adds them
    //          to spellList.
    public void registerSpells(Spell[] spells) {
        clearSpells();
        for (int i = 0; i < spells.length; i++) {
            bottomPanel.add(new SpellItem(spells[i].getName(), i, this));
            add(Box.createRigidArea(new Dimension(0, 10)));
        }
        spellList = Arrays.asList(spells);
        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: displays information from the spell in spell list at the given index, using the spell title and
    //          spell description text areas.
    public void selectSpell(int index) {
        if (index < 0 || index >= spellList.size()) {
            return;
        }
        Spell spell = spellList.get(index);
        spellTitle.setForeground(Colours.getColour("spell_" + spell.getSchool()));
        spellTitle.setText(spell.getName().toUpperCase());
        String contents = printSpellType(spell.getSchool()) + "\n" + printSpellDamage(spell.getDamage()) + "\n\n"
                + spell.getDescription();
        spellDescription.setText(contents);
    }

    // MODIFIES: this
    // EFFECTS: remove all components from this object's bottom panel
    public void clearSpells() {
        bottomPanel.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: clears all the displaying text
    public void clearTopPanel() {
        spellTitle.setText("");
        spellDescription.setText("");
    }

    // EFFECTS: formats and returns a String describing a spell with the given magic type
    public String printSpellType(String school) {
        return "TYPE: " + school.substring(0, 1).toUpperCase() + school.substring(1) + " Magic.";
    }

    // EFFECTS: formats and returns a String describing a spell with the given damage
    public String printSpellDamage(float damage) {
        return "DAMAGE: " + damage;
    }

    // MODIFIES: this
    // EFFECTS: resizes this panel and the bottom scroll pane appropriately
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        MainWindow.setComponentSize(bottomScrollPane, width, height - 250);
    }
}

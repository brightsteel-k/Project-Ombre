package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// The frame that holds all the GUI components of the game
public class MainWindow extends JFrame implements WindowListener, WindowStateListener, ComponentListener {

    private Game game;
    private JPanel mainPanel;
    private SidebarPanel sidebarPanel;
    private ConsolePanel consolePanel;
    private SpellsPanel spellsPanel;

    // EFFECTS: MainWindow stores a reference to the given game and configures its size, close operation, listeners,
    //          layouts, and components. Finally, initializes Colours class and sets itself to visible.
    public MainWindow(Game game) {
        Colours.initializeColours();
        this.game = game;
        configureWindow();
        addComponents();
        setVisible(true);
    }

    public ConsolePanel getConsolePanel() {
        return consolePanel;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets the default size, minimum size, and close operation, and registers listeners to track changes
    //          in states.
    private void configureWindow() {
        setSize(1400, 800);
        setMinimumSize(new Dimension(735, 490));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
        addWindowStateListener(this);
        addComponentListener(this);
    }

    // MODIFIES: this, this object's sidebar panel, this object's console panel, this object's spells panel
    // EFFECTS: initializes and adds a main panel comprised of a sidebar and console; adds a spells panel on top of
    //          that.
    private void addComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        sidebarPanel = new SidebarPanel(this);
        consolePanel = new ConsolePanel();
        spellsPanel = new SpellsPanel();

        mainPanel.add(sidebarPanel, BorderLayout.EAST);
        mainPanel.add(consolePanel);
        spellsPanel.setVisible(false);
        fixComponentSizes();

        getLayeredPane().add(mainPanel, new Integer(0), 0);
        getLayeredPane().add(spellsPanel, new Integer(1), 1);
    }

    // MODIFIES: this object's spells panel, this object's console panel
    // EFFECTS: if value == true: activates spell panel, loads all known spells into it, and pauses console input.
    //          Else, deactivates spell panel and unpauses console input.
    public void setSpellsPanelActive(boolean value) {
        spellsPanel.setVisible(value);
        consolePanel.setPaused(value);
        spellsPanel.registerSpells(game.getKnownSpells());
        spellsPanel.selectSpell(0);
    }

    // MODIFIES: this object's spells panel, this object's console panel, this object's corresponding game instance
    // EFFECTS: clears spells panel and console panel, restarts the story with an empty player
    public void restartGame() {
        spellsPanel.clearTopPanel();
        //spellsPanel.setVisible(false);
        consolePanel.reset();
        game.restartGame();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Unused
    }

    // MODIFIES: this, device disk
    // EFFECTS: prompts user with option to save progress or go back before closing the program. Saves game state
    //          if user chooses to do so.
    @Override
    public void windowClosing(WindowEvent e) {
        if (game.hasSaved()) {
            System.exit(0);
        }

        int i = JOptionPane.showConfirmDialog(this, "Would you like to save your progress?",
                "Save game", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        switch (i) {
            case JOptionPane.YES_OPTION:
                game.saveGameState();
                System.exit(0);
            case JOptionPane.NO_OPTION:
                System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // Unused
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // Unused
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // Unused
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // Unused
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // Unused
    }

    // REQUIRES: width >= 0, height >= 0
    // MODIFIES: c
    // EFFECTS: sets given component's minimum, preferred, and maximum size to the given dimensions
    public static void setComponentSize(Component c, int width, int height) {
        Dimension dimension = new Dimension(width, height);
        c.setMinimumSize(dimension);
        c.setPreferredSize(dimension);
        c.setMaximumSize(dimension);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        fixComponentSizes();
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    @Override
    public void windowStateChanged(WindowEvent e) {
        fixComponentSizes();
    }

    // MODIFIES: this, this object's spellsPanel
    // EFFECTS: resizes main and spells panel based on main window dimensions
    private void fixComponentSizes() {
        mainPanel.setBounds(0, 0, getWidth() - 10, getHeight() - 35);
        spellsPanel.setBounds(getWidth() - 500, 20, 400, getHeight() - 95);
    }
}

package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame implements WindowListener {

    private Game game;
    private SidebarPanel sidebarPanel;
    private ConsolePanel consolePanel;

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

    private void configureWindow() {
        setSize(1400, 800);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    private void addComponents() {
        sidebarPanel = new SidebarPanel();
        consolePanel = new ConsolePanel();

        add(sidebarPanel, BorderLayout.EAST);
        add(consolePanel);
    }


    @Override
    public void windowOpened(WindowEvent e) {
        // Unused
    }

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
}

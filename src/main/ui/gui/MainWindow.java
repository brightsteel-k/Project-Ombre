package ui.gui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private SidebarPanel sidebarPanel;
    private ConsolePanel consolePanel;

    public MainWindow() {
        Colours.initializeColours();
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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void addComponents() {
        sidebarPanel = new SidebarPanel();
        consolePanel = new ConsolePanel();

        add(sidebarPanel, BorderLayout.EAST);
        add(consolePanel);
    }
}

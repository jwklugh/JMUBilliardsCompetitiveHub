package gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    JPanel mainPanel;
    JLabel label;

    public LoadingWindow() {
        mainPanel = new JPanel();
        label = new JLabel("Loading...");
        setContentPane(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 7)));
        mainPanel.add(label);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        setMinimumSize(new Dimension(100,60));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public void close() {
        dispose();
    }
}

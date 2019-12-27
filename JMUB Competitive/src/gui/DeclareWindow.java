package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DeclareWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    JPanel mainPanel;
    JLabel prompt;
    JPanel buttonPanel;
    JButton defenderButton;
    JButton challengerButton;
    JButton cancelButton;

    public DeclareWindow() {
        createElements();
        createLayout();
        createListener();
        createSettings();
    }

    private void createElements() {
        mainPanel = new JPanel();
        prompt = new JLabel("Please specify a winner:");
        buttonPanel = new JPanel();
        defenderButton = new JButton("Defender");
        challengerButton = new JButton("Challenger");
        cancelButton = new JButton("Cancel");
    }

    private void createLayout() {
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new BorderLayout());

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,15,0,15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));

        this.setContentPane(mainPanel);
        mainPanel.add(prompt,BorderLayout.NORTH);
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel,BorderLayout.CENTER);
        buttonPanel.add(defenderButton,BorderLayout.NORTH);
        buttonPanel.add(challengerButton,BorderLayout.CENTER);
        buttonPanel.add(cancelButton,BorderLayout.SOUTH);
    }

    private void createListener() {
        Listener l = new Listener();

        defenderButton.setName("DeclareDefenderButton");
        challengerButton.setName("DeclareChallengerButton");
        cancelButton.setName("DeclareCancelButton");

        defenderButton.addActionListener(l);
        challengerButton.addActionListener(l);
        cancelButton.addActionListener(l);
    }

    private void createSettings() {
        defenderButton.setPreferredSize(new Dimension(75,30));
        challengerButton.setPreferredSize(new Dimension(75,30));
        cancelButton.setPreferredSize(new Dimension(75,30));

        setTitle("Winner Declaration");
        setMinimumSize(new Dimension(250,150));
        setResizable(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }
}

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import struct.Challenge;

public class UpcomingChallengeWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    JPanel mainPanel;
    JLabel challengeDescriptionArea;
    JPanel promptPanel;
    JLabel prompt;
    JPanel buttonPanel;
    JButton openScoreboardButton;
    JButton declareWinnerButton;

    Challenge challenge;

    public UpcomingChallengeWindow(Challenge c) {
        challenge = c;
        createElements();
        createLayout();
        createListener();
        createSettings();
    }

    private void createElements() {
        mainPanel = new JPanel();
        challengeDescriptionArea = new JLabel(challenge.toString());
        promptPanel = new JPanel();
        prompt = new JLabel("Add an approver to the match:");
        buttonPanel = new JPanel();
        openScoreboardButton = new JButton("Open Scoreboard");
        declareWinnerButton = new JButton("Declare Winner");
    }

    private void createLayout() {
        mainPanel.setLayout(new BorderLayout());
        promptPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new BorderLayout());

        mainPanel.setBorder(BorderFactory.createEmptyBorder(2,5,5,5));
        promptPanel.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

        this.setContentPane(mainPanel);
        mainPanel.add(challengeDescriptionArea,BorderLayout.CENTER);
        mainPanel.add(promptPanel,BorderLayout.SOUTH);
        promptPanel.add(prompt,BorderLayout.NORTH);
        promptPanel.add(buttonPanel,BorderLayout.SOUTH);
        buttonPanel.add(openScoreboardButton,BorderLayout.WEST);
        buttonPanel.add(declareWinnerButton,BorderLayout.EAST);
    }

    private void createListener() {
        Listener l = new Listener();

        openScoreboardButton.setName("UpcomingScoreboardButton");
        declareWinnerButton.setName("UpcomingDeclareButton");

        openScoreboardButton.addActionListener(l);
        declareWinnerButton.addActionListener(l);

        this.addWindowListener(Listener.exitListener);
    }

    private void createSettings() {
        setTitle("Upcoming Challenge");
        setMinimumSize(new Dimension(300,165));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }
}

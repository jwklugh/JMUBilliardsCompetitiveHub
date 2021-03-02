package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import struct.Challenge;

public class AddApproverWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    JPanel mainPanel;
    JLabel challengeDescriptionArea;
    JPanel promptPanel;
    JLabel prompt;
    JTextField inputField;
    JPanel buttonPanel;
    JButton denyButton;
    JButton confirmButton;

    Challenge challenge;

    public AddApproverWindow(Challenge c) {
        challenge = c;
        createElements();
        createLayout()  ;
        createListener();
        createSettings();
    }

    private void createElements() {
        mainPanel = new JPanel();
        challengeDescriptionArea = new JLabel(challenge.toString());
        promptPanel = new JPanel();
        prompt = new JLabel("Add an approver to the match:");
        inputField = new JTextField();
        buttonPanel = new JPanel();
        denyButton = new JButton("Deny");
        confirmButton = new JButton("OK");
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
        promptPanel.add(inputField,BorderLayout.CENTER);
        promptPanel.add(buttonPanel,BorderLayout.SOUTH);
        buttonPanel.add(denyButton,BorderLayout.WEST);
        buttonPanel.add(confirmButton,BorderLayout.EAST);
    }

    private void createListener() {
        Listener l = new Listener();

        inputField.setName("ApproverInputField");
        denyButton.setName("ApproverDenyButton");
        confirmButton.setName("ApproverConfirmButton");

        inputField.getDocument().addDocumentListener(l);

        inputField.addActionListener(l);
        denyButton.addActionListener(l);
        confirmButton.addActionListener(l);

        this.addWindowListener(Listener.exitListener);
    }

    private void createSettings() {
        checkValid();
        setTitle("Approval");
        setMinimumSize(new Dimension(300,165));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public void checkValid() {
        confirmButton.setEnabled(inputField.getText().length() > 0);
    }

    public String getInput() {
        return inputField.getText();
    }

    public Challenge getChallenge() {
        return challenge;
    }

}

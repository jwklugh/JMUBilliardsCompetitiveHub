package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import io.SheetsReadWriter.RankEditor;
import main.Main;
import struct.Challenge;
import struct.Player;

public class MainWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    JPanel mainPanel;
    JPanel westPanel;
    JPanel rankingsPanel;
    JPanel rankingsLabelPanel;
    JLabel rankingsLabel;
    JScrollPane rankingsPane;
    JList<Player> rankingsList;
    DefaultListModel<Player> rankingsModel;
    JPanel spreadsheetPanel;
    JPanel spreadsheetLabelPanel;
    JLabel spreadsheetLabel;
    JPanel spreadsheetButtonPanel;
    JButton openMembersButton;
    JButton openRankingsButton;
    JButton openChallengesButton;
    JPanel challengesPanel;
    JPanel challengesLabelPanel;
    JLabel challengesLabel;
    JPanel challengesSubPanel;
    JPanel requestedChallengesPanel;
    JPanel requestedChallengesLabelPanel;
    JLabel requestedChallengesLabel;
    JScrollPane requestedChallengesPane;
    JList<Challenge> requestedChallengesList;
    DefaultListModel<Challenge> requestedChallengesModel;
    JPanel upcomingChallengesPanel;
    JPanel upcomingChallengesLabelPanel;
    JLabel upcomingChallengesLabel;
    JScrollPane upcomingChallengesPane;
    JList<Challenge> upcomingChallengesList;
    DefaultListModel<Challenge> upcomingChallengesModel;
    JPanel spreadsheetButtonPanel2;
    JButton refreshButton;

    RankEditor spreadsheet;

    public MainWindow() throws IOException {
        spreadsheet = Main.getSheet();
        createElements();
        createLayout();
        createListener();
        populateRankings();
        populateChallenges();
        createSettings();
    }

    private void createElements() {
        mainPanel = new JPanel();
        westPanel = new JPanel();
        rankingsPanel = new JPanel();
        rankingsLabelPanel = new JPanel();
        rankingsLabel = new JLabel(" Rankings:");
        rankingsModel = new DefaultListModel<>();
        rankingsList = new JList<>(rankingsModel);
        rankingsPane = new JScrollPane(rankingsList);
        spreadsheetPanel = new JPanel();
        spreadsheetLabelPanel = new JPanel();
        spreadsheetLabel = new JLabel(" Spreadsheet:");
        spreadsheetButtonPanel = new JPanel();
        openMembersButton = new JButton("Open Members");
        openRankingsButton = new JButton("Open Rankings");
        openChallengesButton = new JButton("Open Challenges");
        challengesPanel = new JPanel();
        challengesLabelPanel = new JPanel();
        challengesLabel = new JLabel("Challenges:");
        challengesSubPanel = new JPanel();
        requestedChallengesPanel = new JPanel();
        requestedChallengesLabelPanel = new JPanel();
        requestedChallengesLabel = new JLabel(" Requested:");
        requestedChallengesModel = new DefaultListModel<>();
        requestedChallengesList = new JList<>(requestedChallengesModel);
        requestedChallengesPane = new JScrollPane(requestedChallengesList);
        upcomingChallengesPanel = new JPanel();
        upcomingChallengesLabelPanel = new JPanel();
        upcomingChallengesLabel = new JLabel(" Upcoming:");
        upcomingChallengesModel = new DefaultListModel<>();
        upcomingChallengesList = new JList<>(upcomingChallengesModel);
        upcomingChallengesPane = new JScrollPane(upcomingChallengesList);
        spreadsheetButtonPanel2 = new JPanel();
        refreshButton = new JButton(new ImageIcon("./refreshIcon.png"));
    }

    private void createLayout() {
        mainPanel                    .setLayout(new BorderLayout());
        westPanel                    .setLayout(new BorderLayout());
        rankingsPanel                .setLayout(new BorderLayout());
        rankingsLabelPanel           .setLayout(new BorderLayout());
        spreadsheetPanel             .setLayout(new BorderLayout());
        spreadsheetLabelPanel        .setLayout(new BorderLayout());
        challengesPanel              .setLayout(new BorderLayout());
        challengesLabelPanel         .setLayout(new BorderLayout());
        challengesSubPanel           .setLayout(new BorderLayout());
        requestedChallengesPanel     .setLayout(new BorderLayout());
        requestedChallengesLabelPanel.setLayout(new BorderLayout());
        upcomingChallengesPanel      .setLayout(new BorderLayout());
        upcomingChallengesLabelPanel .setLayout(new BorderLayout());
        spreadsheetButtonPanel       .setLayout(new BorderLayout());
        spreadsheetButtonPanel2      .setLayout(new BorderLayout());

        this.setContentPane(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(2,5,5,5));
        challengesPanel.setBorder(BorderFactory.createEmptyBorder(0,3,0,0));
        spreadsheetPanel.setBorder(BorderFactory.createEmptyBorder(3,0,0,3));

        @SuppressWarnings("unused")
        String N = BorderLayout.NORTH, S = BorderLayout.SOUTH, E = BorderLayout.EAST, W = BorderLayout.WEST, C = BorderLayout.CENTER;

        mainPanel.add(westPanel,W);
        westPanel.add(rankingsPanel,C);
        rankingsPanel.add(rankingsLabelPanel, N);
        rankingsLabelPanel.add(rankingsLabel, W);
        rankingsPanel.add(rankingsPane,C);
        westPanel.add(spreadsheetPanel,S);
        spreadsheetPanel.add(spreadsheetLabelPanel,N);
        spreadsheetLabelPanel.add(spreadsheetLabel,W);
        spreadsheetPanel.add(spreadsheetButtonPanel,C);
        spreadsheetButtonPanel.add(spreadsheetButtonPanel2,N);
        spreadsheetButtonPanel2.add(openMembersButton,C);
        spreadsheetButtonPanel2.add(refreshButton,E);
        spreadsheetButtonPanel.add(openRankingsButton,C);
        spreadsheetButtonPanel.add(openChallengesButton,S);
        mainPanel.add(challengesPanel,C);
        challengesPanel.add(challengesLabelPanel,N);
        challengesLabelPanel.add(challengesLabel,W);
        challengesPanel.add(challengesSubPanel,C);
        challengesSubPanel.add(requestedChallengesPanel,N);
        requestedChallengesPanel.add(requestedChallengesLabelPanel,N);
        requestedChallengesLabelPanel.add(requestedChallengesLabel,W);
        requestedChallengesPanel.add(requestedChallengesPane,C);
        challengesPanel.add(upcomingChallengesPanel,S);
        upcomingChallengesPanel.add(upcomingChallengesLabelPanel,N);
        upcomingChallengesLabelPanel.add(upcomingChallengesLabel,W);
        upcomingChallengesPanel.add(upcomingChallengesPane,C);
    }

    private void createListener() {
        Listener l = new Listener();

        openMembersButton.setName("MainOpenMembersButton");
        openRankingsButton.setName("MainOpenRankingsButton");
        openChallengesButton.setName("MainOpenChallengesButton");
        rankingsList.setName("MainRankingsList");
        requestedChallengesList.setName("MainRequestedChallengesList");
        upcomingChallengesList.setName("MainUpcomingChallengesList");
        refreshButton.setName("MainRefreshButton");

        refreshButton.addActionListener(l);
        openMembersButton.addActionListener(l);
        openRankingsButton.addActionListener(l);
        openChallengesButton.addActionListener(l);

        rankingsList.addListSelectionListener(l);
        requestedChallengesList.addListSelectionListener(l);
        upcomingChallengesList.addListSelectionListener(l);

        requestedChallengesList.addFocusListener(l);
        upcomingChallengesList.addFocusListener(l);

        requestedChallengesList.addMouseListener(l);
        upcomingChallengesList.addMouseListener(l);
    }

    private void createSettings() {
        rankingsPane.setPreferredSize(new Dimension(200,300));
        requestedChallengesPane.setPreferredSize(new Dimension(300,150));
        upcomingChallengesPane.setPreferredSize(new Dimension(300,150));
        refreshButton.setPreferredSize(new Dimension(28,26));
        this.setTitle("JMU Biliards Home");
        this.setMinimumSize(new Dimension(600,400));
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void populateRankings() throws IOException {
        rankingsModel.clear();
        for(List<Object> line : spreadsheet.getRankings()) {
            rankingsModel.addElement(new Player(line));
        }
    }

    private void populateChallenges() throws IOException {
        requestedChallengesModel.clear();
        upcomingChallengesModel.clear();
        for(List<Object> line : spreadsheet.getChallenges()) {
            Challenge c = new Challenge(line);
            if(c.approver == null || c.approver == "")
                requestedChallengesModel.addElement(c);
            else if(c.winner == null || c.winner == "")
                upcomingChallengesModel.addElement(c);
        }
    }

    public void deselectRanking() {
        rankingsList.clearSelection();
    }

    public void deselectRequest() {
        requestedChallengesList.clearSelection();
    }

    public void deselectUpcoming() {
        upcomingChallengesList.clearSelection();
    }

    public Challenge getRequested() {
        return requestedChallengesList.getSelectedValue();
    }

    public Challenge getUpcoming() {
        return upcomingChallengesList.getSelectedValue();
    }

    public void refresh() throws IOException {
        populateRankings();
        populateChallenges();
        repaint();
    }
}

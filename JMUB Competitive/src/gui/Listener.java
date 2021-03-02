package gui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Main;

public class Listener extends MouseAdapter implements ActionListener, FocusListener, ListSelectionListener, DocumentListener  {

    @Override
    public void focusGained(FocusEvent arg0) {}

    @Override
    public void focusLost(FocusEvent arg0) {
        switch(((Container) arg0.getSource()).getName()) {
            case "MainRequestedChallengesList" :
                Main.getMainWindow().deselectRequest();
                break;
            case "MainUpcomingChallengesList" :
                Main.getMainWindow().deselectUpcoming();
                break;
        }

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        try {
            String tmp = "";
            switch(((Container) arg0.getSource()).getName()) {

                // Main ////////////////////////////////

                case "MainOpenMembersButton" :
                    io.WebLauncher.openWebpage("https://docs.google.com/spreadsheets/d/1A7TTP6QT4i-86szUjc3X5cBFFoFNAmuQCnHGhUgDpl8/edit#gid=0");
                    break;
                case "MainOpenRankingsButton" :
                    io.WebLauncher.openWebpage("https://docs.google.com/spreadsheets/d/1A7TTP6QT4i-86szUjc3X5cBFFoFNAmuQCnHGhUgDpl8/edit#gid=33465104");
                    break;
                case "MainOpenChallengesButton" :
                    io.WebLauncher.openWebpage("https://docs.google.com/spreadsheets/d/1A7TTP6QT4i-86szUjc3X5cBFFoFNAmuQCnHGhUgDpl8/edit#gid=1622813716");
                    break;
                case "MainRefreshButton" :
                    Main.getMainWindow().refresh();


                    // Approver ////////////////////////

                case "ApproverDenyButton" :
                    tmp = "denied";

                case "ApproverConfirmButton" :
                case "ApproverInputField" :

                    AddApproverWindow AW = Main.getApprovalWindow();

                    if(tmp.equals(""))
                        tmp = AW.getInput();

                    if(tmp.length()>0) {
                        Main.getSheet().setApprover(AW.getChallenge().timestamp, tmp);
                        AW.getChallenge().approver = tmp;
                        Main.getMainWindow().refresh();
                        io.FileLauncher.launchResponder(AW.challenge.toCSV());
                        Main.closeApprovalWindow();
                    }
                    break;


                    // Upcoming ////////////////////////

                case "UpcomingScoreboardButton" :
                    io.FileLauncher.launchScoreboard(Main.getUpcomingWindow().challenge.toCSV());
                    Main.closeUpcomingWindow();
                    break;

                case "UpcomingDeclareButton" :
                    Main.getDeclareWindow();
                    break;


                    // Declare /////////////////////////

                case "DeclareDefenderButton" :
                    tmp = "Defender";

                case "DeclareChallengerButton" :
                    if(tmp.equals(""))
                        tmp = "Challenger";

                case "DeclareCancelButton" :
                    if(!tmp.equals("")) {
                        UpcomingChallengeWindow UW = Main.getUpcomingWindow();
                        Main.getSheet().setWinner(UW.challenge.timestamp, tmp);
                        Main.getSheet().moveRanks(
                                tmp.equals("Defender") ? UW.challenge.defender : UW.challenge.challenger,
                                        tmp.equals("Defender") ? UW.challenge.challenger : UW.challenge.defender,
                                                new ArrayList<List<Object>>());
                        Main.closeUpcomingWindow();
                        Main.getMainWindow().refresh();
                    } else {
                        Main.closeDeclareWindow();
                    }
                    break;

                default :
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        switch(((Container) arg0.getSource()).getName()) {
            case "MainRankingsList" :
                Main.getMainWindow().deselectRanking();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        switch(((Container) arg0.getSource()).getName()) {
            case "MainRequestedChallengesList" :
                if (arg0.getClickCount() == 2) {
                    Main.getApprovalWindow().requestFocus();
                }
                break;
            case "MainUpcomingChallengesList" :
                if (arg0.getClickCount() == 2) {
                    Main.getUpcomingWindow().requestFocus();
                }
                break;
        }
    }

    @Override
    public void changedUpdate(DocumentEvent arg0) {
        Main.getApprovalWindow().checkValid();
    }

    @Override
    public void insertUpdate(DocumentEvent arg0) {
        Main.getApprovalWindow().checkValid();
    }

    @Override
    public void removeUpdate(DocumentEvent arg0) {
        Main.getApprovalWindow().checkValid();
    }


    // Notice I very much dislike Lambda Classes as this,
    // but I also wanted to keep this function in this class
    // Since multiple inheritance wasn't an option, this is what I did.
    static WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            Main.exitWindow(e.getSource());
        }
    };

}

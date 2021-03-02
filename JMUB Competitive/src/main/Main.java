package main;

import java.io.IOException;

import gui.AddApproverWindow;
import gui.DeclareWindow;
import gui.MainWindow;
import gui.UpcomingChallengeWindow;
import io.SheetsReadWriter.RankEditor;

public class Main {

    private static MainWindow mainWindow;
    private static RankEditor spreadsheet;
    private static AddApproverWindow approvalWindow;
    private static UpcomingChallengeWindow upcomingWindow;
    private static DeclareWindow declareWindow;

    public static void main(String[] args) {
        try {
            spreadsheet = new RankEditor("1A7TTP6QT4i-86szUjc3X5cBFFoFNAmuQCnHGhUgDpl8");
            mainWindow = new MainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public static RankEditor getSheet() {
        return spreadsheet;
    }

    public static AddApproverWindow getApprovalWindow() {
        if(approvalWindow == null) {
            approvalWindow = new AddApproverWindow(mainWindow.getRequested());
            mainWindow.setEnabled(false);
        }
        return approvalWindow;
    }

    public static UpcomingChallengeWindow getUpcomingWindow() {
        if(upcomingWindow == null) {
            upcomingWindow = new UpcomingChallengeWindow(mainWindow.getUpcoming());
            mainWindow.setEnabled(false);
        }
        return upcomingWindow;
    }

    public static DeclareWindow getDeclareWindow() {
        if(declareWindow == null)
            if(upcomingWindow != null)
                declareWindow = new DeclareWindow();
        declareWindow.requestFocus();
        return declareWindow;
    }

    public static void closeApprovalWindow() {
        mainWindow.setEnabled(true);
        approvalWindow.dispose();
        approvalWindow = null;
    }

    public static void closeUpcomingWindow() {
        mainWindow.setEnabled(true);
        upcomingWindow.dispose();
        upcomingWindow = null;
        if(declareWindow != null)
            closeDeclareWindow();
    }

    public static void closeDeclareWindow() {
        declareWindow.dispose();
        declareWindow = null;
    }

    public static void exitWindow(Object win) {
        // Would use a switch, cannot with type Object
        if(win.equals(approvalWindow))
            closeApprovalWindow();
        if(win.equals(declareWindow))
            closeDeclareWindow();
        if(win.equals(upcomingWindow))
            closeUpcomingWindow();
    }
}

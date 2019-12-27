package io;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class SheetsReadWriter {

    public static String rankSheet = "Rankings and Records";

    private String appName; // Application Name
    private JsonFactory jF; // Json Factory
    private String toksLoc; // Tokens Directory Path
    private String credsFP; // Credentials File Path
    private List<String> scopes; // Credentials Cache
    private NetHttpTransport transport;
    private Credential credentials;

    private Sheets service;
    private Values activeLink;

    private String sheetID;

    public SheetsReadWriter(String spreadsheetID) {
        setInstanceSettings();
        sheetID = spreadsheetID;
    }

    private void setInstanceSettings() {
        appName = "Google Sheets API Java ReadWriter";
        jF      = JacksonFactory.getDefaultInstance();
        toksLoc = "tokens";
        credsFP = "/credentials.json";
        scopes  = Collections.singletonList(SheetsScopes.SPREADSHEETS);

        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
            credentials = getCredentials();
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            // TODO
            e.printStackTrace();
        }

        service = new Sheets.Builder
                (transport, jF, credentials)
                .setApplicationName(appName)
                .build();

        activeLink = service.spreadsheets().values();
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials()
            throws IOException {
        // Load client secrets.
        InputStream in = SheetsReadWriter.class.getResourceAsStream(credsFP);

        if (in == null) {  throw new FileNotFoundException("Resource not found: " + credsFP); }

        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(jF, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow
                .Builder(
                        transport,
                        jF,
                        clientSecrets,
                        scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new File(toksLoc)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver =
                new LocalServerReceiver
                .Builder()
                .setPort(8888)
                .build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    //    public static void main(String[] args) throws IOException {
    //        SheetsReadWriter test = new SheetsReadWriter("1lbKkwI9_U29jRet7BwtUf61zoTAg6MFJClhB6J3dCO8");
    //        test.moveRanks("Myles", "Daniel", new ArrayList<>());
    //    }

    public Object getData(String pageName, String cell) throws IOException {
        ValueRange response = activeLink.get(sheetID, pageName+"!"+cell).execute();
        List<List<Object>> v = response.getValues();
        if(v != null && !v.isEmpty())
            if(v.get(0) != null && !v.get(0).isEmpty())
                return response.getValues().get(0).get(0);
        return null;
    }

    public List<List<Object>> getRange(String pageName, String range) throws IOException {
        ValueRange response = activeLink.get(sheetID, pageName+"!"+range).execute();
        return response.getValues();
    }

    //test.setData("Sheet1","E2","=C2+D5"); // "USER_ENTERED" Makes this a formula, while "RAW" makes this a String
    public UpdateValuesResponse setData(String pageName, String cell, Object value) throws IOException {

        List<List<Object>> set = new ArrayList<>();
        set.add(new ArrayList<Object>());
        set.get(0).add(value);

        ValueRange rangeSet = new ValueRange().setValues(set);

        return activeLink.update(sheetID, pageName+"!"+cell, rangeSet)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }

    public UpdateValuesResponse setRange(String pageName, String headCell, List<List<Object>> values) throws IOException {

        ValueRange rangeSet = new ValueRange().setValues(values);

        return activeLink.update(sheetID, pageName+"!"+headCell, rangeSet)
                .setValueInputOption("USER_ENTERED") // VS "RAW"
                .execute();
    }

    public String findRow(String pageName, String col, Object value) throws IOException {
        List<List<Object>> data = getRange(pageName,col+"1:"+col);
        for(int i=0; i<data.size(); i++)
            if(value.equals(data.get(i).get(0)))
                return "" + (i+1);
        return null;
    }


    public static class RankEditor extends SheetsReadWriter {

        public RankEditor(String spreadsheetID) {
            super(spreadsheetID);
        }

        public boolean moveRanks(String w, String l) throws IOException {
            String s = rankSheet; String name = "B"; String wins = "C"; String loss = "D";
            Object playerName;

            int winnerRank = 0;
            do { playerName = getData(s,name+ ++winnerRank);
            } while (!playerName.equals(w));

            int loserRank = 0;
            do { playerName = getData(s,name+ ++loserRank);
            } while (!playerName.equals(l));

            setData(s,wins+winnerRank, Integer.parseInt((String) getData(s,wins+winnerRank)) + 1);
            setData(s,loss+loserRank , Integer.parseInt((String) getData(s,loss+loserRank )) + 1);

            while(winnerRank > loserRank)
                swapPlayers(--winnerRank);

            return true;
        }

        public void swapPlayers(int topRow) throws IOException {
            int botRow = topRow + 1;
            Player top = new Player(getData(rankSheet,"B"+topRow),getData(rankSheet,"C"+topRow),getData(rankSheet,"D"+topRow));
            Player bot = new Player(getData(rankSheet,"B"+botRow),getData(rankSheet,"C"+botRow),getData(rankSheet,"D"+botRow));
            setData(rankSheet,"B"+topRow,bot.name);setData(rankSheet,"C"+topRow,bot.wins);setData(rankSheet,"D"+topRow,bot.loss);
            setData(rankSheet,"B"+botRow,top.name);setData(rankSheet,"C"+botRow,top.wins);setData(rankSheet,"D"+botRow,top.loss);
        }

        public boolean moveRanks(String w, String l, List<List<Object>> list) throws IOException {
            gui.LoadingWindow loading = new gui.LoadingWindow();
            list = getRange(rankSheet,"B2:D");
            Object playerName;

            int winnerRank = -1;
            do { playerName = list.get(++winnerRank).get(0);
            } while (!playerName.equals(w));

            int loserRank = -1;
            do { playerName = list.get(++loserRank).get(0);
            } while (!playerName.equals(l));

            list.get(winnerRank).set(1, Integer.parseInt((String)list.get(winnerRank).get(1))+1);
            list.get( loserRank).set(2, Integer.parseInt((String)list.get( loserRank).get(2))+1);

            while(winnerRank > loserRank) {
                swapPlayers(list, --winnerRank);
            }

            setRange(rankSheet,"B2",list);
            loading.close();
            return true;
        }

        public void swapPlayers(List<List<Object>> ranks, int topRow) {
            List<Object> tmp = ranks.get(topRow);
            ranks.set(topRow, ranks.get(topRow+1));
            ranks.set(topRow+1, tmp);
        }

        private class Player {
            String name;
            int wins;
            int loss;
            public Player(Object n, Object w, Object l) {
                name = (String) n; wins = Integer.parseInt((String) w); loss = Integer.parseInt((String) l);
            }
        }

        public List<List<Object>> getRankings() throws IOException {
            gui.LoadingWindow loading = new gui.LoadingWindow();
            List<List<Object>> rankings = getRange("Rankings and Records","A2:D");
            loading.close();
            return rankings;
        }

        public List<List<Object>> getChallenges() throws IOException {
            return getRange("Challenge Forum","A2:I");
        }

        public void setApprover(String timestamp, String approver) throws IOException {
            setData("Challenge Forum","H"+findRow("Challenge Forum","A",timestamp),approver);
            if(approver.equals("denied"))
                setWinner(timestamp,"-");
        }

        public void setWinner(String timestamp, String winner) throws IOException {
            setData("Challenge Forum","I"+findRow("Challenge Forum","A",timestamp),winner);
        }
    }
}

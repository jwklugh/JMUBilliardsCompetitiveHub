package struct;

import java.util.List;

public class Challenge {

    public String timestamp;
    public String challenger;
    public int cRank;
    public String defender;
    public int dRank;
    public Date date;
    public Time time;
    public String approver;
    public String winner;

    public Challenge() {

    }

    public Challenge(String line) {

    }

    public Challenge(List<Object> line) {
        if(line.size() >= 7) {
            timestamp = (String) line.get(0);
            challenger = ((String) line.get(1)).trim();
            cRank = Integer.parseInt((String) line.get(2));
            defender = ((String) line.get(3)).trim();
            dRank = Integer.parseInt((String) line.get(4));
            date = new Date((String) line.get(5));
            time = new Time((String) line.get(6));
            if(line.size() > 7)
                approver = ((String) line.get(7)).trim();
            if(line.size() > 8)
                winner = ((String) line.get(8)).trim();
        }
    }

    @Override
    public String toString() {
        String s = "<html>";
        if(approver != null && approver != "") {
            s += approver;
            s += "<br>" + date.toString() + " at " + time.toString() + " for #" + dRank;
            s += "<br>" + defender + " - " + dRank;
            s += "<br>" + challenger + " - " + cRank;
        } else {
            s += timestamp;
            s += "<br>" + date.toString() + " at " + time.toString() + " for #" + dRank;
            s += "<br>" + challenger + " (" + cRank + ") vs. "+ defender + " (" + dRank + ")";
        }
        return s;
    }

    public String toCSV() {
        String s = ";"; //separator
        return timestamp + s + challenger + s + cRank + s
                + defender + s + dRank + s + date.toCSV()
                + s + time.toCSV() + (approver!=null?s +approver:"");
    }
}

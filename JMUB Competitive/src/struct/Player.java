package struct;

import java.util.List;

public class Player {
    public int rank;
    public String name;
    public int wins;
    public int loss;

    public Player() {

    }

    public Player(String s) {
        String[] line = s.split("\t");
        if(line.length == 3) {
            name =                  line[0] ;
            wins = Integer.parseInt(line[1]);
            loss = Integer.parseInt(line[2]);
        }
        if(line.length == 4) {
            rank = Integer.parseInt(line[0]);
            name =                  line[1] ;
            wins = Integer.parseInt(line[2]);
            loss = Integer.parseInt(line[3]);
        }
    }

    public Player(List<Object> line) {
        if(line.size() == 3) {
            name =                  (String) line.get(0) ;
            wins = Integer.parseInt((String) line.get(1));
            loss = Integer.parseInt((String) line.get(2));
        }
        if(line.size() == 4) {
            rank = Integer.parseInt((String) line.get(0));
            name =                  (String) line.get(1) ;
            wins = Integer.parseInt((String) line.get(2));
            loss = Integer.parseInt((String) line.get(3));
        }
    }

    @Override
    public String toString() {
        return "" + (rank>0?rank+". ":"") + name + " (" + wins + "-" + loss + ")";
    }
}

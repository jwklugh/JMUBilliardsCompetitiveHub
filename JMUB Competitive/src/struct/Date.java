package struct;

public class Date {
    public int year;
    public int month;
    public int day;
    public String dow;

    public Date(String s) {
        String[] parts = s.split("/");
        year = Integer.parseInt(parts[2]);
        month = Integer.parseInt(parts[0]);
        day = Integer.parseInt(parts[1]);
        setDayOfWeek();
    }

    public Date(int y, int m, int d) {
        year = y;
        month = m;
        day = d;
        setDayOfWeek();
    }

    private void setDayOfWeek() {
        int ccode;
        switch(year/100) {
            case 19: ccode = 0 ; break;
            case 20: ccode = 6 ; break;
            case 21: ccode = 4 ; break;
            default: ccode = -1;
        }
        int ycode = (year%100 + year%100/4) %7;
        int mcode;
        switch(month) {
            case 1 : mcode = 0 ; break;
            case 2 : mcode = 3 ; break;
            case 3 : mcode = 3 ; break;
            case 4 : mcode = 6 ; break;
            case 5 : mcode = 1 ; break;
            case 6 : mcode = 4 ; break;
            case 7 : mcode = 6 ; break;
            case 8 : mcode = 2 ; break;
            case 9 : mcode = 5 ; break;
            case 10: mcode = 0 ; break;
            case 11: mcode = 3 ; break;
            case 12: mcode = 5 ; break;
            default: mcode = -1;
        }
        int dcode = day;
        // If you can divide a Gregorian year by 4, it's a leap year, unless it's divisible by 100.
        // But it is a leap year if it's divisible by 400.
        int lcode = month <= 2 ? year % 4 != 0 ? 0 : year % 100 != 0 ? 1 : year % 400 != 0 ? 0 : 1 : 0;

        int dowcode = (ccode + ycode + mcode + dcode - lcode)%7;
        switch(dowcode) {
            case 0: dow = "Sunday";    break;
            case 1: dow = "Monday";    break;
            case 2: dow = "Tuesday";   break;
            case 3: dow = "Wednesday"; break;
            case 4: dow = "Thursday";  break;
            case 5: dow = "Friday";    break;
            case 6: dow = "Saturday";  break;
            default:dow = "INVALID";   break;
        }
    }

    @Override
    public String toString() {
        return dow + ", " + month + "/" + day + "/" + year;
    }

    public String toCSV() {
        return "" + month + "/" + day + "/" + year;
    }
}

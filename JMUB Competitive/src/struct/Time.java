package struct;

public class Time {

    int hour;
    int minute;
    String xm;

    public Time(String s) {
        String[] parts1 = s.split(" ");
        if(parts1.length > 1) {
            xm = parts1[1].toLowerCase();
            String[] parts2 = parts1[0].split(":");
            hour = Integer.parseInt(parts2[0]);
            minute = Integer.parseInt(parts2[1]);
        }
    }

    public Time(int h, int m) {
        h += m/60;
        hour = h%24;
        minute = m%60;
        convert();
    }

    private void convert() {
        xm = hour < 12 ? "am" : "pm";
        hour %= 12;
        if(hour == 0) hour=12;
    }

    @Override
    public String toString() {
        return "" + hour + ":" + (minute<10?"0":"") + minute + " " + xm;
    }

    public String toCSV() {
        return "" + hour + ":" + (minute<10?"0":"") + minute + ":00 " + xm.toUpperCase();
    }
}

package Parser;

import java.util.ArrayList;
public class course {
    String name;
    String number;
    ArrayList<String> instructors;
    String attempted;
    String earned;
    String points;

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public ArrayList<String> getInstructors() {
        return instructors;
    }

    public String getAttempted() {
        return attempted;
    }

    public String getEarned() {
        return earned;
    }

    public String getPoints() {
        return points;
    }

    public course(String nam, String num, ArrayList<String> ins, String a, String e, String p){
        name = nam;
        number = num;
        instructors = ins;
        attempted = a;
        earned = e;
        points = p;
    }

    @Override
    public String toString() {
        return this.number;
    }
}

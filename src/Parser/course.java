package Parser;

import java.util.ArrayList;
public class course {
    String name;
    String number;
    ArrayList<String> instructors;
    String attempted;
    String earned;
    String points;

    public course(String nam, String num, ArrayList<String> ins, String a, String e, String p){
        name = nam;
        number = num;
        instructors = ins;
        attempted = a;
        earned = e;
        points = p;
    }

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

    public void printAll(){
        System.out.println(name);
        System.out.println(number);
        System.out.println("INSTRUCTORS: [");
        for(int i = 0; i < instructors.size(); i++){
            System.out.println(instructors.get(i));
        }
        System.out.println("]");
        System.out.println(attempted);
        System.out.println(earned);
        System.out.println(points);
    }
}

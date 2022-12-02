package Parser;

import java.util.ArrayList;

public class transSem {
    public String getYear() {
        return year;
    }

    public String getSeason() {
        return season;
    }

    String year;
    String season;
    ArrayList<course> courses;
    Double transGpa;
    Double attempted;
    Double earned;
    Double points;

    public transSem(String y, String s, ArrayList<course> c, Double tg, Double a, Double e, Double p){
        year = y;
        season = s;
        courses = c;
        transGpa = tg;
        attempted = a;
        earned = e;
        points = p;
    }

    public ArrayList<course> getCourses(){
        return courses;
    }

    public Double getAttempted(){
        return attempted;
    }

    public Double getEarned(){
        return earned;
    }
    public Double getPoints(){
        return points;
    }

    public Double getTransGpa(){
        return transGpa;
    }

    public void printAll(){
        System.out.println("TRANS SEMESTER: " + year + " " + season + "{");
        for(int i = 0; i < courses.size(); i++){
            courses.get(i).printAll();
        }
        System.out.println(transGpa);
        System.out.println(attempted);
        System.out.println(earned);
        System.out.println(points);
        System.out.println("}");
    }
}

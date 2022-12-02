package Parser;

import java.util.ArrayList;

public class semester {
    String year;
    String season;
    ArrayList<course> courses;
    gpa term;
    gpa transfer;
    gpa combined;
    gpa cum;
    gpa transCum;
    gpa combinedCum;

    public String getYear() {
        return year;
    }

    public String getSeason() {
        return season;
    }

    public ArrayList<course> getCourses() {
        return courses;
    }

    public gpa getTerm() {
        return term;
    }

    public gpa getTransfer() {
        return transfer;
    }

    public gpa getCombined() {
        return combined;
    }

    public gpa getCum() {
        return cum;
    }

    public gpa getTransCum() {
        return transCum;
    }

    public gpa getCombinedCum() {
        return combinedCum;
    }

    public semester(String y, String s, ArrayList<course> c, gpa ter, gpa tran, gpa comb, gpa cu, gpa tc, gpa cc){
        year = y;
        season = s;
        courses = c;
        term = ter;
        transfer = tran;
        combined = comb;
        cum = cu;
        transCum = tc;
        combinedCum = cc;
    }

    public void printAll(){
        System.out.println("SEMESTER: " + year + " " + season + "{");
        for(int i = 0; i < courses.size(); i++){
            courses.get(i).printAll();
        }
        term.printAll();
        transfer.printAll();
        combined.printAll();
        cum.printAll();
        transCum.printAll();
        combinedCum.printAll();
        System.out.println("}");
    }
}

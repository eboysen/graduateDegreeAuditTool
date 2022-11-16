package Parser;

import java.util.ArrayList;

public class transcript {
    String name;
    String studentId;
    ArrayList<externalDeg> exDegs;
    ArrayList<semester> sems;
    gpa totCumGpa;
    gpa totTransGpa;
    gpa totCombGpa;

    public transcript(String n, String si, ArrayList<externalDeg> ed, ArrayList<semester> s, gpa tcug, gpa ttg, gpa tcog){
        name = n;
        studentId = si;
        exDegs = ed;
        sems = s;
        totCumGpa = tcug;
        totTransGpa = ttg;
        totCombGpa = tcog;
    }
}

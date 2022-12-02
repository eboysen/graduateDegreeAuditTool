package Parser;

import java.util.ArrayList;

public class transcript {
    public String name;
    public String studentId;
    ArrayList<transSem> transSems = new ArrayList<>();
    ArrayList<semester> sems;
    gpa totCumGpa;
    gpa totTransGpa;
    gpa totCombGpa;

    public transcript(String n, String si, ArrayList<transSem> ts, ArrayList<semester> s, gpa tcug, gpa ttg, gpa tcog){
        name = n;
        studentId = si;
        transSems = ts;
        sems = s;
        totCumGpa = tcug;
        totTransGpa = ttg;
        totCombGpa = tcog;
    }

    public void printAll(){
        System.out.println(name);
        System.out.println(studentId);
        for(int i = 0; i < transSems.size(); i++){
            transSems.get(i).printAll();
        }
        for(int i = 0; i < sems.size(); i++){
            sems.get(i).printAll();
        }
        totCumGpa.printAll();
        totTransGpa.printAll();
        totCombGpa.printAll();
    }
}
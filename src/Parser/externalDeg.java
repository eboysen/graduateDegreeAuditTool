package Parser;

public class externalDeg {
    String school;
    String degreeName;
    String dateFinished;

    public externalDeg(String s, String dN, String dF){
        school = s;
        degreeName = dN;
        dateFinished = dF;
    }
    
    public void printAll(){
        System.out.println("EX DEGS: {");
        System.out.println(school);
        System.out.println(degreeName);
        System.out.println(dateFinished);
        System.out.println("}");
    }
}

package Parser;

public class gpa {
    String type;
    Double outOf4;
    Double attemptedTotal;
    Double earnedTotal;
    Double GPAUts;
    Double Points;

    public gpa(String t, Double o, Double a, Double e, Double g, Double p){
        type = t;
        outOf4 = o;
        attemptedTotal = a;
        earnedTotal = e;
        GPAUts = g;
        Points = p;
    }

    public void printAll(){
        System.out.println("GPA: (");
        System.out.println(type);
        System.out.println(outOf4);
        System.out.println(attemptedTotal);
        System.out.println(earnedTotal);
        System.out.println(GPAUts);
        System.out.println(Points);
        System.out.println(")");
    }
}

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class main {
    public static void main(String[] args){
        try{
            Degree CyberSecurityDegree = new Degree("Cyber Security");
            Degree DataScienceDegree = new Degree("Data Science");
            Degree IntelligentSystemsDegree = new Degree("Intelligent Systems");
            Degree InteractiveComputingDegree = new Degree("Interactive Computing");
            Degree NetworksTelecommDegree = new Degree("Networks and Telecommunications");
            Degree SystemsDegree = new Degree("Systems");
            Degree TraditionalDegree = new Degree("Traditional");
            System.out.println(CyberSecurityDegree);
            System.out.println(DataScienceDegree);
            System.out.println(IntelligentSystemsDegree);
            System.out.println(InteractiveComputingDegree);
            System.out.println(NetworksTelecommDegree);
            System.out.println(SystemsDegree);
            System.out.println(TraditionalDegree);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Failed to Deserialize Degree");

        }
        finally{
            try{
                String currentPath = new java.io.File(".").getCanonicalPath();
                System.out.println("\nCurrent dir:" + currentPath);
            }
            catch(Exception e){
                System.out.println("Big Failure");
            }
        }

    }
}

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class main {
    public static void main(String[] args){
        String filename = Degree.getDegreeFile("Cyber Security"); 
        Degree CyberSecurityDegree;
        try{
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            CyberSecurityDegree = (Degree)ois.readObject();
            ois.close();
            System.out.println(CyberSecurityDegree);
        }
        catch(Exception e){
            System.out.println("Failed to serialize degree");
            System.out.println(e);
        }
    }
}

import java.io.Serializable;

public class Degree implements Serializable{
    private String TrackName;
    private Requirement[] Requirements;

    public Degree(){
        this.TrackName = "Default";
    }

    public Degree(String degreeName){

    }

    public static String getDegreeFile(String degreeName){
        switch (degreeName) {
            case "Cyber Security":
                return "../degreePlans/CyberSecurityTrack.json";

            case "Data Science":
                return "../degreePlans/DataScienceTrack.json";

            case "Intelligent Systems":
                return "../degreePlans/IntelligentSystemsTrack.json";
            
            case "Interactive Computing":
                return "../degreePlans/InteractiveComputingTrack.json";   
            
            case "Networks and Telecommunications":
                return "../degreePlans/NetworksAndTeleCommunicationsTrack.json";  

            case "Systems":
                return "../degreePlans/SystemsTrack.json";   

            case "Traditional":
                return "../degreePlans/TraditionalTrack.json"; 

            default:
                return "../degreePlans/TraditionalTrack.json";
        }
    }

    public String toString(){
        String str = "";
        str += "\n"+TrackName;
        for(int x = 0; x<this.Requirements.length; x++){
            str += "\n\n"+Requirements[x];
        }
        return str;
    }

}

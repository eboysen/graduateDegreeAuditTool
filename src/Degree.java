import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.ArrayList;

public class Degree implements Serializable{
    private String TrackName;
    private ArrayList<Requirement> Requirements;

    public Degree(){
        this.TrackName = "Default";
    }

    public Degree(String degreeName) throws IOException{
        createDegreeFromJSON(degreeName);
    }

    public void createDegreeFromJSON(String TrackName){
        try {
            this.Requirements = new ArrayList<Requirement>();
            String filename = getDegreeFile(TrackName);
            File file = new File(filename);
            JsonFactory factory = new JsonFactory();
            JsonParser jsonParser = factory.createParser(file);
            jsonParser.nextToken();
            jsonParser.nextToken();
            String property = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if(!property.equals("TrackName")){
                System.out.println("ERROR: failed to get TrackName");
            }
            String track = jsonParser.getText();
            setTrackName(track);

            jsonParser.nextToken();
            ArrayList<Requirement> req = readRequirements(jsonParser);
            setRequirements(req);
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDegreeFile(String degreeName){
        switch (degreeName) {
            case "Cyber Security":
                return "./src/degreePlans/CyberSecurityTrack.json";

            case "Data Science":
                return "./src/degreePlans/DataScienceTrack.json";

            case "Intelligent Systems":
                return "./src/degreePlans/IntelligentSystemsTrack.json";
            
            case "Interactive Computing":
                return "./src/degreePlans/InteractiveComputingTrack.json";
            
            case "Networks and Telecommunications":
                return "./src/degreePlans/NetworksAndTeleCommunication.json";

            case "Systems":
                return "./src/degreePlans/SystemsTrack.json";

            case "Traditional":
                return "./src/degreePlans/TraditionalTrack.json";

            default:
                return "./src/degreePlans/TraditionalTrack.json";
        }
    }

    public ArrayList<Requirement> readRequirements(JsonParser jsonParser) {
        ArrayList<Requirement> requirements = new ArrayList<Requirement>();
        try {
            if (jsonParser.nextToken() != JsonToken.START_ARRAY) {
                System.out.println("\nERROR: "+jsonParser.getText());
                throw new IllegalStateException("Expected content to be an array");
            }

            //Go through full array
            while(jsonParser.nextToken() != JsonToken.END_ARRAY){
                //check for start of object on each object
                if (jsonParser.currentToken() != JsonToken.START_OBJECT) {
                    throw new IllegalStateException("Expected content to be an object");
                }
                jsonParser.nextToken();
                String property = jsonParser.getCurrentName();
                jsonParser.nextToken();
                //check that the first property is the requirement type
                if(!property.equals("type")){
                    throw new IllegalStateException("Expected type of Track");
                }
                else{ // Start custom parsing dependent upon polymorphic type of Requirement
                    String reqType = jsonParser.getText();
                    switch(reqType){
                        case "Course":
                            CourseRequirement req1 = new CourseRequirement(jsonParser);
                            requirements.add(req1);
                            break;
                        case "Multiple":
                            MultipleRequirement req2 = new MultipleRequirement(jsonParser);
                            requirements.add(req2);
                            break;
                        case "Elective":
                            ElectiveRequirement req3 = new ElectiveRequirement(jsonParser);
                            requirements.add(req3);
                            break;
                        default:
                            System.out.println("Non Standard Requirement Type: "+reqType);
                            break;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return requirements;
    }
    public ArrayList<Requirement> getRequirements() {
        return Requirements;
    }

    public void setRequirements(ArrayList<Requirement> Requirements){
        this.Requirements = Requirements;
    }

    public void setTrackName(String TrackName){
        this.TrackName = TrackName;
    }
    public String toString(){
        String str = "";
        str += "\nTrackName: "+TrackName;
        for(int x = 0; x<this.Requirements.size(); x++){
            str += Requirements.get(x);
        }
        return str;
    }

}

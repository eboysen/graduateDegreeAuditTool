import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import Parser.course;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Degree implements Serializable{
    private String TrackName;
    private Double CoreGPA;
    private Double CoreRequiredGPA;
    private Double ElectiveGPA;
    private Double ElectiveRequiredGPA6;
    private Double ElectiveRequiredGPA7;
    private Double OverallGPA;
    private ArrayList<Requirement> Requirements;

    public Degree(){
        this.TrackName = "Default";
    }

    public Degree(String degreeName) throws IOException{
        createDegreeFromJSON(degreeName);
    }

    public void validateDegreePlan(HashMap<String, course> transcript){
        calculateOverallGPA(transcript);

        HashMap<String,course> transcriptCores = new HashMap<>();
        HashMap<String, course> transcriptElectives = new HashMap<>();
        ArrayList<Requirement> choiceRequirements = new ArrayList<>();

        //Separate requirements out into three types
        for(Requirement requirement: this.Requirements){
            requirement.setFulfilled(transcript);
            if(requirement.type.equals("Course")) {
                transcriptCores.putAll(requirement.fulfillingCourses);
            }
            else if(requirement.type.equals("Multiple")) {
                transcriptCores.putAll(requirement.usedCourses);
                transcriptElectives.putAll(requirement.fulfillingCourses);
                choiceRequirements.add(requirement);
            }
            else if(requirement.type.equals("Elective")) {
                transcriptElectives.putAll(requirement.fulfillingCourses);
            }
        }

        Double coreAttempted = 0.0;
        Double corePoints = 0.0;
        for(course credit : transcriptCores.values()){
            coreAttempted += Double.parseDouble(credit.getAttempted());
            corePoints += Double.parseDouble(credit.getPoints());
        }
        this.CoreGPA = corePoints/coreAttempted;
        Double remainingPoints = 48-corePoints;
        this.CoreRequiredGPA = remainingPoints/(5-transcriptCores.size());

        int numElectives = 6;

        Double electiveAttempted = 0.0;
        Double electivePoints = 0.0;
        HashMap<String, course> finalElectives = new HashMap<>();
        for(Requirement req: this.Requirements){
            if(req.type.equals("Elective")){
                ElectiveRequirement electiveReq = (ElectiveRequirement) req;
                electiveReq.setUsed(transcriptElectives);
                finalElectives.putAll(electiveReq.usedCourses);
            }
        }
        System.out.println("Final Electives: ");
        for(course credit: finalElectives.values()){
            System.out.println(credit.getNumber());
            electiveAttempted += Double.parseDouble(credit.getAttempted());
            electivePoints += Double.parseDouble(credit.getPoints());
        }
        System.out.println(electivePoints);
        this.ElectiveGPA = electivePoints/electiveAttempted;
        remainingPoints = (18*3)-electivePoints;
        this.ElectiveRequiredGPA6 = (remainingPoints/(6-finalElectives.size()))/3;
        this.ElectiveRequiredGPA7 = (remainingPoints/(7-finalElectives.size()))/3;

        System.out.println("Cores");
        for(course credit : transcriptCores.values()){
            System.out.println(credit.getNumber());
        }

        System.out.println("Electives");
        for(course credit : finalElectives.values()){
            System.out.println(credit.getNumber());
        }

        System.out.println("Core GPA:" +this.CoreGPA);
        System.out.println("Elective GPA:" +this.ElectiveGPA);
        System.out.println("Overall GPA:" +this.OverallGPA);
        System.out.println("Required Remaining Core GPA:" +this.CoreRequiredGPA);
        System.out.println("Required Remaining Elective GPA (6 electives):" +this.ElectiveRequiredGPA6);
        System.out.println("Required Remaining Elective GPA (7 electives):" +this.ElectiveRequiredGPA7);
        System.out.println("Remaining Elective Credits: "+remainingPoints);
        System.out.println("Transcript Electives: "+finalElectives.size());

        System.out.println(this.getFulfillment());
    }

    public void calculateOverallGPA(HashMap<String, course> transcript){
        Double attempted = 0.0;
        Double points = 0.0;
        for(course credit : transcript.values()){
            attempted += Double.parseDouble(credit.getAttempted());
            points += Double.parseDouble(credit.getPoints());
        }
        this.OverallGPA = points/attempted;
    }

    public String getFulfillment(){
        String str="";
        for(Requirement req: this.Requirements){
            str+="\n"+req.getFulfillmentStatus();
        }
        return str;
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

    public void printTranscriptSet(HashMap<String, course>transcript){
        for(course credit: transcript.values()){
            System.out.println(credit.getNumber());
        }
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

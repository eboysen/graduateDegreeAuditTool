import Parser.course;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectiveRequirement extends Requirement{
    private String level;
    private int credits;

    private int remainingCredits;

    public ElectiveRequirement(JsonParser jsonParser) throws IOException {
        this.type = "Elective";
        this.fulfillingCourses = new HashMap<>();
        this.usedCourses = new HashMap<>();
        while(jsonParser.nextToken()!= JsonToken.END_OBJECT) {
            String property = jsonParser.getText();
            jsonParser.nextToken();
            if (property.equals("level")) {
                this.level = jsonParser.getText();
            }
            if(property.equals("credits")){
                this.credits = jsonParser.getIntValue();
                this.remainingCredits = this.credits;
            }
        }
    }

    public boolean setFulfilled(HashMap<String, course>takenCourses){
        for(course takenCourse: takenCourses.values()){
            int currentLevel = Integer.parseInt(takenCourse.getNumber().split(" ")[1].substring(0,1));
            if(currentLevel>=Integer.parseInt(this.level) &&
                    takenCourse.getAttempted().equals(takenCourse.getEarned())&&
                    takenCourse.getNumber().split(" ")[0].equals("CS")){
                this.fulfillingCourses.put(takenCourse.getNumber(), takenCourse);
                if(remainingCredits<=0) {
                    this.remainingCredits -= Double.parseDouble(takenCourse.getEarned());
                    takenCourses.remove(takenCourse.getNumber());
                }
            }
        }
        if(this.remainingCredits <= 0){
            this.isFulfilled = true;
            return true;
        }
        return false;
    }

    public void setUsed(HashMap<String, course>takenCourses){
        int NumCredits = 0;
        while(NumCredits < this.credits) {
            Double high = 0.0;
            course highCourse = null;
            for (course takenCourse : takenCourses.values()) {
                int currentLevel = Integer.parseInt(takenCourse.getNumber().split(" ")[1].substring(0, 1));
                if (currentLevel >= Integer.parseInt(this.level) &&
                        takenCourse.getAttempted().equals(takenCourse.getEarned()) &&
                        takenCourse.getNumber().split(" ")[0].equals("CS") &&
                        Double.parseDouble(takenCourse.getPoints())/Double.parseDouble(takenCourse.getAttempted())>high) {

                    highCourse = takenCourse;
                    high = Double.parseDouble(takenCourse.getPoints())/Double.parseDouble(takenCourse.getAttempted());
                }
            }
            if(highCourse == null){
                break;
            }
            else {
                NumCredits += Double.parseDouble(highCourse.getEarned());
                this.usedCourses.put(highCourse.getNumber(), highCourse);
                takenCourses.remove(highCourse.getNumber());
            }
        }
        this.remainingCredits -= NumCredits;
    }

    public String getFulfillmentStatus(){
        if(this.remainingCredits<=0){
            return "\n✓ Fulfilled: \n"+this.toString();
        }
        else{
            return "\n✗ Failed to fulfill (Must complete "+
                    this.credits+" courses, only completed "+
                    (this.credits-this.remainingCredits)+"): \n"+
                    this.toString();
        }
    }

    public String toString(){
        return "\nTake " + this.credits + " credit hours approved by Advisor of level "+ this.level+ "000 or above";
    }
}
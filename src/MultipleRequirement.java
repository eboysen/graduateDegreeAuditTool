import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import Parser.course;

public class MultipleRequirement extends Requirement{
    private int amount;
    private ArrayList<String> options;

    public MultipleRequirement(JsonParser jsonParser) throws IOException {
        this.type = "Multiple";
        this.options = new ArrayList<String>();
        this.fulfillingCourses = new HashMap<>();
        this.usedCourses = new HashMap<>();
        while(jsonParser.nextToken()!= JsonToken.END_OBJECT){
            String property = jsonParser.getText();
            jsonParser.nextToken();
            if(property.equals("amount")){
                this.amount = jsonParser.getIntValue();
            }
            if(property.equals("options")){
                if(jsonParser.getCurrentToken() != JsonToken.START_ARRAY){
                    System.out.println("ERROR: Wrong Token ("+jsonParser.getText()+")");
                }
                while(jsonParser.nextToken()!=JsonToken.END_ARRAY){
                    this.options.add(jsonParser.getText());
                }
            }
        }
    }

    public boolean setFulfilled(HashMap<String, course> takenCourses){
        int amtTaken = 0;
        //add all the courses that fulfill the requirement
        for(int x = 0; x<options.size(); x++){
            String currentOption = options.get(x);
            if(takenCourses.get(currentOption) != null
                    && takenCourses.get(currentOption).getAttempted().equals(takenCourses.get(currentOption).getEarned())){
                this.fulfillingCourses.put(currentOption,takenCourses.get(currentOption));
                amtTaken++;
            }
        }
        //filter out the ones that will lbe used to fulfill the req into used
        for(int x = 0; x<this.amount; x++){
            Double high =0.0;
            course highCourse = null;
            for(course credit : this.fulfillingCourses.values()){
                if(Double.parseDouble(credit.getPoints())/Double.parseDouble(credit.getAttempted())>high){
                    high = Double.parseDouble(credit.getPoints())/Double.parseDouble(credit.getAttempted());
                    highCourse = credit;
                }
            }
            if(highCourse!= null) {
                this.usedCourses.put(highCourse.getNumber(),highCourse);
                this.fulfillingCourses.remove(highCourse.getNumber());
                takenCourses.remove(highCourse.getNumber());
            }
        }
        isFulfilled = amtTaken>=this.amount;
        return amtTaken>=this.amount;
    }

    public String getFulfillmentStatus(){
        if(usedCourses.size()==this.amount){
            return "\n✓ Fulfilled: \n"+this.toString();
        }
        else{
            return "\n✗ Failed to fulfill (Must complete "+
                    this.amount+" courses, only completed "+
                    this.usedCourses.size()+"): \n"+
                    this.toString();
        }
    }

    public String toString(){
        String str = "\nPick "+ this.amount+" of the following";
        for(int x = 0; x<options.size(); x++){
            str += "\n\t"+options.get(x);
        }
        return str;
    }

    public int getAmount(){
        return this.amount;
    }

    public ArrayList<String> getOptions() { return this.options; }

    public HashMap<String, course> getFulfillingCourses() { return this.fulfillingCourses; }
}
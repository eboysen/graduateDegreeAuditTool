import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Parser.course;

public class CourseRequirement extends Requirement{
    private String id;

    public CourseRequirement(JsonParser jsonParser) throws IOException {
        this.type = "Course";
        this.isFulfilled = false;
        this.fulfillingCourses = new HashMap<>();
        this.usedCourses = new HashMap<>();
        while(jsonParser.nextToken()!=JsonToken.END_OBJECT) {
            String property = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if (property.equals("id")) {
                this.id = jsonParser.getText();
            }
        }
    }
    public boolean setFulfilled(HashMap<String, course> takenCourses){
        if(takenCourses.get(id)!=null && takenCourses.get(id).getAttempted().equals(takenCourses.get(id).getEarned())){
            this.fulfillingCourses.put(id,takenCourses.get(id));
            this.usedCourses.put(id,takenCourses.get(id));
            this.isFulfilled = true;
            takenCourses.remove(id);
            return true;
        }
        return false;
    }

    public void scan(){

    }
    
    public String toString(){
        return "\nID: "+id;
    }
}
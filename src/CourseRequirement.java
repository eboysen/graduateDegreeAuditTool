import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;

public class CourseRequirement extends Requirement{
    private String id;

    public CourseRequirement(JsonParser jsonParser) throws IOException {
        this.type = "Course";
        while(jsonParser.nextToken()!=JsonToken.END_OBJECT){
            String property = jsonParser.getCurrentName();
            jsonParser.nextToken();
            if(property.equals("id")){
                this.id = jsonParser.getText();
            }
        }
    }
    public boolean isFullfilled(ArrayList<String> option){
        for(int x = 0; x<option.size(); x++){
            if(id.equals(option.get(x))){
                option.remove(x);
                return true;
            }
        }
        return false;
    }

    public void scan(){

    }
    
    public String toString(){
        return "\nID: "+id;
    }
}
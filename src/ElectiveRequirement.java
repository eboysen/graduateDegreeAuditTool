import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;

public class ElectiveRequirement extends Requirement{
    private String level;
    private int credits;

    public ElectiveRequirement(JsonParser jsonParser) throws IOException {
        this.type = "Elective";
        while(jsonParser.nextToken()!= JsonToken.END_OBJECT) {
            String property = jsonParser.getText();
            jsonParser.nextToken();
            if (property.equals("level")) {
                this.level = jsonParser.getText();
            }
            if(property.equals("credits")){
                this.credits = jsonParser.getIntValue();
            }
        }
    }

    public boolean isFullfilled(ArrayList<String>option){
        for(int x = 0;x<option.size();x++){
            if(option.get(x).substring(2,3).equals(this.level)){
                option.remove(x);
                return true;
            }
        }
        return false;
    }

    public void scan(){

    }

    public String toString(){
        return "\nTake " + this.credits + " credit hours approved by Advisor of level "+ this.level+ "000 or above";
    }
}
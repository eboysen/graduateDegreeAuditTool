import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;

public class MultipleRequirement extends Requirement{
    private int amount;
    private ArrayList<String> options;

    public MultipleRequirement(JsonParser jsonParser) throws IOException {
        this.type = "Multiple";
        this.options = new ArrayList<String>();
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

    public boolean isFullfilled(ArrayList<String> option){
        for(int y = 0; y<option.size(); y++){
            for(int x = 0; x<options.size(); x++){
                if(options.get(x).equals(option.get(y))){
                    option.remove(y);
                    return true;
                }
            }
        }
        return false;
    }

    public void scan(){

    }

    public String toString(){
        String str = "\nPick "+ this.amount+" of the following";
        for(int x = 0; x<options.size(); x++){
            str += "\n\t"+options.get(x);
        }
        return str;
    }
}
import java.util.ArrayList;

public class ElectiveRequirement extends Requirement{
    private int level;
    private int credits;

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
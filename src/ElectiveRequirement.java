public class ElectiveRequirement extends Requirement{
    private int level;
    private int credits;

    public boolean isFullfilled(ArrayList<String>option){
        for(int x = 0;x<option.size();x++){
            if(option.substring(2,3).equals(this.level)){
                option.remove(x);
                return true;
            }
        }
    }
}
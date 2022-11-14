public class MultipleRequirement extends Requirement{
    private int amount;
    private ArrayList<String> options;

    public boolean isFullfilled(ArrayList<String> option){
        for(int y = 0; y<option.size(); y++){
            for(int x = 0; x<options.size(); x++){
                if(options.get(x)==option.get(y)){
                    option.remove(y)
                    return true;
                }
            }
        }
        return false;
    }
}
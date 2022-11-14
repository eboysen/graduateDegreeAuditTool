public class CourseRequirement extends Requirement{
    private String id;
    public boolean isFullfilled(ArrayList<String> option){
        for(int x = 0; x<option.size(); x++){
            if(id == option.get(x)){
                option.remove(x);
                return true;
            }
        }
        return false;
    }
}
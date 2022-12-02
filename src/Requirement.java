import java.io.Serializable;
import java.util.ArrayList;
import Parser.course;
import java.util.HashMap;

public abstract class Requirement implements Serializable{
    protected String type;
    public HashMap<String,course> fulfillingCourses;
    protected HashMap<String,course> usedCourses;
    protected boolean isFulfilled;
    public abstract boolean setFulfilled(HashMap<String,course> option);
    public abstract String getFulfillmentStatus();
    public abstract String toString();
}
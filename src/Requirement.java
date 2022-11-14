import java.io.Serializable;
import java.util.ArrayList;

public abstract class Requirement implements Serializable{
    private String type;
    public abstract boolean isFullfilled(ArrayList<String> option);
    public abstract void scan();
    public abstract String toString();
}
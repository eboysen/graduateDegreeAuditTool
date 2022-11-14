public abstract class Requirement{
    private String type;
    public abstract boolean isFullfilled(ArrayList<String> option);
    public abstract void scan();
}
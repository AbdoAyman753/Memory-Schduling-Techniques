package sample;

public class Segment {
    private String name;
    private int size;
    private int base;
    public Segment(String Name,int Size){
        this.name=Name;
        this.size=Size;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    public void setBase(int base) { this.base = base; }
    public int getBase(){return  base; }
}

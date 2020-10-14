package sample;

public class Hole {
    private int Base;
    private int limit;

    public Hole(){
        //Nothing
         }

    public Hole(int base, int limit){
        this.Base=base;
        this.limit=limit;
    }

    public int getBase() {
        return Base;
    }

    public void setBase(int base) {
        Base = base;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}

package sample;

public class Processe {
    public Segment [] segmentsTable;
    private int segNum;
    private boolean firstFited;
    private boolean allocated;

    public  Processe(int n){
        segmentsTable=new Segment[n];
        this.segNum=n;
        this.allocated=false;
    }

    public boolean isAllocated() {
        return allocated;
    }

    public void setAllocated(boolean allocated) {
        this.allocated = allocated;
    }

    public Segment getSegments(int i) {
        return segmentsTable[i];
    }

    public void setSegments(Segment segments,int num) {
        this.segmentsTable[num] = segments;
    }

    public int getSegNum() {
        return segNum;
    }

    public void setSegNum(int segNum) {
        this.segNum = segNum;
    }

    public boolean isFirstFited() {
        return firstFited;
    }

    public void setFirstFited(boolean firstFited) {
        this.firstFited = firstFited;
    }
}

package sample;

public class Memory {
    private int address;
    private String data;

    public Memory() {
        this.address = 0;
        this.data = "";
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}

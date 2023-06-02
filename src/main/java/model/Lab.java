package model;

public class Lab extends Field {
    private final Code code;

    public Lab(int _i, int _j, Code c) {
        super(_i, _j);
        this.code = c;
        s = "Laborra leptel";
    }

    @Override
    public Code getCode() {
        return code;
    }
    public String getPath(){
        return "lab.png";
    }

    @Override
    public String getName(){
        return "Laborra leptel. ";
    }
}

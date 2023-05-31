package gradiens.model;

public class Lab extends Field {
    private final Code code;

    public Lab(int _i, int _j, Code c) {
        super(_i, _j);
        this.code = c;
        s = "Laborra léptél";
    }

    @Override
    public Code GetCode() {
        return code;
    }
    public String getPath(){
        return "lab.png";
    }

    @Override
    public String getName(){
        return "Laborra léptél. ";
    }
}

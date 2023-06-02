package model;

import java.util.List;

public class Code implements java.io.Serializable {
    private List<Material> required;
    private final Agent result;

    public Code(List<Material> req, Agent res) {
        required = req;
        result = res;
    }

    public List<Material> getRequired() {
        return required;
    }

    public void setRequired(List<Material> required) {
        this.required = required;
    }

    public Agent getResult() {
        return result;
    }
}

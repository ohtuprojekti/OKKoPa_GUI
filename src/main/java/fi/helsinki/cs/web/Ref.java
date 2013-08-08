package fi.helsinki.cs.web;

public class Ref {
    private String id;
    private String code;
    private String email;

    public Ref(String id, String code, String email) {
        this.id = id;
        this.code = code;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

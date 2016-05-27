package models;


public class Login {
    public Integer Id;
    public String Pass;



    private Boolean authenticate(Integer Id, String Pass) {
        return t_syain.authenticate(Id, Pass);
    }
}
package models;

public class Login {
    public String username;
    public String password;

    public String validate() {
        if (authenticate(username, password)) {
            return null;
        }
        return "※社員IDかパスワードが間違っています";
    }

    private Boolean authenticate(String username, String password) {
        return (username.equals("gongo") && password.equals("pizza"));
//        return User.authenticate(username, password);
    }
}
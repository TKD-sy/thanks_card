/*package models;


public class Login {
    public Integer Id;
    public String Pass;


//    authenticateの中をみてfalseだったらエラー文をhasErrorに入れる。
    public String validate() {
        if (authenticate(Id, Pass)) {
            return null;
        }
        return "※社員IDかパスワードが間違っています";
    }

//    syain_Idとsyain_Passの中身がDBの中にあるかどうかを判断する
    public static Boolean authenticate(Integer Id, String Pass) {
        t_syain user = t_syain.find.where().eq("syain_id", Id).findUnique();
        return (user != null && user.syain_pass.equals(Pass));
    }


}



*/
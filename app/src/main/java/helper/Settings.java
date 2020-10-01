package helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Settings {

    SharedPreferences myPref;
    public Settings(Context context){
        myPref = context.getSharedPreferences("myshop", Activity.MODE_PRIVATE);
    }

    public void SaveLogin(String userId , String username , String email){
        SharedPreferences.Editor editor =  myPref.edit();
        editor.putString("userId" , userId);
        editor.putString("username" , username);
        editor.putString("email" , email);
        editor.apply();
    }

    public String GetLoginInfo(){
        return myPref.getString("userId",null);
    }
    public String GetUsername(){
        return myPref.getString("username",null);
    }
    public String GetEmail(){
        return myPref.getString("email",null);
    }

    public void Logout(){
        SharedPreferences.Editor editor =  myPref.edit();
        editor.remove("userId");
        editor.remove("username");
        editor.remove("email");
        editor.apply();
    }
}

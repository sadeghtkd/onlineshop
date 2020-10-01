package ghanbari.sadegh.newshop;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import helper.Settings;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //My Code
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBar);
    }

    public void btnLoginClicked(View view) {

        if(txtUsername.getText().toString().length() ==0 || txtPassword.getText().toString().length()==0 ){
            Toast.makeText( this , getString(R.string.msgEnterUser) , Toast.LENGTH_LONG ).show();
        }else if( txtPassword.getText().toString().length() < 4 ){
            Toast.makeText( this , getString(R.string.msgBadPass) , Toast.LENGTH_LONG ).show();
        }else
        {
            progressBar.setVisibility(View.VISIBLE);
            ParseUser.logInInBackground(txtUsername.getText().toString(), txtPassword.getText().toString(), new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    progressBar.setVisibility(View.GONE);
                    if (user != null) {
                        Settings settings = new Settings(LoginActivity.this);
                        settings.SaveLogin( user.getObjectId() , user.getUsername() , user.getEmail());
                        // Hooray! The user is logged in.
                        Toast.makeText(LoginActivity.this , getString(R.string.welcome),Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // Signup failed. Look at the ParseException to see what happened.
                        Toast.makeText(LoginActivity.this , getString(R.string.invalidLogin),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void btnShowRegClicked(View view){
        startActivity(new Intent(this , RegisterActivity.class));
    }
}

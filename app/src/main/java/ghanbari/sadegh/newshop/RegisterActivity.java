package ghanbari.sadegh.newshop;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText txtUsername;
    EditText txtPass;
    EditText txtRepass;
    EditText txtEmail;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Find textboxes
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPass = findViewById(R.id.txtPassword);
        txtRepass = findViewById(R.id.txtRepass);
        progressBar = findViewById(R.id.progressBar2);
    }

    public void btnRegClicked(View view) {
        if(txtUsername.getText().toString().length() < 4
            || txtPass.getText().toString().length() < 4)
        {
            Toast.makeText(this , getString(R.string.msgEnterData),Toast.LENGTH_LONG).show();
        }else if(txtEmail.getText().toString().length() == 0)
            Toast.makeText(this , getString(R.string.msgEnterEmail),Toast.LENGTH_LONG).show();
        else if(txtPass.getText().toString().equals( txtRepass.getText().toString() ) == false)
            Toast.makeText(this , getString(R.string.msgPassNotSame),Toast.LENGTH_LONG).show();
        else
        {
            //Register new user
            progressBar.setVisibility(View.VISIBLE);
            ParseUser pUser = new ParseUser();
            pUser.setEmail(txtEmail.getText().toString());
            pUser.setPassword(txtPass.getText().toString());
            pUser.setUsername(txtUsername.getText().toString());
            pUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    progressBar.setVisibility(View.GONE);
                    if(e==null){
                        Toast.makeText(RegisterActivity.this , getString(R.string.registerDone),Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(RegisterActivity.this , getString(R.string.registerError),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}

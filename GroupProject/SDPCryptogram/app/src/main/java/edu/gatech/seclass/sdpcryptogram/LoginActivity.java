package edu.gatech.seclass.sdpcryptogram;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    Library mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLibrary = Library.getInstance(super.getApplicationContext());
        Library.pullDataFromServer();
    }

    public void userLogin(View view) {
        Intent intent;
        User mUser = new User(((EditText) findViewById(R.id.textfield_login_username)).getText().toString().trim());

        if(mUser.login() == 1){
            intent  = new Intent(LoginActivity.this, AdminMainActivity.class);
            startActivity(intent);
        } else if(mUser.login() == 2) {
            intent  = new Intent(LoginActivity.this, PlayerMainActivity.class);
            intent.putExtra("username", ((EditText) findViewById(R.id.textfield_login_username)).getText().toString().trim());
            startActivity(intent);
        } else {
            DialogFragment invalidLoginPopup = new LoginDialogFragment();
            invalidLoginPopup.show(getSupportFragmentManager(), "invalid_login");
        }
    }

    public static class LoginDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Invalid Login");
            adBuilder.setMessage("Sorry, but the username you have entered is not in the database. "+
            "Please contact the application administrator for assistance.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }
}

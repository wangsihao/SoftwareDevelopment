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

public class AddEditUserActivity extends AppCompatActivity {
    static boolean isExisting;
    static String newUsername, newFirstname, newLastname;
    Library mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_user);
        mLibrary = Library.getInstance();

        Intent intent = getIntent();
        isExisting = intent.getBooleanExtra("isExisting", false);

        if(isExisting) {
            ((EditText) findViewById(R.id.admin_addedit_user_firstname)).setText(intent.getStringExtra("first"));
            ((EditText) findViewById(R.id.admin_addedit_user_lastname)).setText(intent.getStringExtra("last"));
            ((EditText) findViewById(R.id.admin_addedit_user_username)).setText(intent.getStringExtra("username"));
        }
    }

    public void onCancel(View view) {
        Intent intent;
        intent  = new Intent(AddEditUserActivity.this, AdminMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("tab_index", 1);

        startActivity(intent);
    }

    public void onSubmit(View view) {
        boolean unique = true;
        DialogFragment confirmationPopup;

        newUsername = ((EditText) findViewById(R.id.admin_addedit_user_username)).getText().toString().trim();
        newFirstname = ((EditText) findViewById(R.id.admin_addedit_user_firstname)).getText().toString().trim();
        newLastname = ((EditText) findViewById(R.id.admin_addedit_user_lastname)).getText().toString().trim();

        for(Player p : Library.libraryPlayer) {
            if((newUsername != null) && (p.getUsername() != null) && (newUsername.equalsIgnoreCase(p.getUsername()))) {
                unique = false;
            }
        }

        if(unique) {
            confirmationPopup = new AddEditUserDialogFragment();
            Library.addPlayer(new Player(newUsername, newFirstname, newLastname));
        } else {
            confirmationPopup = new NotUniqueDialogFragment();
        }
        confirmationPopup.show(getSupportFragmentManager(), "user_confirmation");
    }

    public static class AddEditUserDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Confirmation");
            if(isExisting) {
                adBuilder.setMessage("The player "+newUsername+" has been successfully updated in the local database.");
            } else {
                adBuilder.setMessage("The new player "+newUsername+" has been successfully added to the local database.");
            }
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent;

                            intent  = new Intent(getActivity(), AdminMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("tab_index", 1);

                            startActivity(intent);
                        }
                    });
            return adBuilder.create();
        }
    }

    public static class NotUniqueDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Input Validation");
            adBuilder.setMessage("The username "+newUsername+" is already in use.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }
}

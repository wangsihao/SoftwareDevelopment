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

public class AddEditCryptogramActivity extends AppCompatActivity {
    static int currentID;
    static boolean isExisting;
    Library mLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit_cryptogram);

        mLibrary = Library.getInstance();

        Intent intent = getIntent();
        isExisting = intent.getBooleanExtra("isExisting", false);

        if(isExisting) {
            currentID = Integer.parseInt(intent.getStringExtra("cryptogramID"));
            ((EditText) findViewById(R.id.admin_addedit_crypt_cleartext)).setText(intent.getStringExtra("clear"));
            ((EditText) findViewById(R.id.admin_addedit_crypt_ciphertext)).setText(intent.getStringExtra("cipher"));
        }
    }

    public void onCancel(View view) {
        Intent intent;
        intent  = new Intent(AddEditCryptogramActivity.this, AdminMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    public void onSubmit(View view) {
        DialogFragment confirmationPopup;
        String newSolutionPhrase = ((EditText) findViewById(R.id.admin_addedit_crypt_cleartext)).getText().toString().trim();
        String newEncodedPhrase = ((EditText) findViewById(R.id.admin_addedit_crypt_ciphertext)).getText().toString().trim();

        try{
            if(newSolutionPhrase.length() != newEncodedPhrase.length()) {
                confirmationPopup = new CryptogramLengthErrorDialogFragment();
            } else {
                Boolean mismatch = false;

                for(int i = 0; i < newSolutionPhrase.length(); i++) {
                    if (newSolutionPhrase.substring(i, i+1).matches("[a-zA-Z]") != newEncodedPhrase.substring(i, i+1).matches("[a-zA-Z]")) {
                        mismatch = true;
                        break;
                    }
                }

                if(mismatch) {
                    confirmationPopup = new CryptogramMismatchErrorDialogFragment();
                } else if((!newSolutionPhrase.matches(".*[a-zA-Z]+.*")) || (!newEncodedPhrase.matches(".*[a-zA-Z]+.*"))) {
                    confirmationPopup = new CryptogramNoValidErrorDialogFragment();
                } else {
                    currentID = mLibrary.addCryptogram(new Cryptogram(newEncodedPhrase, newSolutionPhrase));
                    confirmationPopup = new AddEditCryptogramDialogFragment();
                }
            }
        } catch (IllegalArgumentException e) {
            confirmationPopup = new CryptogramIllegalArgErrorDialogFragment();
        }
        confirmationPopup.show(getSupportFragmentManager(), "cryptogram_confirmation");
    }

    public static class AddEditCryptogramDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Confirmation");
            if(isExisting) {
                adBuilder.setMessage("The cryptogram has been successfully updated.");
            } else {
                adBuilder.setMessage("The new cryptogram has been successfully added."+
                        "\n\n\n Cryptogram ID: "+ currentID );
            }
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent;
                    intent  = new Intent(getActivity(), AdminMainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
            return adBuilder.create();
        }
    }

    public static class CryptogramLengthErrorDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Input Validation");
            adBuilder.setMessage("The encoded and solution phrases you have entered for the new "+
                    "cryptogram are of differing lengths. Please check your entries.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }

    public static class CryptogramMismatchErrorDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Input Validation");
            adBuilder.setMessage("The encoded and solution phrases you have entered for the new "+
                    "cryptogram have mismatched character types. Please check your entries.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }

    public static class CryptogramIllegalArgErrorDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Input Validation");
            adBuilder.setMessage("The encoded and solution phrases you have entered for the new "+
                    "cryptogram has generated an Illegal Argument Error. This is most likely due "+
                    "to an invalid substitution key for your encoding. Please check your entries.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }

    public static class CryptogramNoValidErrorDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Input Validation");
            adBuilder.setMessage("The encoded and/or solution phrase you have entered for the new "+
                    "cryptogram have no valid alphabet characters. Please check your entries.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }
}
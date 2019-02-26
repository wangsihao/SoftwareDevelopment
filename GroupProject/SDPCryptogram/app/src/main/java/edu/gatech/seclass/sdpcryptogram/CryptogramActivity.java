package edu.gatech.seclass.sdpcryptogram;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CryptogramActivity extends AppCompatActivity {

    int currentID;
    String currentUsername;
    ArrayList<EditText> etList = new ArrayList<>();
    ArrayList<CryptogramInstance> currentInstanceArray;
    Library mLibrary;
    CryptogramInstance currentInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cryptogram);

        mLibrary = Library.getInstance();

        Intent intent = getIntent();
        currentID = intent.getIntExtra("cryptogramID", -1);
        currentUsername = intent.getStringExtra("username");

        currentInstanceArray = Library.getAllCryptogramInstances(currentUsername);
        for(CryptogramInstance ci: currentInstanceArray) {
            if(ci.getCryptogramID() == currentID){
                currentInstance = ci;
            }
        }
        if(currentInstance.isStarted()==false) {
            Library.startCryptogramInstance(currentInstance.getCryptogramID(), currentUsername);
        }
        if(currentInstance.getCurrentSolution().equalsIgnoreCase("")){
            currentInstance.setCurrentSolution(Library.getCryptogram(currentInstance.getCryptogramID()).getEncodedPhrase());
        }

        ((TextView) findViewById(R.id.cryptogram_ciphertext)).setText(Library.getCryptogram(currentInstance.getCryptogramID()).getEncodedPhrase());
        ((TextView) findViewById(R.id.cryptogram_currentsolution)).setText(currentInstance.getCurrentSolution());
        ((TextView) findViewById(R.id.cryptogram_attempts)).setText(String.valueOf(currentInstance.getIncorrectAttempts()));

        if(currentInstance.isSolved()){
            findViewById(R.id.cryptogram_solved_label).setVisibility(View.VISIBLE);
        }

        //NOT IDEAL BUT IT WORKS.
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_A));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_B));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_C));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_D));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_E));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_F));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_G));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_H));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_I));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_J));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_K));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_L));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_M));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_N));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_O));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_P));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_Q));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_R));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_S));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_T));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_U));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_V));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_W));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_X));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_Y));
        etList.add((EditText) findViewById(R.id.cryptogram_textinput_Z));

        for(final EditText et: etList) {
            if(currentInstance.isSolved()){
                et.setFocusable(false);
                et.setFocusableInTouchMode(false);
            }
            et.setSelectAllOnFocus(true);
            et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) { }

                @Override
                public void afterTextChanged(Editable s) {
                    for(EditText subET: etList) {
                        if(subET != et) {
                            if (subET.getText().toString().equalsIgnoreCase(et.getText().toString())) {
                                subET.getText().clear();
                            }
                        }
                    }
                }
            });
        }
    }

    public static class IncompleteDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder adBuilder = new AlertDialog.Builder(getActivity());
            adBuilder.setTitle("Incomplete Key");
            adBuilder.setMessage("The substitution key you have entered is incomplete. You "+
                    "must match a substitution letter to each letter in the cryptogram +" +
                    "before you submit.");
            adBuilder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            });
            return adBuilder.create();
        }
    }

    public void onSubmit(View view) {
        String cipher = ((EditText) findViewById(R.id.cryptogram_ciphertext)).getText().toString().trim();
        String temp = ((EditText) findViewById(R.id.cryptogram_ciphertext)).getText().toString().trim();
        String alphabetL = "abcdefghijklmnopqrstuvwxyz";
        String alphabetU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String finalSolution = "";
        boolean isSolved = false;
        HashMap<Character, Character> key = new HashMap<>();

        for(int i = 0; i < etList.size(); i++) {
            if (!etList.get(i).getText().toString().equalsIgnoreCase("")) {
                key.put(alphabetL.charAt(i), etList.get(i).getText().toString().toLowerCase().charAt(0));
                key.put(alphabetU.charAt(i), etList.get(i).getText().toString().toUpperCase().charAt(0));
            }
        }

        for(int i = 0; i < cipher.length(); i++) {
            if(cipher.substring(i, i + 1).matches("[A-Za-z]")) {
                if(key.get(cipher.charAt(i)) != null) {
                    finalSolution += key.get(cipher.charAt(i));
                    temp = temp.replace(cipher.charAt(i), ' ');
                }
            } else {
                finalSolution += cipher.charAt(i);
            }
        }

        Pattern mPattern = Pattern.compile("[A-Za-z]+");
        Matcher mMatcher = mPattern.matcher(temp);

        if(mMatcher.find()) {
            DialogFragment incompletePopup = new IncompleteDialogFragment();
            incompletePopup.show(getSupportFragmentManager(), "incomplete_popup");
        } else {
            ((EditText) findViewById(R.id.cryptogram_currentsolution)).setText(finalSolution);
            if(finalSolution.equalsIgnoreCase(Library.getCryptogram(currentInstance.getCryptogramID()).getSolutionPhrase())) {
                isSolved = true;
                findViewById(R.id.cryptogram_solved_label).setVisibility(View.VISIBLE);
            } else {
                currentInstance.setIncorrectAttempts(currentInstance.getIncorrectAttempts() + 1);
            }
        }
        Library.updateCryptogramInstance(currentInstance.getCryptogramID(), Library.getCryptogram(currentInstance.getCryptogramID()).getEncodedPhrase(), finalSolution, isSolved, currentInstance.getIncorrectAttempts(), currentUsername);
        ((TextView) findViewById(R.id.cryptogram_attempts)).setText(String.valueOf(currentInstance.getIncorrectAttempts()));
    }

    public void onCancel(View view) {
        Intent intent;
        intent = new Intent(CryptogramActivity.this, PlayerMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("username", currentUsername);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent;
            intent = new Intent(CryptogramActivity.this, PlayerMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("username", currentUsername);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
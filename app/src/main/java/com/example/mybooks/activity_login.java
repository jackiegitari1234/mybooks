package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.SQL.DatabaseHelper;
import com.example.myapplication.helpers.InputValidation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class activity_login extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = activity_login.this;
    private LinearLayout linearLayout;
    private TextView textViewEmail;
    private EditText editTextEmail;
    private TextView textViewPassword;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewLinkRegister;
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        initViews();
        initListeners();
        initObjects();

    }

    /**
            * This method is to initialize views
     */
    private void initViews() {
        linearLayout = findViewById(R.id.linearLayoutView);
        textViewEmail = findViewById(R.id.emailText);
        textViewPassword = findViewById(R.id.passwordText);
        editTextEmail = findViewById(R.id.inputEmail);
        editTextPassword = findViewById(R.id.inputPwd);
        buttonLogin = findViewById(R.id.btnLogin);
        textViewLinkRegister = findViewById(R.id.lnkRegister);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }
    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                verifyFromSQLite();
                break;
            case R.id.lnkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), activity_register.class);
                startActivity(intentRegister);
                break;
        }
    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextEmail, textViewEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, textViewEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, textViewPassword, getString(R.string.error_message_email))) {
            return;
        }
        if (databaseHelper.checkUser(editTextEmail.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {
//            Intent accountsIntent = new Intent(activity, UsersListActivity.class);
//            accountsIntent.putExtra("EMAIL", editTextEmail.getText().toString().trim());
            emptyInputEditText();
//            startActivity(accountsIntent);
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(linearLayout, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }
    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextEmail.setText(null);
        editTextPassword.setText(null);
    }
}
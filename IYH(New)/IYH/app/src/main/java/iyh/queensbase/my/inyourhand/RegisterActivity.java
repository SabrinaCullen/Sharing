package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.UserFunctions;

public class RegisterActivity extends Activity implements View.OnClickListener
{
    private Button mRegisterButton;
    private Button mClearButton;

    private EditText mInputUsername;
    private EditText mInputFullName;
    private EditText mInputEmail;
    private EditText mInputPassword;
    private EditText mInputBusinessName;

    private TextView mRegisterErrorTextView;

    // JSON Response node names
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_ERROR = "error";
    private static final String KEY_ERROR_MSG = "error_msg";
    private static final String KEY_UID = "uid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_FULLNAME = "fullname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_BUSINESSNAME = "businessname";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String[] ERROR_MSG = new String[]
            {
                    "One or more fields are empty",
                    "Username field is empty",
                    "Full name field is empty",
                    "Password field are empty",
                    "Email field is empty",
                    "Business Name field is empty",
                    "Not An Valid Email",
                    "Minimum password length is 6",
                    "Email already existed"
            };

    // the TAG is same as the name of the class
    // this is use as TAG of Log so we can know what class actually give us message
    private static final String TAG = "RegisterActivity";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Importing all assets like buttons, text fields
        mInputUsername     = (EditText)findViewById(R.id.username);
        mInputFullName     = (EditText)findViewById(R.id.fullname);
        mInputPassword     = (EditText)findViewById(R.id.password);
        mInputEmail        = (EditText)findViewById(R.id.email);
        mInputBusinessName = (EditText)findViewById(R.id.businessname);

        mRegisterButton = (Button)findViewById(R.id.btnRegister);
        mClearButton    = (Button)findViewById(R.id.b_clearAll);

        mRegisterErrorTextView = (TextView)findViewById(R.id.register_error);

        // Register Button Click event
        mRegisterButton.setOnClickListener(RegisterActivity.this);
        mClearButton.setOnClickListener(RegisterActivity.this);
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.btnRegister:
            {
                String username     = mInputUsername.getText().toString();
                String fullname     = mInputFullName.getText().toString();
                String password     = mInputPassword.getText().toString();
                String email        = mInputEmail.getText().toString();
                String businessname = mInputBusinessName.getText().toString();

                RegisterThread thread = new RegisterThread(username, fullname, password, email, businessname);
                thread.start();

                break;
            }

            case R.id.b_clearAll:
            {
                mInputUsername.setText("");
                mInputFullName.setText("");
                mInputPassword.setText("");
                mInputEmail.setText("");
                mInputBusinessName.setText("");

                break;
            }
        }
    }

    // Because we can only update UI in UI Thread, this is a workaround to display error message
    private void displayErrorMessage(final String message)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                mRegisterErrorTextView.setText(message);
            }
        });
    }

    private class RegisterThread extends Thread
    {
        private String mUsername;
        private String mFullname;
        private String mPassword;
        private String mEmail;
        private String mBusinessname;

        public RegisterThread(String username, String fullname, String password, String email, String businessname)
        {
            mUsername     = username;
            mFullname     = fullname;
            mPassword     = password;
            mEmail        = email;
            mBusinessname = businessname;
        }

        public void run()
        {
            UserFunctions userFunction = new UserFunctions();

            JSONObject json = userFunction.registerUser(mUsername, mFullname, mEmail, mPassword, mBusinessname);

            try
            {
                if (json.getString(KEY_SUCCESS) != null)
                {
                    int keySuccess = json.getInt(KEY_SUCCESS);


                    if (keySuccess == 1)
                    {
                        // user successfully registred
                        // Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        // Clear all previous data in database
                        userFunction.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_USERNAME), json_user.getString(KEY_FULLNAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_BUSINESSNAME), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
                        // Launch Dashboard Screen
                        // Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                        // Close all views before launching Dashboard
                        // dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // startActivity(dashboard);
                        // Close Registration Screen
                        finish();

                        return;
                    }

                    displayErrorMessage(ERROR_MSG[json.getInt(KEY_ERROR)]);
                }
            }
            catch (JSONException e)
            {
                Log.e(TAG, "Error on register user", e);
            }
        }

    }
}

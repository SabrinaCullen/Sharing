package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.UserFunctions;

public class LoginActivity extends Activity {
    Button btnLogin;
    EditText inputUsername;
    EditText inputFullName;
    EditText inputEmail;
    EditText inputPassword;
    EditText inputBusinessName;
    TextView loginErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_USERNAME = "username";
    private static String KEY_FULLNAME = "fullname";
    private static String KEY_EMAIL = "email";
    private static String KEY_BUSINESSNAME = "businessname";
    private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);

		// Importing all assets like buttons, text fields
        inputUsername = (EditText) findViewById(R.id.username);
        inputFullName = (EditText) findViewById(R.id.fullname);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputBusinessName = (EditText) findViewById(R.id.businessname);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        loginErrorMsg = (TextView) findViewById(R.id.register_error);



		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				Log.d("Button", "Login");
				JSONObject json = userFunction.loginUser(email, password);

				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						loginErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS);
						if(Integer.parseInt(res) == 1){
							// user successfully logged in
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_USERNAME), json_user.getString(KEY_FULLNAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_BUSINESSNAME), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));

							// Launch Dashboard Screen
							Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);

							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);

							// Close Login Screen
							finish();
						}else{
							// Error in login
							loginErrorMsg.setText("Incorrect email/password");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // start Facebook Login
                Session.openActiveSession(LoginActivity.this, true, new Session.StatusCallback() {

                    // callback when session changes state
                    @Override
                    public void call(Session session, SessionState state, Exception exception) {
                        if (session.isOpened()) {

                            // make request to the /me API
                            Request.newMeRequest(session, new Request.GraphUserCallback() {

                                // callback after Graph API response with user
                                // object
                                @Override
                                public void onCompleted(GraphUser user, Response response) {
                                    if (user != null) {
                                        Intent intent = new Intent();
                                        intent.putExtra("isFacebook", true);
                                        intent.setClass(LoginActivity.this, DashboardActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).executeAsync();
                        }
                    }
                });
            }
        });

	}
}

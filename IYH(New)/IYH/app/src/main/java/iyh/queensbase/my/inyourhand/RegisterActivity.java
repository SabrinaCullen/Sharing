package iyh.queensbase.my.inyourhand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import org.json.JSONException;
import org.json.JSONObject;

import library.DatabaseHandler;
import library.UserFunctions;

public class RegisterActivity extends Activity {
	Button btnRegister;
    EditText inputUsername;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
    EditText inputBusinessName;
	TextView registerErrorMsg;
	
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
        setContentView(R.layout.activity_sign_up);



        Button btn_clear=(Button) findViewById(R.id.b_clearAll);
        btn_clear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText username=(EditText) findViewById(R.id.username);
                EditText fullname=(EditText) findViewById(R.id.fullname);
                EditText password=(EditText) findViewById(R.id.password);
                EditText email=(EditText) findViewById(R.id.email);
                EditText businessname=(EditText) findViewById(R.id.businessname);

                username.setText("");
                fullname.setText("");
                password.setText("");
                email.setText("");
                businessname.setText("");
            }
        });

		// Importing all assets like buttons, text fields
        inputUsername = (EditText) findViewById(R.id.username);
		inputFullName = (EditText) findViewById(R.id.fullname);
        inputPassword = (EditText) findViewById(R.id.password);
		inputEmail = (EditText) findViewById(R.id.email);
        inputBusinessName = (EditText) findViewById(R.id.businessname);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
                String username = inputUsername.getText().toString();
				String fullname = inputFullName.getText().toString();
                String password = inputPassword.getText().toString();
				String email = inputEmail.getText().toString();
                String businessname = inputBusinessName.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(username, fullname, email, password, businessname);

				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						registerErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS);
                        String err = json.getString(KEY_ERROR);
						if(Integer.parseInt(res) == 1){
							// user successfully registred
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_USERNAME), json_user.getString(KEY_FULLNAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_BUSINESSNAME), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));
							// Launch Dashboard Screen
//							Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
							// Close all views before launching Dashboard
//							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//							startActivity(dashboard);
							// Close Registration Screen
							finish();
                        }
                        else if(Integer.parseInt(err) == 1){
                            registerErrorMsg.setText("One or more fields are empty");
                        }
                        else if(Integer.parseInt(err) == 2){
                            registerErrorMsg.setText("Username field is empty");
                        }
                        else if(Integer.parseInt(err) == 3){
                            registerErrorMsg.setText("Full name field is empty");
                        }
                        else if(Integer.parseInt(err) == 4){
                            registerErrorMsg.setText("Password field are empty");
                        }
                        else if(Integer.parseInt(err) == 5){
                            registerErrorMsg.setText("Email field is empty");
                        }
                        else if(Integer.parseInt(err) == 6){
                            registerErrorMsg.setText("Business Name field is empty");
                        }
                        else if(Integer.parseInt(err) == 7){
                            registerErrorMsg.setText("Not An Valid Email");

						}else if(Integer.parseInt(err) == 8){
							registerErrorMsg.setText("Minimum password length is 6");

                        }else if(Integer.parseInt(err) == 9){
                            registerErrorMsg.setText("Email already existed");
                    }
                   }

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

	}
}

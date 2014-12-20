package library;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFunctions {

    private JSONParser jsonParser;

    private static String loginURL = "http://192.168.0.106/android_login_api/";
    private static String registerURL = "http://192.168.0.106/android_login_api/";

    private static String login_tag = "login";
    private static String register_tag = "register";

    // constructor
    public UserFunctions()
    {
    }

    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password)
    {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        return JSONParser.getJSONFromUrl(loginURL, params);
    }

    /**
     * function make Login Request
     * @param username
     * @param fullname
     * @param email
     * @param password
     * @param businessname
     * */
    public JSONObject registerUser(String username, String fullname, String email, String password, String businessname)
    {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("fullname", fullname));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("businessname", businessname));

        // getting JSON Object
        return JSONParser.getJSONFromUrl(registerURL, params);
    }

    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();

        return count > 0;
    }

    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context)
    {
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();

        return true;
    }

}

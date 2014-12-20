package library;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.List;

public class JSONParser
{
    private static final String TAG = "JSONParser";

    public static JSONObject getJSONFromUrl(String url, List<NameValuePair> params)
    {
        // Making HTTP request
        try
        {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();

            if (httpEntity == null)
            {
                Log.d(TAG, "HTTP Entity is null!");
                return null;
            }

            String content = EntityUtils.toString(httpEntity);

            Log.d(TAG, content);

            return new JSONObject(content);
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error on getJSONFromUrl", e);

            return null;
        }
    }
}

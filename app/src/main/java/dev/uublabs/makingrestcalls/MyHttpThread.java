package dev.uublabs.makingrestcalls;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Admin on 11/16/2017.
 */

public class MyHttpThread extends Thread
{
    String myURL;
    private HttpURLConnection urlConnection;
    public static final String TAG = "MyHttpThread";

    public MyHttpThread(String myURL)
    {
        this.myURL = myURL;
    }

    @Override
    public void run()
    {
        super.run();

        try
        {
            URL url = new URL(myURL);
            //open connection to the url
            urlConnection = (HttpURLConnection) url.openConnection();
            //read the response
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            //print the response
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext())
            {
                Log.d(TAG, "run: " + scanner.nextLine());
            }
            //close the connection
            urlConnection.disconnect();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

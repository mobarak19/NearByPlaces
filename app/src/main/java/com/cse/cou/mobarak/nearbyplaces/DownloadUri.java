package com.cse.cou.mobarak.nearbyplaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mobarak on 8/20/2018.
 */

public class DownloadUri{
    public String readUrl(String myUrl) throws IOException {
        String data="";
        InputStream inputStream=null;
        HttpURLConnection urlConnection=null;

        try {
            URL url=new URL(myUrl);
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream=urlConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb=new StringBuffer();
            String line="";
            while ((line=bufferedReader.readLine())!=null){
                sb.append(line);
            }
            data=sb.toString();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;

    }

}

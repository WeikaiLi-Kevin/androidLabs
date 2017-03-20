package com.weikaili.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.in;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar pb1;
    String min = "", max = "", curTem = "";
    Bitmap pic;
    TextView tv1, tv2, tv3;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        tv1 = (TextView) findViewById(R.id.temp);
        tv2 = (TextView) findViewById(R.id.min);
        tv3 = (TextView) findViewById(R.id.max);
        img = (ImageView) findViewById(R.id.picWeather);

        pb1 = (ProgressBar)findViewById(R.id.progressBar);
        pb1.setVisibility(View.VISIBLE);
        ForecastQuery thread = new ForecastQuery();
        thread.execute();
    }
    private class ForecastQuery extends AsyncTask<String, Integer, String>
    {

        protected String doInBackground(String ...args)
        {
           byte buffer[] = new byte[512];
            String in = "";
            String name = "";
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream inStream = conn.getInputStream();
              //  Log.i("url input stream:" , "" + inStream);
               // inStream.read(buffer);
                //in = new String(buffer);
                //Log.i("in",in);
              //  conn.setReadTimeout(10000 /* milliseconds */);
              //  conn.setConnectTimeout(15000 /* milliseconds */);
               // conn.setRequestMethod("GET");
               // conn.setDoInput(true);
                // Starts the query
               // conn.connect();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser parser = factory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(inStream, null);
                //parser.nextTag();
                Log.i("XML parsing:" , "" + parser.getEventType());
                Log.i("parser get name", parser.getName()==null? "null":parser.getName() );
                while(parser.getEventType() != XmlPullParser.END_DOCUMENT)
                {
                    Log.i("parser get name",parser.getName()==null? "null":parser.getName());
                    if(parser.getEventType() == XmlPullParser.START_TAG) {
                        if (parser.getName().equals("temperature") ){
                            min = parser.getAttributeValue(null, "min");
                            Log.i("min:", min);
                            publishProgress(25);
                            max = parser.getAttributeValue(null, "max");
                            Log.i("max:", max);
                            publishProgress(50);
                            curTem = parser.getAttributeValue(null, "value");
                            Log.i("value:", curTem);
                            publishProgress(75);
                        }
                        else if (parser.getName().equals( "weather")) {
                            String name1 = parser.getAttributeValue(null, "icon");
                            Log.i("name:", name1);




                            try {
                                URL weather = new URL("http://openweathermap.org/img/w/"+name1+".png");

                                HttpURLConnection connection = null;
                                try {
                                    connection = (HttpURLConnection) weather.openConnection();
                                    connection.connect();
                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == 200) {


                                        if(fileExistance(name1 + ".png")==true){
                                            FileInputStream fis = null;
                                            try {    fis = openFileInput(name1 + ".png");   }
                                            catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            pic = BitmapFactory.decodeStream(fis);

                                        }
                                        else {
                                            Bitmap image  = BitmapFactory.decodeStream(connection.getInputStream());
                                            FileOutputStream outputStream = openFileOutput( name1 + ".png", Context.MODE_PRIVATE);
                                            image.compress(Bitmap.CompressFormat.PNG, 10, outputStream);
                                            outputStream.flush();
                                            outputStream.close();
                                            pic = image;
                                        }

                                    }
                                } catch (Exception e) {
                                } finally {
                                    if (connection != null) {
                                        connection.disconnect();
                                    }
                                }
                            } catch (MalformedURLException e) {

                            }



                        }
                    }
                    parser.next();
                    }
            } catch (Exception ex) {
                Log.d("XML:", ex.getMessage());
            }
            return in;
        }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        public void onProgressUpdate(Integer ...value)
        {

            pb1.setVisibility(View.VISIBLE);
            pb1.setProgress(value[0]);



        }

        public void onPostExecute(String work)
        {
            tv1.setText("Current temperature: "+curTem);
            tv2.setText("Minimum: "+min);
            tv3.setText("Maximum: "+max);
            img.setImageBitmap(pic);
            pb1.setVisibility(View.INVISIBLE);
        }

    }
}

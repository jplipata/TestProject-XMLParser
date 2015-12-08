package com.lipata.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {

    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;
    TextView textView; 

    // Dummy data
    StringReader stream = new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<product>\n" +
            "\t<asset_type>audio</asset_type>\n" +
            "\t<artist>Adele</artist>\n" +
            "\t<title>Greatest Hits</title>\n" +
            "\t<product_number>G010007656789Z</product_number>\n" +
            "\t<track>\n" +
            "\t\t<track_number>01</track_number>\n" +
            "\t\t<track_title>Hello</track_title>\n" +
            "\t\t<track_duration>3:51</track_duration>\n" +
            "\t\t<track_isrc>USSM0111111</track_isrc>\n" +
            "\t</track>\n" +
            "\t\n" +
            "</product>");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_view);

        // Parse XML in `stream`
        // In real life I probably wouldn't do this on the main thread
        try{
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();
            myparser.setInput(stream);

            int event = myparser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT)
            {
                String name=myparser.getName();
                if(event == XmlPullParser.START_DOCUMENT) {
                    textView.append("\nStart document");
                } else if(event == XmlPullParser.START_TAG) {
                    textView.append("\nStart tag " + myparser.getName());
                } else if(event == XmlPullParser.END_TAG) {
                    textView.append("\nEnd tag " + myparser.getName());
                } else if(event == XmlPullParser.TEXT) {
                    textView.append("\nText " + myparser.getText());
                }
                event = myparser.next();
            }

        } catch (XmlPullParserException e){
            Log.e("XML error", e.toString());
            Toast.makeText(this, "XmlPullParserException error", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Log.e("IOException", e.toString());
            Toast.makeText(this, "IO error", Toast.LENGTH_SHORT).show();
        }

    }
}

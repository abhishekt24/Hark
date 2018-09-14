package com.techiespace.projects.hark;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParseXML {
    private String str;
    private Date startTime;
    private Date stopTime;
    private String finalTranscript = "";
    private static final String ns = null;

    ParseXML(String str, Date startTime, Date stopTime) {
        this.str = str;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    String parseXML() throws IOException, XmlPullParserException {
        parse(str);
        finalTranscript = finalTranscript.replaceAll("\\(.*\\)", "");
        finalTranscript = finalTranscript.replaceAll("&#39;", "'");
        finalTranscript = finalTranscript.replaceAll("&quot;", "\"");

        removeBrackets();
        handleQuotes();
        return finalTranscript;
    }

    private void handleQuotes() {
    }

    private void removeBrackets() {
    }

    public List parse(String in) throws XmlPullParserException, IOException {
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(in));
            parser.nextTag();
            return readFeed(parser);
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "transcript");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("text")) {
                entries.add(readText(parser));
            }
        }
        return entries;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "text");
        String startTimeStr = parser.getAttributeValue(null, "start");
        String durStr = parser.getAttributeValue(null, "dur");
        int startTimeXML = (int) (float) (Float.valueOf(startTimeStr));
        int stopTimeXML = (int) (Float.valueOf(startTimeStr) + Float.valueOf(durStr));

        String text = "";
        if (parser.next() == XmlPullParser.TEXT) {
            text = parser.getText();
            parser.nextTag();
        }

        if (startTimeXML >= (startTime.getTime() / 1000) && stopTimeXML <= (stopTime.getTime() / 1000)) {
            finalTranscript += text + " ";
        }
        parser.require(XmlPullParser.END_TAG, ns, "text");
        return text;
    }


}

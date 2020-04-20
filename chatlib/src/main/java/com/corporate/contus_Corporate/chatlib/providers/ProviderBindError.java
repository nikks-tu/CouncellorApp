package com.corporate.contus_Corporate.chatlib.providers;

import com.corporate.contus_Corporate.chatlib.iqresponse.ResponseBindErrorIQ;
import com.corporate.contus_Corporate.chatlib.utils.LogMessage;
import com.corporate.contus_Corporate.chatlib.utils.Utils;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.provider.IQProvider;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by user on 29/9/16.
 */
public class ProviderBindError extends IQProvider<ResponseBindErrorIQ> {
    @Override
    public ResponseBindErrorIQ parse(XmlPullParser parser, int initialDepth) throws XmlPullParserException, IOException, SmackException {

        JSONObject jsonObject = new JSONObject();
        String response = null;
        String type = null;
        try {
            type = Utils.getType(parser);
            aa:
            while (true) {
                int eventType = parser.next();
                switch (eventType) {
                    case XmlPullParser.END_TAG:
                        if (parser.getDepth() == initialDepth)
                            break aa;
                    case XmlPullParser.TEXT:
                        response = parser.getText();
                        break;
                    default:
                        break;
                }
            }
            jsonObject.put("type", type);
            jsonObject.put("response", response);
        } catch (Exception e) {
            LogMessage.e(e);
        }
        return new ResponseBindErrorIQ(jsonObject);
    }
}

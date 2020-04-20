package com.corporate.contus_Corporate.chatlib.iqfilter;

import com.corporate.contus_Corporate.chatlib.iqresponse.ResponseBindErrorIQ;
import com.corporate.contus_Corporate.chatlib.utils.LogMessage;

import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.packet.Stanza;

/**
 * Created by user on 29/9/16.
 */
public class BindErrorIQFilter implements StanzaFilter {
    @Override
    public boolean accept(Stanza stanza) {
        boolean CustomIQReceived = false;
        if (stanza instanceof ResponseBindErrorIQ) {
            LogMessage.v("BindErrorIQFilter", "called::Bind error::" + stanza.getFrom());
            CustomIQReceived = true;
        }
        return CustomIQReceived;
    }
}

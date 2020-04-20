package com.corporate.contus_Corporate.chatlib.iqs;

import com.corporate.contus_Corporate.chatlib.utils.LibConstants;
import com.corporate.contus_Corporate.chatlib.utils.LogMessage;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.XmlStringBuilder;

/**
 * Created by user on 28/9/16.
 */
public class IQSetProfile extends IQ {

    private String imageUrl;
    private String status;
    private String userName;

    public IQSetProfile(String userName, String imageUrl, String status) {
        super(LibConstants.PROFILE, LibConstants.MODULE_PROFILE);
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.status = status;
        this.setType(Type.set);
    }


    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
        xml.rightAngleBracket();
        XmlStringBuilder contact = xml.halfOpenElement(LibConstants.PROFILE);
        contact.attribute(LibConstants.NAME, userName);
        contact.attribute(LibConstants.IMAGE, imageUrl);
        contact.attribute(LibConstants.STATUS_MSG, status);
        contact.attribute(LibConstants.STATUS, LibConstants.STATUS_SET_PROFILE);
        contact.closeEmptyElement();
        LogMessage.e("IQSetProfile", "IQSetProfile::" + xml.toString());
        return xml;
    }
}

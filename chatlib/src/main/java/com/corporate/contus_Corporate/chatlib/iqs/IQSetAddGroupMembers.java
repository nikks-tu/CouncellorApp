package com.corporate.contus_Corporate.chatlib.iqs;

import com.corporate.contus_Corporate.chatlib.utils.LibConstants;
import com.corporate.contus_Corporate.chatlib.utils.LogMessage;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.util.XmlStringBuilder;

/**
 * Created by user on 28/9/16.
 */
public class IQSetAddGroupMembers extends IQ {

    private String groupid;
    private String members;

    public IQSetAddGroupMembers(String groupid, String members) {
        super(IQ.QUERY_ELEMENT, LibConstants.MODULE_CONTACT);
        this.groupid = groupid;
        this.members = members;
        this.setType(Type.set);
    }


    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {
        xml.rightAngleBracket();
        XmlStringBuilder contact = xml.halfOpenElement(LibConstants.CONTACT);
        contact.attribute(LibConstants.GROUP, groupid);
        contact.attribute(LibConstants.MEMBERS, members);
        contact.attribute(LibConstants.STATUS, LibConstants.STATUS_SET_ADD_GROUP_MEMBERS);
        contact.closeEmptyElement();
        LogMessage.e("IQSetRemoveGroupMembers", "IQSetRemoveGroupMembers::" + xml.toString());
        return xml;
    }
}

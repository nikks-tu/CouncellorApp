package com.corporate.contus_Corporate.chatlib.parcels;

import com.corporate.contus_Corporate.chatlib.enums.RosterType;


/**
 * Created by user on 29/9/16.
 */
public interface IRoster {
    RosterType getType();

    void setType(RosterType var1);

    Jid getJid();

    void setJid(Jid var1);
}

package com.corporate.contus_Corporate.chatlib.listeners;

import com.corporate.contus_Corporate.chatlib.models.ChatMessage;
import com.corporate.contus_Corporate.chatlib.models.Contact;

import java.util.List;

/**
 * Created by user on 29/9/16.
 */
public interface RosterListener {
    void profileUpdated(Boolean isUpDated);

    void rosterCallback(List<Contact> contacts);

    void userProfileCallback(Contact contact);

    void userProfileUpdateCallback(ChatMessage chatMessage);

    void presenceCallback(String userName, String status);

    void deleteAccount(String result, String resultType);

    void getCurrentAvailability(String userName, String availability, String lastSeen);

    void onSessionExpire(String message);

    void onSessionCleared();
}

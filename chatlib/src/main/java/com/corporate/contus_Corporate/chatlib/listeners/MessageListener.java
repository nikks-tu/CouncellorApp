package com.corporate.contus_Corporate.chatlib.listeners;

import com.corporate.contus_Corporate.chatlib.models.ChatMessage;

/**
 * Created by user on 3/10/16.
 */
public interface MessageListener {

    void messageCallback(ChatMessage chatMessage);

    void messageAcknowledgementCallback(ChatMessage chatMessage);

    void messageReceiptCallback(ChatMessage chatMessage);

    void messageSeenCallback(ChatMessage chatMessage);

    void getTypingStatus(String userGrpId, String status, String userId);

    void deleteMessage(boolean result);

    void clearChat(boolean result);
}

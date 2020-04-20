package com.techuva.new_changes_corporate.models;


import com.techuva.councellorapp.contusfly_corporate.model.AnnouncementReadObject;

import java.util.ArrayList;

public class AnnouncementModel {
    String adminCount;
    String announcement;
    String announcementCommentsCounts;
    String announcementLikesCounts;
    String announcementParticipateCount;
    String created_at;
    String id;
    String image;
    String is_delete;
    String status ;
    String title;
    String updated_at;
    String userAnnouncementLikes;
    String userCount;
    String category_name;
    String adminProfilePic;
    public ArrayList<AnnouncementReadObject> user_participate_announcements;


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }



    public String getAdminCount() {
        return adminCount;
    }

    public void setAdminCount(String adminCount) {
        this.adminCount = adminCount;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getAnnouncementCommentsCounts() {
        return announcementCommentsCounts;
    }

    public void setAnnouncementCommentsCounts(String announcementCommentsCounts) {
        this.announcementCommentsCounts = announcementCommentsCounts;
    }

    public String getAnnouncementLikesCounts() {
        return announcementLikesCounts;
    }

    public void setAnnouncementLikesCounts(String announcementLikesCounts) {
        this.announcementLikesCounts = announcementLikesCounts;
    }

    public String getAnnouncementParticipateCount() {
        return announcementParticipateCount;
    }

    public void setAnnouncementParticipateCount(String announcementParticipateCount) {
        this.announcementParticipateCount = announcementParticipateCount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUserAnnouncementLikes() {
        return userAnnouncementLikes;
    }

    public void setUserAnnouncementLikes(String userAnnouncementLikes) {
        this.userAnnouncementLikes = userAnnouncementLikes;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }


    public ArrayList<AnnouncementReadObject> getUser_participate_announcements() {
        return user_participate_announcements;
    }

    public void setUser_participate_announcements(ArrayList<AnnouncementReadObject> user_participate_announcements) {
        this.user_participate_announcements = user_participate_announcements;
    }


    public String getAdminProfilePic() {
        return adminProfilePic;
    }

    public void setAdminProfilePic(String adminProfilePic) {
        this.adminProfilePic = adminProfilePic;
    }
}

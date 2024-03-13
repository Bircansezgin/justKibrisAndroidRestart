package com.softrestart.justkibrisrestart.Class

import java.io.Serializable
import java.util.Date

class adaPostClass(
        val activityCategory: String,
        val activityID: String,
        val activityName: String,
        val activityPlace: String,
        val activityPosterImageURL: String,
        val isActivePost: Int,
        val likeCount: Int,
        val postDocumentID: String,
        val postID: String,
        val postPhotoURL: String,
        val postSendingDate: Date? = null,
        val postingUserID: String,
        val postingUserImageURL: String,
        val userName: String

): Serializable {
        // Boş yapıcı metod
        constructor() : this(
                "", "", "", "", "",
                0, 0, "", "",
                "", null, "", "",
                ""
        )
}
//kk

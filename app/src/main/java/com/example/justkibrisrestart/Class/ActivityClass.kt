package com.softrestart.justkibrisrestart.Class

import com.google.firebase.Timestamp
import java.io.Serializable
import java.util.Date

data class ActivityClass(
    val activityBarCodeNo: String?,
    val activityCategory: String?,
    val activityDate: String?,
    val activityDescription: String?,
    val activityDocumentID: String?,
    val activityID: String?,
    val activityName: String?,
    val activityPhoneNumber: String?,
    val activityPrice: String?,
    val etkinlikEklenisTarihi: Date? = null,
    val isActive: String?,
    val mekanName: String?,
    val photoURL: String?,
    val reservationOn: String?,
    val xCoordinate: Double?,
    val yCoordinate: Double?
) : Serializable {
    // Firebase Firestore için gereken boş yapıcı metot
    constructor() : this("", "", "", "", "", "", "", "", "", null, null, "", "", "", null, null)
}

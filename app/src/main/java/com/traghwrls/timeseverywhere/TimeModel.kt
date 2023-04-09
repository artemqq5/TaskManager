package com.traghwrls.timeseverywhere


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class TimeModel(
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("gmt_offset")
    val gmtOffset: Int?,
    @SerializedName("is_dst")
    val isDst: Boolean?,
    @SerializedName("latitude")
    val latitude: Double?,
    @SerializedName("longitude")
    val longitude: Double?,
    @SerializedName("requested_location")
    val requestedLocation: String?,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String?,
    @SerializedName("timezone_location")
    val timezoneLocation: String?,
    @SerializedName("timezone_name")
    val timezoneName: String?
)
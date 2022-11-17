package com.app.flobiz_assignment.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuestionResponse {


    data class QuesResponse(
        @SerializedName("items")
        @Expose
        val items : List<Item>,
        @SerializedName("has_more")
        val hasMore: Boolean,
        @SerializedName("quota_max")
        val quotaMax: Int,
        @SerializedName("quota_remaining")
        val quotaRem: Int
    )

    data class roomData(
        @SerializedName("view_count")
        @Expose
        val viewCount: Long,

        @SerializedName("answer_count")
        @Expose
        val answerCount: Long,

        @SerializedName("score")
        @Expose
        val score: Long,

        @SerializedName("last_activity_date")
        @Expose
        val lastActivityDate: Long,

        @SerializedName("creation_date")
        @Expose
        val creationDate: Long,

        @SerializedName("question_id")
        @Expose
        val questionID: Long,

        @SerializedName("link")
        @Expose
        val link: String?,

        @SerializedName("title")
        @Expose
        val title: String,

        @SerializedName("last_edit_date")
        @Expose
        val lastEditDate: Long? = null,

        @SerializedName("reputation")
        @Expose
        val reputation: Long,
        @SerializedName("user_id")
        @Expose
        val userID: Long,
        @SerializedName("user_type")
        @Expose
        val userType: String,
        @SerializedName("profile_image")
        @Expose
        val profileImage: String,

        @SerializedName("display_name")
        @Expose
        val displayName: String,
        @SerializedName("dplink")
        @Expose
        val dplink: String,
    )

    data class Item (

        @SerializedName("tags")
        @Expose
        val tags: List<String>,
        @SerializedName("owner")
        @Expose
        val owner: Owner,

        @SerializedName("is_answered")
        @Expose
        val isAnswered: Boolean,

        @SerializedName("view_count")
        @Expose
        val viewCount: Long,

        @SerializedName("answer_count")
        @Expose
        val answerCount: Long,

        @SerializedName("score")
        @Expose
        val score: Long,

        @SerializedName("last_activity_date")
        @Expose
        val lastActivityDate: Long,

        @SerializedName("creation_date")
        @Expose
        val creationDate: Long,

        @SerializedName("question_id")
        @Expose
        val questionID: Long,

        @SerializedName("content_license")
        @Expose
        val contentLicense: String,

        @SerializedName("link")
        @Expose
        val link: String,

        @SerializedName("title")
        @Expose
        val title: String,

        @SerializedName("last_edit_date")
        @Expose
        val lastEditDate: Long? = null,

        @SerializedName("accepted_answer_id")
        @Expose
        val acceptedAnswerID: Long? = null
    )

    data class Owner (
        @SerializedName("reputation")
        @Expose
        val reputation: Long,
        @SerializedName("user_id")
        @Expose
        val userID: Long,
        @SerializedName("user_type")
        @Expose
        val userType: String,
        @SerializedName("profile_image")
        @Expose
        val profileImage: String,

        @SerializedName("display_name")
        @Expose
        val displayName: String,
        @SerializedName("link")
        @Expose
        val link: String,
    )

}
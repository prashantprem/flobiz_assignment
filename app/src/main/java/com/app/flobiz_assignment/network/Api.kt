package com.app.flobiz_assignment.network

import com.app.flobiz_assignment.models.QuestionResponse.QuesResponse
import com.app.flobiz_assignment.network.Endpoints.GET_QUESTIONS
import com.app.flobiz_assignment.network.Endpoints.KEY
import com.app.flobiz_assignment.network.Endpoints.ORDER
import com.app.flobiz_assignment.network.Endpoints.SITE
import com.app.flobiz_assignment.network.Endpoints.SORT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(GET_QUESTIONS)
    suspend fun getQuestionList(
        @Query(KEY) key: String,
        @Query(ORDER) order: String,
        @Query(SORT) sort: String,
        @Query(SITE) site: String,
        ) : Response<QuesResponse>
}
package com.app.flobiz_assignment.main

import com.app.Constants
import com.app.flobiz_assignment.models.QuestionResponse.Item
import com.app.flobiz_assignment.network.Api
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getQuestionList() : Response<Item> {
        return api.getQuestionList(Constants.KEY,Constants.ORDER,Constants.SORT,Constants.SITE)
    }
}
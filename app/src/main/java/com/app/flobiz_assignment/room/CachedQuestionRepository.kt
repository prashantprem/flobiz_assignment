package com.app.flobiz_assignment.room

import androidx.lifecycle.LiveData
import com.app.flobiz_assignment.models.QuestionResponse

class CachedQuestionRepository(private val questionDao: QuestionDao) {

    val allQuestions: LiveData<List<QuestionEntity>> = questionDao.getQuestions()


    suspend fun insert(question: QuestionEntity) {
        questionDao.insertQuestions(question)
    }

    suspend fun delete(){
        questionDao.deleteAllQuestions()
    }
}
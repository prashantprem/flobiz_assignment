package com.app.flobiz_assignment.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuestionDao {

    @Query("Select * from cached_questions")
    fun getQuestions() : LiveData<List<QuestionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestions(questions : QuestionEntity)

    @Query("delete from cached_questions")
    fun deleteAllQuestions()
}
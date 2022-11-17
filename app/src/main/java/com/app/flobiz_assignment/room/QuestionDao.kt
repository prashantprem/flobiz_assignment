//package com.app.flobiz_assignment.room
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.app.flobiz_assignment.models.QuestionResponse
//
//@Dao
//interface QuestionDao {
//
//    @Query("Select * from cached_questions")
//    fun getQuestions() : LiveData<List<QuestionResponse.Item>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertQuestions(questions : QuestionResponse.Item)
//
//    @Query("delete from cached_questions")
//    fun deleteAllQuestions()
//}
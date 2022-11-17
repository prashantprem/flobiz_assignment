package com.app.flobiz_assignment.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.flobiz_assignment.models.QuestionResponse
import org.jetbrains.annotations.NotNull

@Entity(tableName = "cached_questions")
data class QuestionEntity(
    @ColumnInfo(name = "questions")
    @NotNull
    var questions : QuestionResponse.Item
){
    @PrimaryKey(autoGenerate = true)
    var id: Int =0
}

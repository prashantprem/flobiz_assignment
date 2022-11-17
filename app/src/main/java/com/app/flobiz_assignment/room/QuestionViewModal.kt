//package com.app.flobiz_assignment.room
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.viewModelScope
//import com.app.flobiz_assignment.models.QuestionResponse
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class QuestionViewModal(application: Application) :AndroidViewModel(application) {
//    var allQuestions : LiveData<List<QuestionResponse.Item>> ?= null
//    var repository : CachedQuestionRepository ?= null
//
//
//    init {
//        val dao = QuestionDatabase.getInstance(application)?.questionDao()
//        repository = dao?.let { CachedQuestionRepository(it) }
//        allQuestions = repository?.allQuestions
//    }
//
//    fun addQuestion(item: QuestionResponse.Item) = viewModelScope.launch(Dispatchers.IO) {
//        repository?.insert(item)
//    }
//
//    fun deleteQuestions() = viewModelScope.launch(Dispatchers.IO){
//        repository?.delete()
//    }
//}
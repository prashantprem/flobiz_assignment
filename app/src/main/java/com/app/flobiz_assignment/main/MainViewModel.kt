package com.app.flobiz_assignment.main

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.flobiz_assignment.models.QuestionResponse.Item
import com.app.flobiz_assignment.models.QuestionResponse.QuesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    var allQuestionList: MutableLiveData<List<Item>> = MediatorLiveData()



    fun fetchQuestions() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getQuestionList()
            if(response.isSuccessful && response.body() != null){
                allQuestionList.postValue(response.body()!!.items)
            }
        }
    }


}
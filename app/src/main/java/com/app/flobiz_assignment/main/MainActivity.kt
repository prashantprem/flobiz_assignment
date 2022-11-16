package com.app.flobiz_assignment.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.fetchQuestions()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.allQuestionList.observe(this){
            Timber.tag("Test").d(it.toString())
        }

    }
}
package com.app.flobiz_assignment.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.Utils
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.adapter.QuestionListAdapter
import com.app.flobiz_assignment.databinding.ActivityMainBinding
import com.app.flobiz_assignment.models.QuestionResponse.Item
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private var question : List<Item> ? = null
    private var avgView = 0
    private var avgAns = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.progressBar.visibility = View.VISIBLE
        binding.tvAvgAnswerCount.visibility = View.GONE
        binding.tvAvgViewCount.visibility = View.GONE
        setContentView(binding.root)
        if(Utils.isNetworkAvailable(this)){
            viewModel.fetchQuestions()
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.allQuestionList.observe(this){
            Timber.tag("Test").d(it.toString())
            if(it != null){
                question = it
                initRcv(it)
            }
        }

    }

    private fun initRcv( mList: List<Item>){
        calculateAverage()
        binding.progressBar.visibility = View.GONE
        binding.tvAvgAnswerCount.visibility = View.VISIBLE
        binding.tvAvgViewCount.visibility = View.VISIBLE
        val adapter = QuestionListAdapter(mList)
        binding.rcvQuestions.layoutManager = LinearLayoutManager(this)
        binding.rcvQuestions.adapter = adapter
    }

    private fun calculateAverage(){
        if(question != null){
            var sumView = 0
            var sumAns = 0
            for(q in question!!){
                sumView += q.viewCount.toInt()
                sumAns += q.answerCount.toInt()
            }
            val view = sumView/ question!!.size
            val ans = sumAns/question!!.size
            binding.tvAvgViewCount.text = resources.getString(R.string.avg_view_count,view.toString())
            binding.tvAvgAnswerCount.text = resources.getString(R.string.avg_ans_count,ans.toString())
        }
    }

}
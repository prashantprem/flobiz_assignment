package com.app.flobiz_assignment.main

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.Utils
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.adapter.QuestionListAdapter
import com.app.flobiz_assignment.bottomsheet.FilterBottomSheet
import com.app.flobiz_assignment.databinding.ActivityMainBinding
import com.app.flobiz_assignment.models.QuestionResponse
import com.app.flobiz_assignment.models.QuestionResponse.Item
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
//    private val questionViewModel by viewModels<QuestionViewModal>()
    private lateinit var binding: ActivityMainBinding
    private var question : List<Item> ? = null
    private var cachedQuestion : List<QuestionResponse.roomData> ? = null
    private var avgView = 0
    private var avgAns = 0
    private var filters : HashSet<String> ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainContainerLayout.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        setContentView(binding.root)
        if(Utils.isNetworkAvailable(this)){
            viewModel.fetchQuestions()
        }
        observeViewModel()
        setUpListeners()
    }

    private fun setUpListeners() {

        binding.filter.setOnClickListener {
            openFilterBottomSheet();
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()){
                    filter(p0)
                }else{
                    initRcv(question!!)
                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()){
                    filter(p0)
                }else{
                    initRcv(question!!)
                }

                return false
            }

        })
    }

    private fun openFilterBottomSheet() {
        filters?.let { ArrayList(it) }
            ?.let { FilterBottomSheet.newInstance(it).show(supportFragmentManager,"BsFilter") }
    }

    private fun filter(text: String){
        val adapter = question?.let { QuestionListAdapter(it) }
        binding.rcvSearch.adapter = adapter
        val filteredList = mutableListOf<Item>()
        for(q in question!!){
            if (q.title.lowercase().contains(text.lowercase())
                || q.owner.displayName.lowercase().contains(text.lowercase())) {
                filteredList.add(q)
            }
        }

        if(filteredList.isEmpty()){
            binding.searchLayout.visibility = View.GONE
            binding.mainContainerLayout.visibility = View.VISIBLE
            initRcv(question!!)
        } else {
            binding.mainContainerLayout.visibility = View.GONE
            binding.searchLayout.visibility = View.VISIBLE
            adapter?.filterList(filteredList)
        }



    }

    private fun observeViewModel(){
        viewModel.allQuestionList.observe(this){
            Timber.tag("Test").d(it.toString())
            if(it != null){
                question = it
                initRcv(it)
//                saveToDb(question!!)
            }
        }


//        questionViewModel.allQuestions?.observe(this){
//            if(!Utils.isNetworkAvailable(this) && it != null){
//                updateRcv(it)
//            }
//        }

    }

    private fun updateRcv(mList: List<Item>){
        calculateAverage(mList)
        binding.progressBar.visibility = View.GONE
        binding.mainContainerLayout.visibility = View.VISIBLE
        binding.searchLayout.visibility = View.GONE
        val adapter = QuestionListAdapter(mList)
        binding.rcvQuestions.layoutManager = LinearLayoutManager(this)
        binding.rcvQuestions.adapter = adapter

    }

//    private fun saveToDb(question: List<Item>) {
//        for(q in question){
//            questionViewModel.addQuestion(q)
//        }
//    }

    private fun initRcv( mList: List<Item>){
        calculateAverage(mList)
        binding.progressBar.visibility = View.GONE
        binding.mainContainerLayout.visibility = View.VISIBLE
        binding.searchLayout.visibility = View.GONE
        val adapter = QuestionListAdapter(mList)
        binding.rcvQuestions.layoutManager = LinearLayoutManager(this)
        binding.rcvQuestions.adapter = adapter
    }

    private fun calculateAverage(mList: List<Item>){
        var sumView = 0
        var sumAns = 0
        filters = HashSet()
        for(q in mList){
            sumView += q.viewCount.toInt()
            sumAns += q.answerCount.toInt()
            for(tag in q.tags){
                filters?.add(tag)
            }
        }
        val view = sumView/ mList!!.size
        val ans = sumAns/mList!!.size
        binding.tvAvgViewCount.text = resources.getString(R.string.avg_view_count,view.toString())
        binding.tvAvgAnswerCount.text = resources.getString(R.string.avg_ans_count,ans.toString())
    }

}
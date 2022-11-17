package com.app.flobiz_assignment.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.Utils
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.adapter.FilterAdapter
import com.app.flobiz_assignment.adapter.QuestionListAdapter
import com.app.flobiz_assignment.bottomsheet.FilterBottomSheet
import com.app.flobiz_assignment.databinding.ActivityMainBinding
import com.app.flobiz_assignment.models.QuestionResponse.Item
import com.app.flobiz_assignment.room.QuestionEntity
import com.app.flobiz_assignment.room.QuestionViewModal
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FilterAdapter.OnTagClickInterface {

    private val viewModel by viewModels<MainViewModel>()
    private val questionViewModel by viewModels<QuestionViewModal>()
    private lateinit var binding: ActivityMainBinding
    private var question : List<Item>  = ArrayList()
    private var filters : HashSet<String> ? = null
    private var networkCallback: NetworkCallback? = null
    private var connectivityManager: ConnectivityManager? = null
    private var isNetworkAvailable = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainContainerLayout.visibility = View.GONE
        binding.tvAvgViewCount.visibility = View.GONE
        binding.tvAvgAnswerCount.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        setContentView(binding.root)
        if(Utils.isNetworkAvailable(this)){
            viewModel.fetchQuestions()
            isNetworkAvailable = true
        }
        observeViewModel()
        setUpListeners()
        observeNetworkChange()
    }

    private fun setUpListeners() {

        binding.clearFilter.setOnClickListener {
            updateRcv()
        }

        binding.filter.setOnClickListener {
            openFilterBottomSheet()
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()){
                    filter(p0)
                }else{
                    initRcv(question)
                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()){
                    filter(p0)
                }else{
                    initRcv(question)
                }

                return false
            }

        })
    }

    private fun openFilterBottomSheet() {
        filters?.let { ArrayList(it) }
            ?.let { FilterBottomSheet.newInstance(it,this).show(supportFragmentManager,"BsFilter") }
    }



    private fun observeViewModel(){
        viewModel.allQuestionList.observe(this){
            Timber.tag("Test").d(it.toString())
            if(it != null){
                question = it
                initRcv(it)
                saveToDb(question)
            }
        }


        questionViewModel.allQuestions?.observe(this){
            Timber.tag("room").d(it.toString())
            if(question.isEmpty() && !Utils.isNetworkAvailable(this)){
                loadDataFromRoom(it)
            }
        }

    }


    private fun saveToDb(question: List<Item>) {
        for(q in question){
            questionViewModel.addQuestion(QuestionEntity(q))
        }
    }

    private fun initRcv( mList: List<Item>){
        calculateAverage(mList)
        binding.progressBar.visibility = View.GONE
        binding.tvAvgViewCount.visibility = View.VISIBLE
        binding.tvAvgAnswerCount.visibility = View.VISIBLE
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
        val view = sumView/ mList.size
        val ans = sumAns/ mList.size
        binding.tvAvgViewCount.text = resources.getString(R.string.avg_view_count,view.toString())
        binding.tvAvgAnswerCount.text = resources.getString(R.string.avg_ans_count,ans.toString())
    }

    override fun onTagSelected(tag: String?) {
        super.onTagSelected(tag)
        if (tag != null) {
            filterItemByTag(tag)
        }
    }

    private fun filterItemByTag(tag: String){
        if(question.isNotEmpty()){
            val adapter = QuestionListAdapter(question)
            binding.rcvQuestions.adapter = adapter
            val filteredList = mutableListOf<Item>()
            for(q in question){
                if(q.tags.contains(tag)){
                    filteredList.add(q)
                }
            }
            if(filteredList.isEmpty()){
                binding.clearFilter.visibility = View.GONE
                adapter.filterList(question)
            } else {
                binding.clearFilter.visibility = View.VISIBLE
                adapter.filterList(filteredList)
            }
        }
    }
    private fun filter(text: String){
        val adapter = QuestionListAdapter(question)
        binding.rcvSearch.adapter = adapter
        val filteredList = mutableListOf<Item>()
        for(q in question){
            if (q.title.lowercase().contains(text.lowercase())
                || q.owner.displayName.lowercase().contains(text.lowercase())) {
                filteredList.add(q)
            }
        }

        if(filteredList.isEmpty()){
            binding.searchLayout.visibility = View.GONE
            binding.mainContainerLayout.visibility = View.VISIBLE
            initRcv(question)
        } else {
            binding.mainContainerLayout.visibility = View.GONE
            binding.searchLayout.visibility = View.VISIBLE
            adapter.filterList(filteredList)
        }
    }

    private fun updateRcv(){
        binding.mainContainerLayout.visibility = View.VISIBLE
        binding.tvAvgViewCount.visibility = View.VISIBLE
        binding.tvAvgAnswerCount.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        binding.clearFilter.visibility = View.GONE
        val adapter = QuestionListAdapter(question)
        binding.rcvQuestions.layoutManager = LinearLayoutManager(this)
        binding.rcvQuestions.adapter = adapter
    }

    private fun loadDataFromRoom(list: List<QuestionEntity>) {
        if(list.isNotEmpty()){
            val cachedQuestions = mutableListOf<Item>()
            for(item in list){
                cachedQuestions.add(item.questions)
            }
            Timber.tag("Room").d("Loading From Room")
            question = cachedQuestions
            calculateAverage(question)
            updateRcv()
        }
    }


    private fun observeNetworkChange() {
        networkCallback = object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                runOnUiThread{
                    if(!isNetworkAvailable){
                        binding.progressBar.visibility = View.VISIBLE
                        viewModel.fetchQuestions()
                    }
                    isNetworkAvailable = true
                }
                Timber.tag("ConnectionCallback").d("Network Available")
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                isNetworkAvailable = false
                Timber.tag("ConnectionCallback").d("Network Lost")
            }
        }
        connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager?.registerDefaultNetworkCallback(networkCallback as NetworkCallback)
        } else {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
            connectivityManager?.registerNetworkCallback(request,
                networkCallback as NetworkCallback
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(connectivityManager != null && networkCallback != null){
            connectivityManager?.unregisterNetworkCallback(networkCallback!!)
            Timber.tag("ConnectionCallback").d("Stopped listening to connection")
        }
    }

}
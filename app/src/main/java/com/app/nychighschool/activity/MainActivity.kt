package com.app.nychighschool.activity

import NYCDataAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.nychighschool.Utility.isNetworkConnected
import com.app.nychighschool.databinding.ActivityMainBinding
import com.app.nychighschool.model.NYCData
import com.app.nychighschool.viewmodel.NYCDataViewModel

class MainActivity : AppCompatActivity(), NYCDataAdapter.OnItemClick {

    private val nycDataViewModel: NYCDataViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NYCDataAdapter
    private lateinit var nycDataList: NYCData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressLoader.visibility = View.VISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        binding.progressLoader.visibility = View.VISIBLE

        nycDataViewModel.nycData.observe(this) { nycDataList ->
            adapter = NYCDataAdapter(nycDataList, this)
            binding.recyclerView.adapter = adapter
            //adapter.nycDataList = nycDataList
            adapter.notifyDataSetChanged()
            binding.progressLoader.visibility = View.GONE

        }

        nycDataViewModel.errorMessage.observe(this) { it ->
            binding.progressLoader.visibility = View.GONE
            binding.cardNoInternet.visibility = View.VISIBLE
            binding.tvErrorMessage.setText(it)
        }

        callApiData()

        binding.btnRetry.setOnClickListener {
            callApiData()
        }


        searchSchoolData()

    }


    private fun searchSchoolData() {

        binding.etSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int, count: Int
            ) {
                adapter.filter.filter(s.toString())
                adapter.notifyDataSetChanged()
                val filteredCount = adapter.getFilteredCount()

                if (filteredCount == 0) {
                    binding.tvNoResultFound.visibility = View.VISIBLE
                } else {
                    binding.tvNoResultFound.visibility = View.GONE
                }
                Log.d("FilterCount", "onTextChanged: " + filteredCount)
            }

        })
    }

    private fun callApiData() {
        if (isNetworkConnected(this@MainActivity)) {
            nycDataViewModel.fetchNYCData()
            binding.cardNoInternet.visibility = View.GONE
            binding.progressLoader.visibility = View.VISIBLE
        } else {
            binding.progressLoader.visibility = View.GONE
            binding.cardNoInternet.visibility = View.VISIBLE
            binding.tvErrorMessage.setText("No Internet Connected")
        }
    }

    override fun onclick(dbnNo: String, nycData: NYCData) {
        val intent = Intent(this, AdditionalDataActivity::class.java)
        intent.putExtra("dbnNo", dbnNo)
        intent.putExtra("PassData", nycData)

        Log.d("position", "onclick: " + dbnNo)
        startActivity(intent)
    }
}


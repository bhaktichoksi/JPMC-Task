package com.app.nychighschool.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.nychighschool.R
import com.app.nychighschool.Utility.collapse
import com.app.nychighschool.Utility.expand
import com.app.nychighschool.databinding.ActivityAdditionalDataBinding
import com.app.nychighschool.model.AdditionalData
import com.app.nychighschool.model.NYCData
import com.app.nychighschool.viewmodel.NYCSchoolDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdditionalDataActivity : AppCompatActivity() {

    private val nycSchoolDataViewModel: NYCSchoolDataViewModel by viewModels()
    private lateinit var binding: ActivityAdditionalDataBinding


    private lateinit var schoolNo: String
    private var modelData: NYCData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdditionalDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        collapse(binding.layoutScoreInfo.linearScoreDetail)


        GlobalScope.launch(Dispatchers.IO) {
            binding.mainContainer.visibility = View.GONE
            binding.progressLoadr.visibility = View.VISIBLE

            schoolNo = intent.getStringExtra("dbnNo")!!
            modelData = intent.getParcelableExtra("PassData")!!

            Log.d("schoolNo", "onCreate: " + schoolNo)

            onNYCDataItemClick(schoolNo!!)

            launch(Dispatchers.Main) {
                initViewModelData()
            }
        }

        binding.layoutScoreInfo.scoreDateRelative.setOnClickListener {
            if (binding.layoutScoreInfo.linearScoreDetail.visibility == View.VISIBLE) {
                collapse(binding.layoutScoreInfo.linearScoreDetail)
            } else {
                expand(binding.layoutScoreInfo.linearScoreDetail)
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }


    private fun initViewModelData() {

        nycSchoolDataViewModel.additionalData.observe(this) { additionalDataList ->
            Log.d("additionalDataList", "initViewModelData: " + additionalDataList)

            var mathScore: String? = null
            var writingScore: String? = null
            var readingScore: String? = null

            for (i in 0 until additionalDataList.size) {
                mathScore = additionalDataList[i].sat_math_avg_score
                writingScore = additionalDataList[i].sat_writing_avg_score
                readingScore = additionalDataList[i].sat_critical_reading_avg_score
            }



            binding.textSchoolName.text = modelData!!.school_name
            binding.textEmail.text = modelData!!.school_email
            binding.textPhoneNo.text = modelData!!.phone_number
            binding.textLocation.text =
                modelData!!.primary_address_line_1 + ", " + modelData!!.city + " " + modelData!!.state_code + " " + modelData!!.zip


            binding.tvOverviewData.setText(modelData!!.overview_paragraph)


            binding.layoutScoreInfo.apply {
                tvMathScore.setText(mathScore)
                tvReadingScore.setText(writingScore)
                tvWritingScore.setText(readingScore)
            }


            binding.progressLoadr.visibility = View.GONE
            binding.mainContainer.visibility = View.VISIBLE

        }
    }

    private fun onNYCDataItemClick(dbn: String) {
        Log.d("onNYCDataItemClick", "onNYCDataItemClick: " + dbn)
        nycSchoolDataViewModel.fetchAdditionalData(dbn)
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

}

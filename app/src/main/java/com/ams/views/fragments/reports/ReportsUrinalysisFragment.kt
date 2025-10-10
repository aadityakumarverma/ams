package com.ams.views.fragments.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.ams.R
import com.ams.databinding.FragmentReportsUrinalysisBinding
import com.ams.databinding.LayoutReportItemBinding
import com.ams.databinding.LayoutReportSubitemBinding
import com.ams.domain.models.ReportData
import com.ams.utils.SharedPreferencesHelper
import com.ams.utils.UtilsFunctions.collapse
import com.ams.utils.UtilsFunctions.expand
import com.ams.views.activities.MainActivity.Companion.mySystemBars

class ReportsUrinalysisFragment : Fragment() {
    lateinit var binding: FragmentReportsUrinalysisBinding
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReportsUrinalysisBinding.inflate(layoutInflater)
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance(requireContext())
        navController = findNavController()

        binding.parentView.setPadding(0, mySystemBars.top,0,0)

        val arrListItem = ArrayList<ReportData>()

        arrListItem.apply {
            add(ReportData("Iron Profile", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Metabolites", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Lipid Profile", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Enzymes", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Hormones", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Vitamin", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Vitamin", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Minerals", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
            add(ReportData("Liver Function Tests", subData = arrayListOf(
                ReportData.ReportSubData("Hemoglobin (gm%)","Normal Range - 14-18", "12"),
                ReportData.ReportSubData("Serum Iron (ug/dl)","Normal Range - 14-18", "25"),
                ReportData.ReportSubData("Ferritin (ng/mL)","Normal Range - 14-18", "122"),
                ReportData.ReportSubData("Unsaturated Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "435"),
                ReportData.ReportSubData("Total Iron Binding Capacity (ug/dl)","Normal Range - 14-18", "65"),
                ReportData.ReportSubData("Transferring Saturation","Normal Range - 14-18", "35"),
            )))
        }
        arrListItem.forEach {
            addReportItem(it)
        }



        return binding.root
    }


    fun addReportItem(data: ReportData) {
        val container = binding.llContainer

        val itemBinding = LayoutReportItemBinding.inflate(
            layoutInflater,
            container,
            false
        )

        itemBinding.apply {
            tvName.text = data.name
            setExpandCollapseView(Pair(ivExpand, llSubContainer))
            val subContainer = llSubContainer
            subContainer.removeAllViews()

            // Inflate each subitem separately
            data.subData.forEach { sub ->
                val subItemBinding = LayoutReportSubitemBinding.inflate(
                    layoutInflater,
                    subContainer,
                    false
                )
                subItemBinding.apply {
                    tvSubName.text = sub.subName
                    tvResult.text = sub.result
                    tvCount.text = sub.count
                }
                subContainer.addView(subItemBinding.root)
            }
        }

        container.addView(itemBinding.root)
    }


    fun setExpandCollapseView(vararg pairs: Pair<ImageView, View>){
        pairs.forEach { pair ->
            pair.first.setOnClickListener {
                if (pair.second.isGone) {
                    // Expand the RadioGroup (drop-down animation)
                    pair.second.expand()
                    pair.first.setImageResource(R.drawable.ic_minus)

                } else {
                    // Collapse the RadioGroup (close animation)
                    pair.second.collapse()
                    pair.first.setImageResource(R.drawable.ic_plus)
                }
            }
        }
    }

}
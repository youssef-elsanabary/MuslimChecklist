package com.example.muslimchecklistmobile.content.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.muslimchecklistmobile.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import kotlinx.android.synthetic.main.fragment_reports.*


class ReportsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     var option = arrayOf("يناير","فبراير","مارس","ابريل","مايو","يونيو","يوليو","اغسطس","سبتمر","اكتوبر","نوفمبر","ديسمبر")


     month_spinner.adapter =ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,option)
    /* month_spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
      override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       TODO("Not yet implemented")
      }

      override fun onNothingSelected(parent: AdapterView<*>?) {
         Toast.makeText(context,"Plesase choose a month",Toast.LENGTH_SHORT).show()
      }*/

//     }

       val dataList = ArrayList<BarEntry>()
        dataList.add(BarEntry(20f,0f))
        dataList.add(BarEntry(30f,1f))
        dataList.add(BarEntry(40f,2f))
        dataList.add(BarEntry(50f,3f))
        dataList.add(BarEntry(10f,4f))

       val  actionList = ArrayList<String>()
        actionList.add("الوضوء")
        actionList.add("الصيام")
        actionList.add("القرآن")
        actionList.add("السنن")
        actionList.add("الاذكار")
        actionList.add("الصلوات")

        val  y_axis = ArrayList<String>()
        y_axis.add("20%")
        y_axis.add("40%")
        y_axis.add("60%")
        y_axis.add("80%")
        y_axis.add("100%")

      //  reports_morning_progress.isIndeterminate = false
        val barDataSet =BarDataSet(dataList,"")
        val barData =  BarData(barDataSet)
        barDataSet.color = R.color.colorAccent
        barDataSet.setDrawIcons(false)
        barDataSet.setDrawValues(false)
        chart.description.isEnabled = false
        chart.description.textSize = 0f
        barData.setValueFormatter(LargeValueFormatter())
        chart.data = barData
        chart.xAxis.axisMinimum = 0f
        chart.xAxis.axisMaximum = 6f
        chart.data.isHighlightEnabled = false
        chart.invalidate()

        //X-axis
        val xAxis = chart.xAxis
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 14f

        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(actionList)

        xAxis.labelCount = 6
        xAxis.mAxisMaximum = 6f
        xAxis.setCenterAxisLabels(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.spaceMin = 4f
        xAxis.spaceMax = 4f

        chart.setVisibleXRangeMaximum(6f)
        chart.setVisibleXRangeMinimum(6f)
        chart.isDragEnabled = true

        //Y-axis
        chart.axisLeft.isEnabled = false
        chart.setScaleEnabled(true)

        val rightAxis = chart.axisRight
        rightAxis.valueFormatter = IndexAxisValueFormatter(y_axis)
        rightAxis.setDrawGridLines(true)
        rightAxis.spaceTop = 1f
        rightAxis.axisMinimum = 0f


        chart.data = barData
        chart.setVisibleXRange(1f, 6f)
    }


}
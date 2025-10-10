package com.ams.domain.models

data class ReportData(
    val name : String,
    val subData : ArrayList<ReportSubData>
){
    data class ReportSubData(
        val subName : String,
        val result : String,
        val count : String,
    )

}

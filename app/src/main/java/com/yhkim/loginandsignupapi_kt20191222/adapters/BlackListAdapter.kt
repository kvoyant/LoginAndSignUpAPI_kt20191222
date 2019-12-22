package com.yhkim.loginandsignupapi_kt20191222.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yhkim.loginandsignupapi_kt20191222.R
import com.yhkim.loginandsignupapi_kt20191222.datas.BlackListData

class BlackListAdapter(context:Context, resId:Int, list:ArrayList<BlackListData>) : ArrayAdapter<BlackListData>(context, resId, list) {
    val mContext = context
    val mList = list
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        tempRow?.let {

        }.let {
            //null 일 경우
            tempRow = inf.inflate(R.layout.black_list_item, null)
        }

        val row = tempRow!! // !! 절대 널이 아님

        var data = mList.get(position)

        val titleTxt = row.findViewById<TextView>(R.id.titleTxt)
        val phoneNumTxt = row.findViewById<TextView>(R.id.phoneNumTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val writerNameTxt = row.findViewById<TextView>(R.id.writerNameTxt)

        titleTxt.text = data.title
        phoneNumTxt.text = "(${data.phoneNum})"
        contentTxt.text = data.content
        writerNameTxt.text = "- By : ${data.writer.name}"

        return row
    }
}
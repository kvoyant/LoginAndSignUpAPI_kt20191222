package com.yhkim.loginandsignupapi_kt20191222.datas

import org.json.JSONObject
import java.io.Serializable

class BlackListData(phone:String, title:String, content:String) : Serializable{

    var phoneNum = phone
    var title = title
    var content = content

    constructor() : this("폰번모름", "미입력", "미작성")

    companion object {

        fun getBlackListDataFromJson(json:JSONObject) : BlackListData {
            val bld = BlackListData() //기본생성자 작성해야 함 (위의 constructor())

            bld.phoneNum = json.getString("phone_num")
            bld.content = json.getString("content")
            bld.title = json.getString("title")

            return bld
        }
    }

}
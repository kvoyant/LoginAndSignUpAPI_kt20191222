package com.yhkim.loginandsignupapi_kt20191222.datas

import org.json.JSONObject
import java.io.Serializable

class BlackListData(phone:String, title:String, content:String) : Serializable{

    var phoneNum = phone
    var title = title
    var content = content
    var writer = User() // 작성자 정보

    constructor() : this("폰번모름", "미입력", "미작성")

    companion object {

        fun getBlackListDataFromJson(json:JSONObject) : BlackListData {
            val bld = BlackListData() //기본생성자 작성해야 함 (위의 constructor())

            bld.phoneNum = json.getString("phone_num")
            bld.content = json.getString("content")
            bld.title = json.getString("title")
            bld.writer = User.getUserDataFromJson(json.getJSONObject("writer"))

            return bld
        }
    }

/*

"writer":{
               "id":2,
               "login_id":"test123",
               "name":"테스트",
               "phone":"01012345678",
               "memo":"",
               "category":{
                  "id":1,
                  "title":"일반매장",
                  "color":"#FF0000"
               }
 */

}
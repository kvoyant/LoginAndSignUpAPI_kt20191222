package com.yhkim.loginandsignupapi_kt20191222.datas

import org.json.JSONObject
import java.io.Serializable

class User (id:String, name:String, phone:String) : Serializable{
    var loginId = "" // String 이라고 명시
    var name = ""
    var phoneNum = ""

    constructor() : this("미입력","이름모름","폰번모름")//보조생성자

    companion object {
        fun getUserDataFromJson(json:JSONObject) : User{
            val userData = User()

//            파싱해서 데이터를 채워넣기.
            userData.name = json.getString("name")
            userData.loginId = json.getString("login_id")
            userData.phoneNum = json.getString("phone")

            return userData
        }
    }
}
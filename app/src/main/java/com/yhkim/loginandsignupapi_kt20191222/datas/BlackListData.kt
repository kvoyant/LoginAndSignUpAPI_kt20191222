package com.yhkim.loginandsignupapi_kt20191222.datas

import java.io.Serializable

class BlackListData(phone:String, title:String, content:String) : Serializable{

    var phoneNum = phone
    var title = title
    var content = content

}
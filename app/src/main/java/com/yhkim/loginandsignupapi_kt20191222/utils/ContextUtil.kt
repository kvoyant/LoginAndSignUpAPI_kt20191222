package com.yhkim.loginandsignupapi_kt20191222.utils

import android.content.Context

class ContextUtil {

    companion object {
        val prefName = "LoginPracticePreference"

        val USER_TOKEN = "USER_TOKEN"

        fun setUserToken(context: Context, token:String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(USER_TOKEN, token).apply()
        }

        fun getUserToken(context: Context) : String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            return pref.getString(USER_TOKEN, "")!! // !! => 절대 널이 아님
        }
    }
}
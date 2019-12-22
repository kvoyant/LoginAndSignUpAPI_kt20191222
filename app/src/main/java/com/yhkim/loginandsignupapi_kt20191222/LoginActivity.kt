package com.yhkim.loginandsignupapi_kt20191222

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yhkim.loginandsignupapi_kt20191222.datas.User
import com.yhkim.loginandsignupapi_kt20191222.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val inputId = loginEdt.text.toString()
            val inputPw = passwordEdt.text.toString()

            ConnectServer.postRequestLogin(mContext, inputId, inputPw, object: ConnectServer.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    Log.d("로그인 응답", json.toString())
                    val code = json.getInt("code")

                    if(code == 200) {
                        val data = json.getJSONObject("data")
                        val user = data.getJSONObject("user")

//                        val userName = user.getString("name")
//                        val userId = user.getString("login_id")
//                        val userPhone = user.getString("phone")
//
//                        val userData = User(userId, userName, userPhone)

                        val userData = User.getUserDataFromJson(user)

                        val intent = Intent(mContext, MainActivity::class.java)
//                        intent.putExtra("name", userName)
                        intent.putExtra("name", userData.name)
                        intent.putExtra("id", userData.loginId)
                        intent.putExtra("phone", userData.phoneNum)
                        startActivity(intent)
                    }
                    else {
                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext,message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        signUpBtn.setOnClickListener {
            val intent = Intent(mContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun setValues() {
    }

}

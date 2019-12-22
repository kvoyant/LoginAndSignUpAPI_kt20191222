package com.yhkim.loginandsignupapi_kt20191222

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yhkim.loginandsignupapi_kt20191222.datas.User
import com.yhkim.loginandsignupapi_kt20191222.utils.ConnectServer
import com.yhkim.loginandsignupapi_kt20191222.utils.ContextUtil
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

                        val userData = User.getUserDataFromJson(user)

//                        서버에서 내려주는 토큰
                        val token = data.getString("token")
//                        sharedPreference에 반영구 저장
                        ContextUtil.setUserToken(mContext, token)

                        val intent = Intent(mContext, MainActivity::class.java)
                        intent.putExtra("user", userData)
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

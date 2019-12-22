package com.yhkim.loginandsignupapi_kt20191222

import android.os.Bundle
import android.util.Log
import com.yhkim.loginandsignupapi_kt20191222.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        signUpBtn.setOnClickListener {
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()
            val inputName = nameEdt.text.toString()
            val inputPhone = phoneEdt.text.toString()

            ConnectServer.putRequestSignUp(mContext, inputId, inputPw, inputName, inputPhone, object: ConnectServer.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    Log.d("회원가입응답", json.toString())
                }

            })
        }
    }

    override fun setValues() {
    }

}

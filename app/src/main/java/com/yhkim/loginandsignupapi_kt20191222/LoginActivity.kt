package com.yhkim.loginandsignupapi_kt20191222

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {
        signUpBtn.setOnClickListener {
            val intent = Intent(mContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun setValues() {
    }

}

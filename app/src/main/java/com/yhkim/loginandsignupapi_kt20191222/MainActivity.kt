package com.yhkim.loginandsignupapi_kt20191222

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        val userName = intent.getStringExtra("name")
        val userId = intent.getStringExtra("id")
        val userPhone = intent.getStringExtra("phone")

        userNameAndIdTxt.text = "${userName}( ${userId} )"
        userPhoneTxt.text = userPhone
    }

}

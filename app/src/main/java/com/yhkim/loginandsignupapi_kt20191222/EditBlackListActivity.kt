package com.yhkim.loginandsignupapi_kt20191222

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yhkim.loginandsignupapi_kt20191222.datas.User
import com.yhkim.loginandsignupapi_kt20191222.utils.ConnectServer
import com.yhkim.loginandsignupapi_kt20191222.utils.ContextUtil
import kotlinx.android.synthetic.main.activity_edit_black_list.*
import org.json.JSONObject

class EditBlackListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_black_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        editBlackListBtn.setOnClickListener {
            val title = titleEdt.text.toString()
            val phoneNum = phoneNumEdt.text.toString()
            val content = contentEdt.text.toString()

/*
응답:
 */
            ConnectServer.postRequestEditBlackList(mContext, title, phoneNum, content, object: ConnectServer.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    Log.d("블랙리스트 저장후 응답", json.toString())
                    val code = json.getInt("code")

                    if(code == 200) {
                        val data = json.getJSONObject("data")
                        val user = data.getJSONObject("user")


//                        서버에서 내려주는 토큰
                        val token = data.getString("token")
//                        sharedPreference에 반영구 저장
                        ContextUtil.setUserToken(mContext, token)

                        val intent = Intent(mContext, MainActivity::class.java)

                        //데이터 넘긱기
                        val userData = User.getUserDataFromJson(user)
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



    }

    override fun setValues() {
    }
}

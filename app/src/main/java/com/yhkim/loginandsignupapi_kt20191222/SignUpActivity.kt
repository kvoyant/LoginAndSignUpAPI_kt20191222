package com.yhkim.loginandsignupapi_kt20191222

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

/*

회원가입응답: {"code":200,"message":"회원가입 성공입니다.","data":{"user":{"id":32,"login_id":"yhkim","name":"chris","phone":"01012345678","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:18:43"},"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6MzIsImxvZ2luX2lkIjoieWhraW0iLCJwYXNzd29yZCI6IjE4YjUzZjNiYThhMjIwNjRmMTg4OGRjNjJlOTZlYWJjIn0.HU0IDGIHPF2SgD1Xwg_JEy1IKX4i9J5Tyr54U34h2WV0yINVi5JJpOmvl6DR8IZRDLVV9uNwzWW00TUPhuwVKA"}}

*/

            ConnectServer.putRequestSignUp(mContext, inputId, inputPw, inputName, inputPhone, object: ConnectServer.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {
                    Log.d("회원가입응답", json.toString())

//                    서버가 내려주는 code값이 몇인지 확인
                    val code = json.getInt("code")
                    Log.d("회원가입코드","${code}") //200

                    runOnUiThread {
//                        연습문제 : 토스트이 내용을 직접 코딩이 아니라
//                                 서버에서 내려주는 message 로 뿌려 준다.

                        val message = json.getString("message")

//                        무조건 토스트 띄우고 정상이면 화면을 닫아준다.
                        Toast.makeText(mContext, "${message}", Toast.LENGTH_SHORT).show()

                        if(code == 200 ) {
                            finish()
                        }
/*
                        else {
//                            {"code":400,"message":"중복된 유저 아이디가 존재합니다."}

                            Toast.makeText(mContext, "${message}", Toast.LENGTH_SHORT).show()
                        }
 */
                    }

                }

            })
        }
    }

    override fun setValues() {
    }

}

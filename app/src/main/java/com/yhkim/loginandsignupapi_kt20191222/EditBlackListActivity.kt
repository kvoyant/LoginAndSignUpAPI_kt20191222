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
블랙리스트 목록응답: {"code":200,"message":"블랙리스트 조회 성공","data":{"black_lists":[{"id":24,"phone_num":"010 4141 3333","title":"블랙리스트 입니다","content":"블랙리스트 신고합니다!","created_at":"2019-12-22 07:40:06","writer":{"id":32,"login_id":"yhkim","name":"chris","phone":"01012345678","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:18:43"}},{"id":23,"phone_num":"01012123434","title":"abab","content":"ccccc","created_at":"2019-12-22 07:38:49","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":22,"phone_num":"01012123434","title":"aaa","content":"bbb","created_at":"2019-12-22 07:37:23","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":21,"phone_num":"010 1231 1411","title":"black list one","content":"bad write","created_at":"2019-12-22 07:36:39","writer":{"id":32,"login_id":"yhkim","name":"chris","phone":"01012345678","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:18:43"}},{"id":20,"phone_num":"01012123434","title":"제목제목","content":"블랙리스트 신고합니다","created_at":"2019-12-22 07:35:36","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":19,"phone_num":"111","title":"ㄱㄱㄹ","content":"ㄹㄹㅎ","created_at":"2019-12-22 07:32:51","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":2,"phone_num":"01053242152","title":"둘째 게시글","content":"작성해봅니다.","created_at":"2019-09-27 11:36:37","writer":{"id":2,"login_id":"test123","name":"테스트","phone":"01012345678","memo":"","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":"2019-09-30","created_at":"2019-08-23 17:32:12"}},{"id":1,"phone_num":"01012345678","title":"첫 신고사례","content":"테스트용 게시글입니다.","created_at":"2019-08-26 21:41:48","writer":{"id":1,"login_id":"cho881020","name":"조경진","phone":"01051123237","memo":"ㅇㅇ","category":{"id":3,"title":"식당","color":"#0000FF"},"is_admin":false,"start_date":"2019-11-01","expire_date":"2019-12-31","created_at":"2019-08-23 17:32:12"}}]}}
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

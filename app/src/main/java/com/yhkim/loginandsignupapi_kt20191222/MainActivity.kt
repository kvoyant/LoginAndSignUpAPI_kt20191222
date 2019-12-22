package com.yhkim.loginandsignupapi_kt20191222

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yhkim.loginandsignupapi_kt20191222.datas.BlackListData
import com.yhkim.loginandsignupapi_kt20191222.datas.User
import com.yhkim.loginandsignupapi_kt20191222.utils.ConnectServer
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val blackList = ArrayList<BlackListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        val user = intent.getSerializableExtra("user") as User

        userNameAndIdTxt.text = "${user.name}( ${user.loginId} )"
        userPhoneTxt.text = user.phoneNum

        getBlackListsFromServer()
    }

/*
블랙리스트 목록응답: {"code":200,"message":"블랙리스트 조회 성공","data":{"black_lists":[{"id":2,"phone_num":"01053242152","title":"둘째 게시글","content":"작성해봅니다.","created_at":"2019-09-27 11:36:37","writer":{"id":2,"login_id":"test123","name":"테스트","phone":"01012345678","memo":"","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":"2019-09-30","created_at":"2019-08-23 17:32:12"}},{"id":1,"phone_num":"01012345678","title":"첫 신고사례","content":"테스트용 게시글입니다.","created_at":"2019-08-26 21:41:48","writer":{"id":1,"login_id":"cho881020","name":"조경진","phone":"01051123237","memo":"ㅇㅇ","category":{"id":3,"title":"식당","color":"#0000FF"},"is_admin":false,"start_date":"2019-11-01","expire_date":"2019-12-31","created_at":"2019-08-23 17:32:12"}}]}}
* */


    fun getBlackListsFromServer() {
        ConnectServer.getRequestBlackList(mContext, object : ConnectServer.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {
                Log.d("블랙리스트 목록응답",json.toString())

                val code = json.getInt("code")

                if(code == 200) {
                    val data = json.getJSONObject("data")
                    val black_lists = data.getJSONArray("black_lists")

                    for (i in 0..black_lists.length()-1) {

//                        JSONArray 내부의 i번째 JSONObject를 추출
//                        var tempJson = black_lists.getJSONObject(i)
//                        뽑아낸 JSONObject => BlackListData 클래스로 변경 (리스트뷰에서 활용 가능)
//                        val tempData = BlackListData.getBlackListDataFromJson(tempJson)
//                        BlackListData 클래스로 변경된 변수를 ArrayList에 추가
//                        blackList.add(tempData)

                        blackList.add(BlackListData.getBlackListDataFromJson(black_lists.getJSONObject(i)))

                    }
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

package com.yhkim.loginandsignupapi_kt20191222.utils

import android.content.Context
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ConnectServer {

    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }

    companion object {

        //        접속할 서버의 호스트 주소 (BASE_URL)
        val BASE_URL = "http://192.168.0.17:5000"

        fun postRequestLogin(context: Context, id:String, pw:String, handler: JsonResponseHandler?) {
            val client = OkHttpClient()
            val url = "${BASE_URL}/auth"
            val formData = FormBody.Builder()
                .add("login_id", id)
                .add("password", pw)
                .build()

            val request = Request.Builder()
                .url(url)
                .post(formData)
                .build()
/*
로그인 응답: {"code":200,"message":"로그인 성공","data":{"user":{"id":32,"login_id":"yhkim","name":"chris","phone":"01012345678","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:18:43"},"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6MzIsImxvZ2luX2lkIjoieWhraW0iLCJwYXNzd29yZCI6IjE4YjUzZjNiYThhMjIwNjRmMTg4OGRjNjJlOTZlYWJjIn0.HU0IDGIHPF2SgD1Xwg_JEy1IKX4i9J5Tyr54U34h2WV0yINVi5JJpOmvl6DR8IZRDLVV9uNwzWW00TUPhuwVKA"}}
 */
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    handler?.onResponse(JSONObject(response.body()!!.toString()))

                    val body = response.body()!!.string() //!! 절대 널이 아님
                    val json = JSONObject(body)
                    handler?.onResponse(json) //handler? => 핸들러가 널이 아니면

                }
            })
        }

        //        회원가입 요청 함수 : ? => null 허용
        fun putRequestSignUp(
            context: Context,
            inputId: String,
            inputPw: String,
            inputName: String,
            inputPhone: String,
            handler: JsonResponseHandler?
        ) {

//            이 앱이 클라이언트 역할을 할 수 있게 해주는 클래스 객체화
            val client = OkHttpClient()
//            서버의 기능중 어느 주소로 갈지 명시 => 주소 : url
            val url = "${BASE_URL}/auth"

//            서버로 들고갈 파라미터들을 미리 작성.
//            put 메소드 이므로 폼데이터에 파리미터들을 실어주자.
//            okHttp에서는 formData를 formBody라는 이름으로 사용.

            val formData = FormBody.Builder()
                .add("login_id", inputId)
                .add("password", inputPw)
                .add("name", inputName)
                .add("phone", inputPhone)
                .build() // 모두 추가했으면 FormBody 완성(build() )

//            실제로 날아갈 요청을 작성. => 화면을 넘어갈때 Intent 만드는것과 비슷한 개념
            val request = Request.Builder()
                .url(url)
                .put(formData)
                .build() //request에 필요한 데이터를 다 넣었으면 build()로 완성

//            만든 요청을 실제로 실행 (클라이언트) => startActivity와 비슷한 개념
//            요청에 대한 응답 처리 코드 => 액티비티가 실행하도록 연결

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    서버 요청에 실패하면 왜 실패했는지 로그캣에서 확인
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
//                    서버요청에 성공하면 응답 내용( String으로 받아서)을 JSON으로 가공
                    val body = response.body()!!.string() //!! 절대 널이 아님
                    val json = JSONObject(body)
                    handler?.onResponse(json) //handler? => 핸들러가 널이 아니면
                }

            })
        }
    }
}
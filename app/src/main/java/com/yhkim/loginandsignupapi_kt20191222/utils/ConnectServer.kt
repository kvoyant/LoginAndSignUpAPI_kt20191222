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



        fun postRequestEditBlackList(context: Context, title:String, phone_num:String, content:String, handler: JsonResponseHandler?) {
            val client = OkHttpClient()
            val url = "${BASE_URL}/black_list"
            val formData = FormBody.Builder()
                .add("title", title)
                .add("phone_num", phone_num)
                .add("content", content)
                .build()

            val request = Request.Builder()
                .url(url)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .post(formData)
                .build()
/*
블랙리스트 목록응답: {"code":200,"message":"블랙리스트 조회 성공","data":{"black_lists":[{"id":24,"phone_num":"010 4141 3333","title":"블랙리스트 입니다","content":"블랙리스트 신고합니다!","created_at":"2019-12-22 07:40:06","writer":{"id":32,"login_id":"yhkim","name":"chris","phone":"01012345678","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:18:43"}},{"id":23,"phone_num":"01012123434","title":"abab","content":"ccccc","created_at":"2019-12-22 07:38:49","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":22,"phone_num":"01012123434","title":"aaa","content":"bbb","created_at":"2019-12-22 07:37:23","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":21,"phone_num":"010 1231 1411","title":"black list one","content":"bad write","created_at":"2019-12-22 07:36:39","writer":{"id":32,"login_id":"yhkim","name":"chris","phone":"01012345678","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:18:43"}},{"id":20,"phone_num":"01012123434","title":"제목제목","content":"블랙리스트 신고합니다","created_at":"2019-12-22 07:35:36","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":19,"phone_num":"111","title":"ㄱㄱㄹ","content":"ㄹㄹㅎ","created_at":"2019-12-22 07:32:51","writer":{"id":55,"login_id":"cha03","name":"cha","phone":"01012123434","memo":"새 회원","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":null,"created_at":"2019-12-22 03:44:16"}},{"id":2,"phone_num":"01053242152","title":"둘째 게시글","content":"작성해봅니다.","created_at":"2019-09-27 11:36:37","writer":{"id":2,"login_id":"test123","name":"테스트","phone":"01012345678","memo":"","category":{"id":1,"title":"일반매장","color":"#FF0000"},"is_admin":false,"start_date":null,"expire_date":"2019-09-30","created_at":"2019-08-23 17:32:12"}},{"id":1,"phone_num":"01012345678","title":"첫 신고사례","content":"테스트용 게시글입니다.","created_at":"2019-08-26 21:41:48","writer":{"id":1,"login_id":"cho881020","name":"조경진","phone":"01051123237","memo":"ㅇㅇ","category":{"id":3,"title":"식당","color":"#0000FF"},"is_admin":false,"start_date":"2019-11-01","expire_date":"2019-12-31","created_at":"2019-08-23 17:32:12"}}]}}
 */
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {

                    val body = response.body()!!.string() //!! 절대 널이 아님
                    val json = JSONObject(body)
                    handler?.onResponse(json) //handler? => 핸들러가 널이 아니면

                }
            })
        }





        fun getRequestBlackList(context: Context, handler: JsonResponseHandler?) {
            val client = OkHttpClient()
//            GET 방식 호출은 파라미터를 query에 담는다 => 주소창에 같이 적어주는 방식
//            url을 만들때 애초에 파라미터도 같이 첨부 해야함

            val urlBuilder = HttpUrl.parse("${BASE_URL}/black_list")!!.newBuilder()
//            파라미터 첨부한다면 예시..
//            urlBuilder.addEncodedQueryParameter("파라미터이름","필요변수")

//            URL빌더를 통해 가공된 URL을 실제 String 타입의 url로 변경.

            val url = urlBuilder.build().toString()

//            요청을 날리는 request 생성
//            GET방식은 제일 기본적인 리퀘스트 => post, put 등와는 다르게 메소드 지정하지 않는다 => 바로 build()로 마무리

            //API가 헤더를 요구한다면 ,request 생성시에 첨부.
            val request = Request.Builder()
                .url(url)
                .header("X-Http-Token", ContextUtil.getUserToken(context))
                .build()

//            클라이언트를 이용해 서버에 실제 요청
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
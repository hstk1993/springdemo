package com.example.springdemo.api


import com.example.springdemo.utils.SingUtils
import com.example.springdemo.utils.SocketMsg
import com.google.common.hash.Hashing
import com.google.gson.Gson
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.utils.URIBuilder
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.web.bind.annotation.*
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*

@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api")
class Login {
    fun request(url: String, param: RequestModel): String {
        val httpClient = HttpClients.createDefault()
        val gson = Gson()
        try {
            val httpGet = HttpPost(url)

            httpGet.entity = StringEntity(gson.toJson(param))
            httpGet.setHeader("Content-Type", "application/json")
            val response = httpClient.execute(httpGet)
            return EntityUtils.toString(response.entity)
        } catch (e: Exception) {
        } finally {
            httpClient.close()
        }
        return ""
    }

    fun requestByGet(url: String): String {
        val httpClient = HttpClients.createDefault()
        val gson = Gson()
        try {
            val uri = URIBuilder(url)
                .addParameter("app_id", Constants.webankAppId)
                .addParameter("access_token", Constants.token)
                .addParameter("type", "NONCE")
                .addParameter("version", Constants.version)
                .addParameter("user_id", Constants.userId)
                .build()
            val httpGet = HttpGet(uri)
            httpGet.setHeader("Content-Type", "application/json")
            val response = httpClient.execute(httpGet)
            val gson = Gson()

            return gson.fromJson(EntityUtils.toString(response.entity), NonceTicketModel::class.java).tickets[0].value
        } catch (e: Exception) {
        } finally {
            httpClient.close()
        }
        return ""
    }

    fun sign(values: MutableList<String>, ticket: String): String { //values传ticket外的其他参数
        values.add(ticket)
        values.sort()
        val sb = StringBuilder()
        for (s in values) {
            sb.append(s)
        }
        return Hashing.sha1().hashString(
            sb,
            UTF_8
        ).toString().uppercase(Locale.getDefault())
    }

    @GetMapping("/login")
    fun test(
        @RequestParam(value = "name", defaultValue = "张磊") name: String,
        @RequestParam(value = "id", defaultValue = "140524199309027413") id: String
    ): String {
        val gson = Gson()
//        val sign = sign(
//            mutableListOf(
//                Constants.version,
//                Constants.webankAppId,
//                Constants.userId,
//                Constants.nonce,
//            ), Constants.ticket
//
//        )
//        val requestParams = RequestModel(
//            Constants.webankAppId,
//            "T1515dd",
//            name,
//            id,
//            Constants.userId,
//            Constants.version,
//            sign,
//            Constants.nonce,
//            "1"
//        )
//        val res = request("https://kyc.qcloud.com/api/server/getAdvFaceId", requestParams)
//        val aModel = gson.fromJson(res, AdvResponseModel::class.java)
//        val advResponseModel = aModel.result
//        val nonceTicket = requestByGet("https://kyc.qcloud.com/api/oauth2/api_ticket")
//        val loginSign = sign(
//            mutableListOf(
//                Constants.webankAppId,
//                advResponseModel.orderNo,
//                Constants.userId,
//                Constants.version,
//                advResponseModel.faceId,
//                Constants.nonce
//
//            ), nonceTicket
//        )
//        val url =
//            "https://${advResponseModel.optimalDomain}/api/web/login?appId=${Constants.webankAppId}&version=1.0.0&nonce=${Constants.nonce}&orderNo=${advResponseModel.orderNo}&faceId=${advResponseModel.faceId}&url=http://192.168.110.200:5173/result&from=browser&userId=${Constants.userId}&sign=${loginSign}&redirectType=1"
//        return gson.toJson(BaseResponse(0, "成功", url))
        val s = SingUtils.sign(mutableListOf(), "d")
        val a = SocketMsg(2, "dsd", s, "ss");
        return gson.toJson(a)
    }
}
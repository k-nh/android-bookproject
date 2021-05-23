package com.example.bookreviewsver.src.Main.Report.OCR

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_translation.*
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class TranslationActivity: AppCompatActivity() {
    var selectedSource : String ?= null
    var selectedTarget : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        val OCRTextData = intent.getStringExtra("OCRTextData")
        translationText.setText(OCRTextData)
        val sourceLan = listOf("번역 전 언어","한국어","영어","일본어","베트남어","중국어","인도네시아어","태국어","독일어","러시아어","스페인어","프랑스어")
        val targetLan = listOf("번역 후 언어","한국어","영어","일본어","베트남어","중국어","인도네시아어","태국어","독일어","러시아어","스페인어","프랑스어")

        val sourceAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sourceLan)
        val targetAdapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,targetLan)
        source_spinner.adapter = sourceAdapter
        target_spinner.adapter = targetAdapter
        source_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSource = when (sourceLan[position]) {
                    "한국어" -> "ko"
                    "영어" -> "en"
                    "일본어" -> "ja"
                    "중국어" -> "zh-CN"
                    "베트남어" -> "vi"
                    "인도네시아어" -> "id"
                    "태국어" -> "th"
                    "독일어" -> "de"
                    "러시아어" -> "ru"
                    "스페인어" -> "es"
                    "이탈리아어" -> "it"
                    "프랑스어" -> "fr"
                    else -> "en"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

            target_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTarget = when (targetLan[position]) {
                    "한국어" -> "ko"
                    "영어" -> "en"
                    "일본어" -> "ja"
                    "중국어" -> "zh-CN"
                    "베트남어" -> "vi"
                    "인도네시아어" -> "id"
                    "태국어" -> "th"
                    "독일어" -> "de"
                    "러시아어" -> "ru"
                    "스페인어" -> "es"
                    "이탈리아어" -> "it"
                    "프랑스어" -> "fr"
                    else -> "ko"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        translationButton.setOnClickListener{
            MyApplication.sSharedPreferences.edit().putString("selectedSource", selectedSource).apply()
            MyApplication.sSharedPreferences.edit().putString("selectedTarget", selectedTarget).apply()
            Log.d("언어", selectedSource+selectedTarget)

            val translateTask = TranslateTask(translationText.text.toString())
            resultText.text = translateTask.execute().get()
            //Log.e("test1", "번역되었습니다. ${translateTask.execute().get()}.")
        }
    }


    class TranslateTask(translationText: String) : AsyncTask<String, Void, String> () {
        var translationText = translationText
        override fun doInBackground(vararg params: String?): String {
            val clientId = "0BSr8hBdj3FAeV6M8H9a";//애플리케이션 클라이언트 아이디값";
            val clientSecret = "Q6Ylcg6a7e";//애플리케이션 클라이언트 시크릿값";

            val apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            var text: String=translationText

            text = try {
                URLEncoder.encode(translationText, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("인코딩 실패", e)
            }

            val requestHeaders: MutableMap<String, String> = HashMap()
            requestHeaders["X-Naver-Client-Id"] = clientId
            requestHeaders["X-Naver-Client-Secret"] = clientSecret

            fun connect(apiUrl: String): HttpURLConnection {
                return try {
                    val url = URL(apiUrl)
                    (url.openConnection() as HttpURLConnection)
                } catch (e: MalformedURLException) {
                    throw java.lang.RuntimeException("API URL이 잘못되었습니다. : $apiUrl", e)
                } catch (e: IOException) {
                    throw java.lang.RuntimeException("연결이 실패했습니다. : $apiUrl", e)
                }
            }

            fun readBody(body: InputStream):String {
                val streamReader = InputStreamReader(body)
                try{
                    BufferedReader(streamReader).use { lineReader ->
                        val responseBody = StringBuilder()
                        var line: String?
                        while (lineReader.readLine().also { line = it } != null) {
                            responseBody.append(line)
                        }
                        return responseBody.toString()
                    }
                } catch (e: IOException) {
                    throw java.lang.RuntimeException("API 응답을 읽는데 실패했습니다.", e)
                }
            }

            fun post(apiUrl: String, requestHeaders: Map<String, String>, text: String): String {
                val con: HttpURLConnection = connect(apiUrl)
                //val postParams = "source=en&target=ko&text=$text" //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
                var selectedSource = MyApplication.sSharedPreferences.getString("selectedSource",null)
                var selectedTarget = MyApplication.sSharedPreferences.getString("selectedTarget",null)
                val postParams = "source=${selectedSource}&target=${selectedTarget}&text=$text"
                try {
                    con.requestMethod = "POST"

                    for(header : Map.Entry<String, String> in requestHeaders.entries) {
                        con.setRequestProperty(header.key, header.value)
                    }
                    con.doOutput = true
                    DataOutputStream(con.outputStream).use { wr ->
                        wr.write(postParams.toByteArray())
                        wr.flush()
                    }

                    val responseCode = con.responseCode
                    if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                        return readBody(con.inputStream);
                    } else {  // 에러 응답
                        return readBody(con.errorStream);
                    }
                }catch (e: IOException) {
                    throw java.lang.RuntimeException("API 요청과 응답 실패", e)
                }finally {
                    con.disconnect();
                }
            }

            val responseBody: String = post(apiURL, requestHeaders, text)
            println(responseBody)

            var parser: JsonParser = JsonParser()
            var element: JsonElement = parser.parse(responseBody)
            var data:String=""

            if(element.asJsonObject.get("errorMessage") != null){
                Log.e(
                    "번역 오류",
                    "번역 오류가 발생했습니다." + "[오류 코드: " + element.asJsonObject.get("errorCode").asString + "]"
                )
                data = "A: 오류입니다."
            } else if (element.asJsonObject.get("message") != null){
                data = element.asJsonObject.get("message").getAsJsonObject().get("result")
                    .asJsonObject.get("translatedText").asString
                Log.e("번역 성공", "번역되었습니다. $data")
            }

            return data;
        }
    }
}
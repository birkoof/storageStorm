package com.basic.storagestorm.api

import android.os.AsyncTask
import android.util.Log
import at.tugraz.ikarus.api.IkarusApi

class API : AsyncTask<Void, Void, String>() {
    override fun doInBackground(vararg params: Void?): String {
        val ikarus = IkarusApi("http://muffin-ti.me:8084")

        return ikarus.hello("User")
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d("IKARUS", "result: $result")
    }
}

/*
class getQuestions : AsyncTask<Void, Void, String>() {

        lateinit var progressDialog: ProgressDialog
        var hasInternet = false

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Downloading Questions...")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg p0: Void?): String {
            if (isNetworkAvailable()) {
                hasInternet = true
                val client = OkHttpClient()
                val url = "https://script.googleusercontent.com/macros/echo?user_content_key=1tgBN-ES-vsiLin8Lggs7R094sUSEWlBY3Lv7yLt0KnrexUuaTvreORsTenxGH0HaPDQ0rUkXVqmkc903P_gQrpXCbi98gcsm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnBg4Wj9So2Q_mI0_S0Bm21-AGmcRnplmVaRcxvVzvCi9cnQQJegsnVb9TgJzPufw35cdv3aNHr6K&lib=MKMzvVvSFmMa3ZLOyg67WCThf1WVRYg6Z"
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                return response.body()?.string().toString()
            } else {
                return ""
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressDialog.dismiss()

            if (hasInternet) {
                try {
                    val resultArray = JSONArray(result)

                    for (i in 0..(resultArray.length() - 1)) {
                        val currentObject = resultArray.getJSONObject(i)
                        val obj = Question()
                        obj.Question = currentObject.getString("Question")
                        obj.Option1 = currentObject.getString("Option1")
                        obj.Option2 = currentObject.getString("Option2")
                        obj.Option3 = currentObject.getString("Option3")
                        obj.Option4 = currentObject.getString("Option4")
                        obj.Answer = currentObject.getInt("Answer")
                        QuestionList.add(obj)
                    }

                    if (index == -1) {
                        index++
                        tv_question.text = QuestionList[index].Question
                        rb_choice1.text = QuestionList[index].Option1
                        rb_choice2.text = QuestionList[index].Option2
                        rb_choice3.text = QuestionList[index].Option3
                        rb_choice4.text = QuestionList[index].Option4
                    }

                    btn_next.isEnabled = true
                    btn_next.alpha = 1.toFloat()

                    btn_next.setOnClickListener({
                        UpdateQuestion()
                    })
                } catch (e: JSONException) {

                }
            }

        }

    }

* */

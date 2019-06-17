package com.example.swim_2

import android.app.Activity
import android.content.SharedPreferences
import com.example.swim_2.models.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferencesService {
    private var sharedPref: SharedPreferences? = null

    fun createSharedPref(act: Activity) {
        if (sharedPref == null)
            sharedPref = act.getSharedPreferences("bmi_history_file",0)
    }

    fun loadHistoryData(): ArrayList<Image> {
        val resultsJson = sharedPref!!.getString("images", "[]")
        class Token : TypeToken<ArrayList<Image>>()
        return Gson().fromJson(resultsJson, Token().type)
    }

    fun commitHistoryChanges(results: ArrayList<Image>) {
        val editor = sharedPref!!.edit()
        val resultsJson = Gson().toJson(results)
        editor.putString("images", resultsJson).apply()
    }

    fun removeKeyValue(key: String) {
        sharedPref!!.edit().remove(key).apply()
    }

    fun clearData() {
        sharedPref!!.edit().clear().apply()
    }
}
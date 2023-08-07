package com.example.screen_lake.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.example.screenlake.utils.Constants.IntegerConstants.NEGATIVE_ONE
import com.example.screenlake.utils.Constants.StringConstants.EMPTY
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreference @Inject constructor(@ApplicationContext val context: Context) {
    enum class SpKeys(val key:String){
        FILE_NAME("Sift"),
    }
    private val preferences =
        context.getSharedPreferences(SpKeys.FILE_NAME.key, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = preferences.edit()

    private fun getSharedPreferenceStringEmptyDefault(key: String):String?{
        return preferences.getString(key, EMPTY)
    }

    private fun getSharedPreferenceStringNullDefault(key: String):String?{
        return preferences.getString(key, null)
    }

    private fun getSharedPreferenceInteger(key: String):Int{
        return preferences.getInt(key, NEGATIVE_ONE)
    }

    private fun putSharedPreferenceString(key: String,value:String?){
        editor.apply {
            putString(key, value)
            apply()
        }
    }

    private fun getSharedPreferenceBoolean(key: String):Boolean{
        return preferences.getBoolean(key, false)
    }

    private fun getTrueSharedPreferenceBoolean(key: String):Boolean{
        return preferences.getBoolean(key, true)
    }

    private fun putSharedPreferenceBoolean(key: String,value:Boolean){
        editor.apply {
            putBoolean(key, value)
            apply()
        }
    }

    private fun putSharedPreferenceInt(key: String,value:Int?){
        editor.apply {
            putInt(key, value?:NEGATIVE_ONE)
            apply()
        }
    }

    fun clearPrefs( cleared: () -> Unit) {
        editor.clear().apply()
        cleared.invoke()
    }
}
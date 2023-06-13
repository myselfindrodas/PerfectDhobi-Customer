package com.dhobi.perfectdhobi.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Patterns
import com.dhobi.perfectdhobi.application.App


object Shared_Preferences {
    private const val PREF_IS_LANG = "lang_select"
    private const val PREF_TOKEN = "token"
    private const val PREF_IS_REMEMBERED = "forget_password"
    private const val PREF_IS_LOGIN = "logged_in"
    private const val PREF_USER_ID = "user_id"

    private const val PREF_PHONE_NO = "phone_no"
    private const val PREF_PASSWORD = "password"


    private const val PREF_USER_EMAIL = "userEmail"

    private const val PREF_USER_FIRST_NAME = "userFirstName"

    private const val IS_LOGIN = "IsLoggedIn"
    var editor: SharedPreferences.Editor?=null
    const val KEY_username = "username"
    const val KEY_password = "password"
    const val KEY_userId = "userid"




    private fun getSharedPreferences(): SharedPreferences {
        return App.getInstance()
            .getSharedPreferences(
                "APPPref",
                Context.MODE_PRIVATE
            )
    }


    fun getIsRemembered(): Boolean {
        return getSharedPreferences().getBoolean(PREF_IS_REMEMBERED, false)
    }

    fun setIsRemembered(isRemember: Boolean?) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(PREF_IS_REMEMBERED, isRemember!!)
        editor.apply()
    }

    fun getLangStatus(): String {
        return getSharedPreferences().getString(PREF_IS_LANG, "en")!!
    }

    fun setLangStatus(lang: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_IS_LANG, lang!!)
        editor.apply()
    }


    fun getPhoneNo(): String {
        return getSharedPreferences().getString(PREF_PHONE_NO, "")!!
    }

    fun setPhoneNo(phone_no: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_PHONE_NO, phone_no!!)
        editor.apply()
    }

    fun getUserId(): String {
        return getSharedPreferences().getString(PREF_USER_ID, "0")!!
    }

    fun setUserId(userId: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_USER_ID, userId!!)
        editor.apply()
    }


    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }


    fun getLoginStatus(): Boolean? {
        return getSharedPreferences().getBoolean(PREF_IS_LOGIN, false)
    }

    fun setLoginStatus(token: Boolean?) {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(PREF_IS_LOGIN, token!!)
        editor.apply()
    }


    fun getToken(): String? {
        return getSharedPreferences().getString(PREF_TOKEN, "")
    }

    fun setUserToken(token: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_TOKEN, token)
        editor.commit()
    }


    fun getLat(): String? {
        return getSharedPreferences().getString("latitude", "")
    }

    fun setLat(lat: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("latitude", lat)
        editor.apply()
    }



    fun getLong(): String? {
        return getSharedPreferences().getString("longitude", "")
    }

    fun setLong(long: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("longitude", long)
        editor.apply()
    }


    fun getUrcid(): String? {
        return getSharedPreferences().getString("urcid", "")
    }

    fun setUrcid(urcid: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("urcid", urcid)
        editor.apply()
    }


    fun getselectedLat(): String? {
        return getSharedPreferences().getString("selectedlat", "")
    }

    fun setselectedLat(selectedlat: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("selectedlat", selectedlat)
        editor.apply()
    }


    fun getselectedLon(): String? {
        return getSharedPreferences().getString("selectedlon", "")
    }

    fun setselectedLon(selectedlon: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("selectedlon", selectedlon)
        editor.apply()
    }



    fun getselectedAddress(): String? {
        return getSharedPreferences().getString("selectedaddress", "")
    }

    fun setselectedAddress(selectedaddress: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("selectedaddress", selectedaddress)
        editor.apply()
    }


    fun getSelectedPickupdate(): String? {
        return getSharedPreferences().getString("pickupdate", "")
    }

    fun setSelectedPickupdate(pickupdate: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("pickupdate", pickupdate)
        editor.apply()
    }


    fun getSelectedPickuptime(): String? {
        return getSharedPreferences().getString("pickuptime", "")
    }

    fun setSelectedPickuptime(pickuptime: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("pickuptime", pickuptime)
        editor.apply()
    }




    fun getSelectedDeliverydate(): String? {
        return getSharedPreferences().getString("deliverydate", "")
    }

    fun setSelectedDeliverydate(deliverydate: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("deliverydate", deliverydate)
        editor.apply()
    }


    fun getSelectedDeliverytime(): String? {
        return getSharedPreferences().getString("deliverytime", "")
    }

    fun setSelectedDeliverytime(deliverytime: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString("deliverytime", deliverytime)
        editor.apply()
    }






    fun getName(): String? {
        return getSharedPreferences().getString(PREF_USER_FIRST_NAME, "")
    }

    fun setName(firstName: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_USER_FIRST_NAME, firstName)
        editor.apply()
    }

    fun getEmail(): String? {
        return getSharedPreferences().getString(PREF_USER_EMAIL, "")
    }

    fun setEmail(email: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_USER_EMAIL, email)
        editor.apply()
    }


    fun getPassword(): String? {
        return getSharedPreferences().getString(PREF_PASSWORD, "")
    }

    fun setPassword(password: String?) {
        val editor = getSharedPreferences().edit()
        editor.putString(PREF_PASSWORD, password)
        editor.apply()
    }


    fun clearPref() {
        val editor = getSharedPreferences().edit()
        editor.clear()
        editor.apply()
    }


    // Get Login State
    val isLoggedIn: Boolean
        get() = getSharedPreferences().getBoolean(IS_LOGIN, false)



    fun logoutUser() {
        // Clearing all data from Shared Preferences
        editor?.clear()
        editor?.commit()

    }



    fun createLoginSession(username: String?, password: String?) {
        editor?.putBoolean(IS_LOGIN, true)
        editor?.putString(KEY_username, username)
        editor?.putString(KEY_password, password)
        editor?.commit()
    }

}
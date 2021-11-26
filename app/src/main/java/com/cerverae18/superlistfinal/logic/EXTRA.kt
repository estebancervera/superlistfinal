package com.cerverae18.superlistfinal.logic

import android.app.Activity
import android.content.Context
import com.cerverae18.superlistfinal.R


/*
Class to add static values as KEYS for Intents
 */
class EXTRA {
    companion object EXTRA {
        const val EXTRA_LIST_ID = "superlist.list_id"
        const val THEME = "Theme"
        const val THEME_NAME = "ThemeName"


         fun getThemeColor(activity: Activity):Int{

            when (activity.getSharedPreferences(com.cerverae18.superlistfinal.logic.EXTRA.THEME, Context.MODE_PRIVATE).getInt(
                com.cerverae18.superlistfinal.logic.EXTRA.THEME_NAME, 1)){
                1 -> { return R.style.Theme_Superlistfinal }
                2 -> { return R.style.PinkTheme }
                3 -> { return R.style.PinkTheme }
                4 -> { return R.style.PinkTheme }
                5 -> { return R.style.PinkTheme }
            }
            return R.style.Theme_Superlistfinal
        }

    }
}


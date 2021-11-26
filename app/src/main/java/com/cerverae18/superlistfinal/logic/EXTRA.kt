package com.cerverae18.superlistfinal.logic

import android.app.Activity
import android.content.Context
import com.cerverae18.superlistfinal.R


/**
 * A Static Class for constants
 *
 * This class has a companion object inside to serve as "Static" so the constants associated with them can be accessed without an instance of the class
 * This class is used as an utility class to keep constants and methods that are used in multiple activities and/or in multiple different classes.
 * @property EXTRA_LIST_ID is a value used in an Intent as Key to send Data through and intent
 * @property THEME is a value used in the UserPreference to access the Theme from the user
 * @property THEME_NAME is a value used in the UserPreference to save and access the selected Theme from user.
 */
class EXTRA {
    companion object EXTRA {

        const val EXTRA_LIST_ID = "superlist.list_id"
        const val THEME = "Theme"
        const val THEME_NAME = "ThemeName"

        /**
         *  This method retrieves the Theme SharedPreferences and gets the selected Theme from the user
         *  @param activity is an activity that will call this method to receive the theme according to the users preference
         *  @return an Int representing the Id from the theme selected.
         */
         fun getThemeColor(activity: Activity):Int{

            when (activity.getSharedPreferences(THEME, Context.MODE_PRIVATE).getInt(
               THEME_NAME, 1)){
                1 -> { return R.style.Theme_Superlistfinal }
                2 -> { return R.style.PinkTheme }
                3 -> { return R.style.GrayTheme }
                4 -> { return R.style.RedTheme }
                5 -> { return R.style.PurpleTheme }
            }
            return R.style.Theme_Superlistfinal
        }

    }
}


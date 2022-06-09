package pt.amn.moveon.utils

import android.content.Context
import android.net.ConnectivityManager


class AppUtils {

    companion object {

        /**
         * Check an internet connection
         */
        fun isOnline(context: Context?): Boolean {

            if (context == null) return false

            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

    }

}
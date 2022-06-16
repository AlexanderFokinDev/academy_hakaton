package pt.amn.moveon.common

import android.content.Context
import android.net.ConnectivityManager
import pt.amn.moveon.BuildConfig
import android.util.Base64


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

        fun getGoogleApiKey(): String =
            String(Base64.decode(BuildConfig.MAPS_API_KEY_B64, Base64.DEFAULT))


    }

}
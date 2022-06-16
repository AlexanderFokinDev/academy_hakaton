package pt.amn.moveon.common

import android.content.Context
import android.widget.Toast
import timber.log.Timber

class LogNavigator {

    companion object {
        fun debugMessage(message: String) {
            Timber.d(message)
        }

        fun toastMessage(context: Context, idRes: Int) {
            Toast.makeText(context, idRes, Toast.LENGTH_LONG).show()
        }

        fun toastMessage(context: Context, message: String?) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}
package com.vaibhavjoshi.quizapp.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import com.google.gson.Gson

object DialogHelper {
    fun showPermissionDialog(activity : Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Permission required")
        builder.setMessage("Some permissions are needed to be allowed to use this app without any problems.")
        builder.setPositiveButton("Grant") { dialog, _ ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromParts("package", activity.packageName, null)
            intent.data = uri
            activity.startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}
object  AppHelper {
    //region CONVERT STRING INTO JSON

    inline fun <reified T> convertJsonToModel(jsonString: String): T? {
        return try {
            Gson().fromJson(jsonString, T::class.java)
        } catch (e: Exception) {
            Log.i("==>", "ERROR: Unable to parse JSON into model")
            null
        }
    }

    //endregion CONVERT STRING INTO JSON
}
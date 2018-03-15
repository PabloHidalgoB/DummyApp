package com.example.trabajo.dummyapp

import android.content.ContentValues
import android.os.Bundle
import android.os.Environment
import android.os.FileObserver
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.File
import java.io.IOException
import android.widget.Toast




class MainActivity : AppCompatActivity() {

    private val ACTIVATEADB = "setprop persist.sys.usb.config mtp,adb"
    private val MOUNTMTP = "adb shell svc usb setFunction mtp"


    private val mLocalDownloadDirectory = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}"


    val TAG = "DummyApp"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runAdbCommands(ACTIVATEADB)
        runAdbCommands(MOUNTMTP)


        /*var state = Environment.getExternalStorageState()

        var shared = Environment.MEDIA_MOUNTED

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Sd card has connected to PC in MSC mode

            Log.d(TAG, "==> TRUE")

        } else {

            Log.d(TAG, "==> FALSE")

        }*/

        // 1.- implementar un metodo que detecte los cambios en un archivo local
        // - Environent.DOWNLOAD_DIRECTORY
        // - val mFile = File("ruta")
        // - val mFileObserver = u otra clase que detecte los cambios

        val mFile = File(mLocalDownloadDirectory, "hola.txt")


        val fileObserver = object : FileObserver(mFile.canonicalPath) {
            override fun onEvent(event: Int, path: String?) {

                when (event) {
                    FileObserver.CREATE -> Log.d(ContentValues.TAG, "CREATE:")
                    FileObserver.DELETE -> Log.d(ContentValues.TAG, "DELETE:")
                    FileObserver.DELETE_SELF -> Log.d(ContentValues.TAG, "DELETE_SELF:")
                    FileObserver.MODIFY -> Log.d(ContentValues.TAG, "MODIFY:")
                    FileObserver.MOVED_FROM -> Log.d(ContentValues.TAG, "MOVED_FROM:")
                    FileObserver.MOVED_TO -> Log.d(ContentValues.TAG, "MOVED_TO:")
                    FileObserver.MOVE_SELF -> Log.d(ContentValues.TAG, "MOVE_SELF:")
                    else -> {
                    }
                }// just ignore
            }
        }

        fileObserver.startWatching()


        // 2.- Ejecutar un comando en un directorio
        // 3.- Ejecutar un Sh file en un directorio de la tablet


    }

    private fun runAdbCommands(command: String) {
        try {

            val p = Runtime.getRuntime().exec(arrayOf("su", "-c", command))
            p.waitFor()

        } catch (e: IOException) {
            e.printStackTrace()

        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Log.i(TAG, "==> runAdbCommands Done!")
    }
}

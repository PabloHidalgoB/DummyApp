package com.example.trabajo.dummyapp

import android.content.ContentValues
import android.os.Bundle
import android.os.Environment
import android.os.FileObserver
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.*


class MainActivity : AppCompatActivity() {

    private val ACTIVATEADB = "setprop persist.sys.usb.config ptp,adb"
    private val MOUNTMTP = "adb shell svc usb setFunction ptp"


    private val mLocalDownloadDirectory = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}"


    val TAG = "DummyApp"

    private var mFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runAdbCommands(ACTIVATEADB)
        runAdbCommands(MOUNTMTP)

        // 1.- implementar un metodo que detecte los cambios en un archivo local
        // - Environent.DOWNLOAD_DIRECTORY
        // - val mFile = File("ruta")
        // - val mFileObserver = u otra clase que detecte los cambios

        mFile = File(mLocalDownloadDirectory, "command.txt")


        val fileObserver = object : FileObserver(mFile!!.canonicalPath) {
            override fun onEvent(event: Int, path: String?) {
                //Log.d(TAG, event.toString())
                when (event) {
                    FileObserver.CLOSE_WRITE -> {
                        readCommand()
                    }
                    else -> {
                    }
                }// just ignore
            }
        }

        fileObserver.startWatching()


        // 2.- Ejecutar un comando en un directorio
        // 3.- Ejecutar un Sh file en un directorio de la tablet


    }

    private fun readCommand() {
        try {

            when {
                !mFile!!.exists() -> {
                    //LibraryUtilities.showToastMessage(this, getString(R.string.message_file_not_exist, "logpipe.txt"))
                }
                else -> {
                    //Get the text file
                    val mFileInputStream = FileInputStream(mFile)
                    val mDataInputStream = DataInputStream(mFileInputStream)
                    val mBufferReader = BufferedReader(InputStreamReader(mDataInputStream))
                    val mText = StringBuilder()

                    mBufferReader.forEachLine {
                        mText.append(it)
                        mText.append('\n')
                    }
                    mBufferReader.close()

                    if (!mText.toString().isEmpty()) {
                        Log.d(TAG, "==> Contenido: $mText")
                        //pocesslin(mtext)
                    }
                }
            }

        } catch (eio: IOException) {
            eio.printStackTrace()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getText (): String {

        //Read text from file
        val text = StringBuilder()

        try {
            val br = BufferedReader(FileReader(mFile))

            var line: String? = br.readLine()
            while (line != null) {
                text.append(line)
                line = br.readLine()
            }

            br.close()
        } catch (e: Exception) {
            System.err.println("Error: Target File Cannot Be Read")
        }

        return text.toString()

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

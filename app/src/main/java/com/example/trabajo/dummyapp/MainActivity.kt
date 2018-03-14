package com.example.trabajo.dummyapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private val ADBCOMMAND = "setprop persist.sys.usb.config mtp,adb"
    private val ENABLEADB = "setprop persist.service.adb.enable 1"
    private val ENABLEDEBUGGABLE = "setprop persist.service.debuggable 1"
    private val MOUNTSYS = "mount -o remount,rw /system"
    private val MOUNTSDC = "mount -o remount,rw /sdcard"
    private val REBOOT = "reboot"


    val TAG = "DummyApp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runAdbCommands(ADBCOMMAND)
        //runAdbCommands(ENABLEADB)
        //runAdbCommands(ENABLEDEBUGGABLE)
        //runAdbCommands(MOUNTSYS)
        //runAdbCommands(MOUNTSDC)
        runAdbCommands(REBOOT)


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

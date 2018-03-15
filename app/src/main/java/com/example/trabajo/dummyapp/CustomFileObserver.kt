package com.example.trabajo.dummyapp

import android.content.ContentValues.TAG
import android.os.FileObserver
import android.util.Log
import java.io.File

/**
 * Created by trabajo on 15-03-18.
 */
class CustomFileObserver(mFile: String): FileObserver(mFile, mask) {

    private var rootPath: String

    companion object {

        val mask = FileObserver.CREATE or
                FileObserver.DELETE or
                FileObserver.DELETE_SELF or
                FileObserver.MODIFY or
                FileObserver.MOVED_FROM or
                FileObserver.MOVED_TO or
                FileObserver.MOVE_SELF
    }

    init {
        var mFile = mFile

        if (!mFile.endsWith(File.separator)) {
            mFile += File.separator
        }
        rootPath = mFile
    }

    override fun onEvent(event: Int, path: String) {

        when (event) {
            FileObserver.CREATE -> Log.d(TAG, "CREATE:")
            FileObserver.DELETE -> Log.d(TAG, "DELETE:")
            FileObserver.DELETE_SELF -> Log.d(TAG, "DELETE_SELF:")
            FileObserver.MODIFY -> Log.d(TAG, "MODIFY:")
            FileObserver.MOVED_FROM -> Log.d(TAG, "MOVED_FROM:")
            FileObserver.MOVED_TO -> Log.d(TAG, "MOVED_TO:")
            FileObserver.MOVE_SELF -> Log.d(TAG, "MOVE_SELF:")
            else -> {
            }
        }// just ignore
    }

    fun close() {
        super.finalize()
    }
}
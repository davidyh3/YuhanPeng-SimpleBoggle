package com.example.simpleboggle.utils

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import java.io.*
import java.net.URL

class DictionaryManager {

    fun readFileFromAssetManager(filename: String, context: Context): HashSet<String>{
        try{
            val assetManager: AssetManager = context.assets
            val inputStream: InputStream = assetManager.open(filename) // replace "example.txt" with the name of your file
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val hashSet = hashSetOf<String>()
            bufferedReader.useLines { lines ->
                lines.forEach {
                    hashSet.add(it)
                }
            }
            return hashSet
        }catch (e: IOError){
            return hashSetOf<String>()
        }

    }

    fun getFile(filename: String): File{
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename)
    }


    fun createDictionaryFromURL(url: String): HashSet<String>{
        val downloadedUrl = URL(url)
        val bufferedReader = downloadedUrl.openStream().bufferedReader()
        var dic = HashSet<String>()
        bufferedReader.useLines { lines ->
            lines.forEach {
                dic.add(it)
            }
        }
        return dic
    }

}
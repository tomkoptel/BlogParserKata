package com.tom.kata.coroutines.parser

import android.app.Application
import android.content.Context
import com.ncornette.cache.OkCacheControl
import com.tom.kata.coroutines.parser.utils.CoroutinesProvider
import okhttp3.Cache
import okhttp3.OkHttpClient
import okreplay.OkReplayInterceptor
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Entry point of every Android applicaiton.
 */
class ParserApp : Application() {
    val component = Component(this)

    companion object {
        fun component(context: Context): Component {
            return (context.applicationContext as ParserApp).component
        }
    }

    /**
     * Simplified implementation of DI. The following component holds reference for the whole app lifecycle.
     */
    class Component(private val context: Context) {
        private val okClient: OkHttpClient by lazy {
            val cacheSize = 10 * 1024 * 1024 // 20 MiB
            val cacheDir = File(context.cacheDir, "parse-app").apply { mkdir() }
            val cache = Cache(cacheDir, cacheSize.toLong())

            val builder = OkHttpClient.Builder()
                .addInterceptor(OkReplayInterceptor())
            OkCacheControl.on(builder)
                .overrideServerCachePolicy(30, TimeUnit.MINUTES)
                .apply()
                .cache(cache)
                .build()
        }
        var coroutinesProvider = CoroutinesProvider.default()


        fun okHttpClient(): OkHttpClient = okClient
    }
}
package com.tom.kata.coroutines.parser

import com.tom.kata.coroutines.parser.data.ApiService
import com.tom.kata.coroutines.parser.domain.firstNthCharacter
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.OkHttpClient
import okreplay.*
import org.amshove.kluent.shouldEqual
import org.junit.Rule
import org.junit.Test
import java.io.File

/**
 * It was developed at early stage of the app to make sure network setup was 'ok'.
 */
class ApiServiceTest {
    private val okReplayInterceptor = OkReplayInterceptor()
    private val resourcesFolder = javaClass.classLoader.getResource("dummy.txt").run { File(this!!.path).parentFile }
    private val okReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(DefaultTapeRoot(resourcesFolder))
        .interceptor(okReplayInterceptor)
        .sslEnabled(true)
        .build()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(okReplayInterceptor)
        .build()

    @JvmField @Rule val recorderRule = RecorderRule(okReplayConfig)
    private val apiService = ApiService.Factory.create(okHttpClient)

    @Test
    @OkReplay(mode = TapeMode.READ_WRITE)
    fun get_every_first_symbol() = runBlocking {
        val response = apiService.blogPost().await()

        val character = response.charStream()!!.firstNthCharacter(0)
        character shouldEqual '<'
    }
}
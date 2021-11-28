package com.example.withmeapp

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.withmeapp.databinding.ActivityGlobalApplicationBinding
import com.example.withmeapp.databinding.ActivityLoginBinding
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "f8155b05179a3391368249285b441634")
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    fun getGlobalApplicationContext(): GlobalApplication {
        checkNotNull(instance) { "this application does not inherit com.kakao.GlobalApplication" }
        return instance!!
    }

    companion object {
        var instance: GlobalApplication? = null
    }
}

package com.littlegnal.singleenginemultifragment

import android.content.Context
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterFragmentManager private constructor(){

    private object Holder {
        val instance = FlutterFragmentManager()
    }

    companion object {
        private val instance: FlutterFragmentManager by lazy { Holder.instance }

        fun get() = instance
    }

    private var flutterFragment: FlutterFragment? = null

    fun getFlutterFragment(context: Context): FlutterFragment {
        if (flutterFragment == null) {
            if (FlutterEngineCache.getInstance().get("cache_engine") == null) {
                val flutterEngine = FlutterEngine(context.applicationContext)
                flutterEngine.navigationChannel.setInitialRoute("/")
                flutterEngine.dartExecutor
                    .executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
                FlutterEngineCache.getInstance().put("cache_engine", flutterEngine)
            }

            flutterFragment = FlutterFragment
                .withCachedEngine("cache_engine")
                .transparencyMode(FlutterView.TransparencyMode.transparent)
                .renderMode(FlutterView.RenderMode.texture)
                .build()
        }

        return flutterFragment!!
    }
}
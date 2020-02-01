package com.littlegnal.singleenginemultifragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : AppCompatActivity() {

    private val TAG_FLUTTER_FRAGMENT = "flutter_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flutterEngine = FlutterEngine(applicationContext)
        flutterEngine.navigationChannel.setInitialRoute("/")
        flutterEngine.dartExecutor
            .executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put("cache_engine", flutterEngine)

        // Add a new FlutterFragment
        findViewById<AppCompatButton>(R.id.btnAddFragment).setOnClickListener {
            val flutterFragment: FlutterFragment = FlutterFragment
                .withCachedEngine("cache_engine")
                .transparencyMode(FlutterView.TransparencyMode.transparent)
                .renderMode(FlutterView.RenderMode.texture)
                .shouldAttachEngineToActivity(false)
                .build()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.flFlutterFragment,
                    flutterFragment as Fragment,
                    null
                )
                .addToBackStack(null)
                .commit()
        }

        // Pop a FlutterFragment
        findViewById<AppCompatButton>(R.id.btnPopFragment).setOnClickListener {
            onBackPressed()
        }

        var flutterFragment: FlutterFragment? = supportFragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as? FlutterFragment

        if (flutterFragment == null) {
            flutterFragment = FlutterFragment
                .withCachedEngine("cache_engine")
                .transparencyMode(FlutterView.TransparencyMode.transparent)
                .renderMode(FlutterView.RenderMode.texture)
                .shouldAttachEngineToActivity(false)
                .build()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.flFlutterFragment,
                    flutterFragment as Fragment,
                    TAG_FLUTTER_FRAGMENT
                )
                .commit()
        }
    }
}

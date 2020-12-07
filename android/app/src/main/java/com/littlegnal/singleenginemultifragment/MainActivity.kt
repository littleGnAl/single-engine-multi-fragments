package com.littlegnal.singleenginemultifragment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import io.flutter.Log
import io.flutter.embedding.android.*

class FlutterFragmentBuilder(subclass: Class<out FlutterFragment>, engineId: String) :
    FlutterFragment.CachedEngineFragmentBuilder(subclass, engineId) {
}

class CustomXFlutterFragment : XFlutterFragment() {}

class MainActivity : AppCompatActivity() {

    private val TAG_FLUTTER_FRAGMENT = "flutter_fragment"

    private var flutterFragment: FlutterFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.setLogLevel(android.util.Log.VERBOSE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start a new FlutterFragment which host by MainActivity
        findViewById<AppCompatButton>(R.id.btnAddActivity).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        // Finish MainActivity
        findViewById<AppCompatButton>(R.id.btnFinishActivity).setOnClickListener {
            finish()
        }

        flutterFragment = supportFragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as? FlutterFragment



        if (flutterFragment == null) {
//            val bundle = Bundle().apply {
//
//                putString("cached_engine_id", "cache_engine")
//                putString("flutterview_transparency_mode", TransparencyMode.transparent.name)
//                putString("flutterview_render_mode", RenderMode.texture.name)
//
//            }
//            val ff = CustomXFlutterFragment().apply { arguments = bundle }
            val ff: CopiedFlutterFragment = CopiedFlutterFragment
                .withCachedEngine("cache_engine")
                .transparencyMode(TransparencyMode.opaque)
                .renderMode(RenderMode.texture)
                .build()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.flFlutterFragment,
                    ff as Fragment,
                    TAG_FLUTTER_FRAGMENT
                )
                .commit()
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterFragment?.onPostResume()
    }

    override fun onNewIntent(intent: Intent) {
        flutterFragment?.onNewIntent(intent)
        super.onNewIntent(intent)
    }

    override fun onBackPressed() {
        flutterFragment?.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        flutterFragment?.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onUserLeaveHint() {
        flutterFragment?.onUserLeaveHint()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        flutterFragment?.onTrimMemory(level)
    }
}

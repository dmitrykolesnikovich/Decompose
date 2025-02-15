package com.arkivanov.sample.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.android.DefaultViewContext
import com.arkivanov.decompose.extensions.android.child
import com.arkivanov.essenty.lifecycle.essentyLifecycle
import com.arkivanov.sample.shared.dynamicfeatures.dynamicfeature.DefaultFeatureInstaller
import com.arkivanov.sample.shared.root.Root
import com.arkivanov.sample.shared.root.RootComponent
import com.arkivanov.sample.shared.root.RootContent
import com.arkivanov.sample.shared.root.RootView

class MainActivity : AppCompatActivity() {

    private val mode = Mode.COMPOSE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root =
            RootComponent(
                componentContext = defaultComponentContext(),
                featureInstaller = DefaultFeatureInstaller(context = this),
            )

        when (mode) {
            Mode.COMPOSE -> drawViaCompose(root)
            Mode.VIEWS -> drawViaViews(root)
        }.let {}
    }

    private fun drawViaCompose(root: Root) {
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RootContent(root = root, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun drawViaViews(root: Root) {
        setContentView(R.layout.main_activity)

        val viewContext =
            DefaultViewContext(
                parent = findViewById(R.id.content),
                lifecycle = essentyLifecycle(),
            )

        viewContext.apply {
            child(parent) {
                RootView(root)
            }
        }
    }

    private enum class Mode {
        COMPOSE, VIEWS
    }
}

package net.accelf.contral.android

import net.accelf.contral.core.ui.Main
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import net.accelf.contral.android.config.AndroidConfigurationProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Main("Android", AndroidConfigurationProvider(LocalContext.current))
        }
    }
}

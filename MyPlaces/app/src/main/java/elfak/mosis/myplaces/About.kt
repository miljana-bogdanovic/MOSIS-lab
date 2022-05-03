package elfak.mosis.myplaces

import android.app.Activity
import android.os.Bundle
import android.widget.Button

class About:Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about)

        findViewById<Button>(R.id.about_ok).apply {
            setOnClickListener { finish() }
        }
    }
}
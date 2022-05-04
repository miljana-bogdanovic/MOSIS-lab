package elfak.mosis.myplace

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button

class About: Activity(){
    override fun onCreate(savedInStanceState : Bundle?){
        super.onCreate(savedInStanceState)
        setContentView(R.layout.about)
        val ok: Button= findViewById<Button>(R.id.about_ok)
        ok.setOnClickListener { finish() }
    }
}
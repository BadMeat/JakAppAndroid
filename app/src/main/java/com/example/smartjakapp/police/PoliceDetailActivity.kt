package com.example.smartjakapp.police

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.smartjakapp.MapBoxActivity
import com.example.smartjakapp.R
import com.example.smartjakapp.model.police.Data
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.intentFor

class PoliceDetailActivity : AppCompatActivity() {

    private var data: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        data = intent?.extras?.get("all") as Data
        titles.text = data?.name
        phone.text = " - "
        address.text = data?.address

        initializeAnimation()

        route.setOnClickListener {
            startActivity(
                intentFor<MapBoxActivity>(
                    "lat" to data?.lat,
                    "lng" to data?.lng,
                    "name" to data?.name
                )
            )
        }
    }

    private fun initializeAnimation() {
        val imageIn = AnimationUtils.loadAnimation(baseContext, R.anim.image_fade_in)
        val titleCome = AnimationUtils.loadAnimation(baseContext, R.anim.title_come)
        val detailCome = AnimationUtils.loadAnimation(baseContext, R.anim.detail_come)

        logo.animation = imageIn
        titles.animation = titleCome
        phone.animation = detailCome
        address.animation = detailCome


        phone_text.animation = detailCome
        address_text.animation = detailCome
        route.animation = imageIn
    }
}

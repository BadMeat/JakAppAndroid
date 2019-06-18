package com.example.smartjakapp.hospital

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.smartjakapp.MapBoxActivity
import com.example.smartjakapp.R
import com.example.smartjakapp.model.hospital.Feature
import com.example.smartjakapp.setNullData
import kotlinx.android.synthetic.main.activity_detail.address
import kotlinx.android.synthetic.main.activity_detail.address_text
import kotlinx.android.synthetic.main.activity_detail.logo
import kotlinx.android.synthetic.main.activity_detail.phone
import kotlinx.android.synthetic.main.activity_detail.phone_text
import kotlinx.android.synthetic.main.activity_detail.route
import kotlinx.android.synthetic.main.activity_detail.titles
import kotlinx.android.synthetic.main.activity_hospital.*
import org.jetbrains.anko.intentFor

class HospitalDetailActivity : AppCompatActivity() {

    private var data: Feature? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)
        initialize()
    }

    private fun initialize() {
        val kosong = resources.getString(R.string.kosong)
        data = intent?.extras?.get("all") as Feature
        titles.text = data?.properties?.namaRsu
        phone.text = setNullData(data?.properties?.telepon.toString(), kosong)
        address.text = setNullData(data?.properties?.location?.alamat, kosong)
        website.text = setNullData(data?.properties?.website.toString(), kosong)
        email.text = setNullData(data?.properties?.email.toString(), kosong)

        initializeAnimation()

        route.setOnClickListener {
            startActivity(
                intentFor<MapBoxActivity>(
                    "lat" to data?.properties?.location?.latitude,
                    "lng" to data?.properties?.location?.longitude,
                    "name" to data?.properties?.namaRsu
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
        website.animation = detailCome
        email.animation = detailCome

        phone_text.animation = detailCome
        address_text.animation = detailCome
        website_text.animation = detailCome
        email_text.animation = detailCome

        route.animation = imageIn
    }
}

package com.example.smartjakapp.satpolpp

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.smartjakapp.MapBoxActivity
import com.example.smartjakapp.R
import com.example.smartjakapp.model.satpolpp.Data
import com.example.smartjakapp.setNullData
import kotlinx.android.synthetic.main.activity_detail.address
import kotlinx.android.synthetic.main.activity_detail.address_text
import kotlinx.android.synthetic.main.activity_detail.logo
import kotlinx.android.synthetic.main.activity_detail.phone
import kotlinx.android.synthetic.main.activity_detail.phone_text
import kotlinx.android.synthetic.main.activity_detail.route
import kotlinx.android.synthetic.main.activity_detail.titles
import kotlinx.android.synthetic.main.activity_satpolpp.*
import org.jetbrains.anko.intentFor

class SatpolppDetailActivity : AppCompatActivity() {

    private var data: Data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satpolpp)
        initialize()
    }


    private fun initialize() {
        val kosong = resources.getString(R.string.kosong)
        data = intent?.extras?.get("all") as Data
        titles.text = data?.nama
        phone.text = setNullData(data?.phone, kosong)
        address.text = setNullData(data?.alamat, kosong)
        position.text = setNullData(data?.jabatan, kosong)
        initializeAnimation()

        route.setOnClickListener {
            startActivity(
                intentFor<MapBoxActivity>(
                    "lat" to data?.lat,
                    "lng" to data?.lng,
                    "name" to data?.nama
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
        position.animation = detailCome

        phone_text.animation = detailCome
        address_text.animation = detailCome
        position_text.animation = detailCome
        route.animation = imageIn
    }
}

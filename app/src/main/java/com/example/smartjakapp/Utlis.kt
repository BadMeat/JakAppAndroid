package com.example.smartjakapp

import android.view.View
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import com.airbnb.lottie.LottieAnimationView

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun setNullData(data: String?, kosong: String): String? {
    return if (data != null && data != "null") {
        data
    } else {
        kosong
    }
}

fun checkExistDb(
    favoritedId: List<Int>,
    id: Int,
    favorite: ImageView,
    fav: (Any) -> Unit,
    unit: Any,
    animationParam: LottieAnimationView
) {

    var isFavorited = false

    for (i in 0 until favoritedId.size) {
        if (id == favoritedId[i]) {
            favorite.setImageResource(R.drawable.favorited)
            isFavorited = true
            break
        }
    }

    favorite.setOnClickListener {
        isFavorited = !isFavorited
        if (isFavorited) {
            favorite.setImageResource(R.drawable.favorited)
            animationParam.visible()
            animationParam.playAnimation()
            animationParam.addAnimatorUpdateListener {
                it.doOnEnd {
                    animationParam.invisible()
                }
            }
        } else {
            favorite.setImageResource(R.drawable.favorite)
        }
        fav(unit)
    }
}
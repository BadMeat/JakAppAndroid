package com.example.smartjakapp.police

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.example.smartjakapp.R
import org.jetbrains.anko.*

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)
            relativeLayout {
                backgroundColor = resources.getColor(R.color.skyblue, null)
                /**
                 * Background Image
                 */
                imageView {
                    setImageResource(R.drawable.police)
                }.lparams(500, 500) {
                    alignParentRight()
                    alignParentBottom()
                }

                /**
                 * Name
                 */
                textView {
                    id = R.id.jeneng
                    backgroundColor = Color.RED
                }.lparams(wrapContent, wrapContent) {
                    alignParentBottom()
                }

                /**
                 * Address
                 */
                textView {
                    id = R.id.alamat
                    backgroundColor = Color.GREEN
                }.lparams(wrapContent, wrapContent) {
                    below(R.id.jeneng)
                }
            }.lparams(wrapContent, wrapContent) {
                margin = 20
            }
        }
    }
}
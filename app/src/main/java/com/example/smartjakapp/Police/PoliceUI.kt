package com.example.smartjakapp.Police

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
        relativeLayout {
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
                    id = R.id.address
                    backgroundColor = Color.GREEN
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }

                /**
                 * Address
                 */
                textView {
                    id = R.id.name
                    backgroundColor = Color.RED
                }.lparams(matchParent, wrapContent) {
                    above(R.id.address)
                }
            }.lparams(matchParent, matchParent) {
                margin = 20
            }
        }
    }
}
package com.example.smartjakapp.Police

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.smartjakapp.R
import org.jetbrains.anko.*

/**
 * Created by Bencoleng on 21/05/2019.
 */
class PoliceUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(matchParent, wrapContent)
            orientation = LinearLayout.VERTICAL
            textView {
                id = R.id.name
            }.lparams(matchParent, wrapContent)
            textView {
                id = R.id.address
            }.lparams(matchParent, wrapContent)
        }
    }
}
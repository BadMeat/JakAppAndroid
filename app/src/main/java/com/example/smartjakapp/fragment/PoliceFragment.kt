package com.example.smartjakapp.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartjakapp.R
import com.example.smartjakapp.model.Police
import com.example.smartjakapp.presenter.PolicePresenter
import com.example.smartjakapp.view.PoliceView
import org.jetbrains.anko.*
import retrofit2.Response

class PoliceFragment : Fragment(), AnkoComponent<ViewGroup>, PoliceView.MainView {

    override fun getData(response: Response<List<Police>>) {
        Log.d("Response", "${response.body()}")
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            textView {
                text = resources.getString(R.string.police)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(context!!, container!!, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        val ab = PolicePresenter(this)
        ab.loadData()
    }
}
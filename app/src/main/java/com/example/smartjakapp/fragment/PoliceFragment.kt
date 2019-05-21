package com.example.smartjakapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.Police.PoliceAdapter
import com.example.smartjakapp.model.Data
import com.example.smartjakapp.presenter.PolicePresenter
import com.example.smartjakapp.view.PoliceView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class PoliceFragment : Fragment(), AnkoComponent<ViewGroup>, PoliceView.MainView {

    private var data: MutableList<Data> = mutableListOf()
    private lateinit var adapter: PoliceAdapter
    private lateinit var recycler: RecyclerView

    override fun getData(response: List<Data>?) {
        if (response != null) {
            data.addAll(response)
            adapter.notifyDataSetChanged()
        }
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        linearLayout {
            lparams(matchParent, matchParent)
            recycler = recyclerView {
                layoutManager = LinearLayoutManager(context)
            }.lparams(matchParent, matchParent)
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
        adapter = PoliceAdapter(data)
        recycler.adapter = adapter
    }
}
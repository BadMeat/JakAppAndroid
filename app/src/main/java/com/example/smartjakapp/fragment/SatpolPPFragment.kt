package com.example.smartjakapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.model.satpolpp.Data
import com.example.smartjakapp.satpolpp.SatpolppAdapter
import com.example.smartjakapp.satpolpp.SatpolppPresenter
import com.example.smartjakapp.satpolpp.SatpolppView
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class SatpolPPFragment : Fragment(), AnkoComponent<ViewGroup>, SatpolppView.MainView {

    private var e: MutableList<Data> = mutableListOf()
    private lateinit var adapter: SatpolppAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var presenter: SatpolppPresenter

    override fun getData(data: List<Data>?) {
        if (data != null) {
            e.addAll(data)
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
        presenter = SatpolppPresenter(this)
        presenter.loadData()
        adapter = SatpolppAdapter(e)
        recycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
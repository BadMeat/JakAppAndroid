package com.example.smartjakapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.hospital.HospitalAdapter
import com.example.smartjakapp.hospital.HospitalPresenter
import com.example.smartjakapp.hospital.HospitalView
import com.example.smartjakapp.model.hospital.Feature
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class HospitalFragment : Fragment(), AnkoComponent<ViewGroup>, HospitalView.MainView {

    private var data: MutableList<Feature> = mutableListOf()
    private lateinit var adapter: HospitalAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var presenter: HospitalPresenter

    override fun getData(e: List<Feature>?) {
        if (e != null) {
            data.addAll(e)
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
        init()
    }

    private fun init() {
        presenter = HospitalPresenter(this)
        presenter.loadData()
        adapter = HospitalAdapter(data)
        recycler.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
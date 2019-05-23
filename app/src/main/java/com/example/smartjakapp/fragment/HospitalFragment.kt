package com.example.smartjakapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.hospital.HospitalAdapter
import com.example.smartjakapp.hospital.HospitalPresenter
import com.example.smartjakapp.hospital.HospitalView
import com.example.smartjakapp.invisible
import com.example.smartjakapp.model.hospital.Feature
import com.example.smartjakapp.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class HospitalFragment : Fragment(), AnkoComponent<ViewGroup>, HospitalView.MainView, SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private var data: MutableList<Feature> = mutableListOf()
    private lateinit var adapter: HospitalAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var presenter: HospitalPresenter
    private lateinit var progressBar: ProgressBar

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val myMenu = menu?.findItem(R.id.search_action)
        searchView = myMenu?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(this)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }

    override fun getData(e: List<Feature>?) {
        if (e != null) {
            data.addAll(e)
            adapter.notifyDataSetChanged()
        }
    }

    override fun loadingStart() {
        progressBar.visible()
    }

    override fun loadingEnd() {
        progressBar.invisible()
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)
            recycler = recyclerView {
                layoutManager = LinearLayoutManager(context)
            }.lparams(matchParent, matchParent)
            progressBar = progressBar {

            }.lparams(wrapContent, wrapContent) {
                centerHorizontally()
                centerVertically()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
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
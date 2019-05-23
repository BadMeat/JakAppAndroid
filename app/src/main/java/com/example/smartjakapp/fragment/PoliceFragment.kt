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
import com.example.smartjakapp.DetailActivity
import com.example.smartjakapp.R
import com.example.smartjakapp.invisible
import com.example.smartjakapp.model.police.Data
import com.example.smartjakapp.police.PoliceAdapter
import com.example.smartjakapp.police.PolicePresenter
import com.example.smartjakapp.police.PoliceView
import com.example.smartjakapp.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.intentFor

class PoliceFragment : Fragment(), AnkoComponent<ViewGroup>, PoliceView.MainView, SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val myMenu = menu?.findItem(R.id.search_action)
        searchView = myMenu?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(this)
        super.onPrepareOptionsMenu(menu)
    }

    private var data: MutableList<Data> = mutableListOf()
    private lateinit var adapter: PoliceAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun getData(response: List<Data>?) {
        if (response != null) {
            data.addAll(response)
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
        initialize()
    }

    private fun initialize() {
        val ab = PolicePresenter(this)
        ab.loadData()
        adapter = PoliceAdapter(data) {
            startActivity(intentFor<DetailActivity>())
        }
        recycler.adapter = adapter
    }
}
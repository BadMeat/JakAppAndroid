package com.example.smartjakapp.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.MapBoxActivity
import com.example.smartjakapp.R
import com.example.smartjakapp.invisible
import com.example.smartjakapp.model.satpolpp.Data
import com.example.smartjakapp.satpolpp.SatpolppAdapter
import com.example.smartjakapp.satpolpp.SatpolppPresenter
import com.example.smartjakapp.satpolpp.SatpolppView
import com.example.smartjakapp.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.intentFor

class SatpolPPFragment : Fragment(), AnkoComponent<ViewGroup>, SatpolppView.MainView, SearchView.OnQueryTextListener {

    private var e: MutableList<Data> = mutableListOf()
    private lateinit var adapter: SatpolppAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var presenter: SatpolppPresenter
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    lateinit var imageLogo : ImageView
    private lateinit var title: TextView
    private lateinit var detail: TextView

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val myMenu = menu.findItem(R.id.search_action)
        searchView = myMenu?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(this)
        super.onPrepareOptionsMenu(menu)
    }

    override fun getData(data: List<Data>?) {
        if (data != null) {
            e.addAll(data)
            adapter.notifyDataSetChanged()
            startAnimation()
        }
    }

    override fun loadingStart() {
        progressBar.visible()
    }

    override fun loadingEnd() {
        progressBar.invisible()
    }

    override fun loadingError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        relativeLayout {
            lparams(matchParent, matchParent)

            /**
             * Header Image Dan Text
             */
            relativeLayout {
                id = R.id.place_holder
                imageLogo = imageView {
                    id = R.id.logo
                    setImageResource(R.drawable.satpolpp_logo)
                }.lparams(wrapContent, wrapContent)
                linearLayout {
                    weightSum = 3f
                    orientation = LinearLayout.VERTICAL
                    title = textView {
                        text = "POLRI"
                        textColor = Color.WHITE
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, 0) {
                        weight = 1f
                    }
                    detail = textView {
                        text = "Kepolisian Nasional di Indonesia, yang bertanggung jawab langsung di bawah Presiden"
                        textColor = Color.WHITE
                    }.lparams(matchParent, 0) {
                        weight = 2f
                    }
                }.lparams(matchParent, wrapContent) {
                    endOf(R.id.logo)
                    sameBottom(R.id.logo)
                    sameTop(R.id.logo)
                    marginStart = 25
                }
            }.lparams(matchParent, wrapContent)





            recycler = recyclerView {
                layoutManager = LinearLayoutManager(context)
                background = resources.getDrawable(R.drawable.gradient_bg, null)
            }.lparams(matchParent, matchParent) {
                below(R.id.place_holder)
                topMargin = 10
            }
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
        presenter = SatpolppPresenter(this)
        presenter.loadData()
        adapter = SatpolppAdapter(e) {
            startActivity(
                intentFor<MapBoxActivity>(
                    "lat" to it.lat,
                    "lng" to it.lng,
                    "name" to it.nama
                )
            )
        }
        recycler.adapter = adapter
        imageLogo.animation = AnimationUtils.loadAnimation(context,R.anim.image_fade_in)
        title.animation = AnimationUtils.loadAnimation(context, R.anim.title_come)
        detail.animation = AnimationUtils.loadAnimation(context, R.anim.detail_come)
    }

    private fun startAnimation() {
        recycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)
        recycler.scheduleLayoutAnimation()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
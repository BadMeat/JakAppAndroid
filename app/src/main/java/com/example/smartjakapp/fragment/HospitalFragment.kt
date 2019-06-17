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
import com.example.smartjakapp.hospital.HospitalAdapter
import com.example.smartjakapp.hospital.HospitalPresenter
import com.example.smartjakapp.hospital.HospitalView
import com.example.smartjakapp.invisible
import com.example.smartjakapp.model.hospital.Feature
import com.example.smartjakapp.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.intentFor

class HospitalFragment : Fragment(), AnkoComponent<ViewGroup>, HospitalView.MainView, SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private val data: MutableList<Feature> = mutableListOf()
    private val favoritedId: MutableList<Int> = mutableListOf()
    private lateinit var adapter: HospitalAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var presenter: HospitalPresenter
    private lateinit var progressBar: ProgressBar
    lateinit var imageLogo: ImageView

    private lateinit var title: TextView
    private lateinit var detail: TextView

    override fun onPrepareOptionsMenu(menu: Menu) {
        val myMenu = menu.findItem(R.id.search_action)
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
                    setImageResource(R.drawable.hospitallogo)
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
        init()
    }

    private fun init() {
        presenter = HospitalPresenter(this, context)
        presenter.loadData(favoritedId)
        adapter = HospitalAdapter(data, {
            startActivity(
                intentFor<MapBoxActivity>(
                    "lat" to it.properties.location.latitude,
                    "lng" to it.properties.location.longitude,
                    "name" to it.properties.namaRsu
                )
            )
        }, {
            it as Feature
            presenter.saveData(it)
        }, favoritedId)
        recycler.adapter = adapter
        imageLogo.animation = AnimationUtils.loadAnimation(context, R.anim.image_fade_in)
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
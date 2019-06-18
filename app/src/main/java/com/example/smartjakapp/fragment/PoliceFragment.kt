package com.example.smartjakapp.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartjakapp.R
import com.example.smartjakapp.invisible
import com.example.smartjakapp.model.police.Data
import com.example.smartjakapp.police.PoliceAdapter
import com.example.smartjakapp.police.PoliceDetailActivity
import com.example.smartjakapp.police.PolicePresenter
import com.example.smartjakapp.police.PoliceView
import com.example.smartjakapp.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.intentFor
import java.lang.ref.WeakReference

class PoliceFragment : Fragment(), AnkoComponent<ViewGroup>, PoliceView.MainView, SearchView.OnQueryTextListener {
    private var data: MutableList<Data> = mutableListOf()
    private var favorited: MutableList<Int> = mutableListOf()
    private lateinit var adapter: PoliceAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var searchView: SearchView
    private lateinit var imageLogo: ImageView
    private lateinit var title: TextView
    private lateinit var detail: TextView
    private lateinit var presenter: PolicePresenter

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


    override fun getData(response: List<Data>?) {
        if (response != null) {
            data.addAll(response)
            adapter.notifyDataSetChanged()
            recycler.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down)
            recycler.scheduleLayoutAnimation()
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
                    setImageResource(R.drawable.police_logo)
                }.lparams(wrapContent, wrapContent)
                linearLayout {
                    weightSum = 3f
                    orientation = LinearLayout.VERTICAL
                    title = textView {
                        text = resources.getString(R.string.police)
                        textColor = Color.WHITE
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, 0) {
                        weight = 1f
                    }
                    detail = textView {
                        text = resources.getString(R.string.police_quote)
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
        val weak = WeakReference(context)
        presenter = PolicePresenter(this, weak)
        presenter.loadData(favorited)
        adapter = PoliceAdapter(data, {
            startActivity(
                intentFor<PoliceDetailActivity>(
                    "lat" to it.lat,
                    "lng" to it.lng,
                    "name" to it.name,
                    "all" to it
                ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            )
        }, {
            it as Data
            presenter.saveData(it)
        }, favorited)
        recycler.adapter = adapter
        imageLogo.animation = AnimationUtils.loadAnimation(context, R.anim.image_fade_in)
        title.animation = AnimationUtils.loadAnimation(context, R.anim.title_come)
        detail.animation = AnimationUtils.loadAnimation(context, R.anim.detail_come)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::presenter.isInitialized) {
            presenter.onDestroy()
        }
    }
}

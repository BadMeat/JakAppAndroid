package com.example.smartjakapp.satpolpp

import android.content.Context
import android.widget.Toast
import com.example.smartjakapp.db.Favorite
import com.example.smartjakapp.db.database
import com.example.smartjakapp.model.satpolpp.Data
import com.example.smartjakapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.lang.ref.WeakReference

/**
 * Created by Bencoleng on 21/05/2019.
 */
class SatpolppPresenter(
    private val mainView: SatpolppView.MainView,
    private val contexts: WeakReference<Context?>
) : SatpolppView.PresenterView {


    private var disposable: Disposable? = null

    private fun addFavorite(data: Data) {
        val context = contexts.get()
        if (context != null) {
            context.database.use {
                insert(
                    Favorite.TABLE_FAVORITE,
                    Favorite.ID_ITEM to data.userid,
                    Favorite.NAME to data.nama,
                    Favorite.PHONE to data.phone,
                    Favorite.ADDRESS to data.alamat,
                    Favorite.LAT to data.lat,
                    Favorite.LNG to data.lng,
                    Favorite.TYPE to 2
                )
            }
            Toast.makeText(context, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectFavorite(favorited: MutableList<Int>) {
        val context = contexts.get()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            for (i: Favorite in favorite) {
                if (i.id_item != null) {
                    favorited.add(i.id_item)
                }
            }
        }
    }

    private fun selectFavoriteId(data: Data): Boolean {
        var isFavorited = false
        val context = contexts.get()
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(ID_ITEM = {id} AND TYPE_ = {type})",
                    "id" to data.userid,
                    "type" to 2
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isNullOrEmpty()) isFavorited = true
        }
        return isFavorited
    }

    private fun deleteFavorite(data: Data) {
        val context = contexts.get()
        if (context != null) {
            context.database.use {
                delete(
                    Favorite.TABLE_FAVORITE,
                    "(ID_ITEM = {id} AND TYPE_ = {type})",
                    "id" to data.userid,
                    "type" to 2
                )
            }
            Toast.makeText(context, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
        }
    }

    override fun saveData(data: Data) {
        if (selectFavoriteId(data)) {
            deleteFavorite(data)
        } else {
            addFavorite(data)
        }
    }

    override fun loadData(favorited: MutableList<Int>) {
        disposable = ApiClient.instance
            .getListSatpolpp()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.data
            }
            .doOnSubscribe {
                mainView.loadingStart()
            }
            .doFinally {
                mainView.loadingEnd()
            }
            .subscribe(
                { result -> mainView.getData(result) },
                { error -> mainView.loadingError(error.localizedMessage) }
            )
        selectFavorite(favorited)
    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}
package com.example.smartjakapp.police

import android.content.Context
import android.widget.Toast
import com.example.smartjakapp.db.Favorite
import com.example.smartjakapp.db.database
import com.example.smartjakapp.model.police.Data
import com.example.smartjakapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class PolicePresenter(
    private val mainView: PoliceView.MainView
) : PoliceView.PresenterView {
    override fun addFavorite(data: Data, context: Context?) {
        context?.database?.use {
            insert(
                Favorite.TABLE_FAVORITE,
                Favorite.ID_ITEM to data.placemarkId,
                Favorite.NAME to data.name,
                Favorite.PHONE to 62,
                Favorite.ADDRESS to data.address,
                Favorite.LAT to data.lat,
                Favorite.LNG to data.lng
            )
        }
        Toast.makeText(context, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
    }

    override fun selectFavorite(context: Context?, favorited: MutableList<Int>) {
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            for (i: Favorite in favorite) {
                favorited.add(i.id_item)
            }
        }
    }

    override fun selectFavoriteId(data: Data, context: Context?): Boolean {
        var isFavorited = false
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(ID_ITEM = {id})", "id" to data.placemarkId)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isNullOrEmpty()) isFavorited = true
        }
        return isFavorited
    }

    override fun deleteFavorite(data: Data, context: Context?) {
        context?.database?.use {
            delete(
                Favorite.TABLE_FAVORITE, "(ID_ITEM = {id})",
                "id" to data.placemarkId
            )
        }
        Toast.makeText(context, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
    }

    override fun saveData(data: Data, context: Context?) {
        if (selectFavoriteId(data, context)) {
            deleteFavorite(data, context)
        } else {
            addFavorite(data, context)
        }
    }

    private var disposable: Disposable? = null

    override fun loadData() {
        disposable = ApiClient.instance
            .getListPolice()
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
                { result ->
                    mainView.getData(result)
                },
                { error -> mainView.loadingError(error.localizedMessage) }
            )
    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}
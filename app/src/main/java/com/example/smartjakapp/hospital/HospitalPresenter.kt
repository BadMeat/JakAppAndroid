package com.example.smartjakapp.hospital

import android.content.Context
import android.widget.Toast
import com.example.smartjakapp.db.Favorite
import com.example.smartjakapp.db.database
import com.example.smartjakapp.model.hospital.Feature
import com.example.smartjakapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * Created by Bencoleng on 21/05/2019.
 */
class HospitalPresenter(
    private val mainView: HospitalView.MainView,
    private val context: Context?
) : HospitalView.PresenterView {

    private var disposable: Disposable? = null

    private fun addFavorite(data: Feature) {
        var phone = ""
        for (i: String in data.properties.telepon) {
            phone += "$i,"
        }
        context?.database?.use {
            insert(
                Favorite.TABLE_FAVORITE,
                Favorite.ID_ITEM to data.properties.id,
                Favorite.NAME to data.properties.namaRsu,
                Favorite.PHONE to phone,
                Favorite.ADDRESS to data.properties.location.alamat,
                Favorite.LAT to data.properties.location.latitude,
                Favorite.LNG to data.properties.location.longitude,
                Favorite.TYPE to 3
            )
        }
        Toast.makeText(context, "Berhasil ditambahkan ke favorite", Toast.LENGTH_SHORT).show()
    }

    private fun selectFavorite(favorited: MutableList<Int>) {
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE).whereArgs(
                "(TYPE_ = {type})",
                "type" to 3
            )
            val favorite = result.parseList(classParser<Favorite>())
            for (i: Favorite in favorite) {
                if (i.id_item != null) {
                    favorited.add(i.id_item)
                }
            }
        }
    }

    private fun selectFavoriteId(data: Feature): Boolean {
        var isFavorited = false
        context?.database?.use {
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs(
                    "(ID_ITEM = {id} AND TYPE_ = {type})",
                    "id" to data.properties.id,
                    "type" to 3
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isNullOrEmpty()) isFavorited = true
        }
        return isFavorited
    }

    private fun deleteFavorite(data: Feature) {
        context?.database?.use {
            delete(
                Favorite.TABLE_FAVORITE, "(ID_ITEM = {id})",
                "id" to data.properties.id
            )
        }
        Toast.makeText(context, "Berhasil dihapus dari favorite", Toast.LENGTH_SHORT).show()
    }

    override fun saveData(data: Feature) {
        if (selectFavoriteId(data)) {
            deleteFavorite(data)
        } else {
            addFavorite(data)
        }
    }

    override fun loadData(favorited: MutableList<Int>) {
        disposable = ApiClient.instance.getListHospital()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.features
            }.doOnSubscribe {
                mainView.loadingStart()
            }.doFinally {
                mainView.loadingEnd()
            }.subscribe(
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
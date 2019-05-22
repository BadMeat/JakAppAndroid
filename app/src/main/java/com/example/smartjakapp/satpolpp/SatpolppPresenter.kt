package com.example.smartjakapp.satpolpp

import android.util.Log
import com.example.smartjakapp.network.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Bencoleng on 21/05/2019.
 */
class SatpolppPresenter(
    private val mainView: SatpolppView.MainView
) : SatpolppView.PresenterView {

    private var disposable: Disposable? = null

    override fun loadData() {
        disposable = ApiClient.instance
            .getListSatpolpp()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.data
            }
            .subscribe(
                { result -> mainView.getData(result) },
                { error -> Log.e("Error", "Data Error $error") }
            )

    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}
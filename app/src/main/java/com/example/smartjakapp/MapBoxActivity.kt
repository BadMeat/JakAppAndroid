package com.example.smartjakapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
import kotlinx.android.synthetic.main.activity_map_box.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapBoxActivity : AppCompatActivity(), PermissionsListener, LocationEngineListener, OnMapReadyCallback,
    MapboxMap.OnMapClickListener {

    //1
    val REQUEST_CHECK_SETTINGS = 1
    var settingsClient: SettingsClient? = null

    //2
    lateinit var map: MapboxMap
    lateinit var permissionManager: PermissionsManager
    var originLocation: Location? = null

    var locationEngine: LocationEngine? = null
    var locationComponent: LocationComponent? = null

    var navigationMapRoute: NavigationMapRoute? = null
    var currentRoute: DirectionsRoute? = null

    private var destiLocation: Location? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(
            this,
            "pk.eyJ1IjoiYXJpZmtlYnVtZW4iLCJhIjoiY2p3MnpjYnd5MGRxczQ5cXI1YzloZ2x0MiJ9.XG0FrIa16ibCGqRT_ECfOQ"
        )
        setContentView(R.layout.activity_map_box)
        settingsClient = LocationServices.getSettingsClient(this)
        mapbox.onCreate(savedInstanceState)
        mapbox.getMapAsync(this)
        btnNavigate.isEnabled = false
        btnNavigate.setOnClickListener {
            val navigationLauncherOptions = NavigationLauncherOptions.builder() //1
                .directionsRoute(currentRoute) //2
                .shouldSimulateRoute(true) //3
                .build()

            NavigationLauncher.startNavigation(this, navigationLauncherOptions) //4
        }
        zoomIn.setOnClickListener {
            if (destiLocation != null) {
                setCameraPosition(destiLocation!!)
            }

        }
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        Toast.makeText(
            this,
            "This app needs location permission to be able to show your location on the map",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocation()
        } else {
            Toast.makeText(this, "User location was not granted", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onLocationChanged(location: Location?) {
        location?.run {
            originLocation = this
            setCameraPosition(this)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onConnected() {
        locationEngine?.requestLocationUpdates()
    }

    override fun onMapReady(mapboxMap: MapboxMap?) {
        //1
        map = mapboxMap ?: return
        //2
        val locationRequestBuilder = LocationSettingsRequest.Builder().addLocationRequest(
            LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        )
        //3
        val locationRequest = locationRequestBuilder?.build()

        settingsClient?.checkLocationSettings(locationRequest)?.run {
            addOnSuccessListener {
                enableLocation()
            }

            addOnFailureListener {
                val statusCode = (it as ApiException).statusCode

                if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    val resolvableException = it as? ResolvableApiException
                    resolvableException?.startResolutionForResult(this@MapBoxActivity, REQUEST_CHECK_SETTINGS)
                }
            }
        }
    }

    override fun onMapClick(point: LatLng) {
        if (map.markers.isNotEmpty()) {
            map.clear()
        }
        map.addMarker(MarkerOptions().position(point))
        map.addMarker(MarkerOptions().setTitle("I'm a marker :]").position(point))
        checkLocation()
        originLocation?.run {
            val startPoint = Point.fromLngLat(longitude, latitude)
            val endPoint = Point.fromLngLat(point.longitude, point.latitude)

            getRoute(startPoint, endPoint)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                enableLocation()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                finish()
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationEngine?.requestLocationUpdates()
            locationComponent?.onStart()
        }
        mapbox.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapbox.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapbox.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapbox.onStop()
        locationEngine?.removeLocationUpdates()
        locationComponent?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapbox.onDestroy()
        locationEngine?.deactivate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapbox.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            mapbox.onSaveInstanceState(outState)
        }
    }

    //1
    fun enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationComponent()
            initializeLocationEngine()
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
        map.addOnMapClickListener(this)
    }

    //2
    @SuppressWarnings("MissingPermission")
    fun initializeLocationEngine() {
        locationEngine = LocationEngineProvider(this).obtainBestLocationEngineAvailable()
        locationEngine?.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine?.activate()
        locationEngine?.addLocationEngineListener(this)

        val lastLocation = locationEngine?.lastLocation
        if (lastLocation != null) {
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else {
            locationEngine?.addLocationEngineListener(this)
        }
    }

    @SuppressWarnings("MissingPermission")
    fun initializeLocationComponent() {
        locationComponent = map.locationComponent
        locationComponent?.activateLocationComponent(this)
        locationComponent?.isLocationComponentEnabled = true
        locationComponent?.cameraMode = CameraMode.TRACKING
    }

    //3
    fun setCameraPosition(location: Location) {
        destiLocation = Location(location)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location.latitude,
                    location.longitude
                ), 30.0
            )
        )
    }

    fun getRoute(originPoint: Point, endPoint: Point) {
        btnNavigate.isEnabled = true
        NavigationRoute.builder(this) //1
            .accessToken(Mapbox.getAccessToken()!!) //2
            .origin(originPoint) //3
            .destination(endPoint) //4
            .build() //5
            .getRoute(object : Callback<DirectionsResponse> { //6
                override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                    Log.d("MapBoxActivity", t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<DirectionsResponse>,
                    response: Response<DirectionsResponse>
                ) {
                    if (navigationMapRoute != null) {
                        navigationMapRoute?.updateRouteVisibilityTo(false)
                    } else {
                        navigationMapRoute = NavigationMapRoute(null, mapbox, map)
                    }

                    currentRoute = response.body()?.routes()?.first()
                    if (currentRoute != null) {
                        navigationMapRoute?.addRoute(currentRoute)
                    }
                }
            })
    }

    @SuppressLint("MissingPermission")
    private fun checkLocation() {
        if (originLocation == null) {
            map.locationComponent.lastKnownLocation?.run {
                originLocation = this
            }
        }
    }
}

package maxmudov.farrux.mytaxi.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Point
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.attribution.attribution
import com.mapbox.maps.plugin.compass.compass
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.logo.logo
import com.mapbox.maps.plugin.scalebar.scalebar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import maxmudov.farrux.mytaxi.App
import maxmudov.farrux.mytaxi.R
import maxmudov.farrux.mytaxi.databinding.FragmentMapboxBinding
import maxmudov.farrux.mytaxi.domain.model.MyLocationModel
import maxmudov.farrux.mytaxi.ui.BaseFragment
import javax.inject.Inject


class MapboxFragment : BaseFragment<FragmentMapboxBinding>() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject()
    lateinit var viewModel: MapboxViewModel

    private var longtitute: Double = 0.0
    private var latitute: Double = 0.0
    private var controlzoom: Double = 20.0


    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentMapboxBinding {
        return FragmentMapboxBinding.inflate(inflater, container, false)
    }

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun created(view: View, savedInstanceState: Bundle?) {


        App.appComponent.inject(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())


        clickButtons()
        requestPermission()
        moveToCurrentLocation()
        checkGps()
        getLocationFromDb()


        when (requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.mapView.getMapboxMap()
                    .loadStyleUri(Style.TRAFFIC_DAY) {
                        enableUserLocation()
                    }
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.mapView.getMapboxMap().loadStyleUri(Style.TRAFFIC_NIGHT) {
                    enableUserLocation()
                }
            }
        }
        binding.mapView.attribution.updateSettings {
            enabled = false
        }
        binding.mapView.logo.updateSettings {
            enabled = false
        }
        binding.mapView.scalebar.updateSettings {
            enabled = false
        }
        binding.mapView.compass.updateSettings {
            enabled = false
        }

    }

    private fun getLocationFromDb() {
        lifecycleScope.launch {
            viewModel.getAllLocation().collectLatest {
                it.data?.let { location ->
                    if (location.isNotEmpty()) Log.d(
                        "My location",
                        "created: ${location.last().latitute} ${location.last().longtitute}"
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    fun requestPermission() {

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            Log.d("MainActivityTAG", ":${permissions} ")
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    enableUserLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    enableUserLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_BACKGROUND_LOCATION, false) -> {
                    enableUserLocation()
                }
                else -> {

                }
            }
        }
        if (!PermissionsManager.areLocationPermissionsGranted(requireContext())) {
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
            )
        }

    }

    private fun enableUserLocation() {
        binding.mapView.location.updateSettings {
            enabled = false
            pulsingEnabled = true
        }
    }

    private fun moveToCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: android.location.Location? ->
            location?.let { loc ->
                binding.mapView.getMapboxMap().flyTo(cameraOptions {
                    longtitute = loc.longitude
                    latitute = loc.latitude
                    createMapMarker(Point.fromLngLat(loc.longitude, loc.latitude))
                    center(Point.fromLngLat(loc.longitude, loc.latitude))
                    zoom(controlzoom)
                    bearing(180.0)
                    pitch(50.0)
                }, MapAnimationOptions.mapAnimationOptions {
                    duration(500)
                })

                lifecycleScope.launch {
                    viewModel.addLocation(
                        MyLocationModel(
                            System.currentTimeMillis().toInt(), loc.longitude, loc.latitude
                        )
                    )
                }

                getLocationFromDb()
            }

        }
    }

    private fun createMapMarker(point: Point) {

        val annotationApi = binding.mapView.annotations
        val pointAnnotationManager = annotationApi.createPointAnnotationManager()
        val pointAnnotationOptions: PointAnnotationOptions =
            PointAnnotationOptions().withPoint(point).withIconImage(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.yandex_car
                )!!.toBitmap()
            )
        pointAnnotationManager.create(pointAnnotationOptions)
    }

    @SuppressLint("ResourceAsColor")
    private fun clickButtons() {


        binding.myLocation.setOnClickListener {
            checkGps()
            controlzoom = 22.5
            moveToCurrentLocation()
        }

        binding.zoomin.setOnClickListener {
            checkGps()
            zoomInOut()
            if (controlzoom < 22.5) {
                controlzoom += 2.5
            }

        }
        binding.zoomout.setOnClickListener {
            checkGps()
            zoomInOut()
            if (controlzoom > 0.0) {
                controlzoom -= 2.5
            }
        }


        binding.free.setTextColor(Color.WHITE)
        binding.free.setOnClickListener {
            binding.free.setBackgroundResource(R.drawable.for_left)
            binding.busy.setBackgroundResource(R.drawable.for_right)
            binding.free.setTextColor(Color.WHITE)
            binding.busy.setTextColor(R.color.black)
        }

        binding.busy.setOnClickListener {
            binding.busy.setBackgroundResource(R.drawable.for_left_2)
            binding.free.setBackgroundResource(R.drawable.for_right_2)
            binding.busy.setTextColor(Color.WHITE)
            binding.free.setTextColor(R.color.black)
        }

    }

    private fun checkGps() {

        val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = false
        var network_enabled = false

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        } catch (ex: Exception) {

        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder(context).setMessage(R.string.gps_network_not_enabled)
                .setPositiveButton(
                    R.string.open_location_settings,
                    DialogInterface.OnClickListener { _, _ ->
                        requireContext()!!.startActivity(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        )
                    }).setIcon(R.drawable.bolt).setNegativeButton(R.string.Cancel, null).show()
        }
    }

    private fun zoomInOut() {
        binding.mapView.getMapboxMap().flyTo(cameraOptions {
            this.zoom(controlzoom).build()
        })
    }

}

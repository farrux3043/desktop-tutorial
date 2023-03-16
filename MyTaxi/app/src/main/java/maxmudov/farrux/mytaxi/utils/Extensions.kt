package uz.adkhamjon.taskmanagement.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import maxmudov.farrux.mytaxi.App.Companion.resources
import java.util.*


fun getString(@StringRes int: Int) = resources.getString(int)

fun getString(@StringRes int: Int, str: String) = resources.getString(int, str)

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Fragment.popBackStack() {
    findNavController().popBackStack()
}

fun Fragment.navigate(id: Int) {
    findNavController().navigate(id)
}

fun Fragment.navigateWithArgs(id: Int, bundle: Bundle) {
    findNavController().navigate(id, bundle)
}


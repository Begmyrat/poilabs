package com.example.poilabschallange.presentation.detail.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.poilabschallange.R
import com.example.poilabschallange.databinding.FragmentDetailBinding
import com.example.poilabschallange.utils.extension.isZero
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class DetailFragment : Fragment(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var mapFragment: SupportMapFragment
    lateinit var binding: FragmentDetailBinding

    private val countryName: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.COUNTRY_NAME)
    }
    private val capitalName: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.CAPITAL_NAME)
    }
    private val area: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.AREA)
    }
    private val population: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.POPULATION)
    }
    private val currency: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.CURRENCY)
    }
    private val flagUrl: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.FLAG_URL)
    }
    private val flagDesc: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.FLAG_DESC)
    }
    private val independent: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.INDEPENDENT)
    }
    private val phone: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.PHONE)
    }
    private val region: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.REGION)
    }
    private val lat: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.LAT)
    }
    private val long: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.LON)
    }
    private val borders: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.BORDERS)
    }
    private val timeZone: String? by lazy {
        requireActivity().intent.extras?.getString(DetailActivity.TIME_ZONE)
    }

    lateinit var tCountryName: TextView
    lateinit var tCapitalName: TextView
    lateinit var tPopulation: TextView
    lateinit var tArea: TextView
    lateinit var tCurrency: TextView
    lateinit var tFlagDesc: TextView
    lateinit var tIndependent: TextView
    lateinit var tPhone: TextView
    lateinit var tRegion: TextView
    lateinit var tBorders: TextView
    lateinit var tTimeZone: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        tCountryName = binding.root.findViewById(R.id.t_countryName)
        tCapitalName = binding.root.findViewById(R.id.t_capitalName)
        tPopulation = binding.root.findViewById(R.id.t_population)
        tArea = binding.root.findViewById(R.id.t_area)
        tCurrency = binding.root.findViewById(R.id.t_currency)
        tIndependent = binding.root.findViewById(R.id.t_independent)
        tPhone = binding.root.findViewById(R.id.t_phone)
        tRegion = binding.root.findViewById(R.id.t_region)
        tBorders = binding.root.findViewById(R.id.t_borders)
        tTimeZone = binding.root.findViewById(R.id.t_timeZone)
        tFlagDesc = binding.root.findViewById(R.id.t_flagDesc)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Glide.with(requireContext()).load(flagUrl).into(binding.root.findViewById(R.id.i_flag))
        tCountryName.text = countryName
        tCapitalName.text = capitalName
        tPopulation.text = population
        tArea.text = area
        tCurrency.text = currency
        tIndependent.text = independent
        tPhone.text = phone
        tRegion.text = region
        tBorders.text = borders
        tTimeZone.text = timeZone
        tFlagDesc.text = flagDesc
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // after map is ready, focus to the capital of the country with given coordinates
        mMap = googleMap
        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        val builder = LatLngBounds.Builder()
        val marker = MarkerOptions()
            .position(
                LatLng(
                    lat?.toDouble().isZero(),
                    long?.toDouble().isZero()
                )
            )
            .icon(bitmapDescriptorFromVector(requireContext(), R.drawable.location))
            .title(countryName)
            .snippet(capitalName)
        googleMap.addMarker(marker)
        builder.include(marker.position)
        mMap?.setMaxZoomPreference(5f)
        val bounds = builder.build()
        val padding = 170
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        googleMap.animateCamera(cameraUpdate)
    }

    // to get bitmap from svg files for using as a marker on map
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}
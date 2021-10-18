package com.platzi.conf2.view.ui.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.platzi.conf2.R
import com.platzi.conf2.model.Ubication

/**
 * A simple [Fragment] subclass.
 * Use the [UbicationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UbicationFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ubication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Llamamos al hijo que está acargo del todo el manejo del fragmento como un MapFragment
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        //Nos trae toda la data de google Maps a la aplicación
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        val ubication = Ubication()

        val zoom = 16f
        //Cramos un objeto LatLng para manejar coordenadas en el mapa
        val centerMap = LatLng(ubication.latitude, ubication.longitude)

        //Con esto centraos el mapa con un cierto nivel de zoom
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))

        val markerOptions = MarkerOptions()
        markerOptions.position(centerMap)
        markerOptions.title("PlatziConf 2019")

        //Obtenemos la imagen para el pin del mapa
        val bitmapDraw = context?.applicationContext?.let { ContextCompat.getDrawable(it, R.mipmap.logo_platzi)} as BitmapDrawable
        //escalamos la imagen a los amaños pasados (150, 150)
        val smallMarker = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 150, 150, false)
        //creamos un marcador compatible con google Maps usando la imagen escalada
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        //añadimos el marcador al mapa
        p0.addMarker(markerOptions)

        p0.setOnMarkerClickListener(this)

        p0.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.custom_map))

    }

    override fun onMarkerClick(p0: Marker): Boolean {
        findNavController().navigate(R.id.action_navUbicationFragment_to_ubicationDetailFragmentDialog)
        return true
    }


}
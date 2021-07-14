package ipvc.estg.cmtrabalho

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.cmtrabalho.api.EndPoints
import ipvc.estg.cmtrabalho.api.Marker
import ipvc.estg.cmtrabalho.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Double.toString
import java.util.Arrays.toString

const val TITULOA="TITULO"
const val DESCRICAOA="DESCRICAO"
const val IDA= "OOO"
const val IMAGEM="IMAGEM"
const val IDU="OOO"
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallBack: LocationCallback
    private lateinit var lastLocation: Location
    lateinit var loc : LatLng
    var id_utilizador: Any? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.login_p), Context.MODE_PRIVATE
        )
        if (sharedPref != null){
            id_utilizador = sharedPref.all[getString(R.string.id_utilizador)]
        }
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReportes()
        var position: LatLng

        call.enqueue(object : Callback<List<Marker>> {
            override fun onResponse(call: Call<List<Marker>>, response: Response<List<Marker>>) {
                if (response.isSuccessful){
                    for(Marker in response.body()!!){
                        position = LatLng(Marker.lat.toDouble(), Marker.lng.toDouble())
                        if(id_utilizador == Marker.idutilizador){
                            mMap.addMarker(MarkerOptions()
                                    .position(position).title( Marker.titulo )
                                    .snippet(Marker.descr + ";" + Marker.imagem + ";" + Marker.idutilizador + ";" + id_utilizador + ";" + Marker.id)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                        }else{
                            mMap.addMarker(MarkerOptions()
                                    .position(position).title(Marker.titulo)
                                    .snippet(Marker.descr + ";" + Marker.imagem + ";" + Marker.idutilizador + ";" + id_utilizador + ";" + Marker.id)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)))
                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<Marker>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, getString(R.string.erro), Toast.LENGTH_SHORT).show()
            }
        })

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0!!.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)

                Log.d("rafa", "latitude: " + loc.latitude.toString() + " - longitude: " + loc.longitude.toString())

            }
        }

        createLocationRequest()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.login_p), Context.MODE_PRIVATE
                )
                with(sharedPref.edit()){
                    putBoolean(getString(R.string.login_shared), false)
                    putString(getString(R.string.nome), "")
                    putInt(getString(R.string.id_utilizador), 0)
                    commit()
                }
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            } R.id.notas -> {
                var intent = Intent(this, AllNotes::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val viana = LatLng(41.693920154899715, -8.830251804481957)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viana, 10.0f))
       // mMap.addMarker(MarkerOptions().position(viana).title("Marker in viana"))
        mMap.setInfoWindowAdapter(WindowInfo(this))
        googleMap.setOnInfoWindowClickListener(this)
    }
    override fun onBackPressed() {

    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallBack, null)
    }
    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallBack)
    }

    public override fun onResume(){
        super.onResume()
        startLocationUpdates()
    }

    override fun onInfoWindowClick(p0: com.google.android.gms.maps.model.Marker) {
        val title = p0?.title?.split(";")?.toTypedArray()  //ID e tituo
        val snippet = p0?.snippet?.split(";")?.toTypedArray() //Divide o snippet

        if(id_utilizador.toString().equals( snippet?.get(2))){
            val intent = Intent(this ,Reportes::class.java).apply {
                Log.d("TAG", snippet?.get(4).toString())
                putExtra(IDA, snippet?.get(4)!!.toInt())
                putExtra(IDU, snippet?.get(2)!!.toInt())
                putExtra(TITULOA, title?.get(0))
                putExtra(DESCRICAOA, snippet?.get(0))
                putExtra(IMAGEM, snippet?.get(1))
            }
            startActivity(intent)
        }else{
            Toast.makeText(this, getString(R.string.visualizar_rep) , Toast.LENGTH_SHORT).show()
        }

    }
    fun add_rep(view: View) {
       val intent = Intent(this, addrep::class.java)
        intent.putExtra("UTL_ATUAL", id_utilizador.toString())
        intent.putExtra("LAT", lastLocation.latitude.toString())
        intent.putExtra("LONG", lastLocation.longitude.toString())
        startActivity(intent)
    }
    }

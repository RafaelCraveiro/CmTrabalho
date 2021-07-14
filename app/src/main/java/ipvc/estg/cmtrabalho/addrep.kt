package ipvc.estg.cmtrabalho

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import ipvc.estg.cmtrabalho.api.EndPoints
import ipvc.estg.cmtrabalho.api.Marker
import ipvc.estg.cmtrabalho.api.ServiceBuilder
import ipvc.estg.cmtrabalho.api.result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class addrep: AppCompatActivity() {

    var utl_atual = ""
    var lat = ""
    var long = ""
    var img_64 = ""

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addreporte)

        utl_atual = intent.getStringExtra("UTL_ATUAL").toString()
        lat = intent.getStringExtra("LAT").toString()
        long = intent.getStringExtra("LONG").toString()





    }


    fun addAnom_add(view: View) {

        val title = this.findViewById<EditText>(R.id.titulo_info_add)
        val desc = this.findViewById<EditText>(R.id.descricao_add)
        val imagem = this.findViewById<ImageView>(R.id.img_add)

        var intent = Intent(this, MapsActivity::class.java)

        if (title.text.isNullOrEmpty() || desc.text.isNullOrEmpty()) {

            if (title.text.isNullOrEmpty()) {
                title.error = getString(R.string.titulofalta)
            }
            if (desc.text.isNullOrEmpty()) {
                desc.error = getString(R.string.descfalta)
            }

        } else if(imagem.drawable == null) {
            Toast.makeText(this, R.string.imgfalta, Toast.LENGTH_SHORT).show()
        }else {
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.addRep(idutilizador = utl_atual.toInt(), descr = desc.text.toString(), lat = lat, lng=long, titulo = title.text.toString(),imagem = img_64)

            call.enqueue(object : Callback<Marker> {

                override fun onResponse(call: Call<Marker>, response: Response<Marker>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@addrep, R.string.reportSuccess, Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this@addrep,"ta quase2", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Marker>, t: Throwable) {
                    Log.d("TAG_", "err: " + t.message)
                }

            })
        }
    }


    fun voltarMapa_add(view: View) {
        var intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    fun escolherImg(view: View) {
        openGalleryForImage()
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var bitmap : Bitmap
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1){
            var imageView = this.findViewById<ImageView>(R.id.img_add)
            imageView.setImageURI(data?.data)
            bitmap = (imageView.drawable as BitmapDrawable).bitmap

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b: ByteArray = baos.toByteArray()
            val encodedImage: String = Base64.encodeToString(b, Base64.DEFAULT)
            img_64 = encodedImage


        }
    }

}
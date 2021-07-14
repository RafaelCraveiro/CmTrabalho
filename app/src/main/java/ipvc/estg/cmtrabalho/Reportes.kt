package ipvc.estg.cmtrabalho

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import ipvc.estg.cmtrabalho.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reportes : AppCompatActivity() {
    var idA: Int? = null;
    var idU: Int? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reportes)

        idA= intent.getIntExtra(IDA, 1)
        idU= intent.getIntExtra(IDU, 1)
        Toast.makeText(this, idA.toString(), Toast.LENGTH_SHORT).show()
        val titulo = intent.getStringExtra(TITULOA)
        val descricao = intent.getStringExtra(DESCRICAOA)
        val imagemA = intent.getStringExtra(IMAGEM)


        findViewById<EditText>(R.id.tituloAnom).setText(titulo)
        findViewById<EditText>(R.id.descricaoAnom).setText(descricao)
        val image = findViewById<ImageView>(R.id.imagemAnom)


        Picasso.get().load(imagemA).into(image);
        image.getLayoutParams().height = 450;image.getLayoutParams().width = 450;
        image.requestLayout();
    }

    fun Delete(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        //Toast.makeText(this@Reportes, idA.toString() , Toast.LENGTH_SHORT).show()
        val call = request.eliminarRep(idA!!.toInt())

        call.enqueue(object : Callback<Markerdel> {
            override fun onResponse(call: Call<Markerdel>, response: Response<Markerdel>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@Reportes, R.string.eliminadosucesso , Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }else{
                    Toast.makeText(this@Reportes, "QUASE" , Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Markerdel>, t: Throwable) {
                Toast.makeText(this@Reportes, R.string.empty_not_saved , Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun Update(view: View) {
        val title = findViewById<EditText>(R.id.tituloAnom)
        val desc = findViewById<EditText>(R.id.descricaoAnom)
        val intent = Intent(this, MapsActivity::class.java)


        if (TextUtils.isEmpty(title.text) || TextUtils.isEmpty(desc.text)) {

            if(TextUtils.isEmpty(title.text)) {
                title.setError("getString(R.string.aviso_titulo")
            }
            if(TextUtils.isEmpty(desc.text)) {
                desc.setError("getString(R.string.aviso_desc")
            }

        }else{
            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.editarRep(idA!!,desc.text.toString(),title.text.toString())

            call.enqueue(object : Callback<result> {

                override fun onResponse(call: Call<result>, response: Response<result>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@Reportes, "getString(R.string.edit_sucess)" , Toast.LENGTH_SHORT).show()
                        startActivity(intent)

                    }else{
                        Toast.makeText(this@Reportes, "QUASE" , Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<result>, t: Throwable) {
                    Log.d("TAG", "err: " + t.message)
                }

            })
        }

    }
}
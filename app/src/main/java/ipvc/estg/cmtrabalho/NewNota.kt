package ipvc.estg.cmtrabalho

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewNota : AppCompatActivity() {

    private lateinit var tituloText: EditText
    private lateinit var descText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note)

        tituloText = findViewById(R.id.titulo)
        descText = findViewById(R.id.desc)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            if(tituloText.text.isEmpty() || descText.text.isEmpty()){
                Toast.makeText(this, R.string.campovazio, Toast.LENGTH_SHORT).show()
            }
            else {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(tituloText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY_TITULO, tituloText.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_DESCRICAO, descText.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }}
    }

    companion object {
        const val EXTRA_REPLY_TITULO = "com.example.android.titulo"
        const val EXTRA_REPLY_DESCRICAO = "com.example.android.descricao"
    }
}
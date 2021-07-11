package ipvc.estg.cmtrabalho

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ipvc.estg.cmtrabalho.adapters.NotaListAdapter
import ipvc.estg.cmtrabalho.entities.Nota
import ipvc.estg.cmtrabalho.viewmodel.NotaViewModel


class AllNotes : AppCompatActivity() {

    private lateinit var notaViewModel: NotaViewModel

    private val newWordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_notes)

        // recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotaListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0 or ItemTouchHelper.RIGHT,
                        ItemTouchHelper.LEFT) {
                    override fun onMove(recyclerView: RecyclerView,
                                        viewHolder: ViewHolder, target: ViewHolder): Boolean {

                        return true // true if moved, false otherwise
                    }

                    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

                        val id: Int? = notaViewModel.allNotas.value?.get(viewHolder.adapterPosition)?.id

                        if (id != null) {
                            notaViewModel.deleteByID(id)

                        }


                    }
                }



        ).attachToRecyclerView(recyclerView)

        ItemTouchHelper(
                object : ItemTouchHelper.SimpleCallback(0 or ItemTouchHelper.LEFT,
                        ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView,
                                        viewHolder: ViewHolder, target: ViewHolder): Boolean {

                        return true // true if moved, false otherwise
                    }

                    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {

                        val id: Int? = notaViewModel.allNotas.value?.get(viewHolder.adapterPosition)?.id
                        val tit: String? = notaViewModel.allNotas.value?.get(viewHolder.adapterPosition)?.titulo
                        val desc: String? = notaViewModel.allNotas.value?.get(viewHolder.adapterPosition)?.descricao
                        val intent = Intent(this@AllNotes, EditarNota::class.java)
                        intent.putExtra("id",id)
                        intent.putExtra("tit",tit)
                        intent.putExtra("desc",desc)
                        startActivityForResult(intent, newWordActivityRequestCode)

                    }
                }
        ).attachToRecyclerView(recyclerView)


        // view model
        notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)
        notaViewModel.allNotas.observe(this, Observer { notas ->
            // Update the cached copy of the notes in the adapter.
            notas?.let { adapter.setNotas(it) }
        })


        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@AllNotes, NewNota::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)

        }




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val ptitu = data?.getStringExtra(NewNota.EXTRA_REPLY_TITULO)
            val pdesc = data?.getStringExtra(NewNota.EXTRA_REPLY_DESCRICAO)

            if (ptitu!= null && pdesc != null) {
                val nota = Nota(titulo = ptitu, descricao = pdesc)
                notaViewModel.insert(nota)
            }

        }
    }

}
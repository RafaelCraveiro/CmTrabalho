package ipvc.estg.cmtrabalho.adapters

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.cmtrabalho.MainActivity
import ipvc.estg.cmtrabalho.R
import ipvc.estg.cmtrabalho.entities.Nota
import kotlinx.coroutines.withContext


class NotaListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NotaListAdapter.NotaViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notasss = emptyList<Nota>() // Cached copy of notas

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notaItemViewt: TextView = itemView.findViewById(R.id.textViewt)
        val notaItemViewdesc: TextView = itemView.findViewById(R.id.textViewdes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val current = notasss[position]
        holder.notaItemViewt.text = current.titulo
        holder.notaItemViewdesc.text = current.descricao

    }


    internal fun setNotas(notasss: List<Nota>) {
        this.notasss = notasss
        notifyDataSetChanged()
    }

    override fun getItemCount() = notasss.size
}
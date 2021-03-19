package ipvc.estg.cmtrabalho.db

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import ipvc.estg.cmtrabalho.dao.NotaDao
import ipvc.estg.cmtrabalho.entities.Nota
import kotlinx.coroutines.flow.Flow

class NotaRepository(private val notaDao: NotaDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allNotas: LiveData<List<Nota>> = notaDao.getAllNotas()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(titulo: Nota) {
        notaDao.insert(titulo)
    }
    suspend fun deleteByID(id: Int){
        notaDao.deleteByID(id)
    }
    suspend fun updateNotaByID(id: Int, titulo: String,descricao: String){
        notaDao.updateNotaByID(id,titulo,descricao)
    }



}

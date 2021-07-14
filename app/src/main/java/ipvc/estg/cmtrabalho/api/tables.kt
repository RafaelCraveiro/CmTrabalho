package ipvc.estg.cmtrabalho.api

data class utilizadores(
    val idutilizador: Int,
    val nome: String,
    val email: String,
    val morada: String,
    val password: String
)

data class Marker(
        val id: Int,
        val idutilizador: Int,
        val descr: String,
        val lat: String,
        val lng: String,
        val titulo: String,
        val imagem: String
)
data class Markerdel(
    val id: Int
)

data class result (
    val status: Boolean,
    val message: String
)


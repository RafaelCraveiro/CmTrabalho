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
        val lat: Double,
        val lng: Double,
        val titulo: String,
        val imagem: String
)
data class Markerdel(
    val id: Int
)



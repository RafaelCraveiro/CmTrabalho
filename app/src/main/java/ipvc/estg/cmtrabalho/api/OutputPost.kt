package ipvc.estg.cmtrabalho.api

data class OutputPost(
    val idutilizador: Int,
    val nome: String,
    val email: String,
    val morada: String,
    val password: String
)
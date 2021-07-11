package ipvc.estg.cmtrabalho.api

import androidx.room.Delete
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {
    @GET("api/listarUti/")
    fun getUsers(): Call<List<utilizadores>>

    @GET("api/uti/{id}")
    fun getUserById(@Path("id") id: Int): Call<utilizadores>

    @GET("api/listarRep")
    fun getReportes(): Call<List<Marker>>

    @FormUrlEncoded
    @POST("api/login")
    fun postUtl(@Field("email") first: String, @Field("password") second: String): Call<List<OutputPost>>

    @FormUrlEncoded
    @POST("api/rep/editar") // pedido POST para editar
    fun editarRep(@Field("id_anom") first: Int, @Field("titulo") second: String,
                   @Field("descricao") third: String): Call<Marker>

    @DELETE("api/eliminar/{id}")
    fun eliminarRep(@Path("id") first: Int): Call<Markerdel>

    @FormUrlEncoded
    @POST("api/addrep") // pedido POST para reportar
    fun addAnom(@Field("idutilizador") first: Int, @Field("descr") second:String,
                @Field("lat") third: Double, @Field("lng") fourth: Double,
                @Field("titulo") fifth: String, @Field("imagem") sixth: String): Call<Marker>

}
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
    fun editarRep1(@Field("id_anom") first: Int, @Field("titulo") second: String,
                   @Field("descricao") third: String): Call<Marker>

    @DELETE("api/eliminar/{id}")
    fun eliminarRep(@Path("id") first: Int): Call<Markerdel>

    @FormUrlEncoded
    @POST("api/addrepp") // pedido POST para reportar
    fun addRep(@Field("idutilizador")idutilizador: Int, @Field("descr") descr: String,
                @Field("lat") lat: String, @Field("lng") lng: String,
                @Field("titulo") titulo: String, @Field("imagem") imagem: String): Call<Marker>


    @FormUrlEncoded
    @PUT("reports/{id}")
    fun editarRep(@Path("id") id: Int,@Field("descr") descr: String, @Field( "titulo") titulo: String): Call<result>


}
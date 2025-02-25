package vianditasONG.serviciosExternos.openCage;


import vianditasONG.config.Config;
import vianditasONG.modelos.entities.datosGenerales.PuntoGeografico;
import vianditasONG.modelos.entities.datosGenerales.direccion.Direccion;
import vianditasONG.serviciosExternos.openCage.entidades.RespuestaOpenCage;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioOpenCage {
    private static ServicioOpenCage instancia = null;
    private static final String urlAPI = Config.getInstancia().obtenerDelConfig("urlOpenCageAPI");
    private Retrofit retrofit;

    private ServicioOpenCage() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioOpenCage getInstancia() {
        if (instancia == null) {
            instancia = new ServicioOpenCage();
        }
        return instancia;
    }

    public RespuestaOpenCage puntoGeografico(Direccion direccion) throws IOException {
        OpenCageServicio openCageServicio = this.retrofit.create(OpenCageServicio.class);
        Call<RespuestaOpenCage> requestPuntos = openCageServicio.obtenerPuntoGeografico(direccion.direccionParaTransformar(),
                Config.getInstancia().obtenerDelConfig("tokenOpenCage"),
                Config.getInstancia().obtenerDelConfig("countryCodeOpenCage"));
        Response<RespuestaOpenCage> respuestaOpenCage = requestPuntos.execute();
        return respuestaOpenCage.body();
    }

    public RespuestaOpenCage obtenerDireccionDesdePunto(PuntoGeografico puntoGeografico) throws IOException {
        OpenCageServicio openCageServicio = this.retrofit.create(OpenCageServicio.class);

        Call<RespuestaOpenCage> requestDireccion = openCageServicio.obtenerDireccionDesdePunto(
                puntoGeografico.getLatitud() + "," + puntoGeografico.getLongitud(),
                Config.getInstancia().obtenerDelConfig("tokenOpenCage"),
                Config.getInstancia().obtenerDelConfig("countryCodeOpenCage")
        );

        Response<RespuestaOpenCage> respuesta = requestDireccion.execute();

        return respuesta.body(); // Retorna la respuesta con los datos de la dirección
    }
}

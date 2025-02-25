package vianditasONG.serviciosExternos.sendGrid;

import vianditasONG.config.Config;
import vianditasONG.serviciosExternos.sendGrid.entidades.SolicitudMail;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioSendGrid{

        private static ServicioSendGrid instancia = null;
        private static final String urlAPI = Config.getInstancia().obtenerDelConfig("urlSendGridAPI");
        private Retrofit retrofit;

        private ServicioSendGrid() {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(urlAPI)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public static ServicioSendGrid getInstancia() {
            if (instancia == null) {
                instancia = new ServicioSendGrid();
            }
            return instancia;
        }
        public void enviarMail(SolicitudMail solicitudMail) throws IOException {
            SendGridServicio sendGridServicio = this.retrofit.create(SendGridServicio.class);
            Call<Void> requestMail = sendGridServicio.enviarMail(solicitudMail);
            requestMail.execute();
        }

    }
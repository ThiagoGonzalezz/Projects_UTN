package vianditasONG.dtos.outputs;

public class DireccionDTO {
        private String nombreLugar;
        private String altura;
        private String provincia;

        public DireccionDTO(String nombreLugar, String altura, String provincia) {
            this.nombreLugar = nombreLugar;
            this.altura = altura;

            this.provincia = provincia;
        }

        // Getters y setters
        public String getCalle() { return nombreLugar; }
        public String getAltura() { return altura; }
        public String getProvincia() { return provincia; }

        public void setCalle(String calle) { this.nombreLugar = calle; }
        public void setAltura(String altura) { this.altura = altura; }
        public void setProvincia(String provincia) { this.provincia = provincia; }


}

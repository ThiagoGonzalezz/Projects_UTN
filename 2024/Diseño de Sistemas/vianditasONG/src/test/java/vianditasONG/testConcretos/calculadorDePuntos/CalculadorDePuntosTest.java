package vianditasONG.testConcretos.calculadorDePuntos;

public class CalculadorDePuntosTest {
    /*public CalculadorDePuntos calculadorDePuntos;

    @BeforeEach
    public void inicializar() {
        calculadorDePuntos = new CalculadorDePuntos();

        CalculadorViandasDonadas calculadorViandasDonadas = new CalculadorViandasDonadas();
        calculadorViandasDonadas.setCoeficiente(1.5);

        CalculadorTarjetasDistribuidas calculadorTarjetasDistribuidas = new CalculadorTarjetasDistribuidas();
        calculadorTarjetasDistribuidas.setCoeficiente(2d);

        CalculadorHeladeras calculadorHeladeras = new CalculadorHeladeras();
        calculadorHeladeras.setCoeficiente(5d);

        CalculadorPesosDonados calculadorPesosDonados = new CalculadorPesosDonados();
        calculadorPesosDonados.setCoeficiente(0.5);

        CalculadorViandasDistribuidas calculadorViandasDistribuidas = new CalculadorViandasDistribuidas();
        calculadorViandasDistribuidas.setCoeficiente(1d);

        calculadorDePuntos.agregarCalculadores(calculadorHeladeras,
                calculadorPesosDonados,
                calculadorTarjetasDistribuidas,
                calculadorViandasDistribuidas,
                calculadorViandasDonadas);
    }

    @Test
    @DisplayName("Test Integral Humanos1")
    public void testHumano() {
        Humano humano = new Humano();

        //Donaciones de viandas
        Vianda vianda = new Vianda();
        DonacionDeVianda donacion1 = new DonacionDeVianda();
        DonacionDeVianda donacion2 = new DonacionDeVianda();
        donacion1.agregarViandas(vianda, vianda);
        donacion2.agregarViandas(vianda, vianda, vianda, vianda, vianda, vianda, vianda, vianda);
        humano.agregarViandasDonadas(donacion1, donacion2);


        //Distribuciones de viandas
        DistribucionDeVianda distribucion1 = new DistribucionDeVianda();
        DistribucionDeVianda distribucion2 = new DistribucionDeVianda();
        distribucion1.setCantidadDeViandas(2);
        distribucion2.setCantidadDeViandas(3);
        humano.agregarViandasDistribuidas(distribucion1, distribucion2);

        //Tarjetas Distribuidas
        PersonaEnSituacionVulnerable persona1 = new PersonaEnSituacionVulnerable();
        humano.agregarRegistroDePersonasVulnerables(persona1, persona1, persona1, persona1, persona1);

        //DonacionDePesos
        DonacionDeDinero donacion = new DonacionDeDinero(0);
        donacion.setMonto(100);
        humano.agregarDonacionesDeDinero(donacion, donacion, donacion);

        Assertions.assertEquals(180d, calculadorDePuntos.calcularPuntos(humano));
    }

    @Test
    @DisplayName("Test Integral Humanos2")
    public void testHumano2() {
        Humano humano = new Humano();

        //Donaciones de viandas
        Vianda vianda = new Vianda();
        DonacionDeVianda donacion1 = new DonacionDeVianda();
        donacion1.agregarViandas(vianda, vianda);
        humano.agregarViandasDonadas(donacion1);


        //Distribuciones de viandas
        DistribucionDeVianda distribucion1 = new DistribucionDeVianda();
        DistribucionDeVianda distribucion2 = new DistribucionDeVianda();
        distribucion1.setCantidadDeViandas(2);
        distribucion2.setCantidadDeViandas(8);
        humano.agregarViandasDistribuidas(distribucion1, distribucion2);

        //DonacionDePesos
        DonacionDeDinero donacion = new DonacionDeDinero(10);
        donacion.setMonto(200);
        donacion.setFecha(LocalDate.now().minusDays(21));
        humano.agregarDonacionesDeDinero(donacion, donacion, donacion);

        Assertions.assertEquals(313d, calculadorDePuntos.calcularPuntos(humano));
    }

    @Test
    @DisplayName("Test Integral Humanos3")
    public void testHumano3() {
        Humano humano = new Humano();

        //Donaciones de viandas
        Vianda vianda = new Vianda();
        DonacionDeVianda donacion1 = new DonacionDeVianda();
        donacion1.agregarViandas(vianda, vianda, vianda, vianda, vianda);
        humano.agregarViandasDonadas(donacion1, donacion1);

        //Tarjetas Distribuidas
        PersonaEnSituacionVulnerable persona1 = new PersonaEnSituacionVulnerable();
        humano.agregarRegistroDePersonasVulnerables(persona1, persona1, persona1);

        //Distribuciones de viandas
        DistribucionDeVianda distribucion1 = new DistribucionDeVianda();
        distribucion1.setCantidadDeViandas(11);
        humano.agregarViandasDistribuidas(distribucion1);

        Assertions.assertEquals(32, calculadorDePuntos.calcularPuntos(humano));
    }

    @Test
    @DisplayName("Test Integral Perspma Juridica 1")
    public void testPersonaJuridica1() {
        PersonaJuridica personaJuridica = new PersonaJuridica();

        //Donaciones de Dinero
        DonacionDeDinero donacion = new DonacionDeDinero(0);
        donacion.setMonto(200);
        personaJuridica.agregarDonacionesDeDinero(donacion, donacion);

        //Heladeras a Cargo
        Heladera heladera = new Heladera();
        heladera.setDiasDesactivada(0);
        heladera.setEstaActiva(true);
        heladera.setFechaDeRegistro(LocalDateTime.now().minusDays(30));
        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera();
        hacerseCargoDeHeladera.setHeladera(heladera);
        personaJuridica.agregarHeladerasACargo(hacerseCargoDeHeladera, hacerseCargoDeHeladera);

        Assertions.assertEquals(220, calculadorDePuntos.calcularPuntos(personaJuridica));
    }

    @Test
    @DisplayName("Test Integral Persona Juridica 2")
    public void testPersonaJuridica2() {
        PersonaJuridica personaJuridica = new PersonaJuridica();

        //Donaciones de Dinero
        DonacionDeDinero donacion = new DonacionDeDinero(10);
        donacion.setMonto(200);
        donacion.setFecha(LocalDate.now().minusDays(21));
        personaJuridica.agregarDonacionesDeDinero(donacion, donacion, donacion);

        //Heladeras a Cargo
        Heladera heladera = new Heladera();
        heladera.setDiasDesactivada(0);
        heladera.setEstaActiva(false);
        heladera.setFechaDeRegistro(LocalDateTime.of(2024,10,23, 8, 25));
        HacerseCargoDeHeladera hacerseCargoDeHeladera = new HacerseCargoDeHeladera();
        hacerseCargoDeHeladera.setHeladera(heladera);
        personaJuridica.agregarHeladerasACargo(hacerseCargoDeHeladera, hacerseCargoDeHeladera);

        Assertions.assertEquals(300, calculadorDePuntos.calcularPuntos(personaJuridica));
    }
  */
}


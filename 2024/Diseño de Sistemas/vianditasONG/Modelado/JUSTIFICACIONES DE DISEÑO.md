# Justificaciones de diseño

## Formularios
* **Formulario como clase:** Consideramos que era necesario tratar al formulario como una clase ya que se utilizaría en repetidas ocasiones para confirmar vianda o colaborador. El formulario vendría a imponer el formato y al confirmar el formulario se contestarían todas las preguntas que pertenecen a ese formato, devolviendo así una serie de respuestas (cada una asociada a una pregunta). Nuestra idea es que una vez que se tenga esa lista de respuestas, la entidad que vaya a dar de alta a alguien o algo analice las respuestas esenciales y tome los datos necesarios para los atributos de la entidad a dar de alta. En algunos casos va a ser necesario castear.

* **RespuestaPosible:** Utilizamos la clase respuesta posible para asegurarnos de que en caso de una pregunta del estilo multiple choice o del tipo de respuestas predefinidas se elija correctamente una de las respuestas esperadas. En caso de no haber respuestas posibles en la lista de respuestasPosibles, quiere decir que era una respuesta a desarrollar. Con este agregado lograríamos una mayor consistencia de datos.

## Colaboradores
* **Humano y Persona Jurídica como clases separadas:** Decidimos tratarlas como clases separadas ya que por el momento comparten muy pocas cosas en común. En un futuro veremos si es necesario realizar una abstracción colaborador pero por el momento no lo vimos necesario. Ambas clases "Humano" y "PersonaJuridica" tienen un usuario y uno o más medios de contacto. Queremos aclarar que en datos opcionales se agregarían las respuestas del formulario que no son escenciales.

* **Tipo Organización en Persona Jurídica como enum:** Decidimos trabajar este campo como enum ya que ganamos una mayor consistencia de datos y abstracción ante la alternativa de trabajarlo como string. Por otro lado, preferimos trabajarlo de esta manera antes que como clase ya que consideramos que no va a ser necesario agregar nuevos tipos de organización, debido a que la consigna no lo aclara y consideramos que abarca una gran cantidad de opciones, por lo que con esta forma de trabajarlo ganamos simplicidad.

* **Contacto como clase:** Decidimos tratarlo como una clase en vez de un string ya que según el tipo de contacto, este podría tener un formato diferente y quisimos diferenciarlos. Por ejemplo, un número de telefono no debería poder guardarse de la misma forma que un correo.

* **TipoDeDocumento como enum:** Decidimos trabajar este campo como enum ya que ganamos una mayor consistencia de datos y abstracción ante la alternativa de trabajarlo como string. Por otro lado, preferimos trabajarlo de esta manera antes que como clase ya que consideramos que rara vez va a ser necesario añadir nuevos tipos de documentos, debido a que asumimos que solo se aceptaran documentos reconocidos por la nacion Argentina, por lo tanto, estamos ganando una mayor simplicidad.

## Colaboraciones
* **Tipo de colaboraciones como clases (DistribucionDeVianda, DonacionDeVianda, DonacionDeDinero, HacerseCargoDeHeladera):** Decidimos trabajarlas como clases ya que, dentro de un mismo tipo de colaboración (por ejemplo donación de dinero) puede haber 2 de ellas con un estado interno distinto (distinta cantidad de dinero) por lo que no es posible manejarla como objeto. Se agregarán formas de colaboración en el futuro.

* **Motivo de distribución como clase y no como enum:** Creemos que los motivos de distribucion de viandas pueden ser variados y/o muy especificos dependiendo el caso, por lo que unsa descripcion en forma de string puede ser lo mas eficiente.

* **Vianda como clase:** Decidimos trabajarlas como clases ya que cada vianda tiene sus propios atributos caracteristicos.

* **Comida de la vianda como string:** Si bien trabajando el tipo de comida de las viandas como string perdemos consistencia de datos ante una alternativa como trabajarlos como clases o enum, ante la gran variedad de comidas que se podrian agregar posteriormente, consideramos que esta seria la manera mas adecuada de implementarlo. Además, observando la funcionalidad de nuestro sistema, no es necesario ni ordenar ni comparar los tipos de comida.

## Direcciones
* **Punto Estrategico como clase:** Consideramos que conseguimos una mejor abstracción agrupando los atributos de un puntoGeografico estratégico pertenenciente a una heladera. Además pensamos que podría suceder que dos o más heladeras se encuentren en el mismo puntoGeografico estratégico, una al lado de la otra.

* **Latitud y Altitud como string:** Por ahora decidimos tratarlo como string para poder seguir avanzando rapido con el proyecto, aunque luego veremos si es conveniente tratarlo de otra manera para poder implementarlo en el mapa. Sabemos que a mayor cantidad de decimales que tenga, con mayor exactitud será la ubicación del puntoGeografico.

## Registro de Alertas
* **Registro de Alertas:** Modelamos en el diagrama de clases el receptor de alertas por temperatura y otro por movimiento. Estos se encargan de recibir una JSON(en formato String) ya que suponemos que están suscritos a un broker y a partir de eso instancian las alertas correspondientes y las agregan al registro de alertas. Con esto tenemos un seguimiento de las alertas que le corresponden a una heladera.

## Recomendador de Puntos
* **Puntos Recomendados:** Utilizamos el patrón adapter en este caso para poder desacoplarnos lo más posible de la REST API y que en caso de que en un futuro se modifique algo en relación a ella sea más sencillo de ajustar en nuestro sistema. Además, modelamos el área de busqueda como una clase para englobar todos los parámetros que deseamos pasarle para buscar los puntos solicitados, y en caso de que en un futuro se quiera agregar otro tipo de filtro se podría hacer fácilmente agregándolo como atributo a dicha clase. 

## Administrador de contraseñas

* **Administrador de contraseñas con una lista de interfaces de validacion:** Optamos por esta alternativa ya que delega algunas responsabilidades del AdministradorDeContrasenias, evitando que esta se vuelva una clase dios, generando así una mayor abstracción y cohesión. Además, pensamos que esta solución proporciona una mayor flexibilidad, mantenibilidad y extensibilidad, debido a que en caso de querer modificar/quitar/agregar una validacion, es tan simple como modificar/borrar esa clase validación o agregar otra.  

* **Administrador de contraseñas con un atributo encriptador:** Volviendo un poco al item anterior, decidimos hacerlo de esta manera para delegar responsabilidades del AdministradorDeContrasenias, evitando que esta se vuelva una clase dios, generando así una mayor abstracción y cohesión. Por otro lado, si bien entendemos que implementar la encriptación no era algo obligatorio, consideramos que la misma va a lograr una mejora en la integridad, confidencialidad y autenticidad de nuestro sistema cuando se almacenen las contraseñas de los usuarios en la base de datos.

## Calculador De Puntos

* **Patron Template Method en los calucladores especificos:** Decidimos implementar este patrón de comportamiento debido a que todos los calculadores específicos realizan el mismo algoritmo para calcular los puntos de los colaboradores: **[CantPuntos = CantidadValorable * coeficiente]** pero el cálculo de esa cantidad valorable varía en cada caso.
De esta manera se garantiza una mayor flexibilidad ya que este algoritmo es fácilmente localizable. Además, proporciona una mayor extensibilidad debido a que, en caso de querer agregar una nueva colaboración y su calculador, sería tan simple como agregar una nueva clase concreta que herede del calculador especifico y se defina su propia forma de calcular la cantidad valorable. Por último, este patrón también permite una mayor cohesión entre la clase calculador especifico y sus clases concretas.

* **Patron Strategy para cada calulador especifico dentro del Calculador de puntos:** Consideramos útil implementar este patrón ya que su uso permite una mayor extensibilidad para incorporar nuevos calculadores especificos, debido a que simplemente habría que crear una nueva clase y agregarla a la lista que posee el CalculadorDePuntos.
Además, este patrón permite una mayor cohesión en la clase general.

## Repositorios

Creamos los repositorios de colaboraciones, humanos y credenciales ya que vamos a necesitar tener consistencia de estos datos en el sistema para que persistan luego de la ejecución.

## Patron Adapter

* **SendGrid API:** Aplicamos el patron adapter para poder encapsular las llamadas a la API de SendGrid. Creemos que esto garantizará un menor aclopamiento entre clases, generando de esta manera una mejor cohesión y mantenibilidad. 

* **Recomendador de Puntos API:** En el caso de el RecomendadorDePuntos, este patrón es especialmente útil ya que desconocemos cómo está implementada la API internamente. De este modo, podemos interactuar con la API sin depender de su implementación específica, lo que facilita futuras modificaciones y pruebas.

## Registro de Tarjetas

* **Codigo de tarjeta:** Lo modelamos como String pero el controlador encargado de generar la tarjeta lo va a hashear y se va a asegurar que ese código sea único.

## Carga Masiva de Colaboradores
* **Acceso a repositorios**
Proponemos que la clase carga de colaboradores conozca a los diferentes repositorios que guardaran los distintos tipos de colaboraciones en nuestro sistema, existiendo un repositorio en especifico por forma de colaboración. Así podría leer las colaboraciones documentadas en el CVS y guardarlas donde corresponde, además de evaluar cuales de los colaboradores tienen usuario en nuestro sistema y cuales aún no. Esto lo implementamos mediante inyección de dependencias para poder mockearlo en el test, debido a que no queremos almacenar falsos datos.

* **Clase LineaColaboracion:**  Ya que cada línea representa una colaboración en el CVS, decidimos crear la clase LineaColaboracion para abstraer esta entidad dentro de la lógica de la migración, y que cuando el lector de colaboraciones, que en este caso es la clase LeerColaboraciones, escanee el archivo devuelva una lista de líneas con las que trabajar.

## Enviador de Mails

Decidimos delegar la responsabilidad de enviar los mails en esta entidad aparte para generar una mayor cohesion en el importador CSV. Esta clase Enviador de Mails es la encargada de, a traves del SendGrid Adapter, enviarle los mails a aquellos colaboradores del antiguo sistema que aún no se han registrado en el nuevo sistema. En el mail se les brindá una credencial y los pasos a seguir para registrarse. 

## Implementación MQTT
* **Clases ReceptroDeMovimientos y ReceptorDeTemperaturas:** Usamos estas clases para recibir de un tópico de alertas y un tópico de temperaturas respectivamente la información necesaria para nuestro sistema. Por ejemplo, el receptor de alertas se encuentra subscrito a un topico de alertas, recibe el JSON enviado por MQTT con el id de la heladera y este se encarga de derivar la información al sensor correspondiente a esa heladera en particular.
* **Clases SensorDeMovimiento y SensorDeTemperatura:** Estas clases vienen a modelar los sensores pero en nuestro sistema. Se encargan de recibir la información y accionar según lo que reciban. En el caso del sensor de movimiento se encarga de registrar el incidente. En el caso del sensor de temperatura, este delega la responsabilidad de alertar a un evaluador de temperaturas, el cual tendrá que verificar que la temperatura se encuentre dentro del rango adecuado. Además, este sensor actualiza la ultima temperatura de la heladera.
* **Clase PublicadorSolicitudApertura:** Se encarga de publicar la acción solicitada para las heladeras que se pretendían abrir mediante esa acción. Esto lo hace en un tópico en el cual las heladeras están escuchando, y luego la evaluación interna de si el colaborador puede abrir o no esa heladera la hace la heladera misma.
* * **Clase ReceptorRespuestasSolicitudes:** Se encarga de recibir las respuestas por parte de la heladera. Puede suceder que hubo un intento de apertura fallido o una apertura fehaciente. Una vez recibido este JSON registra el hecho sucedido asociado a la tarjeta que intento abrir la heladera.

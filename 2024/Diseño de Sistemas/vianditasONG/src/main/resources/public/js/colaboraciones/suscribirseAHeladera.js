document.addEventListener('DOMContentLoaded', function () {
    // Evitar que se inicialice el mapa varias veces
    if (window.mapInitialized) {
        return;
    }

    // Inicializar el mapa
    var mapOptions = {
        center: [-34.6037, -58.4206],
        zoom: 13
    };

    var map = L.map('mapid-destino', mapOptions);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
    }).addTo(map);

    // Definir los iconos de las heladeras
    var heladeraRojaIcon = new L.Icon({
        iconUrl: '../../img/iconosMapa/heladeraInactiva.png',
        iconSize: [60, 60],
        popupAnchor: [1, -34],
    });

    var heladeraVerdeIcon = new L.Icon({
        iconUrl: '../../img/iconosMapa/heladeraActiva.png',
        iconSize: [60, 60],
        popupAnchor: [1, -34],
    });

    // Función para crear el contenido del popup
    function getPopupContent(id, name, address, status, stored, capacity) {
        return `
            <b>${name}</b><br>
            Dirección: ${address}<br>
            Estado: ${status}<br>
            Viandas almacenadas: ${stored}<br>
            Capacidad restante: ${capacity}<br>
            <button type="button" onclick="showModalSuscripcion('${id}')" style="cursor: pointer;">Suscribirse</button>
        `;
    }

    // Fetch para obtener los datos de heladeras
    fetch('/heladeras')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(heladeras => {
            heladeras.forEach(heladera => {
                var icon = heladera.estado === "Activa" ? heladeraVerdeIcon : heladeraRojaIcon;

                // Creación del marcador con los datos de la heladera
                var markerDestino = L.marker([heladera.latitud, heladera.longitud], { icon: icon }).addTo(map);

                // Asignación del popup con el ID de la heladera
                markerDestino.bindPopup(getPopupContent(heladera.id, heladera.nombre, heladera.direccion, heladera.estado, heladera.viandasAlmacenadas, heladera.capacidadRestante));
            });
        })
        .catch(error => {
            console.error('Error al obtener los datos de heladeras:', error);
        });

    // Marcar que el mapa ya ha sido inicializado
    window.mapInitialized = true;

     const form = document.getElementById('notificationForm');

     form.addEventListener('submit', function(event) {
         event.preventDefault(); // Evitar el envío normal del formulario

         const formData = new FormData(form); // Recoger los datos del formulario

         // Usar Fetch API para enviar el formulario
         fetch(form.action, {
             method: 'POST',
             body: formData
         })
         .then(response => {
             if (response.ok) { // Si la respuesta es 200-299
                 popUpBienvenido(); // Llama al popup aquí
             } else {
                 // Si la respuesta no fue ok, aquí se maneja
                 return response.json().then(errData => {
                     // Suponiendo que el backend devuelve un JSON con un mensaje de error
                     console.error('Error:', errData.message || 'Error al suscribirte. Intenta nuevamente.');
                     alert(errData.message || 'Error al suscribirte. Intenta nuevamente.'); // Personaliza el mensaje si se proporciona uno
                 });
             }
         })
         .catch(error => {
             console.error('Error:', error);
             alert('Hubo un problema al procesar tu solicitud.');
         });
     });
});

// Función para mostrar el modal solo cuando se hace clic en el botón
function showModalSuscripcion(heladeraId) {
    // Asignar el ID de la heladera al campo oculto
    document.getElementById('heladeraId').value = heladeraId;

    // Mostrar el modal solo si no está ya abierto
    var modal = document.getElementById('suscripcionModal');
    modal.style.display = 'flex'; // Mostrar el modal
}

// Función para cerrar el modal
function closeModal() {
    var modal = document.getElementById('suscripcionModal');
     console.log('Cerrando modal...');
    modal.style.display = 'none';
}

// Cerrar el pop-up si se presiona el botón de cerrar
const closePopupButton = document.querySelector('.pop-up-btn.cancel');
if (closePopupButton) {
    closePopupButton.addEventListener('click', function () {
        closeModal(); // Usar la función de cierre definida
    });
}
// scriptsMapa.js


var mymap = L.map('mapid').setView([-34.6037, -58.4206], 13);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(mymap);


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
            var marker = L.marker([heladera.latitud, heladera.longitud], { icon: icon }).addTo(mymap);
            marker.bindPopup(`
                <b>${heladera.nombre}</b><br>
                Dirección: ${heladera.direccion}<br>
                Estado: ${heladera.estado}<br>
                Viandas almacenadas: ${heladera.viandasAlmacenadas}<br>
                Capacidad restante: ${heladera.capacidadRestante}<br>
                ID: ${heladera.id}<br>
                <button onclick="seleccionarHeladera(${heladera.id})">Seleccionar esta heladera</button>
            `);
        });
    })
    .catch(error => {
        console.error('Error al obtener los datos de heladeras:', error);
    });

function seleccionarHeladera(idHeladera) {
    document.getElementById("heladeraId").value = idHeladera;
    alert("Heladera con ID " + idHeladera + " seleccionada.");
}




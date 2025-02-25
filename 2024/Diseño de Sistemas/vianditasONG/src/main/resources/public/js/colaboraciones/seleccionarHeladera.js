var mapOptions = {
    center: [-34.6037, -58.4206],
    zoom: 13
};

var map = L.map('mapid-destino', mapOptions);
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '&copy; OpenStreetMap contributors'
}).addTo(map);

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

// Añadido el id como parámetro
function getPopupContent(id, name, address, status, stored, capacity) {
    return `
        <b>${name}</b><br>
        Dirección: ${address}<br>
        Estado: ${status}<br>
        Viandas almacenadas: ${stored}<br>
        Capacidad restante: ${capacity}<br>
        <button type="button" onclick="selectHeladeraDestino('${id}','${name}', '${address}')" style="cursor: pointer;">Seleccionar</button>
    `;
}

window.selectHeladeraDestino = function(id, name, address) {
    document.getElementById('heladera-destino-nombre').value = name;
    document.getElementById('heladera-destino-direccion').value = address;
    document.getElementById('heladera-destino-id').value = id;
};

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
            var markerDestino = L.marker([heladera.latitud, heladera.longitud], { icon: icon }).addTo(map);
            markerDestino.bindPopup(getPopupContent(heladera.id, heladera.nombre, heladera.direccion, heladera.estado, heladera.viandasAlmacenadas, heladera.capacidadRestante));
        });
    })
    .catch(error => {
        console.error('Error al obtener los datos de heladeras:', error);
    });

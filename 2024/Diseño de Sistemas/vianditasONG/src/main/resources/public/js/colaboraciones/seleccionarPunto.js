document.addEventListener('DOMContentLoaded', function() {
    var map = L.map('mapid-destino').setView([-34.6037, -58.3816], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    var marker = L.marker([-34.6037, -58.3816]).addTo(map);


    function buscarDireccion(direccion) {
        if (!direccion) return;

        fetch(`https://nominatim.openstreetmap.org/search?q=${encodeURIComponent(direccion)}&format=json&limit=1`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    var lat = data[0].lat;
                    var lon = data[0].lon;

                    map.setView([lat, lon], 13);
                    marker.setLatLng([lat, lon]);

                    document.getElementById('punto-colocacion-direccion').value = data[0].display_name;
                } else {
                    console.error("No se pudo encontrar la dirección.");
                }
            })
            .catch(error => console.error('Error al buscar la dirección:', error));
    }

    let debounceTimeout;
    const debounceDelay = 500;


    document.getElementById('punto-colocacion-direccion').addEventListener('input', function() {
        clearTimeout(debounceTimeout);
        const direccion = this.value;
        debounceTimeout = setTimeout(() => {
            buscarDireccion(direccion);
        }, debounceDelay);
    });


    map.on('click', function(e) {
        var lat = e.latlng.lat;
        var lon = e.latlng.lng;

        marker.setLatLng([lat, lon]);

        fetch(`https://nominatim.openstreetmap.org/reverse?lat=${lat}&lon=${lon}&format=json`)
            .then(response => response.json())
            .then(data => {
                var address = data.display_name;
                document.getElementById('punto-colocacion-direccion').value = address;
            })
            .catch(error => console.error('Error al obtener la dirección:', error));
    });


    document.getElementById('btn-envio-form').addEventListener('click', function(e) {
        e.preventDefault(); // Prevenir el comportamiento por defecto del botón (submit)


        const formData = new FormData();


        const modeloHeladera = document.getElementById('heladeraModelo').value;
        const nombreHeladera = document.getElementById('nombre').value;
        const direccionHeladera = document.getElementById('punto-colocacion-direccion').value;


        if (modeloHeladera === '-1' || nombreHeladera === '' || direccionHeladera === '') {
            alert("Por favor, complete todos los campos obligatorios.");
            return;
        }

        formData.append("heladeraModelo", modeloHeladera);
        formData.append("nombre", nombreHeladera);
        formData.append("punto-colocacion-direccion", direccionHeladera);


        fetch('/colocar-heladera', {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    popUpBienvenido();
                    alert("Heladera colocada correctamente.");
                    document.getElementById("registro-form").reset(); // Resetear formulario
                } else {
                    alert("Error al colocar la heladera.");
                }
            })
            .catch(error => console.error('Error:', error));
    });















///////////////////////////////////////////*


});


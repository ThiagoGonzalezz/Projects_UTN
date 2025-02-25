function handleCollaboration(form) {
    // Obtener el valor de stringEnum del formulario
    const stringEnum = form.querySelector('input[name="stringEnum"]').value;

    // Comprobar si es HACERSE_CARGO_DE_HELADERAS y si el usuario no tiene dirección cargada
    if (stringEnum === 'HACERSE_CARGO_DE_HELADERAS' && !tieneDireccion) {
        // Mostrar el pop-up solo si no tiene dirección
        document.getElementById('popup-stringEnum').value = stringEnum; // Guardar el valor del stringEnum en el pop-up
        document.getElementById('address-popup').style.display = 'flex';
        return false; // Prevenir el envío del formulario mientras el pop-up esté activo
    }

    // Si el usuario ya tiene dirección o no es HACERSE_CARGO_DE_HELADERAS, activamos la tarjeta y dejamos enviar
    enableCard(form);

    return true; // Dejar que el formulario se envíe normalmente para otras colaboraciones
}

function enableCard(form) {
    const card = form.closest('.collaboration-card');

    // Activar la tarjeta visualmente
    card.classList.remove('disabled');
    card.classList.add('active');

    // Eliminar la overlay y el banner de interés
    const overlay = card.querySelector('.overlay');
    const banner = card.querySelector('.interest-banner');
    if (overlay) overlay.remove();
    if (banner) banner.remove();
}

$(document).ready(function() {
    $('#select-localidad').select2({
        placeholder: 'Ingrese la localidad del domicilio',
        allowClear: true,
        width: '100%'
    });
});

function cargarLocalidades() {
    const selectLocalidades = document.getElementById("select-localidad");
    const selectProvincia = document.getElementById("provincia");
    const provinciaSeleccionada = selectProvincia.value;

    // Limpiar las opciones anteriores
    selectLocalidades.innerHTML = '<option value="-1" disabled selected hidden>Seleccione la localidad</option>';

    fetch(`/localidades?provincia=${provinciaSeleccionada}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Error en la respuesta del servidor: " + response.status);
            }
            return response.json();
        })
        .then(localidades => {
            localidades.forEach(localidad => {
                const option = document.createElement("option");
                option.value = localidad.id;
                option.textContent = localidad.nombre;
                selectLocalidades.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Error al cargar las localidades:", error);
        });
}

function submitAddress() {
    const address = {
        provincia: document.getElementById('provincia').value,
        localidad: document.getElementById('select-localidad').value,
        calle: document.getElementById('calle').value,
        altura: document.getElementById('altura').value
    };
    const stringEnum = document.getElementById('popup-stringEnum').value;

    if (address.provincia !== "-1" && address.localidad !== "-1" && address.calle && address.altura) {
        // Crear formulario temporal
        const form = document.createElement('form');
        form.action = '/menu-personaJuridica';
        form.method = 'POST';

        // Agregar campos al formulario
        Object.keys(address).forEach(key => {
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = key;
            input.value = address[key];
            form.appendChild(input);
        });

        const enumInput = document.createElement('input');
        enumInput.type = 'hidden';
        enumInput.name = 'stringEnum';
        enumInput.value = stringEnum;
        form.appendChild(enumInput);

        // Enviar el formulario
        document.body.appendChild(form);
        form.submit();
    } else {
        alert("Por favor, complete todos los campos de dirección.");
    }
}

function closePopup() {
    document.getElementById('address-popup').style.display = 'none';
}
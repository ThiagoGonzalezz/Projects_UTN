$(document).ready(function() {
    $('#select-localidad').select2({
        placeholder: 'Ingrese la localidad del domicilio',
        allowClear: true,
        width: '100%',
        minimumResultsForSearch: ''
    });
});

function cargarLocalidades() {
    const selectLocalidades = document.getElementById("select-localidad");

    const selectProvincia = document.getElementById("provincia");
    const provinciaSeleccionada = selectProvincia.value;

    // Eliminar todas las opciones previas
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
            console.error("Error al cargar los rubros:", error);
        });
}
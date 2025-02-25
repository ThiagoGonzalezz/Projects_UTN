document.getElementById('file-upload').addEventListener('change', function() {
    var fileName = this.files[0] ? this.files[0].name : 'Ningún archivo seleccionado';
    document.getElementById('file-selected').textContent = fileName;
});

document.getElementById('download-example-csv').addEventListener('click', function () {
    window.location.href = '/csv/importacionDeColaboracionesExample.csv';
});

document.addEventListener('DOMContentLoaded', () => {
    const migrationButton = document.querySelector('.btn.primary');
    const cancelButton = document.querySelector('.btn.secondary');
    const progressBar = document.querySelector('.progress-bar');
    const inputFile = document.getElementById("file-upload");
    let migrationInterval;
    let isMigrating = false;

    migrationButton.addEventListener('click', () => {
        if (isMigrating) return;
        
        eliminarMensajeError();

        if (inputFile.files.length === 0) {
            mostrarMensajeError();
            return;
        }

        isMigrating = true;
        progressBar.style.width = '0%';
        let progress = 0;

        // Inicia el intervalo para actualizar la barra de progreso
        migrationInterval = setInterval(() => {
            if (progress < 100) {
                progress += 2; // Incremento de progreso
                progressBar.style.width = `${progress}%`;
            } else {
                clearInterval(migrationInterval);
                // Solo ejecutar el fetch cuando el progreso alcanza 100%
                const formData = new FormData();
                formData.append('csv-importacion', inputFile.files[0]);

                fetch('/colaboraciones/importacion', {
                    method: 'POST',
                    body: formData,
                })
                .then(response => {
                    console.log(response);
                    if (!response.ok) {
                        throw new Error('Error en la migración: ' + response.statusText);
                    }
                    return response.text(); // o response.json() si esperas un JSON
                })
                .then(data => {
                    alert(data); // Mensaje de éxito
                    progressBar.style.width = '100%'; // Completar barra de progreso
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Error al subir el archivo.');
                    progressBar.style.width = '0%';
                })
                .finally(() => {
                    // Restablecer el estado de migración
                    isMigrating = false;
                });
            }
        }, 100); // Intervalo de tiempo para simular la carga
    });

    cancelButton.addEventListener('click', () => {
        if (!isMigrating) return;

        clearInterval(migrationInterval);
        progressBar.style.width = '0%';
        alert('Migración cancelada.');
        isMigrating = false;
    });
});

function mostrarMensajeError() {
    const archivoInput = document.getElementById("file-upload");
    const btnMigracion = document.getElementById("actions-container");
    const barraProgresoMigracion = document.getElementById("barra-progreso-container");

    // Crear el mensaje de error
    var mensajeError = document.createElement("p");
    mensajeError.classList.add("mensaje-error");
    mensajeError.innerHTML = `
        <i class="fas fa-exclamation-circle icono-error"></i>
        ${"Debes adjuntar el archivo .csv que desees importar"}
    `;

    btnMigracion.parentNode.insertBefore(mensajeError, barraProgresoMigracion);
}

function eliminarMensajeError() {
    var mensajesError = document.querySelectorAll(".mensaje-error");
    mensajesError.forEach(function(mensaje) {
        mensaje.remove();
    });
}
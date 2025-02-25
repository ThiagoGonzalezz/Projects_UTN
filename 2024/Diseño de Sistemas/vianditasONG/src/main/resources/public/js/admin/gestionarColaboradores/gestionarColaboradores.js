$(document).ready(function() {

    // Función de orden para fechas en formato dd/mm/aaaa
    $.fn.dataTable.ext.type.order['date-pre'] = function(date) {
        if (date === null || date === "") {
            return 0;
        }
        const dateParts = date.split("/");
        return Date.parse(`${dateParts[2]}-${dateParts[1]}-${dateParts[0]}`);
    };

    var tablaH = $('#tabla-humanos').DataTable({
        "columnDefs": [
            { "orderable": false, "targets": 'no-sort-th' },
            { "type": "date", "targets": 0 }  // Aplica el tipo de orden personalizado a la columna de fecha
        ],
        "order": [[0, 'asc']], // Ordena por la columna de fecha en orden ascendente
        language: {
            "lengthMenu": "Mostrar _MENU_ solicitudes por página",
            "search": "Buscar:",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ solicitudes",
            "infoEmpty": "No hay solicitudes disponibles",
            "zeroRecords": "No se encontraron solicitudes",
            "emptyTable": "No hay más solicitudes disponibles",
            loadingRecords: "Cargando...",
            processing: "Procesando..."
        },
        "rowCallback": function(row, data, dataIndex) {
            // Alternar clases de fondo inicialmente
            if (dataIndex % 2 === 0) {
                $(row).addClass('fila-blanca').removeClass('fila-gris');;
            } else {
                $(row).addClass('fila-gris').removeClass('fila-blanca');;
            }
        },
        "rowReorder": true // Asegúrate de habilitar el reordenamiento de filas
    });


    var tablaPJ = $('#tabla-personas-juridicas').DataTable({
        "columnDefs": [
            { "orderable": false, "targets": 'no-sort-th' },
            { "type": "date", "targets": 0 }  // Aplica el tipo de orden personalizado a la columna de fecha
        ],
        "order": [[0, 'asc']], // Ordena por la columna de fecha en orden ascendente
        language: {
            "lengthMenu": "Mostrar _MENU_ solicitudes por página",
            "search": "Buscar:",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ solicitudes",
            "infoEmpty": "No hay solicitudes disponibles",
            "zeroRecords": "No se encontraron solicitudes",
            "emptyTable": "No hay más solicitudes disponibles",
            loadingRecords: "Cargando...",
            processing: "Procesando..."
        },
        "rowCallback": function(row, data, dataIndex) {
            // Alternar clases de fondo inicialmente
            if (dataIndex % 2 === 0) {
                $(row).addClass('fila-blanca').removeClass('fila-gris');;
            } else {
                $(row).addClass('fila-gris').removeClass('fila-blanca');;
            }
        },
        "rowReorder": true // Asegúrate de habilitar el reordenamiento de filas
    });


    tablaH.on('draw', function() {
        colorearFilas(tablaH);
    });
    
    tablaPJ.on('draw', function() {
        colorearFilas(tablaPJ);
    });

    // Inicializa el color de las filas al cargar la tabla
    colorearFilas(tablaH);

    console.log("Datatables configurado con éxito");
});



function toggleTables() {
    const toggleSwitch = document.getElementById('toggle-switch');

    if (toggleSwitch.checked) {
        ocultarTabla('tabla-humanos-container');
        mostrarTabla('tabla-personas-juridicas-container');
    } else {
        ocultarTabla('tabla-personas-juridicas-container');
        mostrarTabla('tabla-humanos-container');
    }
}


function ocultarTabla(tablaContainerId) {
    const tablaContainer = document.getElementById(tablaContainerId)

    if (tablaContainer) {
        tablaContainer.style.display = 'none';
    }
}

function mostrarTabla(tablaContainerId) {
    const tablaContainer = document.getElementById(tablaContainerId);

    if (tablaContainer) {
        tablaContainer.style.display = 'block';
    }
}

// Función para colorear filas intercaladamente
function colorearFilas(tabla) {
    tabla.rows({ page: 'current' }).every(function(rowIdx, tableLoop, rowLoop) {
        var row = this.node();
        if (rowLoop % 2 === 0) {
            $(row).addClass('fila-blanca').removeClass('fila-gris');
        } else {
            $(row).addClass('fila-gris').removeClass('fila-blanca');
        }
    });
}

async function visualizarFormulario(idColab, tipoColab){
    await cargarFormularioRespondido(tipoColab, idColab);

    const formulario = document.getElementById("pop-up-formulario");
    formulario.showModal();
}

function cerrarFormulario(){
    const formulario = document.getElementById("pop-up-formulario");
    formulario.close();
}

function popUpRechazo(nombreColab, idColab, tipoColab) {
    const popUp = document.getElementById("pop-up-rechazo");

    const encabezado = popUp.querySelector("h2");
    encabezado.textContent = `¿Seguro que desea rechazar la solicitud de ${nombreColab}?`;

    const confirmarBoton = popUp.querySelector(".confirmar-boton");
    confirmarBoton.setAttribute("onclick", `confirmarRechazo('${nombreColab}', '${idColab}', '${tipoColab}', document.getElementById('motivo').value)`);

    popUp.showModal();
}

function cerrarRechazo() {
    const popUp = document.getElementById("pop-up-rechazo");
    popUp.close();
}

function confirmarRechazo(nombreColab, idColab, tipoColab, motivo) {
    const url = `solicitudes/${tipoColab}/rechazo/${idColab}`;

    // Crear un objeto FormData para formatear los datos como formulario
    const formData = new URLSearchParams();
    formData.append('motivo', motivo);

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: formData.toString(),  // Convertir FormData a una cadena URL codificada
    })
    .then(response => {
        if (response.ok) {
            const popUpRechazo = document.getElementById("pop-up-rechazo")
            const popUpRechazoExitoso = document.getElementById("pop-up-exito-rechazo");

            document.getElementById("nombre-colaborador").innerHTML = `<strong>Colaborador:</strong> ${nombreColab}`;
            document.getElementById("motivo-rechazo").innerHTML = `<strong>Motivo:</strong> ${motivo}`;

            popUpRechazo.close();
            popUpRechazoExitoso.showModal();
            borrarFila(idColab);

        } else {

            console.error('Error en la solicitud:', response.statusText);
        }
    })
}

function cerrarExitoRechazo() {
    const popUp = document.getElementById("pop-up-exito-rechazo");
    popUp.close();
}

function habilitarColaborador(nombreColab, idColab, tipoColab) {

    const url = `solicitudes/${tipoColab}/habilitacion/${idColab}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            // Si la respuesta es exitosa, actualiza el nombre en el pop-up
            const popUpHabilitacionConfirmado = document.getElementById("pop-up-exito-habilitacion");

            document.getElementById("nombre-colaborador-exito").innerHTML = `<strong>Colaborador:</strong> ${nombreColab}`;

            // Mostrar el pop-up
            popUpHabilitacionConfirmado.showModal();
            borrarFila(idColab);
        } else {
            // Manejar error de respuesta (opcional)
            console.error('Error en la habilitación del colaborador:', response.status);
        }
    })
    .catch(error => {
        // Manejar errores de red
        console.error('Error al realizar la solicitud:', error);
    });
}

function cerrarExito(){
    const popUpHabilitacionConfirmado = document.getElementById("pop-up-exito-habilitacion");
    popUpHabilitacionConfirmado.close();
}



async function cargarFormularioRespondido(tipoColab, colabId) {
    try {
        // Realizar la solicitud al backend para obtener el formulario respondido por ID
        const response = await fetch(`formularios-respondidos/${tipoColab}/${colabId}`);

        if (!response.ok) {
            throw new Error("No se pudo cargar el formulario.");
        }

        const formulario = await response.json(); // Convertir la respuesta a JSON

        // Insertar el nombre del colaborador en el título
        document.querySelector("#pop-up-formulario h2").innerText = `Formulario respondido por ${formulario.nombreColaborador}`;

        // Limpiar cualquier respuesta previa en el contenedor
        const respuestasContainer = document.getElementById("respuestas");
        respuestasContainer.innerHTML = ""; // Limpiar el contenido previo

        // Iterar sobre las respuestas y agregarlas al contenedor
        formulario.respuestas.forEach(respuesta => {
            // Crear el contenedor de cada respuesta
            const respuestaDiv = document.createElement("div");
            respuestaDiv.classList.add("respuesta");

            // Crear el elemento de la pregunta
            const preguntaP = document.createElement("p");
            preguntaP.classList.add("pregunta");
            preguntaP.innerHTML = `<strong>${respuesta.enunciado}</strong>`;

            // Crear el elemento de la respuesta
            const respuestaP = document.createElement("p");
            respuestaP.classList.add("respuesta-detalle");
            respuestaP.innerText = respuesta.respuesta;

            // Agregar los elementos al div de respuesta
            respuestaDiv.appendChild(preguntaP);
            respuestaDiv.appendChild(respuestaP);

            // Agregar el div de respuesta al contenedor de respuestas
            respuestasContainer.appendChild(respuestaDiv);
        });

        // Mostrar el diálogo
        document.getElementById("pop-up-formulario").showModal();
    } catch (error) {
        console.error("Error al cargar el formulario:", error);
    }
}

function borrarFila(filaId){
    const fila = document.getElementById(filaId);
    if (fila) {
        fila.parentNode.removeChild(fila);;
    } else {
        console.error(`No se encontró la fila con ID: ${filaId}`);
    }
}

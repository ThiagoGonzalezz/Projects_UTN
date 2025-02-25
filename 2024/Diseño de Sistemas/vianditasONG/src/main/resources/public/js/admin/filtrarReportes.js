document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.btn.primary').addEventListener('click', function() {
        // Obtener los valores seleccionados de los filtros
        const tipoReporte = document.getElementById('tipo-reporte').value;
        const fecha = document.getElementById('fecha').value;

        // Obtener todas las filas de la tabla
        const filas = document.querySelectorAll('.results tbody tr');

        filas.forEach(fila => {
            // Obtener los valores de la fila
            const filaTipo = fila.getAttribute('data-tipo');
            const filaFecha = fila.getAttribute('data-fecha');

            // Verificar si la fila cumple con todos los filtros seleccionados
            const mostrar = (tipoReporte === '' || tipoReporte === filaTipo) &&
                            (fecha === '' || fecha === filaFecha);

            // Mostrar u ocultar la fila según los filtros
            fila.style.display = mostrar ? '' : 'none';
        });
    });
});


document.addEventListener('DOMContentLoaded', function () {
    // Plugin para ordenar fechas en formato yyyy-MM-dd
    $.fn.dataTable.ext.type.order['date-pre'] = function (data) {
        return Date.parse(data); // Convierte el formato yyyy-MM-dd a un valor numérico para ordenar
    };

    $('#tabla-reportes').DataTable({
        columnDefs: [
            { type: 'date', targets: 2 }  // La columna de la fecha es la tercera columna (índice 2)
        ],
        language: {
            "lengthMenu": "Mostrar _MENU_ entradas por página",
            "search": "Buscar:",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ entradas",
            "infoEmpty": "No hay entradas disponibles",
            "zeroRecords": "No se encontraron entradas"
        }
    });
});


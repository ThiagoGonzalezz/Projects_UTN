document.addEventListener('DOMContentLoaded', function () {
    // Plugin para ordenar fechas en formato yyyy-MM-dd
    $.fn.dataTable.ext.type.order['date-pre'] = function (data) {
        return Date.parse(data); // Convierte el formato yyyy-MM-dd a un valor numérico para ordenar
    };

    const table = $('#tabla-colaboradores').DataTable({
        columnDefs: [
            { type: 'date', targets: 3 } // La columna de la fecha es la cuarta columna (índice 3)
        ],
        language: {
            "lengthMenu": "Mostrar _MENU_ entradas por página",
            "search": "Buscar:",
            "info": "Mostrando _START_ a _END_ de _TOTAL_ entradas",
            "infoEmpty": "No hay entradas disponibles",
            "zeroRecords": "No se encontraron entradas"
        }
    });

    $('#tabla-colaboradores tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
        updateSelectedCount();
    });

    $('#recomendar-btn').click(function () {
        const selectedData = [];
        $('#tabla-colaboradores tbody tr.selected').each(function () {
            const id = $(this).data('id');
            selectedData.push(id);
        });

        if (selectedData.length === 0) {
            alert('Por favor, selecciona al menos un colaborador.');
            return;
        }

        const dataToSend = JSON.stringify(selectedData);

        $.ajax({
            url: '/admin/colaboradores/recomendacion',
            method: 'POST',
            contentType: 'application/json',
            data: dataToSend,
            success: function (response) {
                console.log('Respuesta del servidor:', response);
                $('#tabla-colaboradores tbody tr.selected').removeClass('selected');
                alert(response.message);
            },
            error: function (xhr, status, error) {
                console.error('Error al enviar los datos:', xhr, status, error);
                const errorMessage = xhr.responseJSON ? xhr.responseJSON.error : 'Ocurrió un error al realizar la recomendación.';
                alert(errorMessage);
            }
        });
    });

    function updateSelectedCount() {
        const count = $('#tabla-colaboradores tbody tr.selected').length;
        $('.selected-message').text(count + ' colaborador(es) seleccionado(s)');
    }
});
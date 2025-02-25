document.addEventListener('DOMContentLoaded', () => {
    const buttons = document.querySelectorAll('.ver-incidentes-btn');
    const popup = document.getElementById('popup-incidentes');
    const closePopup = document.querySelector('.close-popup');
    const incidentesList = document.getElementById('incidentes-list');

    buttons.forEach(button => {
        button.addEventListener('click', () => {
            const heladeraId = button.getAttribute('data-heladera-id');

            fetch(`/heladeras/${heladeraId}/incidentes`)
                .then(response => response.json())
                .then(data => {
                    incidentesList.innerHTML = ''; // Limpiar contenido previo

                    if (data.mensaje) {
                        incidentesList.innerHTML = `<p>${data.mensaje}</p>`;
                    } else {
                        data.forEach(incidente => {
                            const incidenteHtml = `
                                <div class="incidente-item">
                                 <div class="dato-contenedor"><strong>Descripción:</strong> ${incidente.descripcion}</div>
                                <div class="dato-contenedor"><strong>Fecha de Incidente:</strong> ${incidente.fechaIncidente}</div>
                                    <div class="dato-contenedor"><strong>Fecha de Resolución:</strong> ${incidente.fechaResolucion}</div>
                                    <div class="dato-contenedor"><strong>Técnico asignado:</strong> ${incidente.tecnicoResolutor}</div>
                                    ${incidente.fotoResolucion ? `
                                        <button class="ver-foto-btn" data-id="${incidente.id}">Ver Foto</button>
                                        <div class="historial-fallas-foto" id="foto-${incidente.id}" style="display: none;">
                                            <img src="${incidente.fotoResolucion}" alt="Foto del incidente" style="max-width: 100%; border-radius: 8px;">
                                        </div>
                                    ` : ''}
                                </div>
                            `;
                            incidentesList.innerHTML += incidenteHtml;
                        });

                        // Asignar funcionalidad a los botones "Ver Foto"
                        document.querySelectorAll('.ver-foto-btn').forEach(fotoButton => {
                            fotoButton.addEventListener('click', () => {
                                const id = fotoButton.getAttribute('data-id');
                                const fotoDiv = document.getElementById(`foto-${id}`);

                                // Alternar visibilidad de la imagen
                                if (fotoDiv.style.display === 'none' || fotoDiv.style.display === '') {
                                    fotoDiv.style.display = 'block';
                                } else {
                                    fotoDiv.style.display = 'none';
                                }
                            });
                        });
                    }

                    popup.style.display = 'flex';
                })
                .catch(error => {
                    console.error('Error al cargar incidentes:', error);
                });
        });
    });

    closePopup.addEventListener('click', () => {
        popup.style.display = 'none';
    });
});





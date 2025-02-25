document.addEventListener("DOMContentLoaded", function () {
    let heladeras = [];

    const heladeraListElement = document.getElementById("heladeraList");
    const addButton = document.getElementById("addButton");
    const searchInput = document.getElementById("searchInput");

    const addHeladeraModal = document.getElementById("addHeladeraModal");
    const editHeladeraModal = document.getElementById("editHeladeraModal");

    const popupIncidentes = document.getElementById("popup-incidentes");
    const closePopupIncidentes = document.querySelector(".close-popup");
    const incidentesList = document.getElementById("incidentes-list");

    document.querySelector(".close").onclick = function () {
        addHeladeraModal.style.display = "none";
    };

    document.querySelector(".close-edit").onclick = function () {
        editHeladeraModal.style.display = "none";
    };

    closePopupIncidentes.onclick = function () {
        popupIncidentes.style.display = "none";
    };

    fetch("/admin/heladeras")
        .then(response => response.json())
        .then(data => {
            heladeras = data;
            renderHeladeras(heladeras);
        });

    function renderHeladeras(heladeras) {
        heladeraListElement.innerHTML = "";

        if (heladeras.length === 0) {
            heladeraListElement.innerHTML = "<p>No se encontraron heladeras.</p>";
            return;
        }

        heladeras.forEach((heladera) => {
            const item = document.createElement("button");
            item.className = "gestion-heladeras-item";
            item.dataset.id = heladera.id;
            item.innerText = `Heladera ${heladera.id}`;

            const details = document.createElement("div");
            details.className = "gestion-heladeras-details";
            details.id = `details-${heladera.id}`;
            details.style.display = "none";
            details.innerHTML = `
                <div><strong>Nombre:</strong> ${heladera.nombreHeladera}</div>
                <div><strong>Modelo:</strong> ${heladera.modeloId}</div>
                <div><strong>Ubicación:</strong> ${heladera.nombrePuntoEstrategico}</div>
                <div><strong>Estado:</strong> ${heladera.estadoHeladera}</div>
                <div><strong>Cantidad de viandas:</strong> ${heladera.cantidadViandas}</div>
                <div><strong>Fecha de registro:</strong> ${heladera.fechaDeRegistro}</div>
                <div><strong>Días desactivada:</strong> ${heladera.diasDesactivada}</div>
                <div><strong>Fecha de última desactivación:</strong> ${heladera.fechaUltimaDesactivacion ? heladera.fechaUltimaDesactivacion : 'Nunca desactivada'}</div>
                <div><strong>Temperatura:</strong> ${heladera.temperatura} °C</div>
                <div><strong>Fecha de registro de temperatura:</strong> ${heladera.fechaTempertauraRegistrada}</div>
                <div><strong>Encargado:</strong> ${heladera.encargadoHeladera}</div>
                <div class="gestion-heladeras-actions">
                    <button class="edit-btn" data-id="${heladera.id}">Modificar</button>
                    <button class="delete-btn" data-id="${heladera.id}">Eliminar</button>
                    <button class="ver-incidentes-btn" data-id="${heladera.id}">Ver incidentes</button>
                </div>
            `;

            item.addEventListener("click", function () {
                if (details.style.display === "none") {
                    details.style.display = "block";
                } else {
                    details.style.display = "none";
                }
            });

            details.querySelector(".edit-btn").addEventListener("click", function (event) {
                event.stopPropagation();
                const id = event.target.dataset.id;

                fetch(`/admin/heladeras/${id}`)
                    .then(response => response.json())
                    .then(heladera => {
                        document.getElementById("editHeladeraId").value = heladera.heladeraId;
                        document.getElementById("editHeladeraNombre").value = heladera.nombreHeladera;
                        document.getElementById("editHeladeraModelo").value = heladera.modeloId;
                        document.getElementById("editHeladeraUbicacion").value = heladera.nombrePuntoEstrategico;
                        document.getElementById("editLocalidad").value = heladera.localidad;
                        document.getElementById("editCalle").value = heladera.calle;
                        document.getElementById("editAltura").value = heladera.altura;
                        document.getElementById("editHeladeraEstado").value = heladera.estadoHeladera;
                        document.getElementById("editHeladeraViandas").value = heladera.cantidadViandas;

                        editHeladeraModal.style.display = "block";
                    })
                    .catch(error => {
                        console.error("Error al obtener los datos de la heladera:", error);
                        alert("No se pudieron obtener los datos de la heladera.");
                    });
            });

            details.querySelector(".delete-btn").addEventListener("click", function (event) {
                event.stopPropagation();
                const id = event.target.dataset.id;

                if (confirm("¿Está seguro de que desea eliminar esta heladera?")) {
                    heladeras = heladeras.filter(h => h.id !== id);

                    renderHeladeras(heladeras);
                    fetch(`/admin/heladeras/${id}`, { method: 'DELETE' })
                        .then(response => {
                            if (response.status === 200) {
                                return response.json();
                            } else {
                                throw new Error("Error en el proceso de eliminación");
                            }
                        })
                        .then(data => {
                            alert(data.message);
                        });
                }
            });

            details.querySelector(".ver-incidentes-btn").addEventListener("click", function (event) {
                event.stopPropagation();
                const id = event.target.dataset.id;

                fetch(`/admin/heladeras/${id}/incidentes`)
                    .then(response => response.json())
                    .then(data => {
                        incidentesList.innerHTML = ""; // Limpia la lista de incidentes anterior

                        if (data.mensaje) {
                            incidentesList.innerHTML = `<p>${data.mensaje}</p>`;
                        } else if (Array.isArray(data)) {
                            if (data.length === 0) {
                                incidentesList.innerHTML = "<p>Esta heladera nunca tuvo incidentes.</p>";
                            } else {
                                data.forEach(incidente => {
                                    incidentesList.innerHTML += `
                            <div class="incidente-item">
                                <p><strong>Fecha de Resolución:</strong> ${incidente.fechaResolucion || "No resuelto"}</p>
                                <p><strong>Técnico Resolutor:</strong> ${incidente.tecnicoResolutor || "No asignado"}</p>
                                ${incidente.fotoResolucion ? `
                                    <button class="ver-foto-btn" data-id="${incidente.id}">Ver Foto</button>
                                    <div class="incidente-foto" id="foto-${incidente.id}" style="display: none;">
                                        <img src="/${incidente.fotoResolucion}" alt="Foto del incidente" style="max-width: 100%; border-radius: 8px;">
                                    </div>
                                ` : ''}
                            </div>
                        `;
                                });

                                // Funcionalidad para alternar visibilidad de las fotos
                                const fotoButtons = document.querySelectorAll(".ver-foto-btn");
                                fotoButtons.forEach(fotoButton => {
                                    fotoButton.addEventListener("click", () => {
                                        const id = fotoButton.getAttribute("data-id");
                                        const fotoDiv = document.getElementById(`foto-${id}`);
                                        fotoDiv.style.display = fotoDiv.style.display === "none" ? "block" : "none";
                                    });
                                });
                            }
                        } else {
                            incidentesList.innerHTML = "<p>Error inesperado al obtener los incidentes.</p>";
                        }

                        popupIncidentes.style.display = "flex";
                    })
                    .catch(error => {
                        console.error("Error al obtener los incidentes:", error);
                        incidentesList.innerHTML = "<p>Error al cargar los incidentes. Intente nuevamente.</p>";
                        popupIncidentes.style.display = "flex";
                    });
            });



            heladeraListElement.appendChild(item);
            heladeraListElement.appendChild(details);
        });
    }

    searchInput.addEventListener("input", function () {
        const filterValue = searchInput.value.toLowerCase();
        const filteredHeladeras = heladeras.filter(h => h.id.toString().toLowerCase().includes(filterValue));
        renderHeladeras(filteredHeladeras);
    });

    addButton.addEventListener("click", function () {
        addHeladeraModal.style.display = "block";
    });

    document.getElementById("addHeladeraForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const nombre = document.getElementById("heladeraNombre").value;
        const modeloDescripcion = document.getElementById("heladeraModelo").value;
        const ubicacion = document.getElementById("nombrePuntoEstrategico").value;
        const calle = document.getElementById("calle").value;
        const altura = document.getElementById("altura").value;
        const localidad = document.getElementById("localidad").value;
        const personaJuridica = document.getElementById("personaJuridica").value;

        const formData = new URLSearchParams();
        formData.append('heladeraNombre', nombre);
        formData.append('heladeraModelo', modeloDescripcion);
        formData.append('nombrePuntoEstrategico', ubicacion);
        formData.append('calle', calle);
        formData.append('altura', altura);
        formData.append('localidad', localidad);
        formData.append('personaJuridica', personaJuridica);

        fetch("/admin/gestion-de-heladeras", {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData.toString()
        }).then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error("Error al añadir heladera");
            }
        }).then(data => {
            alert(data.message);
            addHeladeraModal.style.display = "none";
            renderHeladeras(heladeras);
        });
    });

    document.getElementById("editHeladeraForm").addEventListener("submit", function (event) {
        event.preventDefault();

        const id = document.getElementById("editHeladeraId").value;
        const nombre = document.getElementById("editHeladeraNombre").value;
        const modeloId = document.getElementById("editHeladeraModelo").value;
        const ubicacion = document.getElementById("editHeladeraUbicacion").value;
        const calle = document.getElementById("editCalle").value;
        const altura = document.getElementById("editAltura").value;
        const localidad = document.getElementById("editLocalidad").value;
        const estadoHeladera = document.getElementById("editHeladeraEstado").value;
        const cantidadViandas = document.getElementById("editHeladeraViandas").value;
        const temperaturaHeladera = document.getElementById("editTemperaturaHeladera").value;

        const formData = new URLSearchParams();
        formData.append('heladeraId', id);
        formData.append('nombreHeladera', nombre);
        formData.append('modeloId', modeloId);
        formData.append('nombrePuntoEstrategico', ubicacion);
        formData.append('calle', calle);
        formData.append('altura', altura);
        formData.append('localidad', localidad);
        formData.append('estadoHeladera', estadoHeladera);
        formData.append('cantidadViandas', cantidadViandas);
        formData.append('temperaturaHeladera', temperaturaHeladera);

        fetch(`/admin/heladeras/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData.toString()
        }).then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error("Error al actualizar heladera");
            }
        }).then(data => {
            alert(data.message);
            editHeladeraModal.style.display = "none";
        });
    });
});













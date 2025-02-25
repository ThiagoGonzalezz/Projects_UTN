document.addEventListener("DOMContentLoaded", function () {
    let tecnicos = [];

    const tecnicoListElement = document.getElementById("tecnicoList");
    const addButton = document.getElementById("addButton");
    const searchInput = document.getElementById("searchInput");

    const addTecnicoModal = document.getElementById("addTecnicoModal");
    const editTecnicoModal = document.getElementById("editTecnicoModal");

    document.querySelector(".close").onclick = function () {
        addTecnicoModal.style.display = "none";
    };

    document.querySelector(".close-edit").onclick = function () {
        editTecnicoModal.style.display = "none";
    };

    // Fetch inicial para obtener todos los técnicos
    fetch("/admin/tecnicos")
        .then(response => response.json())
        .then(data => {
            tecnicos = data;
            renderTecnicos(tecnicos);
        });

    // Función para renderizar la lista de técnicos
    function renderTecnicos(tecnicos) {
        tecnicoListElement.innerHTML = "";
        if (tecnicos.length === 0) {
            tecnicoListElement.innerHTML = "<p>No se encontraron técnicos.</p>";
            return;
        }

        tecnicos.forEach((tecnico) => {
            const item = document.createElement("button");
            item.className = "gestion-tecnicos-item";
            item.dataset.id = tecnico.cuil;
            item.innerText = `Técnico ${tecnico.cuil}`;

            const details = document.createElement("div");
            details.className = "gestion-tecnicos-details";
            details.id = `details-${tecnico.cuil}`;
            details.style.display = "none";
            details.innerHTML = `
                <div><strong>Nombre:</strong> ${tecnico.nombre}</div>
                <div><strong>Apellido:</strong> ${tecnico.apellido}</div>
                <div><strong>Tipo de Documento:</strong> ${tecnico.tipoDeDocumento}</div>
                <div><strong>Número de Documento:</strong> ${tecnico.numeroDeDocumento}</div>
                <div><strong>Tipo de contacto:</strong> ${tecnico.tipoContacto}</div>
                <div><strong>Contacto:</strong> ${tecnico.contacto}</div>
                <div><strong>Punto de Cobertura:</strong> ${tecnico.areaDeCoberturaDireccion}</div>
                <div><strong>Radio de área de Cobertura:</strong> ${tecnico.areaDeCoberturaKM}</div>
                <div class="gestion-tecnicos-actions">
                    <button class="edit-btn" data-id="${tecnico.cuil}">Modificar</button>
                    <button class="delete-btn" data-id="${tecnico.cuil}">Eliminar</button>
                </div>
            `;

            item.addEventListener("click", () => {
                details.style.display = details.style.display === "none" ? "block" : "none";
            });

            details.querySelector(".edit-btn").addEventListener("click", function (event) {
                event.stopPropagation();
                const id = event.target.dataset.id;
                openEditTecnicoModal(id);
            });

            details.querySelector(".delete-btn").addEventListener("click", function (event) {
                event.stopPropagation();
                const id = event.target.dataset.id;
                if (confirm("¿Está seguro de que desea eliminar este técnico?")) {
                    deleteTecnico(id);
                }
            });

            tecnicoListElement.appendChild(item);
            tecnicoListElement.appendChild(details);
        });
    }

    // Abrir el modal para añadir un nuevo técnico
    addButton.addEventListener("click", () => addTecnicoModal.style.display = "block");

    // Enviar formulario para añadir un nuevo técnico
    document.getElementById("addTecnicoForm").addEventListener("submit", function (event) {
        event.preventDefault();
        const nombre = document.getElementById("nombreTecnico").value;
        const apellido = document.getElementById("apellidoTecnico").value;
        const tipoDocumento = document.getElementById("tipoDocumento").value;
        const numeroDocumento = document.getElementById("numeroDocumento").value;
        const cuil = document.getElementById("cuilTecnico").value;
        const tipoContacto = document.getElementById("tipoContacto").value;
        const contacto = document.getElementById("contacto").value;
        const areaCoberturaKM = document.getElementById("areaCoberturaKM").value;
        const provincia = document.getElementById("provincia").value;
        const localidad = document.getElementById("select-localidad").value;
        const calle = document.getElementById("calle").value;
        const altura = document.getElementById("altura").value;

        const formData = new URLSearchParams();
        formData.append('nombreTecnico', nombre);
        formData.append('apellidoTecnico', apellido);
        formData.append('tipoDocumento', tipoDocumento);
        formData.append('numeroDocumento', numeroDocumento);
        formData.append('cuilTecnico', cuil);
        formData.append('tipoContacto', tipoContacto);
        formData.append('contacto', contacto);
        formData.append('areaCoberturaKM', areaCoberturaKM);
        formData.append('provincia', provincia);
        formData.append('localidad', localidad);
        formData.append('calle', calle);
        formData.append('altura', altura);

        console.log("Enviando formulario de nuevo técnico:", formData.toString());

        fetch("/admin/gestion-de-tecnicos", {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData.toString()
        }).then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error("Error al añadir técnico");
            }
        })
            .then(data => {
                alert(data.message);
                addTecnicoModal.style.display = "none";
                fetchAndRenderTecnicos();
            })
            .catch(error => alert(error.message));
    });


    // Abrir el modal para editar un técnico existente
    function openEditTecnicoModal(id) {
        fetch(`/admin/gestion-de-tecnicos/${id}`)
            .then(response => response.json())
            .then(tecnico => {
                document.getElementById("editCuilTecnico").value = tecnico.cuil;
                document.getElementById("editNombreTecnico").value = tecnico.nombre;
                document.getElementById("editApellidoTecnico").value = tecnico.apellido;
                document.getElementById("editTipoDocumento").value = tecnico.tipoDeDocumento;
                document.getElementById("editNumeroDocumento").value = tecnico.numeroDeDocumento;
                document.getElementById("editContacto").value = tecnico.contacto;
                document.getElementById("editTipoContacto").value = tecnico.tipoDeContacto;
                document.getElementById("editAreaCoberturaKM").value = tecnico.areaDeCoberturaKM;
                document.getElementById("editProvincia").value = tecnico.provincia;
                document.getElementById("editCalle").value = tecnico.calle;
                document.getElementById("editAltura").value = tecnico.altura;

                editTecnicoModal.style.display = "block";
            })
            .catch(error => {
                console.error("Error al obtener los datos del técnico:", error);
                alert("No se pudieron obtener los datos del técnico.");
            });
    }

    document.getElementById("editTecnicoForm").addEventListener("submit", function (event) {
        event.preventDefault();

        // Obtener valores de cada campo del formulario de edición de técnicos
        const cuil = document.getElementById("editCuilTecnico").value;
        const nombre = document.getElementById("editNombreTecnico").value;
        const apellido = document.getElementById("editApellidoTecnico").value;
        const tipoDocumento = document.getElementById("editTipoDocumento").value;
        const numeroDocumento = document.getElementById("editNumeroDocumento").value;
        const contacto = document.getElementById("editContacto").value;
        const tipoContacto = document.getElementById("editTipoContacto").value;
        const areaCoberturaKM = document.getElementById("editAreaCoberturaKM").value;
        const provincia = document.getElementById("editProvincia").value;
        const localidad = document.getElementById("editLocalidad").value;
        const calle = document.getElementById("editCalle").value;
        const altura = document.getElementById("editAltura").value;


        // Crear el objeto formData y agregar los campos manualmente
        const formData = new URLSearchParams();
        formData.append('cuil', cuil);
        formData.append('nombre', nombre);
        formData.append('apellido', apellido);
        formData.append('tipoDocumento', tipoDocumento);
        formData.append('numeroDocumento', numeroDocumento);
        formData.append('contacto', contacto);
        formData.append('tipoContacto', tipoContacto);
        formData.append('areaCoberturaKM', areaCoberturaKM);
        formData.append('provincia', provincia);
        formData.append('localidad', localidad);
        formData.append('calle', calle);
        formData.append('altura', altura);

        console.log("Actualizando técnico con CUIL:", cuil);

        // Enviar la solicitud de actualización
        fetch(`/admin/gestion-de-tecnicos/${cuil}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: formData.toString()
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json(); // Obtenemos el JSON de la respuesta
                } else {
                    throw new Error("Error al actualizar técnico");
                }
            })
            .then(data => {
                alert(data.message);
                editTecnicoModal.style.display = "none";
                fetchAndRenderTecnicos(); // Actualizar la lista de técnicos
            })
            .catch(error => alert(error.message));
    });




    // Función para eliminar un técnico
    function deleteTecnico(id) {
        fetch(`/admin/gestion-de-tecnicos/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    throw new Error("Error en el proceso de eliminación");
                }
            })
            .then(data => {
                alert(data.message);
                fetchAndRenderTecnicos();
            })
            .catch(error => alert(error.message));
    }

    // Filtrar técnicos en función del valor de búsqueda
    searchInput.addEventListener("input", function () {
        const filterValue = searchInput.value.toLowerCase();
        const filteredTecnicos = tecnicos.filter(t => t.cuil.toLowerCase().includes(filterValue));
        renderTecnicos(filteredTecnicos);
    });

    // Función para obtener y renderizar la lista de técnicos
    function fetchAndRenderTecnicos() {
        fetch("/admin/tecnicos")
            .then(response => response.json())
            .then(data => {
                tecnicos = data;
                renderTecnicos(tecnicos);
            });
    }

    function cargarLocalidades() {
        const selectLocalidades = document.getElementById("select-localidad");
        const selectProvincia = document.getElementById("provincia");
        const provinciaSeleccionada = selectProvincia.value;

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


    function cargarLocalidadesEdit() {
        const selectLocalidades = document.getElementById("editLocalidad");
        const selectProvincia = document.getElementById("editProvincia");
        const provinciaSeleccionada = selectProvincia.value;

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

    // Evento para cargar las localidades al cambiar la provincia
    document.getElementById("provincia").addEventListener("change", cargarLocalidades);
    document.getElementById("editProvincia").addEventListener("change", cargarLocalidadesEdit);
});




function enviarFormulario() {
    // Crear un nuevo objeto FormData
    const formData = new FormData();

    // Obtener el ID de la heladera
    const heladeraId = document.getElementById("heladera-destino-id").value;

    // Asegurarse de que el ID de la heladera no esté vacío
    if (!heladeraId) {
        console.error("El ID de la heladera es necesario.");
        return;
    }

    // Agregar el ID de la heladera al FormData
    formData.append("heladera-destino-id", heladeraId);

    // Obtener todos los formularios dentro del contenedor
    const formContainer = document.querySelector('.form-y-envio-container');
    const forms = formContainer.querySelectorAll('form');

    let formularioValido = true;

    // Resetear estilos y mensajes de error
    limpiarMensajesError();

    // Procesar todos los formularios (padre e hijos)
    forms.forEach((form, formIndex) => {
        const inputs = form.querySelectorAll('input[required], select[required]');

        // Validar cada formulario
        if (!procesarCampos(inputs)) {
            formularioValido = false;
        }

        // Si es válido, añadir los datos de este formulario al FormData
        if (formularioValido) {
            inputs.forEach(input => {
                // Cambiar el índice del nombre para que se ajuste al índice actual del formulario
                const fieldName = input.name.replace(/\d+/, formIndex); // Actualizar el índice del nombre del campo
                formData.append(fieldName, input.value);
            });
            popUpBienvenido();
        }
    });

    // Si todos los formularios son válidos, enviar los datos
    if (formularioValido) {
        console.log("Todos los formularios son válidos");

        // Crear una solicitud XMLHttpRequest para enviar los datos
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/donacion-vianda", true); // Cambiar por la ruta adecuada en el backend

        // Enviar el formData al backend
        xhr.send(formData);

        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log("Formulario enviado correctamente");
            } else {
                console.error("Error al enviar el formulario");
            }
        };
    } else {
        console.log("Uno o más formularios tienen errores");
    }
}
// Función para limpiar mensajes de error anteriores
function limpiarMensajesError() {
    const mensajesError = document.querySelectorAll(".mensaje-error");
    mensajesError.forEach(mensaje => mensaje.remove()); // Eliminar mensajes de error anteriores
}

// Función para limpiar errores de un input específico
function limpiarError(input) {
    const label = document.querySelector(`label[for="${input.id}"]`);
    if (label) {
        label.classList.remove("label-error"); // Remover clase de error en el label
    }
    input.classList.remove("input-error"); // Remover clase de error en el input
}

// Función para procesar la validación de los campos de cada formulario
function procesarCampos(inputs) {
    let formularioValido = true;

    inputs.forEach(input => {
        // Validar que el campo no esté vacío o con valor inválido (-1)
        if (input.value === '' || input.value === '-1') {
            const label = document.querySelector(`label[for="${input.id}"]`);

            if (label) {
                label.classList.add("label-error");
            }
            input.classList.add("input-error");

            formularioValido = false;

            // Limpiar error cuando el usuario interactúa con el campo
            if (input.type === 'text') {
                input.addEventListener("input", () => limpiarError(input));
            } else {
                input.addEventListener("click", () => limpiarError(input));
            }
        }
    });

    return formularioValido; // Retornar si los campos son válidos
}
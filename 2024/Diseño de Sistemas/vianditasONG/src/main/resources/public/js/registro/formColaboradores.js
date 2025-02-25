function enviarFormulario() {
    const form = document.getElementById('formulario-registro');
    const inputs = form.querySelectorAll('input[required], select[required]');
    let formularioValido = true;

    limpiarMensajeError();

    formularioValido = procesarCampos(inputs);

    if(formularioValido){postearFormulario()}

    mostrarResultadoEnvio(formularioValido);

}

function postearFormulario(){
     const form = document.getElementById("formulario-registro");

     const formData = new FormData(form);

     fetch(form.action, {
        method: form.method,
        body: formData
      })
      .then(data => {
        console.log("Respuesta del servidor:", data);
      })
      .catch(error => {
        console.error("Error al enviar el formulario:", error);
      });
}

function limpiarMensajeError(){
    var mensajesError = document.querySelectorAll(".mensaje-error");
        mensajesError.forEach(function(mensaje) {
        mensaje.remove();
    });
}

function limpiarError(input){
    var label = document.querySelector(`label[for="${input.id}"]`);

    label.classList.remove("label-error");
    input.classList.remove("input-error");
}


function mostrarMensajeError(){
   const contenedorBtnFormulario = document.getElementById("footer-form-container");
   const btnEnvioForm = document.getElementById("btn-envio-form-container");

    // Crear el mensaje de error
    var mensajeError = document.createElement("p");
    mensajeError.classList.add("mensaje-error");
    mensajeError.innerHTML =`
         <i class="fas fa-exclamation-circle icono-error"></i>
        ${"Debes completar todos los campos obligatorios"}
        `;

    // Insertar el mensaje de error después del campo de la password
    contenedorBtnFormulario.insertBefore(mensajeError, btnEnvioForm);
}

function mostrarResultadoEnvio(flagValido){
    // Mostrar mensaje de error si el formulario no es válido
    if (!flagValido) {
        mostrarMensajeError();
    } else {
        const popUpRegistro = document.getElementById("pop-up-envio-registro");

        // Enviar el formulario si es válido
        //form.submit();

        popUpRegistro.showModal();

    }
}

function procesarCampos(inputs){
    let formularioValido = true;

    formularioValido = validarContactoPrincipal();

    inputs.forEach(input => {
        if (input.value === '' || input.value === '-1'){
            var label = document.querySelector(`label[for="${input.id}"]`);
            var grid = input.closest('.grid-item');

            label.classList.add("label-error");
            input.classList.add("input-error");
            grid.classList.add("select2-error");

            formularioValido = false;


            if(input.type === 'text' || input.type === 'NUMBER' || input.type === 'DATE'){
                input.addEventListener("input", function() {
                    limpiarError(input);
                });
            } else if(input.tagName.toLowerCase() === 'select'){
                input.style.color = "red";

                if ($(input).hasClass('select2-hidden-accessible')) {
                    $(input).on('select2:select', function(e) {
                        limpiarError(input);
                        grid.classList.remove("select2-error");
                    });

                }
                input.addEventListener("input", function() {
                    limpiarError(input);
                    grid.classList.remove("select2-error");
                });
            }
        }
    });

    return formularioValido;
}


function validarContactoPrincipal(){
    const inputValorContacto = document.getElementById("contactos-input-principal");
    let flagFormValido = true;

    if(inputValorContacto.value === ''){
        flagFormValido = false;

        var label = document.querySelector(`label[for="${"contactos-select"}"]`);
        label.classList.add("label-error");
        inputValorContacto.classList.add("input-error")


        inputValorContacto.addEventListener("input", function() {
            var label = document.querySelector(`label[for="${"contactos-select"}"]`);
            label.classList.remove("label-error");
            inputValorContacto.classList.remove("input-error");
        });


    }

    return flagFormValido;
}


function addContact() {
    const container = document.getElementById('contactos-container');
    const contactAddBtn = document.getElementById('contact-add-btn');
    const currentContacts = container.children.length;
    const maxContacts = 4;

    if (currentContacts >= maxContacts - 1) {
        contactAddBtn.style.display = 'none';
    }

    const newContactDiv = document.createElement('div');
    newContactDiv.classList.add('contacto-container');
    newContactDiv.innerHTML = `
        <select id="contacto-${currentContacts}" name="tipo-contacto[]" oninput="updateColorSelect(this.id);" onchange="updatePlaceholder(this)">
            <option value="-1" disabled selected hidden>Seleccione el tipo de contacto </option>
            <option value="TELEGRAM">Telegram</option>
            <option value="WHATSAPP">WhatsApp</option>
            <option value="CORREO">Email</option>
        </select>
        <input type="text" name="contacto[]" placeholder="Ingrese el contacto" class="text-input-contactos">
        <button type="button" onclick="removeContact(this)" class="btn-secundario">Eliminar</button>
    `;
    container.appendChild(newContactDiv);
}

function removeContact(button) {
    button.parentElement.remove();
    const container = document.getElementById('contactos-container');
    const contactAddBtn = document.getElementById('contact-add-btn');
    const currentContacts = container.children.length;
    const maxContacts = 4;

    if (currentContacts < maxContacts) {
        contactAddBtn.style.display = 'inline-block';
        return;
    }
}

function updatePlaceholder(selectElement) {
    const input = selectElement.nextElementSibling;
    const type = selectElement.value;

    if (input) {
        switch (type) {
            case '1':
                input.placeholder = 'Ingrese el usuario de Telegram';
                break;
            case '2':
                input.placeholder = 'Ingrese el número de WhatsApp';
                break;
            case '3':
                input.placeholder = 'Ingrese la dirección de email';
                break;
            default:
                input.placeholder = 'Ingrese el contacto';
        }
    }
}

function updateColorSelect(idSelect){
    const select = document.getElementById(idSelect);

    if(select.value == "-1"){
        select.style.color = "grey";
    }else{
        select.style.color = "black";
    }
}

function cambiarANegro(id){
    const item = document.getElementById(id);
    item.style.color = "black"
}

function updateSelect2(idSelect2) {
    const select2 = document.getElementById(idSelect2);
    const labelSelect2 = document.querySelector(`label[for="${idSelect2}"]`);
    const gridSelect2 = select2.closest('.grid-item');


    if (select2.value !== "-1" && gridSelect2.classList.contains("select2-error")) {
        labelSelect2.classList.remove("label-error");
        gridSelect2.classList.remove("select2-error");
    }
}
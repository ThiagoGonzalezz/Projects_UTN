function toggleDropdown(botonOpcion) {
    const dropdown = botonOpcion.closest('.dropdown');

    dropdown.classList.toggle("open");
}

function volver(){
    window.location.href = "/formularios";
}

function focusTitulo() {
    const titulo = document.getElementById('editable-titulo');
    titulo.focus(); // Coloca el foco en el elemento
}

function actualizarNombreForm() {
    var nuevoNombre = document.getElementById("editable-titulo").innerText;

    var formData = new FormData();
    formData.append('nombre', nuevoNombre);

    try {
        const response = fetch('edicion-nombre', {
            method: 'POST',
            body: formData // Usamos FormData directamente
        });

        // Si es necesario, puedes agregar el manejo de la respuesta aquí.
    } catch(error) {
        console.error('Error en la conexión:', error);
    }
}

///////////////////////////////////////////////////////////////////////////
//---Pop Up eliminar Pregunta----------------------------------------------
///////////////////////////////////////////////////////////////////////////

function popUpEliminar(boton) {
    const popUp = document.getElementById("pop-up-eliminar");

    const enunciado = boton.closest('.formulario').querySelector('h2').textContent;

    const pregunta = boton.closest('.formulario');

    const encabezado = popUp.querySelector("h4");
    encabezado.textContent = `${enunciado}`;

    const confirmarBoton = popUp.querySelector(".confirmar-boton");
    confirmarBoton.onclick = function () {
        confirmarEliminar(pregunta);
    };

    popUp.showModal();
}


function cerrarEliminar() {
    const popUp = document.getElementById("pop-up-eliminar");
    popUp.close();
}

function confirmarEliminar(pregunta){

    fetch(`edicion-pregunta-eliminada/${pregunta.id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (response.status === 200) {
            pregunta.remove();
            document.getElementById("pop-up-eliminar").close();
        } else {
            console.error('Error al eliminar el formulario. Código de respuesta:', response.status);
        }
    })
    .catch(error => {
        console.error('Error al eliminar el formulario:', error);
    });
}

///////////////////////////////////////////////////////////////////////////
//---Pop Up Agregar/Modificar Pregunta-------------------------------------
///////////////////////////////////////////////////////////////////////////

function formAgregarPregunta(){
    limpiarFormulario("AGREGAR");
    abrirModal();
}

function limpiarFormulario(accion) {
    // Limpiar el campo enunciado y restablecer el placeholder
    document.getElementById('enunciado').value = '';

    if(accion === "AGREGAR"){
        document.getElementById('titulo-accion-pop-up').value = "Agregar Nueva Pregunta";
        document.getElementById('boton-confirm-preg').onclick = crearPregunta;
    }else{
        document.getElementById('titulo-accion-pop-up').value = "Editar Pregunta";
    }

    // Restablecer la selección de la modalidad
    document.getElementById('modalidad').selectedIndex = 0;  // Opcional o por defecto
    
    // Restablecer el tipo de pregunta
    document.getElementById('tipo-pregunta').selectedIndex = 0;  // Selección Única por defecto
    
    // Limpiar las opciones existentes
    const opcionesContainer = document.getElementById('opciones-container');
    const opciones = opcionesContainer.getElementsByClassName('opcion-container');
    while (opciones.length > 0) {
        opciones[0].remove();  // Elimina cada opción
    }
    
    // Volver a agregar una opción vacía
    agregarOpcion();  // Llamar a la función que agrega una nueva opción
    
    // Restablecer el tipo de entrada (si respuesta libre)
    document.getElementById('tipo-entrada-container').style.display = 'none';  // Ocultar el tipo de entrada
    
    // Resetear cualquier valor adicional si es necesario (por ejemplo, valores de campos select)
    document.getElementById('tipo-entrada').selectedIndex = 0;
    
    // Cerrar el modal si es necesario
    document.getElementById('modalAgregarPregunta').close();
}

// Función para agregar una opción vacía (utilizada por la función limpiarFormulario)
function agregarOpcion() {
    const opcionesContainer = document.getElementById('opciones-container');
    const nuevaOpcionContainer = document.createElement('div');
    nuevaOpcionContainer.classList.add('opcion-container');
    
    nuevaOpcionContainer.innerHTML = `
        <input type="text" class="opcion" placeholder="Escribe una opción">
        <button class="accion-btn" title="Eliminar" onclick="eliminarOpcion()">
            <i class="fas fa-trash-alt"></i>
        </button>
    `;
    
    opcionesContainer.appendChild(nuevaOpcionContainer);
}

function abrirModal() {
    const modal = document.getElementById('modalAgregarPregunta');
    modal.showModal();
}

// Cierra el modal
function cerrarModal() {
    const modal = document.getElementById('modalAgregarPregunta');
    modal.close();
}

// Control de visibilidad de secciones según el tipo de pregunta
function actualizarFormPregunta() {
    const tipoPregunta = document.getElementById('tipo-pregunta').value;
    const opcionesContainer = document.getElementById('opciones-container');
    const tipoEntradaContainer = document.getElementById('tipo-entrada-container');

    if (tipoPregunta === 'Selección Única' || tipoPregunta === 'Selección Múltiple') {
        opcionesContainer.style.display = 'block';
        tipoEntradaContainer.style.display = 'none';
    } else if (tipoPregunta === 'Respuesta Libre') {
        opcionesContainer.style.display = 'none';
        tipoEntradaContainer.style.display = 'block';
    }
}

// Agregar nuevas opciones cuando sea necesario
function agregarOpcion() {
    // Crear contenedor para la opción
    const opcionContainer = document.createElement('div');
    opcionContainer.className = 'opcion-container';

    // Crear el input
    const opcionInput = document.createElement('input');
    opcionInput.type = 'text';
    opcionInput.className = 'opcion';
    opcionInput.placeholder = 'Escribe una opción';

    // Crear el botón de eliminar
    const eliminarBtn = document.createElement('button');
    eliminarBtn.className = 'accion-btn';
    eliminarBtn.title = 'Eliminar';
    eliminarBtn.innerHTML = '<i class="fas fa-trash-alt"></i>';

    // Agregar funcionalidad al botón de eliminar
    eliminarBtn.onclick = function () {
        const opcionesDiv = document.querySelector('.opciones');
        opcionesDiv.removeChild(opcionContainer);
    };

    // Añadir el input y el botón al contenedor
    opcionContainer.appendChild(opcionInput);
    opcionContainer.appendChild(eliminarBtn);

    // Agregar el contenedor completo al contenedor principal de opciones
    const opcionesDiv = document.querySelector('.opciones');
    opcionesDiv.appendChild(opcionContainer);
}

function guardarPregunta(id){
    const enunciado = document.getElementById('enunciado').value;
    const modalidad = document.getElementById('modalidad').value;
    const tipoPregunta = document.getElementById('tipo-pregunta').value;


    agregarPregunta(enunciado, modalidad, tipoPregunta, id);
    cerrarModal();
}


function agregarPregunta(enunciado, modalidad, tipoPregunta, id) { 


    // Crear el contenedor del nuevo formulario
    const nuevoFormulario = document.createElement('div');
    nuevoFormulario.classList.add('formulario');
    nuevoFormulario.id = id;
    
    // Crear el encabezado del nuevo formulario
    const encabezado = document.createElement('div');
    encabezado.classList.add('encabezado');
    
    const tituloPregunta = document.createElement('h2');
    tituloPregunta.innerText = enunciado;  // Texto predeterminado
    
    const acciones = document.createElement('div');
    acciones.classList.add('acciones');
    
    // Botón para editar la pregunta
    const botonEditar = document.createElement('button');
    botonEditar.classList.add('accion-btn');
    botonEditar.setAttribute('title', 'Editar');
    botonEditar.onclick = () => formEditarPregunta(botonEditar);
    botonEditar.innerHTML = '<i class="fas fa-pencil-alt"></i>';
    
    // Botón para eliminar la pregunta
    const botonEliminar = document.createElement('button');
    botonEliminar.classList.add('accion-btn');
    botonEliminar.setAttribute('title', 'Eliminar');
    botonEliminar.onclick = function () {
        popUpEliminar(this);
    };
    botonEliminar.innerHTML = '<i class="fas fa-trash-alt"></i>';
    
    // Agregar los botones al contenedor de acciones
    acciones.appendChild(botonEditar);
    acciones.appendChild(botonEliminar);
    
    // Agregar el título de la pregunta y las acciones al encabezado
    encabezado.appendChild(tituloPregunta);
    encabezado.appendChild(acciones);
    
    // Crear los detalles del formulario
    const detalle = document.createElement('div');
    detalle.classList.add('detalle');
    
    // Modalidad de la pregunta
    const modalidadRect = document.createElement('p');
    modalidadRect.innerText = modalidad; 
    detalle.appendChild(modalidadRect);
    
    // Tipo de pregunta (ej. Selección Múltiple)
    const tipoPreguntaRect = document.createElement('p');
    tipoPreguntaRect.innerText = tipoPregunta;
    detalle.appendChild(tipoPreguntaRect);


    agregarOpcionesOTipoRespuesta(tipoPregunta, detalle);
    
    
    // Agregar el encabezado y los detalles al formulario
    nuevoFormulario.appendChild(encabezado);
    nuevoFormulario.appendChild(detalle);
    
    const contenedorPregs = document.getElementById('pregs-container');
    const botonNuevaPreg = document.getElementById('boton-nueva-preg');
    
    contenedorPregs.insertBefore(nuevoFormulario, botonNuevaPreg);
}

function agregarOpcionesOTipoRespuesta(tipoRespuesta, detalle){
    if(tipoRespuesta == 'Respuesta Libre'){
        const tipoRespuesta = document.getElementById('tipo-entrada').value;

    
        let nuevoParrafo = document.createElement('p');
        nuevoParrafo.textContent = `Respuesta tipo ${tipoRespuesta}`;
    
        detalle.appendChild(nuevoParrafo);

    }else {
        
        // Crear el contenedor de opciones
        const dropdown = document.createElement('div');
        dropdown.classList.add('dropdown');

        
        const botonOpciones = document.createElement('button');
        botonOpciones.classList.add('dropdown-btn');
        botonOpciones.innerText = 'Opciones';
        botonOpciones.setAttribute('onclick', 'toggleDropdown(this)');

        const listaOpciones = document.createElement('ul');
        listaOpciones.classList.add('dropdown-menu');
        
        // Obtener las opciones dinámicas (aquí estamos asumiendo que las opciones se agregan en un contenedor con id 'opciones-container')
        const opcionesContainer = document.getElementById('opciones-container');
        const opciones = opcionesContainer.querySelectorAll('.opcion-container input');
        
        opciones.forEach(input => {
            const opcionTexto = input.value;
            if(opcionTexto) {
                const li = document.createElement('li');
                li.innerText = opcionTexto;
                listaOpciones.appendChild(li);
            }
        });
        
        dropdown.appendChild(botonOpciones);
        dropdown.appendChild(listaOpciones);
        
        detalle.appendChild(dropdown);
    }
}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////INTERACCIONES CON BACK PARA CREAR////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


function preguntaAGuardar(){
    // Obtener valores de los campos del modal
    const enunciado = document.getElementById("enunciado").value.trim();
    const modalidad = document.getElementById("modalidad").value;
    const tipoPregunta = document.getElementById("tipo-pregunta").value;

    let tipoRespuesta = null; // Por defecto nulo
    if (tipoPregunta === "Respuesta Libre") {
        tipoRespuesta = document.getElementById("tipo-entrada").value;
    }

    const opciones = [];
    if (tipoPregunta !== "Respuesta Libre") {
        // Obtener todas las opciones del contenedor
        const opcionElems = document.querySelectorAll(".opcion-container .opcion");
        opcionElems.forEach((opcionElem, index) => {
            const descripcion = opcionElem.value;
            if (descripcion) {
                opciones.push({
                    id: null,
                    enunciado: descripcion
                });
            }
        });
    }

    const pregunta = {
        id: null,
        enunciado: enunciado,
        modalidad: modalidad,
        tipoPregunta: tipoPregunta,
        tipoRespuesta: tipoRespuesta,
        opciones: opciones
    };

    return pregunta;
}

function crearPregunta() {
    const pregunta = preguntaAGuardar();

    fetch('edicion-pregunta-nueva', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pregunta)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Error en la creación de la pregunta');
        }
    })
    .then(data => {
        guardarPregunta(data.id);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////INTERACCIONES CON BACK PARA MODIFICAR////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function formEditarPregunta(botonEditar){
    limpiarFormulario("MODIFICAR");
    precargarEdicion(botonEditar);
    abrirModal();
}

function precargarEdicion(botonEditar){


    const formulario = botonEditar.closest('.formulario');
    if (!formulario) return;

    // Obtener los datos del formulario
    const id = formulario.id;
    const enunciado = formulario.querySelector('.encabezado h2').textContent.trim();
    const modalidad = formulario.querySelector('.detalle p:nth-child(1)').textContent.trim();
    const tipoPregunta = formulario.querySelector('.detalle p:nth-child(2)').textContent.trim();
    const opcionesElems = formulario.querySelectorAll('.dropdown-menu li');

    // Configurar el modal
    document.getElementById('titulo-accion-pop-up').textContent = `Editar Pregunta`;
    document.getElementById('enunciado').value = enunciado;
    document.getElementById('modalidad').value = modalidad;

    const tipoPreguntaElem = document.getElementById('tipo-pregunta');
    tipoPreguntaElem.value = tipoPregunta;
    actualizarFormPregunta(); // Asegura que la interfaz se actualice según el tipo de pregunta

    // Limpiar las opciones actuales en el modal
    const opcionesContainer = document.getElementById('opciones-container').querySelector('.opciones');
    opcionesContainer.innerHTML = '';

    if (tipoPregunta !== 'Respuesta Libre') {
        // Agregar las opciones al modal
        opcionesElems.forEach((opcionElem) => {
            const descripcion = opcionElem.textContent.trim();
            const nuevaOpcion = document.createElement('div');
            nuevaOpcion.classList.add('opcion-container');
            nuevaOpcion.innerHTML = `
                <input type="text" class="opcion" value="${descripcion}" placeholder="Escribe una opción">
                <button class="accion-btn" title="Eliminar" onclick="eliminarOpcion(this)">
                    <i class="fas fa-trash-alt"></i>
                </button>
            `;
            opcionesContainer.appendChild(nuevaOpcion);
        });
    }else{

    const tipoEntrada = obtenerTipoEntrada(formulario.querySelector('.detalle p:nth-child(3').textContent.trim())
    const tipoEntradaIndex = obtenerIndicePorTexto(tipoEntrada);

    document.getElementById('tipo-entrada').selectedIndex = tipoEntradaIndex;
    }

    document.getElementById('boton-confirm-preg').onclick = function() {
        modificarPregunta(id); // Aquí se llama cuando el botón es presionado
    };
}


function obtenerTipoEntrada(cadena) {
    const palabras = cadena.split(' '); // Divide la cadena en palabras usando el espacio como separador
    if (palabras.length >= 3) {
        return palabras[2]; // Retorna la tercera palabra (índice 2, ya que los índices empiezan desde 0)
    } else {
        return null; // Retorna null si no hay suficiente cantidad de palabras
    }
}

function obtenerIndicePorTexto(textoBuscado) {
    const selectTipoEntrada = document.querySelector('#tipo-entrada');
    const opciones = selectTipoEntrada.options;

    for (let i = 0; i < opciones.length; i++) {
        if (opciones[i].text === textoBuscado) {
            return i; // Retorna el índice de la opción que coincide con el texto
        }
    }
    return -1; // Retorna -1 si no encuentra el texto
}

function modificarPregunta(idPreg) {
    const pregunta = preguntaAGuardar();

    pregunta.id = idPreg;

    fetch(`edicion-pregunta-modificada/${idPreg}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(pregunta)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Error en la creación de la pregunta');
        }
    })
    .then(data => {
        reemplazarPregunta(pregunta);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function reemplazarPregunta(pregunta) {
    const formulario = document.getElementById(pregunta.id);
    const enunciado = document.getElementById('enunciado').value;
    const modalidad = document.getElementById('modalidad').value;
    const tipoPregunta = document.getElementById('tipo-pregunta').value;

    formulario.remove();

    agregarPregunta(enunciado, modalidad, tipoPregunta, pregunta.id);

    cerrarModal();
}

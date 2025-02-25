function editarFormulario(id){
    window.location.href = `formularios/${id}/edicion`;
}

function crearNuevoForm() {
    fetch('formularios', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
        })
    })
    .then(response => {
        if (response.redirected) {
            window.location.href = response.url;
        }
    })
    .catch(error => {
        console.error('Error al crear el formulario:', error);
    });
}

function popUpEliminarFormulario(id) {
    const popUp = document.getElementById("pop-up-eliminar");
    const enunciado = document.getElementById(`nombre-${id}`).textContent;

    const preguntaTitulo = document.getElementById("pregunta");
    preguntaTitulo.textContent = enunciado;

    const confirmarBoton = popUp.querySelector(".confirmar-boton");
    confirmarBoton.onclick = function() {
        eliminarFormulario(id);
    };

    popUp.showModal();
}

function eliminarFormulario(id) {
    fetch(`formularios/${id}/eliminacion`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({})
    })
    .then(response => {
        if (response.status === 200) {
            document.getElementById(id).remove();
            document.getElementById("pop-up-eliminar").close();
        } else {
            console.error('Error al eliminar el formulario. Código de respuesta:', response.status);
        }
    })
    .catch(error => {
        console.error('Error al eliminar el formulario:', error);
    });
}

function cerrarEliminar(){
    document.getElementById("pop-up-eliminar").close();
}
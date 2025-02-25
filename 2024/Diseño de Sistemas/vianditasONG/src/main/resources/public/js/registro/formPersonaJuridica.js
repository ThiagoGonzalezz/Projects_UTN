function addFormaColabPersonaJuridica() {
    const container = document.getElementById('colaboraciones-container');
    const colabAddBtn = document.getElementById('colab-add-btn');
    const currentColabs = container.children.length;
    const maxColabs = 3;

    if (currentColabs >= maxColabs - 1) {
        colabAddBtn.style.display = 'none';
    }

    const newColabDiv = document.createElement('div');
    newColabDiv.classList.add('colab-container');
    newColabDiv.innerHTML = `
        <select id="formas-de-colaboracion-${currentColabs}" name="formas-de-colaboracion[]" required oninput="updateColorSelect(this.id);" onchange="actualizarOpcionesColabPersonaJuridica()">
                                            <option value=-1 hidden disabled selected class="opcion-placeholder">Seleccione un tipo de colaboración </option>
                                            <option value="DONACION_DINERO">Donación de dinero</option>
                                            <option value="HACERSE_CARGO_DE_HELADERAS">Hacerse cargo de una heladera</option>
                                            <option value="OFERTA_PRODUCTOS_Y_SERVICIOS">Oferta de productos y servicios</option>
        </select>
        <button type="button" onclick="removeColab(this)" class="btn-secundario">Eliminar</button>
    `;
    container.appendChild(newColabDiv);

    actualizarOpcionesColabPersonaJuridica();
}

function actualizarOpcionesColabPersonaJuridica(){
    const selects = document.querySelectorAll('#colaboraciones-container select');
    const selectedValues = Array.from(selects).map(select => select.value);

    let tarjetasSeleccionada = false;

    selects.forEach(select => {
        Array.from(select.options).forEach(option => {
            if (selectedValues.includes(option.value) && option.value !== select.value) {
                option.disabled = true;
            } else if(option.value != "-1"){
                option.disabled = false;
            }
        });
    });
}

function removeColab(button) {
    button.parentElement.remove();
    const container = document.getElementById('colaboraciones-container');
    const colabAddBtn = document.getElementById('colab-add-btn');
    const currentColabs = container.children.length;
    const maxColabs = 4;

    if (currentColabs < maxColabs) {
        colabAddBtn.style.display = 'inline-block';
        return;
    }
}

$(document).ready(function() {
    $('#select-rubro').select2({
        placeholder: 'Ingrese el rubro',
        allowClear: true,
        width: '100%',
        minimumResultsForSearch: ''
    });
});


function cargarRubros() {
    const selectRubro = document.getElementById("select-rubro");

    fetch("/rubros-persona-juridica")
        .then(response => {
            if (!response.ok) {
                throw new Error("Error en la respuesta del servidor: " + response.status);
            }
            return response.json();
        })
        .then(rubros => {

            rubros.forEach(rubro => {
                const option = document.createElement("option");
                option.value = rubro.id;
                option.textContent = rubro.descripcion;
                selectRubro.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Error al cargar los rubros:", error);
        });
}

document.addEventListener("DOMContentLoaded", cargarRubros);
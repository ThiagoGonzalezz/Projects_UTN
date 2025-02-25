// scripts.js


document.getElementById("reportarFallaForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const idHeladera = document.getElementById("heladeraId").value;
    const descripcion = document.getElementById("descripcionFalla").value;
    const imagenes = document.getElementById("imagenesFalla").files;


    if (!idHeladera || !descripcion) {
        alert("Por favor, complete todos los campos requeridos.");
        return;
    }


    const formData = new FormData();
    formData.append("heladeraId", idHeladera);
    formData.append("descripcionFalla", descripcion);


    for (let i = 0; i < imagenes.length; i++) {
        formData.append("imagenesFalla", imagenes[i]);
    }


    fetch('/reportar-falla-tecnica', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Falla técnica reportada correctamente.");
                document.getElementById("reportarFallaForm").reset(); // Resetear formulario
            } else {
                alert("Error al reportar la falla.");
            }
        })
        .catch(error => console.error('Error:', error));
});

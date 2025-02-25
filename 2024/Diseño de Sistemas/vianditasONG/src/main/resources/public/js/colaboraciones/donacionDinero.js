document.addEventListener("DOMContentLoaded", function() {


    console.log("JS cargado correctamente");

    const form = document.getElementById("donacionDineroForm");
    form.addEventListener("submit", function(event) {
        event.preventDefault();

        const monto = document.getElementById("Monto").value;
        const frecuencia = document.getElementById("frecuenciaDonacion").value;

        if (!monto || frecuencia === "-1") {
            alert("Por favor, complete todos los campos obligatorios.");
            return;
        }

        const formData = new URLSearchParams();
        formData.append("monto", monto);
        formData.append("frecuenciaDonacion", frecuencia);

        fetch("/donaciones-de-dinero", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
            .then(response => {
                if (response.status === 200) {
                    return response.json(); // Obtenemos el JSON
                } else {
                    throw new Error("Error en el proceso de donación");
                }
            })
            .then(data => {
                popUpBienvenido();
                alert(data.message); // Ahora mostramos el mensaje correcto
                form.reset(); // Reseteamos el formulario
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Hubo un error al procesar la donación. Inténtelo de nuevo.");
            });
    });
});

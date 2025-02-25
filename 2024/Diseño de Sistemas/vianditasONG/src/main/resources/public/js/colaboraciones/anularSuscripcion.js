 let currentId;
    let currentType;

    function showConfirmPopup(id, type) {
        currentId = id;
        currentType = type;
        document.getElementById("confirmModal").style.display = "block";
    }

    document.getElementById("cancelButton").onclick = function() {
        document.getElementById("confirmModal").style.display = "none";
    };

    document.getElementById("confirmButton").onclick = function() {
        fetch('/anular-suscripcion', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: currentId,
                tipo: currentType
            })
        })
        .then(response => {
            if (response.ok) {
                location.reload();
            } else {
                alert('Error al anular la suscripción');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al anular la suscripción');
        });

        document.getElementById("confirmModal").style.display = "none"; // Cerrar el modal
    };


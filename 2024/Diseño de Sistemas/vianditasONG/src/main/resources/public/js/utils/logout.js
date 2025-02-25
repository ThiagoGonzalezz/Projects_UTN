function confirmLogout() {
    document.getElementById("confirmation-modal").style.display = "flex";
}

function closeModal() {
    document.getElementById("confirmation-modal").style.display = "none";
}

function logout() {
    window.location.href = "/logout";
}
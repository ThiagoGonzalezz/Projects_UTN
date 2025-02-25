document.addEventListener('DOMContentLoaded', function () {
    const canjearButtons = document.querySelectorAll('.canjear-btn');
    const popup = document.getElementById('confirm-popup');
    const confirmBtn = document.getElementById('confirm-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const successPopup = document.getElementById('success-popup');
    const successPopupOferta = document.getElementById('success-popup-oferta');
    const successCloseBtn = document.getElementById('success-close-btn');
    const successCloseBtnOferta = document.getElementById('success-close-btn-oferta');
    let currentForm = null;

    canjearButtons.forEach(button => {
        button.addEventListener('click', function () {
            currentForm = this.closest('form');
            popup.style.display = 'block';
        });
    });

    cancelBtn.addEventListener('click', function () {
        popup.style.display = 'none';
    });

    confirmBtn.addEventListener('click', function () {
        if (currentForm) {
            currentForm.submit();
            popup.style.display = 'none';
        }
    });

    successCloseBtn.addEventListener('click', function () {
        successPopup.style.display = 'none';
    });

    successCloseBtnOferta.addEventListener('click', function () {
           successPopupOferta.style.display = 'none';
    });

    window.addEventListener('click', function (event) {
        if (event.target == popup) {
            popup.style.display = 'none';
        }
        if (event.target == successPopup) {
            successPopup.style.display = 'none';
        }
        if (event.target == successPopupOferta) {
            successPopupOferta.style.display = 'none';
        }
    });
});
// static/js/main.js
document.addEventListener('DOMContentLoaded', () => {
    const selector        = document.getElementById('modelSelect');
    const modelTypeHidden = document.getElementById('modelTypeHidden');
    const forms           = document.querySelectorAll('.model-form');

    function showSelectedForm() {
        // 1) ocultar todos
        forms.forEach(f => f.style.display = 'none');
        // 2) guardar el tipo en el hidden
        modelTypeHidden.value = selector.value;
        // 3) mostrar el elegido
        if (selector.value) {
            const div = document.getElementById(`model-form-${selector.value}`);
            if (div) div.style.display = 'block';
        }
    }

    // init y listener
    showSelectedForm();
    selector.addEventListener('change', showSelectedForm);
});

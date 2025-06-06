/* SELECCIÓN DE MODELOS */
document.addEventListener('DOMContentLoaded', () => {
    const tabs   = document.querySelectorAll('.tabs li');
    const forms  = document.querySelectorAll('.model-form');
    const hidden = document.getElementById('modelTypeHidden');

    function activate(model) {
        // 1) marcar pestaña
        tabs.forEach(t => t.classList.toggle('active', t.dataset.model === model));
        // 2) ocultar/mostrar formularios
        forms.forEach(f => {
            f.style.display = (f.id === `model-form-${model}`) ? 'block' : 'none';
        });
        // 3) actualizar campo oculto
        hidden.value = model;
    }

    // click en pestaña
    tabs.forEach(tab => {
        tab.addEventListener('click', () => activate(tab.dataset.model));
    });

    // init (usa el valor actual o la primera pestaña)
    activate(hidden.value || tabs[0].dataset.model);
});

document.addEventListener('DOMContentLoaded', () => {
    const input     = document.getElementById('feature-input');
    const addBtn    = document.getElementById('add-feature-btn');
    const tagsBox   = document.getElementById('tags-container');
    const form      = document.getElementById('ColumnasForm');
    let features    = [];

    function renderTag(value) {
        // etiqueta visible
        const tag = document.createElement('span');
        tag.className = 'tag';
        tag.textContent = value;

        // botón de eliminar
        const close = document.createElement('button');
        close.type = 'button';
        close.className = 'tag-close';
        close.textContent = '×';
        tag.appendChild(close);

        // hidden input
        const hidden = document.createElement('input');
        hidden.type = 'hidden';
        hidden.name = 'resto_columnas';
        hidden.value = value;
        tag.appendChild(hidden);

        // manejar borrado
        close.addEventListener('click', () => {
            features = features.filter(f => f !== value);
            tagsBox.removeChild(tag);
        });

        tagsBox.appendChild(tag);
    }

    function addFeature() {
        const val = input.value.trim();
        if (val && !features.includes(val)) {
            features.push(val);
            renderTag(val);
        }
        input.value = '';
        input.focus();
    }

    // click en botón
    addBtn.addEventListener('click', addFeature);

    // tecla Enter dentro del input
    input.addEventListener('keydown', (e) => {
        if (e.key === 'Enter') {
            e.preventDefault();
            addFeature();
        }
    });
});
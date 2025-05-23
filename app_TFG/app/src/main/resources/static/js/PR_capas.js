document.addEventListener('DOMContentLoaded', () => {
    const addLayerBtn     = document.getElementById('pr-add-layer-btn');
    const layersContainer = document.getElementById('pr-layers-container');
    const layerTemplate   = document.getElementById('pr-layer-template').content;

    // 1) Filas fijas (Input/Output): no envían nada
    function createFixedRow(name) {
        const clone = document.importNode(layerTemplate, true);
        const row   = clone.querySelector('tr');

        row.querySelector('.layer-name-cell').textContent = name;

        // ocultar y quitar name para que no se envíen
        ['pr_units','pr_rate','pr_activation'].forEach(n => {
            const el = row.querySelector(`[name="${n}"]`);
            if (el) {
                el.removeAttribute('name');
                el.style.display = 'none';
            }
        });

        row.querySelector('.pr-remove-layer-btn').style.display = 'none';
        row.classList.add('fixed-layer');
        return row;
    }

    layersContainer.appendChild(createFixedRow('Input'));
    layersContainer.appendChild(createFixedRow('Output'));
    const salidaRow = layersContainer.querySelector('tr.fixed-layer:last-child');

    // 2) Función de estado según tipo
    function applyLayerType(type, unitsInput, rateInput, actSelect) {
        if (type === 'Dropout') {
            unitsInput.style.display = 'none';
            rateInput.style.display  = '';
            rateInput.placeholder    = 'Ratio (ej: 0.2)';
            rateInput.step           = '0.01';
            rateInput.min            = '0';
            rateInput.max            = '1';

            // sólo oculta, NO borra value ni quita name
            actSelect.style.display = 'none';
        } else {
            unitsInput.style.display = '';
            rateInput.style.display  = 'none';
            unitsInput.placeholder   = 'Unidades';
            unitsInput.step          = '1';
            unitsInput.removeAttribute('min');
            unitsInput.removeAttribute('max');

            actSelect.style.display = '';
            // si aún no tenía valor (caso rare), le ponemos ’relu’
            if (!actSelect.value) actSelect.value = 'relu';
        }
    }

    // 3) Añadir nueva capa
    function addDynamicRow() {
        const clone = document.importNode(layerTemplate, true);
        const row   = clone.querySelector('tr');

        // a) Tipo de capa
        const nameCell = row.querySelector('.layer-name-cell');
        nameCell.innerHTML = `
      <select name="pr_layerName">
        <option value="Dense" selected>Dense</option>
        <option value="Dropout">Dropout</option>
      </select>`;
        const typeSelect = nameCell.querySelector('select[name="pr_layerName"]');

        // b) Unidades y ratio
        const unitsInput = row.querySelector('input[name="pr_units"]');
        const rateInput  = row.querySelector('input[name="pr_rate"]');
        unitsInput.name  = 'pr_units';
        rateInput.name   = 'pr_rate';
        rateInput.style.display = 'none';

        // c) Activación (siempre conserva name)
        const actSelect = row.querySelector('select[name="pr_activation"]');
        actSelect.name     = 'pr_activation';
        actSelect.style.display = '';

        // d) Cambio de tipo
        typeSelect.addEventListener('change', () => {
            // vaciar valores previos
            unitsInput.value = '';
            rateInput.value  = '';
            // NO tocamos actSelect.value, así mantiene 'relu' por defecto
            applyLayerType(typeSelect.value, unitsInput, rateInput, actSelect);
        });

        // estado inicial (Dense)
        applyLayerType(typeSelect.value, unitsInput, rateInput, actSelect);

        // e) Botón eliminar
        row.querySelector('.pr-remove-layer-btn')
            .addEventListener('click', () => row.remove());

        // f) Insertar antes de “Salida”
        layersContainer.insertBefore(row, salidaRow);
    }

    // 4) Listener del botón “+ Añadir capa”
    addLayerBtn.addEventListener('click', addDynamicRow);
});

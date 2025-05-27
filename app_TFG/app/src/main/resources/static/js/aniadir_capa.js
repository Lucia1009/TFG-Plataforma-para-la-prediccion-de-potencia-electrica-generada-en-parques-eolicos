document.addEventListener('DOMContentLoaded', () => {
    const modelos = {
        pr: {
            btnId: 'pr-add-layer-btn',
            containerId: 'pr-layers-container',
            templateId: 'pr-layer-template',
            fixed: true,
            layerTypes: ['Dense','Dropout']
        },
        st: {
            btnId: 'st-add-layer-btn',
            containerId: 'st-layers-container',
            templateId: 'st-layer-template',
            fixed: true,
            layerTypes: ['Dense','Dropout','LSTM']
        }
    };

    Object.entries(modelos).forEach(([prefijo, config]) => {
        const btn       = document.getElementById(config.btnId);
        const container = document.getElementById(config.containerId);
        const template  = document.getElementById(config.templateId).content;
        if (!btn || !container || !template) return;

        let outputRow = null;
        if (config.fixed) {
            // 1) FILA FIXED “Input” (sin parámetros)
            const createBlankFixed = name => {
                const clone = document.importNode(template, true);
                const row   = clone.querySelector('tr');
                row.querySelector('.layer-name-cell').textContent = name;
                // oculta TODOS los inputs
                row.querySelectorAll('input, select').forEach(el => {
                    if (el !== row.querySelector('.layer-name-cell select')) {
                        el.removeAttribute('name');
                        el.style.display = 'none';
                    }
                });
                // quita botón eliminar
                row.querySelector(`.${prefijo}-remove-layer-btn`)?.remove();
                row.classList.add('fixed-layer');
                return row;
            };

            // 2) FILA FIXED “LSTM” (basada en template dinámico)
            const createLSTMFixed = () => {
                const clone = document.importNode(template, true);
                const row   = clone.querySelector('tr');
                // inject selector LSTM
                row.querySelector('.layer-name-cell').innerHTML = `
          <select name="${prefijo}_layerName">
            <option value="LSTM" selected>LSTM</option>
          </select>`;
                // deja visibles unidades y activación
                // ratio (st_rate) no aplica a LSTM => ocultarlo
                const rateInput = row.querySelector(`input[name="${prefijo}_rate"]`);
                if (rateInput) rateInput.style.display = 'none';
                // quita botón eliminar
                row.querySelector(`.${prefijo}-remove-layer-btn`)?.remove();
                row.classList.add('fixed-layer');
                return row;
            };

            // 3) FILA FIXED “Output” (sin parámetros)
            // reuse createBlankFixed

            // Insert order: Input, LSTM, then record reference before Output
            const inputRow = createBlankFixed('Input');
            container.appendChild(inputRow);

            if (prefijo === 'st') {
                const lstmRow = createLSTMFixed();
                container.appendChild(lstmRow);
            }

            const outRow = createBlankFixed('Output');
            container.appendChild(outRow);
            outputRow = outRow;
        }

        // función auxiliar para capas dinámicas (igual que antes)
        const applyLayerType = (type, unitsInput, rateInput, actSelect) => {
            if (type === 'Dropout') {
                unitsInput.style.display = 'none';
                rateInput.style.display  = '';
                actSelect.style.display  = 'none';
            } else {
                unitsInput.style.display = '';
                rateInput.style.display  = 'none';
                actSelect.style.display  = '';
            }
        };

        // 4) evento click para “+ Añadir capa”
        btn.addEventListener('click', () => {
            const clone = document.importNode(template, true);
            const row   = clone.querySelector('tr');

            // inject selector con layerTypes
            const nameCell = row.querySelector('.layer-name-cell');
            nameCell.innerHTML = `
        <select name="${prefijo}_layerName">
          ${config.layerTypes
                .map((t,i) => `<option value="${t}"${i===0?' selected':''}>${t}</option>`)
                .join('')}
        </select>`;
            const typeSelect = nameCell.querySelector(`select[name="${prefijo}_layerName"]`);

            const unitsInput = row.querySelector(`input[name="${prefijo}_units"]`);
            const rateInput  = row.querySelector(`input[name="${prefijo}_rate"]`);
            const actSelect  = row.querySelector(`select[name="${prefijo}_activation"]`);
            rateInput.style.display = 'none';

            typeSelect.addEventListener('change', () => {
                unitsInput.value = '';
                rateInput.value  = '';
                applyLayerType(typeSelect.value, unitsInput, rateInput, actSelect);
            });
            applyLayerType(typeSelect.value, unitsInput, rateInput, actSelect);

            row.querySelector(`.${prefijo}-remove-layer-btn`)
                .addEventListener('click', () => row.remove());

            // insert before Output (fixed)
            if (outputRow) container.insertBefore(row, outputRow);
            else          container.appendChild(row);
        });
    });
});

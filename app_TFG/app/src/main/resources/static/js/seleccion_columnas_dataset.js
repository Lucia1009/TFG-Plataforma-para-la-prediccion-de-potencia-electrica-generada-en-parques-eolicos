document.addEventListener('DOMContentLoaded', () => {
    const checkbox = document.getElementById('select-all-columns');
    const datalist = document.getElementById('columnasList');
    const tagsContainer = document.getElementById('tags-container');

    const targetInput = document.getElementById('target');
    const vientoInput = document.getElementById('direccion_viento');
    const tiempoInput = document.getElementById('tiempo');

    const featureInput = document.getElementById('feature-input');
    const addFeatureBtn = document.getElementById('add-feature-btn');

    function crearTag(value) {
        const span = document.createElement('span');
        span.classList.add('tag');
        span.textContent = value;

        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = 'features';
        hiddenInput.value = value;

        const removeBtn = document.createElement('button');
        removeBtn.type = 'button';
        removeBtn.textContent = '×';
        removeBtn.classList.add('remove-btn');
        removeBtn.addEventListener('click', () => {
            tagsContainer.removeChild(span);
            tagsContainer.removeChild(hiddenInput);
        });

        span.appendChild(removeBtn);
        tagsContainer.appendChild(span);
        tagsContainer.appendChild(hiddenInput);
    }

    checkbox.addEventListener('change', () => {
        if (checkbox.checked) {
            const options = datalist.querySelectorAll('option');
            const excluidos = new Set([
                targetInput.value.trim(),
                vientoInput.value.trim(),
                tiempoInput.value.trim()
            ]);

            options.forEach(option => {
                const value = option.value.trim();
                if (
                    value &&
                    !excluidos.has(value) &&
                    !document.querySelector(`input[name="features"][value="${value}"]`)
                ) {
                    crearTag(value);
                }
            });
        } else {
            // Elimina todas las features añadidas automáticamente
            const inputs = tagsContainer.querySelectorAll('input[name="features"]');
            inputs.forEach(input => {
                const span = [...tagsContainer.children].find(el => el.textContent?.startsWith(input.value));
                if (span) tagsContainer.removeChild(span);
                tagsContainer.removeChild(input);
            });
        }
    });

    addFeatureBtn.addEventListener('click', () => {
        const value = featureInput.value.trim();

        if (
            value &&
            value !== targetInput.value.trim() &&
            value !== vientoInput.value.trim() &&
            value !== tiempoInput.value.trim() &&
            !document.querySelector(`input[name="features"][value="${value}"]`)
        ) {
            crearTag(value);
            featureInput.value = '';
        }
    });
});
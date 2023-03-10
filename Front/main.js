const api = 'http://localhost:8080/Securaudit-1.0-SNAPSHOT/api/';

function handleResponse(response) {
    response
        .then(response => {
            if (response.ok) {
                return response.json()
            } else {
                return response.text()
            }
        })
        .then(result => console.log(result))
        .catch(error => console.log(error));
}

function fetchWithFormParams(url, method, form) {
    let formData = new FormData(form);
    return fetch(
        `${api}${url}`,
        {
            method,
            body: new URLSearchParams(formData)
        }
    );
}

function fetchWithQueryParams(url, method, form) {
    let formData = new FormData(form);
    return fetch(
        `${api}${url}?` + new URLSearchParams(formData),
        {
            method
        }
    );
}

function getAuditeurByID(form) {
    handleResponse(fetchWithQueryParams('auditeur/getAuditeurById', 'GET', form));
}

function addAuditeur(form) {
    handleResponse(fetchWithFormParams('auditeur/createAuditeur', 'POST', form));
}

function updateAuditeur(form) {
    handleResponse(fetchWithFormParams('auditeur/updateAuditeur', 'PUT', form));
}

function deleteAuditeurByID(form) {
    handleResponse(fetchWithQueryParams('auditeur/deleteAuditeur', 'DELETE', form));
}

function getIndustrieByID(form) {
    handleResponse(fetchWithQueryParams('industrie/getIndustrieById', 'GET', form));
}

function addIndustrie(form) {
    handleResponse(fetchWithFormParams('industrie/createIndustrie', 'POST', form));
}

function updateIndustrie(form) {
    handleResponse(fetchWithFormParams('industrie/updateIndustrie', 'PUT', form));
}

function deleteIndustrieByID(form) {
    handleResponse(fetchWithQueryParams('industrie/deleteIndustrie', 'DELETE', form));
}

function getAuditByID(form) {
    handleResponse(fetchWithQueryParams('audit/getAuditById', 'GET', form));
}

function addAudit(form) {
    handleResponse(fetchWithFormParams('audit/createAudit', 'POST', form));
}

function updateAudit(form) {
    handleResponse(fetchWithFormParams('audit/updateAudit', 'PUT', form));
}

function deleteAuditByID(form) {
    handleResponse(fetchWithQueryParams('audit/deleteAudit', 'DELETE', form));
}

function getFraisByID(form) {
    handleResponse(fetchWithQueryParams('frais/getFraisById', 'GET', form));
}

function addFrais(form) {
    handleResponse(fetchWithFormParams('frais/createFrais', 'POST', form));
}

function updateFrais(form) {
    handleResponse(fetchWithFormParams('frais/updateFrais', 'PUT', form));
}

function deleteFraisByID(form) {
    handleResponse(fetchWithQueryParams('frais/deleteFrais', 'DELETE', form));
}

function load(page) {
    let routeur = document.getElementById('routeur');
    fetch(page).then(html => html.text()).then(text => routeur.innerHTML = text);
}
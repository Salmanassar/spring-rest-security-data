$(document).ready(getTable())

function getTable() {
    let empty = $('#table_js').empty()
    fetch('/admin/users').then(
        response => response.json()).then(
        data => {
            let tableElementByID = document.getElementById('table_js')
            let roleToSting
            for (let i = 0; i < data.length; i++) {
                // get the roles from json field 'rolesString' it is the field in my project
                if (data[i].rolesString === 'ROLE_ADMIN') {
                    roleToSting = 'ADMIN'
                } else if (data[i].rolesString === 'ROLE_USER') {
                    roleToSting = 'USER'
                } else if (data[i].rolesString === 'ROLE_ADMIN ROLE_USER') {
                    roleToSting = 'ADMIN USER'
                }
                fillDataTable = `<tr>
                    <td>${data[i].id}</td>
                    <td>${data[i].firstName}</td>
                    <td>${data[i].lastName}</td>
                    <td>${data[i].age}</td>
                    <td>${data[i].email}</td>
                    <td>` + roleToSting + `</td>
                    <td>
                        <button type="button" class="btn btn text-white" data-toggle="modal" onclick="clickEdit(` + data[i].id + `)"
                                style="background-color: #008080;" data-target="#exampleModalEdit" id ="edit">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal" onclick="clickDelete(` + data[i].id + `)"
                                data-target="#exampleModalDelete" id="delete">Delete
                        </button>
                    </td>
                </trr>`
                tableElementByID.innerHTML += fillDataTable
            }
        }
    );
}

function clickEdit(id) {
    $.get('/admin/edit/' + id, function (usr) {
        console.log(id);
        $('#idEdit').val(usr.id),
            $('#idFirstNameEdit').val(usr.firstName),
            $('#idLastNameEdit').val(usr.lastName),
            $('#idAgeEdit').val(usr.age),
            $('#idEmailEdit').val(usr.email),
            $('#idPasswordEdit').val(usr.password)
    })
}

function updateUser() {
    const urlEditMethodRest = '/admin/edit/';

    let passData = {
        idEdit: document.getElementById("idEdit").value,
        first_name: document.getElementById("idFirstNameEdit").value,
        last_name: document.getElementById("idLastNameEdit").value,
        age: document.getElementById("idAgeEdit").value,
        email: document.getElementById('idEmailEdit').value,
        password: document.getElementById("idPasswordEdit").value,
        role: document.getElementById("idRoleEditUser").value
    }

    console.log('newData = ' + passData);
    console.log(document.getElementById("idRoleEditUser").value)
    let json_newData = JSON.stringify(passData);

    sendRequest('PUT', urlEditMethodRest, json_newData)
        .then(() => {
            console.log('Send creating user data');
        })
        .catch(err => console.log(err))

    async function sendRequest(method, url, body) {
        await fetch(urlEditMethodRest, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then(refreshTable).then(getTable)
    }

    document.getElementById('closeEdit').click()
}

function clickDelete(id) {
    $.get('/admin/delete/' + id, function (usr) {
        console.log(id);
        $('#idDelete').val(usr.id),
            $('#firstNameDelete').val(usr.firstName),
            $('#lastNameDelete').val(usr.lastName),
            $('#ageDelete').val(usr.age),
            $('#emailDelete').val(usr.email),
            $('#passwordDelete').val(usr.password)
        $('#idRoleDeleteUser').val(usr.roleFromSelection)
    })
}

function deleteUser() {
    let form = document.getElementById('idDeleteForm')
    let userId = document.getElementById('idDelete').value
    const requestURL = '/admin/delete/';
    let urlMethodDeleteRest = requestURL + userId;

    sendRequest('DELETE', urlMethodDeleteRest)
        .then(() => {
                console.log('User ' + userId + ' is deleted')
                let deleteTR = "#user-row-" + userId
                $(deleteTR).remove()
            }
        )
        .catch(err => console.log(err))

    async function sendRequest(method, url) {
        fetch(url, {
            method: method
        })
            .then((response) => {
                return response;
            }).then(refreshTable).then(getTable())
    }

    document.getElementById('closeDelete').click()
}

function createUser() {
    const urlCreateMethodRest = '/admin/create/';

    let passData = {
        firstName: document.getElementById("idFirstNameCreate").value,
        lastName: document.getElementById("idLastNameCreate").value,
        age: document.getElementById("idAgeCreate").value,
        email: document.getElementById('idEmailCreate').value,
        password: document.getElementById("idPasswordCreate").value,
        role: document.getElementById("idRoleCreateUser").value
    }

    console.log('newData = ' + passData.password);
    console.log(document.getElementById("idRoleEditUser").value)
    let json_newData = JSON.stringify(passData);

    sendRequest('POST', urlCreateMethodRest, json_newData)
        .then(() => {
            console.log('Send creating user data');
        })
        .catch(err => console.log(err))

    async function sendRequest(method, url, body) {
        await fetch(urlCreateMethodRest, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        })
    }
}

function getTableAdminUser() {
    fetch('/admin/users').then(
        response => response.json()).then(
        data => {
            let tableElementByID = document.getElementById('table_js')
            let fillDataTable
            let roleToSting
            for (let i = 0; i < data.length; i++) {
                // get the roles from json field 'rolesString' it is the field in my project
                if (data[i].rolesString === 'ROLE_ADMIN') {
                    roleToSting = 'ADMIN'
                } else if (data[i].rolesString === 'ROLE_USER') {
                    roleToSting = 'USER'
                } else if (data[i].rolesString === 'ROLE_ADMIN ROLE_USER') {
                    roleToSting = 'ADMIN USER'
                }
                fillDataTable = `<tr>
                    <td>${data[i].id}</td>
                    <td>${data[i].firstName}</td>
                    <td>${data[i].lastName}</td>
                    <td>${data[i].age}</td>
                    <td>${data[i].email}</td>
                    <td>` + roleToSting + `</td>
                    <td>
                        <button type="button" class="btn btn text-white" data-toggle="modal" onclick="clickEdit(` + data[i].id + `)"
                                style="background-color: #008080;" data-target="#exampleModalEdit" id ="edit">Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger" data-toggle="modal" onclick="clickDelete(` + data[i].id + `)"
                                data-target="#exampleModalDelete" id="delete">Delete
                        </button>
                    </td>
                </trr>`
                tableElementByID.innerHTML += fillDataTable
            }
        }
    );
}

function refreshTable() {
    let empty = $('#table_js').empty()
    const next = $('<tbody id="table_js"> </tbody>')
    empty.replaceWith(next)
}
function registrationFunction() {
    const urlForCreateMethod = '/register';
    let createUser = document.getElementById('createUser');
    let formElements = new FormData(createUser);

    let passData = {
        firstName: formElements.get("firstNameCreate"),
        lastName: formElements.get("lastNameCreate"),
        age: formElements.get("ageCreate"),
        email: formElements.get('emailCreate'),
        password: formElements.get("passwordCreate"),
        role: document.getElementById("roleCreate").value
    }

    console.log('newData = ' + passData);
    console.log(document.getElementById("roleCreate").value)
    let json_newData = JSON.stringify(passData);

    sendRequest('PUT', urlForCreateMethod, json_newData)
        .then( () => {
            console.log('Send creating user data');
        })
        .catch(err => console.log(err))

    async function sendRequest(method, url, body) {
        await fetch(urlForCreateMethod, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
    }
    document.getElementById('closeButthon').click()
}


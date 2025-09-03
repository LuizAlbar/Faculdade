const form = document.querySelector('#form-login'); // form
const loginER = document.querySelector('#erInput'); // Login
const password = document.querySelector('#password');

form.addEventListener("submit", (event) => {

    event.preventDefault(); 

    const validChar = /[^a-zA-Z0-9]/; 

    loginER.placeholder = "ER";
    password.placeholder = "Password";

    let isValid = true; 

    
    if (loginER.value === '') {
        loginER.placeholder = 'Fill in the ER field!';
        isValid = false;
    }

   
    if (validChar.test(password.value)) {
        password.value = '';  
        password.placeholder = 'Password must be alphanumeric only!';
        isValid = false;
    } 
   
    else if (!validatePassword(password.value, 8)) {
        password.value = ''; // Limpa o campo
        password.placeholder = 'Password must have at least 8 digits!';
        isValid = false;
    }

    // Após tudo ser validado...
    if (isValid) {
        form.submit(); 
        window.location.href = "stock.html"; 
        loginER.value = '';
        password.value = '';
    }

    
});


function validatePassword(password, minDigits) {
   
    if (password.length >= minDigits) {
        return true; // Senha válida
    }
    return false; // Senha inválida
}

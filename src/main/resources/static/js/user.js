function loginUser() {

    let email = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    if (email === "" || password === "") {
        alert("Please fill all fields");
        return;
    }

    fetch("/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
    .then(response => response.text())
    .then(data => {

        if (data === "LOGIN_SUCCESS") {
            alert("Login Successful");
            window.location.href = "/index";
        } else {
            alert("Invalid Credentials");
        }

    })
    .catch(error => {
        console.error("Error:", error);
    });
}
//Register
  function registerUser() {

        const userData = {
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            phone: document.getElementById("phone").value,
            password: document.getElementById("password").value
        };

        fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userData)
        })
        .then(response => response.text())
        .then(data => {
            document.getElementById("message").innerHTML =
                "<div class='alert alert-success'>" + data + "</div>";
        })
        .catch(error => {
            document.getElementById("message").innerHTML =
                "<div class='alert alert-danger'>Registration Failed</div>";
        });

    }

/*function registerUser() {
    alert("JS Loaded");   // Add this for testing

}*/
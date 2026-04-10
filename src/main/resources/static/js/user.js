function loginUser() {
    let email = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    if (email === "" || password === "") {
        alert("Please fill all fields");
        return;
    }

    fetch("/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email: email, password: password })
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === "LOGIN_SUCCESS") {
            alert("Login Successful");

            // Pass userId in URL
            window.location.href = "/index?userId=" + data.userId;
        } else {
            alert("Invalid Credentials");
        }
    })
    .catch(err => console.error(err));
}
//Register
  function registerUser() {
const formData = new FormData();

      formData.append("name", document.getElementById("name").value);
      formData.append("email", document.getElementById("email").value);
      formData.append("phone", document.getElementById("phone").value);
      formData.append("password", document.getElementById("password").value);

      const fileInput = document.getElementById("image");

      if (fileInput.files.length > 0) {
          formData.append("image", fileInput.files[0]);
      }

      fetch("http://localhost:8080/auth/register", {
          method: "POST",
          body: formData   // ✅ send FormData directly
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

  function updateProfile() {
      const data = {
          name: document.getElementById("name").value,
          phone: document.getElementById("phone").value,
          email: document.getElementById("email").value
      };

      fetch("/my-account/profile/update", {
          method: "POST",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify(data)
      })
      .then(response => {
          if (!response.ok) {
              throw new Error("Update failed");
          }
          return response.text();
      })
      .then(result => {
          document.getElementById("message").innerText = result;
          // Redirect after 1s to see the message
          setTimeout(() => { window.location.href = "/my-account/profile"; }, 1000);
      })
      .catch(error => {
          document.getElementById("message").innerText = "Error updating profile!";
          console.error(error);
      });
  }
console.log("JS Loaded");

    async function submitAddress() {
        console.log("submit called");
    const form = document.getElementById('addressForm');
    const formData = new FormData(form);
    const data = {};

    formData.forEach((value, key) => {
    if (key === 'default') {
    data['default'] = form.elements[key].checked;
} else {
    data[key] = value;
}
});

    const msgDiv = document.getElementById('message');

    try {
    const response = await fetch('/my-account/address/add', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json'
},
    body: JSON.stringify(data)
});

    if (response.ok) {
    const result = await response.json();
    msgDiv.style.color = 'green';
    msgDiv.textContent = 'Address added successfully! ID: ' + result.id;
    form.reset();
} else if (response.status === 401) {
    msgDiv.textContent = 'Please login first.';
} else {
    const err = await response.json();
    msgDiv.textContent = 'Error: ' + err.message;
}
} catch (err) {
    console.error(err);
    msgDiv.textContent = 'Failed to add address';
}
}

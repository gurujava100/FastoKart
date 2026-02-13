function openSection(id) {
    document.getElementById(id).style.display = "block";
}

function closeSection(id) {
    document.getElementById(id).style.display = "none";
}

//ADD Sub Category


function saveSubCategory() {

    const categoryId = document.getElementById("categoryId").value;
    const name = document.getElementById("name").value;
    const imageFile = document.getElementById("image").files[0];

    // Validation
    if (!categoryId || !name) {
        alert("Please fill all required fields");
        return;
    }

    const formData = new FormData();
    formData.append("categoryId", categoryId);
    formData.append("name", name);

    if (imageFile) {
        formData.append("image", imageFile);
    }

    fetch("/api/subcategories", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to save");
        }
        return response.text();
    })
    .then(data => {
        alert("SubCategory Added Successfully ✅");

        // Clear form
        document.getElementById("subCategoryForm").reset();

        // Optional: reload subcategory list
        // loadSubCategories();
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Something went wrong ❌");
    });
}



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


//LOAD SUB CATEGORY
function loadSubCategories() {


    const section = document.getElementById("subcategorySection");

    // 1️⃣ Build table layout
    section.innerHTML = `
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card shadow-sm">
                    <div class="card-header fw-bold">
                        Sub Categories
                    </div>
                    <div class="card-body p-0">
                        <table class="table table-bordered mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>#</th>
                                    <th>Image</th>
                                    <th>Sub Category Name</th>
                                    <th>Parent Category</th>
                                    <th>Status</th>
                                    <th width="160">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="subCategoryTableBody">
                                <tr>
                                    <td colspan="6" class="text-center text-muted">
                                        Loading...
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    `;

     //2️⃣ Fetch subcategories
    fetch("/api/subcategories")
        .then(res => {
            if (!res.ok) throw new Error("Failed to load subcategories");
            return res.json();
        })
        .then(data => {

            const tbody = document.getElementById("subCategoryTableBody");
            tbody.innerHTML = "";

            if (!data || data.length === 0) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="6" class="text-center text-muted">
                            No sub categories found
                        </td>
                    </tr>
                `;
                return;
            }

            // 3️⃣ Render rows
            data.forEach((sub, index) => {

             const imageUrl = sub.imageName
                 ? `/api/subcategories/image/${sub.imageName}`
                 : `/assets/n.jpg`;


                tbody.innerHTML += `
                    <tr>
                        <td>${index + 1}</td>

                        <td class="text-center">
                            <img src="${sub.imageUrl}"
                                 style="width:50px;height:50px;object-fit:cover;border-radius:6px;">
                        </td>

                        <td>${sub.name}</td>

                        <td>${sub.categoryName || '-'}</td>

                        <td>
                            <span class="badge ${sub.isActive ? 'bg-success' : 'bg-secondary'}">
                                ${sub.isActive ? 'Active' : 'Inactive'}
                            </span>
                        </td>

                        <td>
                            <button class="btn btn-sm btn-warning me-1"
                                    onclick="openEditSubCategoryModal(${sub.id})">
                                Edit
                            </button>

                            <button class="btn btn-sm btn-danger"
                                    onclick="deleteSubCategory(${sub.id})">
                                Delete
                            </button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(err => {
            console.error(err);
            document.getElementById("subCategoryTableBody").innerHTML = `
                <tr>
                    <td colspan="6" class="text-danger text-center">
                        Failed to load sub categories
                    </td>
                </tr>
            `;

        });

}

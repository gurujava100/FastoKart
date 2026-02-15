// Toggle add category form
function toggleAddCategoryForm() {
    const form = document.getElementById("addCategoryForm");
    const title = document.getElementById("addCategoryTitle");

    if (!form || !title) return;

    if (form.classList.contains("d-none")) {
        form.classList.remove("d-none");
        title.innerText = "Close Category Form";
        form.scrollIntoView({ behavior: "smooth" });
    } else {
        form.classList.add("d-none");
        title.innerText = "Add Category";
    }
}

// Submit category via AJAX
function saveCategoryOnClick() {
                               const nameInput = document.getElementById("categoryName");
                               const errorDiv = document.getElementById("categoryNameError");

                               // Clear previous errors
                               nameInput.classList.remove("is-invalid");
                               errorDiv.style.display = "none";
                               errorDiv.innerText = "";

                               const nameValue = nameInput.value.trim();
                               if (!nameValue) {
                                   nameInput.classList.add("is-invalid");
                                   errorDiv.style.display = "block";
                                   errorDiv.innerText = "❌ Category name is required";
                                   return;
                               }

                               const form = document.getElementById("addCategoryFormElement");
                               const formData = new FormData(form);

                               const saveBtn = document.getElementById("saveCategoryBtn");
                               saveBtn.disabled = true;
                               saveBtn.innerText = "Saving...";

                               const xhr = new XMLHttpRequest();
                               xhr.open("POST", "/api/categories/save", true);
                               xhr.onload = function() {
                                   saveBtn.disabled = false;
                                   saveBtn.innerText = "Save Category";

                                   if (xhr.status === 200) {
                                       alert("✅ Category saved successfully!");
                                       form.reset();
                                   } else if (xhr.status === 409) {
                                       // Duplicate category
                                       nameInput.classList.add("is-invalid");
                                       errorDiv.style.display = "block";
                                       errorDiv.innerText = "❌ Category already exists";
                                       nameInput.focus();
                                   } else {
                                       alert("❌ Error saving category");
                                       console.error(xhr.responseText);
                                   }
                               };
                               xhr.onerror = function() {
                                   saveBtn.disabled = false;
                                   saveBtn.innerText = "Save Category";
                                   alert("❌ Request failed");
                               };
                               xhr.send(formData);
                           }
// Card handling function openSection(sectionId) {

                     // hide all sections
                     function openSection(sectionId) {

                         // hide all sections
                         document.querySelectorAll('[data-section]')
                             .forEach(section => section.classList.add('d-none'));

                         // show selected section
                         const activeSection = document.getElementById(sectionId);
                         activeSection.classList.remove('d-none');

                         // smooth scroll
                         activeSection.scrollIntoView({ behavior: 'smooth' });
                     }

                     //Open section for all category
                     document.addEventListener("DOMContentLoaded", () => {
                         openSection('allCategory');
                         loadCategories();
                     });
//Load All category

function loadAllCategories() {

    const section = document.getElementById("categorySection");

    // 1️⃣ Build table layout
    section.innerHTML = `
        <div class="row mt-4">
            <div class="col-md-12">
                <div class="card shadow-sm">
                    <div class="card-header fw-bold">
                        Categories
                    </div>
                    <div class="card-body p-0">
                        <table class="table table-bordered mb-0">
                            <thead class="table-light">
                                <tr>
                                    <th>#</th>
                                    <th>Image</th>
                                    <th>Category Name</th>
                                    <th>Status</th>
                                    <th width="160">Actions</th>
                                </tr>
                            </thead>
                            <tbody id="categoryTableBody">
                                <tr>
                                    <td colspan="5" class="text-center text-muted">
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

    // 2️⃣ Fetch categories
    fetch("/api/categories")
        .then(res => {
            if (!res.ok) throw new Error("Failed to load categories");
            return res.json();
        })
        .then(data => {

            const tbody = document.getElementById("categoryTableBody");
            tbody.innerHTML = "";

            if (!data || data.length === 0) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="5" class="text-center text-muted">
                            No categories found
                        </td>
                    </tr>
                `;
                return;
            }

            // 3️⃣ Render rows
            data.forEach((cat, index) => {

               const imageUrl = cat.imageName
                               ? `/api/categories/image/${cat.imageName}`
                       : `/assets/n.jpg`;

                               tbody.innerHTML += `
                       <tr>
                           <td>${index + 1}</td>

                           <td class="text-center">
                               <img src="${imageUrl}"
                                    style="width:50px;height:50px;object-fit:cover;border-radius:6px;">
                           </td>

                           <td>${cat.name}</td>

                           <td>
                               <span class="badge ${cat.active ? 'bg-success' : 'bg-secondary'}">
                                   ${cat.active ? 'Active' : 'Inactive'}
                               </span>
                           </td>
                           <td>
                            <button class="btn btn-sm btn-warning me-1"
                                    onclick="openEditModal(${cat.id})">
                                Edit
                            </button>

                            <button class="btn btn-sm btn-danger"
                                    onclick="deleteCategory(${cat.id})">
                                Delete
                            </button>
                        </td>
                    </tr>
                `;
            });
        })
        .catch(err => {
            console.error(err);
            document.getElementById("categoryTableBody").innerHTML = `
                <tr>
                    <td colspan="5" class="text-danger text-center">
                        Failed to load categories
                    </td>
                </tr>
            `;
        });
}


function deleteCategory(id) {
  alert(" deleting category"+id);
   if (!confirm("Are you sure you want to delete this category?")) {
        return;
    }

    fetch(`/api/categories/${id}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Delete failed");
        }
        alert("Category deleted successfully");
        loadAllCategories(); // reload table
    })
    .catch(error => {
        console.error(error);
        alert("Error deleting category");
    });
}
//Collecting date for edit
function openEditModal(id) {

    fetch(`/api/categories/${id}`)
        .then(res => {
            if (!res.ok) throw new Error("Category not found");
            return res.json();
        })
        .then(cat => {
            document.getElementById("editCategoryId").value = cat.id;
            document.getElementById("editCategoryName").value = cat.name;
            document.getElementById("editCategoryStatus").checked = cat.isActive;

            new bootstrap.Modal(
                document.getElementById("editCategoryModal")
            ).show();
        })
        .catch(err => {
            console.error(err);
            alert("Error loading category");
        });
}
//EDIT function
function updateCategory() {

    const id = document.getElementById("editCategoryId").value;
    const name = document.getElementById("editCategoryName").value.trim();
    const isActive = document.getElementById("editCategoryStatus").checked;

    if (name === "") {
        alert("Category name cannot be empty");
        return;
    }

    fetch(`/api/categories/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            isActive: isActive
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Update failed");
        return res.json();
    })
    .then(() => {
        alert("Category updated successfully");

        bootstrap.Modal.getInstance(
            document.getElementById("editCategoryModal")
        ).hide();

        loadAllCategories(); // refresh table
    })
    .catch(err => {
        console.error(err);
        alert("Update error");
    });
}

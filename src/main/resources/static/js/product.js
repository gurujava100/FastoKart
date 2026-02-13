
function openSection(sectionId) {

    document.querySelectorAll("[data-section]").forEach(section => {
        section.classList.add("d-none");
    });

    const section = document.getElementById(sectionId);
    if (section) {
        section.classList.remove("d-none");
        section.scrollIntoView({ behavior: "smooth" });
    }
}



function saveProductOnClick() {

    const form = document.getElementById("addProductFormElement");

    if (!form) return;

    const formData = new FormData(form);

    fetch("/api/products/save", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to save product");
        }
        return response.json();
    })
    .then(data => {
        alert("✅ Product saved successfully");

        // Reset form
        form.reset();

        // Close form (optional)
        toggleAddProductForm();

        // Reload product list if you have one
        if (typeof loadProducts === "function") {
            loadProducts();
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("❌ Error saving product");
    });
}
//Load products
function loadAllProducts() {

                   const section = document.getElementById("productContainer");


                   // 1️⃣ Build table layout
                   section.innerHTML = `
                       <div class="row mt-4">
                           <div class="col-md-12">
                               <div class="card shadow-sm">
                                   <div class="card-header fw-bold">
                                       Products
                                   </div>
                                   <div class="card-body p-0">
                                       <table class="table table-bordered mb-0">
                                           <thead class="table-light">
                                               <tr>
                                                   <th>#</th>
                                                   <th>Image</th>
                                                   <th>Product Name</th>
                                                   <th>Price</th>
                                                   <th>Status</th>
                                                   <th width="180">Actions</th>
                                               </tr>
                                           </thead>
                                           <tbody id="productTableBody">
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

                   // 2️⃣ Fetch products
                   fetch("/api/products")
                       .then(res => {
                           if (!res.ok) throw new Error("Failed to load products");
                           return res.json();
                       })
                       .then(data => {
                           productList = data;
                           const tbody = document.getElementById("productTableBody");
                           tbody.innerHTML = "";

                           if (!data || data.length === 0) {
                               tbody.innerHTML = `
                                   <tr>
                                       <td colspan="6" class="text-center text-muted">
                                           No products found
                                       </td>
                                   </tr>
                               `;
                               return;
                           }

                           // 3️⃣ Render rows
                           data.forEach((product, index) => {

                            const imageUrl = product.imageName
                              ? `/api/products/image/${product.imageName}`
                              : `/assets/n.jpg`;

                               tbody.innerHTML += `
                                   <tr>
                                       <td>${index + 1}</td>

                                       <td class="text-center">
                                           <img src="${imageUrl}"
                                                style="width:50px;height:50px;object-fit:cover;border-radius:6px;">
                                       </td>

                                       <td>${product.name}</td>

                                       <td>₹ ${product.price}</td>

                                       <td>
                                          <span class="badge ${product.active ? 'bg-success' : 'bg-secondary'}">
                                              ${product.active ? 'Active' : 'Inactive'}
                                          </span>
                                       </td>

                                       <td>
                                           <button class="btn btn-sm btn-warning me-1"
                                                   onclick="openEditProductModal(${index})">
                                               Edit

                                           </button>

                                           <button class="btn btn-sm btn-danger"
                                                   onclick="deleteProduct(${product.id})">
                                               Delete
                                           </button>
                                       </td>
                                   </tr>
                               `;
                           });
                       })
                       .catch(err => {
                           console.error(err);
                           document.getElementById("productTableBody").innerHTML = `
                               <tr>
                                   <td colspan="6" class="text-danger text-center">
                                       Failed to load products
                                   </td>
                               </tr>
                           `;
                       });
               }
 //edit form open
 function openEditProductModal(index) {

     console.log("Clicked index:", index);
     console.log("Product List:", productList);

     const product = productList[index];

     if (!product) {
         alert("Product not found!");
         return;
     }

     document.getElementById("editId").value = product.id;
     document.getElementById("editName").value = product.name;
     document.getElementById("editPrice").value = product.price;
     document.getElementById("editStatus").checked = product.isActive;

     const modal = new bootstrap.Modal(
         document.getElementById('editProductModal')
     );
     modal.show();
 }
//UPDATE MPRODUCT
function updateProduct() {

         const id = document.getElementById("editId").value;
        // alert(id);

    const updatedProduct = {
             name: document.getElementById("editName").value,
              price: document.getElementById("editPrice").value,
              active: document.getElementById("editStatus").checked
    };

    fetch(`/api/products/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(updatedProduct)
    })
    .then(res => {
        if (!res.ok) throw new Error("Update failed");
        return res.json();
    })
    .then(() => {
        alert("Product updated successfully");
        loadAllProducts(); // reload table
        bootstrap.Modal.getInstance(document.getElementById('editProductModal')).hide();
    })
    .catch(err => alert(err.message));

}
//DELETE PRODUCT
function deleteProduct(id) {
                  alert("Deleting product " + id);

                  if (!confirm("Are you sure you want to delete this product?")) {
                      return;
                  }

                  fetch(`/api/products/${id}`, {
                      method: "DELETE"
                  })
                  .then(response => {
                      if (!response.ok) {
                          throw new Error("Delete failed");
                      }

                      alert("Product deleted successfully");
                      loadAllProducts(); // reload product table
                  })
                  .catch(error => {
                      console.error(error);
                      alert("Error deleting product");
                  });
                }

function getCartId() {
    let cartId = localStorage.getItem("cartId");

    if (!cartId) {
        cartId = crypto.randomUUID();   // permanent id
        localStorage.setItem("cartId", cartId);
    }

    return cartId;
}

//AAD TO Cart
function addToCart(productId) {
    const cartId = getCartId();

    fetch(`/api/cart/add?cartId=${cartId}&productId=${productId}`, {
        method: "POST"
    })
    .then(res => res.json())
    .then(cartCount => {

        // update navbar badge
        const badge = document.getElementById("cartCount");
        if (badge) badge.textContent = cartCount;

        // show toast
        const toastEl = document.getElementById('cartToast');
        if (toastEl) {
            const toast = new bootstrap.Toast(toastEl);
            toast.show();
        }

        // 🔥 important — refresh cart UI instantly
        if (typeof loadCart === "function") {
            loadCart();
        }
    })
    .catch(err => console.error(err));
}

document.addEventListener("DOMContentLoaded", loadCart);
document.addEventListener("DOMContentLoaded", loadCart);
function loadCart() {
    const cartId = getCartId();

    fetch(`/api/cart/${cartId}`)
        .then(res => res.json())
        .then(cart => {
            const cartItemsContainer = document.getElementById("cartItems");
            const cartCountEl = document.getElementById("cartCount");
            const priceTotalEl = document.getElementById("priceTotal");
            const finalTotalEl = document.getElementById("finalTotal");
            const emptyCartEl = document.getElementById("emptyCart");

            if (!cart.items || cart.items.length === 0) {
                cartItemsContainer.innerHTML = "";
                cartCountEl.textContent = 0;
                priceTotalEl.textContent = 0;
                finalTotalEl.textContent = 0;
                emptyCartEl.style.display = "block";
                return;
            }

            emptyCartEl.style.display = "none";

            let html = "";
            let totalQty = 0;
            let totalPrice = 0;

            cart.items.forEach(item => {
                totalQty += item.quantity;
                totalPrice += item.price * item.quantity;

                html += `
                    <div class="cart-card d-flex align-items-center mb-2" id="cart-item-${item.product.id}">
                        <img src="/api/products/image/${item.product.imageName}" class="product-img me-3">
                        <div class="flex-grow-1">
                            <h6>${item.product.name}</h6>
                            <p class="mb-1">₹${item.price} x <span id="qty-${item.product.id}">${item.quantity}</span> = ₹${item.price * item.quantity}</p>
                            <div class="d-flex align-items-center gap-2">
                                <button class="qty-btn" onclick="decreaseQty(${item.product.id})">-</button>
                                <span id="qty-${item.product.id}">${item.quantity}</span>
                                <button class="qty-btn" onclick="increaseQty(${item.product.id})">+</button>
                                <button class="btn btn-sm btn-danger" onclick="removeItem(${item.product.id})">Remove</button>
                                 <button class="btn btn-sm btn-success" onclick="buyProduct(${item.product.id})">Buy</button>

                            </div>

                         </div>
                    </div>
                `;
            });

            cartItemsContainer.innerHTML = html;
            cartCountEl.textContent = totalQty;
            priceTotalEl.textContent = totalPrice.toFixed(2);
            finalTotalEl.textContent = totalPrice.toFixed(2);
        })
        .catch(err => console.error("Error loading cart:", err));
}

//UPDATE CART
function updateCartCount() {
    const cartId = getCartId();
    fetch(`/api/cart/${cartId}`)
        .then(res => res.json())
        .then(cart => {
            const totalQty = cart.items.reduce((sum, item) => sum + item.quantity, 0);
            document.getElementById("cartCount").textContent = totalQty;
        })
        .catch(err => console.error(err));
}

// Call this on page load
document.addEventListener("DOMContentLoaded", updateCartCount);

//add quantity
function increaseQty(productId) {
    addToCart(productId); // just reuse addToCart
    setTimeout(loadCart, 100); // refresh cart after API call
}
//Decrese quantity
function decreaseQty(productId) {
    const cartId = getCartId();

    fetch(`/api/cart/decrease?cartId=${cartId}&productId=${productId}`, {
        method: "POST"
    }).then(res => res.json())
      .then(() => loadCart())
      .catch(err => console.error(err));
}
//Remove quantity
function removeItem(productId) {
    const cartId = getCartId();

    fetch(`/api/cart/remove?cartId=${cartId}&productId=${productId}`, {
        method: "DELETE"
    }).then(res => res.json())
      .then(() => loadCart())
      .catch(err => console.error(err));
}
//BUY SINGLE PRODUCT
/*function buyProduct(productId) {
alert(productId);
   /* const cartId = getCartId();

    fetch(`/api/cart/buy/${cartId}/${productId}`, {
        method: 'POST'
    })
    .then(res => res.json())
    .then(response => {
        alert(`Product purchased successfully!`);
        loadCart(); // Refresh cart after purchase
    })
    .catch(err => console.error("Error buying product:", err));
}*/
//BY SINGLE PRODUCT
function buyProduct(productId) {
alert("Single product clicked "+productId);
    // Redirect to single-product checkout page
   // window.location.href = `/checkout?productId=${productId}`;
  /* const cartId = getCartId(); // If needed for backend validation

       fetch(`/api/checkout/single?productId=${productId}&cartId=${cartId}`, {
           method: 'POST', // Use POST to create an order
           headers: {
               'Content-Type': 'application/json'
           }
       })
       .then(res => {
           if (!res.ok) throw new Error('Failed to checkout product');
           return res.json();
       })
       .then(order => {
           alert(`Product purchased successfully! Order ID: ${order.id}`);
           loadCart(); // Refresh cart
       })
       .catch(err => console.error('Error:', err));*/
}
//BUY CART
function checkoutCart() {
const cartId = getCartId();
alert("checkout clicked");
   /* fetch(`/api/checkout/cart?cartId=${cartId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(res => {
        if (!res.ok) throw new Error('Failed to checkout cart');
        return res.json();
    })
    .then(order => {
        alert(`Cart purchased successfully! Order ID: ${order.id}`);
        loadCart(); // Refresh cart
    })
    .catch(err => console.error('Error:', err));
    */
}// PRODUCT AUTO SUGGEST ONLY
 const input = document.getElementById("searchInput");
 const suggestionsBox = document.getElementById("suggestions");

 let timer = null;

 /* =========================
    FETCH SUGGESTIONS
    ========================= */
 input.addEventListener("keyup", function (e) {

     clearTimeout(timer);

     const keyword = input.value.trim();

     // ENTER → go to search page
     if(e.key === "Enter" && keyword.length > 0){
         window.location.href = `/search-result?q=${encodeURIComponent(keyword)}`;
         return;
     }

     // clear if short
     if (keyword.length < 2) {
         suggestionsBox.innerHTML = "";
         return;
     }

     // debounce (avoid too many API calls)
     timer = setTimeout(() => {

         fetch(`/api/products/suggestions?q=${encodeURIComponent(keyword)}`)
             .then(res => res.json())
             .then(data => {

                 suggestionsBox.innerHTML = "";

                 if (!data || data.length === 0) return;

                 data.forEach(item => {

                     const div = document.createElement("div");
                     div.className = "list-group-item list-group-item-action";
                     div.textContent = item.name;

                     // CLICK → go to search result page
                     div.addEventListener("click", () => {
                       window.location.href = `/search-result?q=${encodeURIComponent(item.name)}`;
                     });

                     suggestionsBox.appendChild(div);
                 });

             })
             .catch(err => console.error("Suggestion error:", err));

     }, 300);
 });


 /* =========================
    CLOSE DROPDOWN OUTSIDE CLICK
    ========================= */
 document.addEventListener("click", function (e) {
     if (!input.contains(e.target) && !suggestionsBox.contains(e.target)) {
         suggestionsBox.innerHTML = "";
     }
 });

 //BY NOW FUNCTION
 function buyProduct(id){
     fetch("/api/buy-now/" + id, { method: "POST" })
        .then(res => res.json())
        .then(data => {
            if(data.success){
               window.location.href = "/checkout";   // ← ONLY THIS
            }
        });
 }
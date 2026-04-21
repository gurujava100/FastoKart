function placeOrder(){

    const addressId = document.getElementById("selectedAddressId").value;
    const paymentMethod = document.getElementById("paymentMethod").value;

    console.log("Sending addressId:", addressId);

    if(!addressId || addressId === "undefined"){
        alert("Address not loaded. Please refresh or select address.");
        return;
    }

    const formData = new FormData();
    formData.append("addressId", addressId);
    formData.append("paymentMethod", paymentMethod);

    fetch("/place-order", {
        method: "POST",
        body: formData
    })
        .then(res => res.text())
        .then(orderId => {
            window.location.href = "/order-success/" + orderId;
        });
}
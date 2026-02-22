function placeOrder(){

    const form = document.getElementById("checkoutForm");
    const formData = new FormData(form);

    fetch("/place-order", {
        method: "POST",
        body: formData
    })
    .then(response => {
        if(!response.ok){
            throw new Error("Server error");
        }
        return response.text();
    })
    .then(orderId => {

        orderId = Number(orderId);

        if(orderId === -1){
            window.location.href = "/session-expired";
            return;
        }

        window.location.href = "/order-success/" + orderId;
    })
    .catch(err=>{
        alert("Order failed");
        console.error(err);
    });
}
function placeOrder(){
    fetch("/place-order", { method: "POST" })
       .then(res => res.text())
       .then(orderId => {
           window.location.href = "/order-success/" + orderId;
       });
}
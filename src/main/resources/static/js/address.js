function deleteAddress(id) {
    if (!confirm("Are you sure you want to delete this address?")) return;

    fetch(`/my-account/address/${id}`, {
        method: "DELETE"
    })
        .then(async res => {
            const msg = await res.text();

            if (res.ok) {
                alert(msg || "Address deleted successfully");
                location.reload();
            } else {
                alert(msg || "Failed to delete address");
            }
        })
        .catch(err => {
            console.error(err);
            alert("Something went wrong!");
        });
}
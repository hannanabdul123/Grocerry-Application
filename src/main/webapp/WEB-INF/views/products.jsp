<%@ page language="java" contentType="text/html;charset=UTF-8" isELIgnored="true" %>
<html>

<head>
    <title>Online Grocery App</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        body{
            font-family: Arial, sans-serif;
            background:#f5f5f5;
            margin:0;
        }

        header{
            background:#2e7d32;
            color:white;
            padding:15px;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            position:relative;
        }

        header a{
            color:white;
            margin-right:15px;
            text-decoration:none;
            font-weight:bold;
        }

        #welcome{
            margin-left: auto;
            margin-right: 10px;
        }

        #container{
            width:90%;
            max-width:1200px;
            margin:auto;
        }

        #productList{
            display:flex;
            flex-wrap:wrap;
            justify-content:center;
        }

        .cart{
            background:white;
            width:220px;
            margin:10px;
            padding:15px;
            border-radius:8px;
            box-shadow:0 0 5px rgba(0,0,0,0.2);
        }

        .price{
            color:green;
            font-weight:bold;
        }

        .addBtn{
            background:#ff9800;
            border:none;
            padding:8px;
            width:100%;
            color:white;
            font-weight:bold;
            margin-top:10px;
            cursor:pointer;
        }

        .deleteBtn{
            background:red;
            border:none;
            padding:6px;
            color:white;
            margin-top:5px;
            width:100%;
            cursor:pointer;
        }

        .adminBtn{
            background:#2196f3;
            color:white;
            border:none;
            padding:10px;
            margin:10px 0;
            cursor:pointer;
        }

        #formDiv{
            display:none;
            background:white;
            padding:15px;
            border-radius:8px;
            margin-bottom:15px;
        }
    </style>

</head>

<body>

<header>
    <a href="/">Home</a>
    <a href="/cart">Cart</a>
    <a href="/orders">Orders</a>
    <span id="welcome"></span>
</header>

<div id="container">

    <h2>🛒 Online Grocery Store</h2>

    <!-- ✅ ADMIN ADD BUTTON -->
    <button id="addBtn" class="adminBtn" style="display:none;">+ Add Product</button>

    <!-- ✅ ADD PRODUCT FORM -->
    <div id="formDiv">
        <h3>Add Product</h3>

        <form id="productForm">
            Name: <input type="text" name="name" required><br><br>
            Price: <input type="number" name="price" required><br><br>
            Image: <input type="file" name="image" required><br><br>
            Description: <input type="text" name="description"><br><br>

            <button type="submit">Save</button>
        </form>
    </div>

    <div id="productList"></div>

</div>

<script>

const token = localStorage.getItem("token");
const role = localStorage.getItem("role");
const username = localStorage.getItem("username");

if(username){
    document.getElementById("welcome").innerText = "👋 " + username;
}

// ✅ Show Add Button only for ADMIN
if(role === "ADMIN"){
    document.getElementById("addBtn").style.display = "block";
}

// ✅ Toggle Form
document.getElementById("addBtn").addEventListener("click", () => {
    const formDiv = document.getElementById("formDiv");
    formDiv.style.display = formDiv.style.display === "none" ? "block" : "none";
});


// ✅ LOAD PRODUCTS
function loadProducts(){

    fetch('/api/products/allproducts')
    .then(res => res.json())
    .then(products => {

        const list = document.getElementById("productList");
        list.innerHTML = "";

        products.forEach(product => {

            let deleteBtn = "";

            // ✅ ADMIN DELETE BUTTON
            if(role === "ADMIN"){
                deleteBtn = `<button class="deleteBtn" onclick="deleteProduct('${product.getProduct_id()}')">Delete</button>`;
            }

            const div = document.createElement("div");
            div.className = "cart";

            div.innerHTML = `
                <h3>${product.name}</h3>

                <img src="/images/${product.imageUrl}" width="150"/>

                <p class="price">₹ ${product.price}</p>

                <button class="addBtn" onclick="addToCart(${product.id})">
                    Add to Cart
                </button>

                ${deleteBtn}
            `;

            list.appendChild(div);
        });
    });
}


// ✅ DELETE PRODUCT
function deleteProduct(id){

    if(!confirm("Delete this product?")) return;

    fetch('/api/admin/delete' + id,{
        method:'DELETE',
        headers:{
            'Authorization':'Bearer ' + token
        }
    })
    .then(() => {
        alert("Deleted!");
        loadProducts();
    });
}


// ✅ ADD PRODUCT
document.getElementById("productForm").addEventListener("submit", function(e){

    e.preventDefault();

    const formData = new FormData(this);

    fetch('/api/products/admin/add',{
        method:'POST',
        headers:{
            'Authorization':'Bearer ' + token
        },
        body: formData
    })
    .then(() => {
        alert("Product Added!");
        document.getElementById("formDiv").style.display = "none";
        loadProducts();
    });

});


// ✅ ADD TO CART (same as before)
function addToCart(productId){

    if(!token){
        alert("Login First");
        window.location="/login";
        return;
    }

    fetch(`/api/cart/add/${productId}?quantity=1`,{
        method:"POST",
        headers:{
            "Authorization":"Bearer "+token
        }
    })
    .then(() => alert("Added to cart"));
}


// ✅ INITIAL LOAD
loadProducts();

</script>

</body>
</html>
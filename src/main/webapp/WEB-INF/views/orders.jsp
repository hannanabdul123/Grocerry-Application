<%@ page contentType="text/html;charset=UTF-8" isELIgnored="true" %>
<html>

<head>

<title>My Orders</title>

<link rel="stylesheet" href="/style.css">

</head>


<body>


<header>

 <!-- 🔥 Nav Links -->
            <nav id="navLinks">
<a href="/">Home</a>
<a href="/cart">Cart</a>  
<a href="#" onclick="logout()">Logout</a>
 </nav>
  <span id="welcome" style="float:right"></span>


</header>


<div id="container">

<h2>📦 My Orders</h2>

<button class="btn backBtn" onclick="goHome()">
Back to Home
</button>

<hr>

<div id="orders"></div>

</div>



<script>

 const name=localStorage.getItem("username");

if(name){

welcome.innerHTML="👋 "+name;

}

const token=localStorage.getItem("token");

if(!token){

    alert("Login first");
    window.location="/login";

}


// ================= LOAD ORDERS =================

fetch('/api/orders/my',{

    headers:{
        'Authorization':'Bearer '+token
    }

})
.then(res=>res.json())
.then(orders=>{

    const ordersDiv=document.getElementById("orders");

    if(orders.length==0){

        ordersDiv.innerHTML="<p>No orders found</p>";
        return;

    }

    orders.forEach(order=>{

        let paymentSection="";

        if(order.paymentStatus==="PENDING"
           && order.orderStatus==="PLACED"){

            paymentSection=`

                <button class="btn payBtn"
                 onclick="payNow(${order.id})">
                 Pay Now
                </button>

                <button class="btn cancelBtn"
                 onclick="cancelNow(${order.id})">
                 Cancel
                </button>

            `;

        }

        else if(order.orderStatus==="CANCELLED"){

            paymentSection=
            `<span style="color:red">
                CANCELLED
             </span>`;

        }

        else{

            paymentSection=`

                <span style="color:green">
                    Paid ✅
                </span>

                <br>
                 <br>

                <a href="/invoice?orderid=${order.id}">
                View Invoice
                </a>

            `;

        }


        const div=document.createElement("div");

        div.className="orderCard";

        div.innerHTML=`

            <h3>Order #${order.id}</h3>

            <p>Status: ${order.orderStatus}</p>

            <p>Payment: ${order.paymentStatus}</p>

            <p>Total: ₹${order.totalAmount}</p>

            <p>Date:
            ${order.orderDate || order.createdAt}
            </p>

            ${paymentSection}

        `;

        ordersDiv.appendChild(div);

    });

});


// ================= CANCEL =================

function cancelNow(orderId){

if(!confirm("Cancel order "+orderId+" ?"))
return;

fetch('/api/orders/'+orderId+'/cancel',{

    method:"PUT",

    headers:{
        'Authorization':'Bearer '+token
    }

})
.then(()=>location.reload());

}



// ================= PAY =================

function payNow(orderId){

if(!confirm("Pay order "+orderId+" ?"))
return;

fetch('/api/orders/'+orderId+'/pay',{

    method:"PUT",

    headers:{
        'Authorization':'Bearer '+token
    }

})
.then(()=>location.reload());

}



function goHome(){

    window.location="/";

}
function logout(){

    localStorage.clear();
    sessionStorage.clear();

    alert("Logged out");
    window.location="/login";
}

</script>


</body>

</html>
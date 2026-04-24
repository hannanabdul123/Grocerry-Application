<%@ page contentType="text/html;charset=UTF-8" isELIgnored="true" %>
<html>

<head>

<title>Login | Grocery App</title>


     <link rel="stylesheet" href="/style.css">

</head>

<body>


<header>

<div>

<a href="/">Home</a>

</div>

<div>

<span id="welcome"></span>

</div>

</header>


<div class="Login">

<div class="Login-cart">

<h2>Login</h2>

<form id="loginForm">

Email
<input type="email" id="email" required>

Password
<input type="password" id="password" required>

<input type="checkbox" id="showPassword">
Show Password

<br><br>

<button type="submit" class="logBtn">
Login
</button>

</form>

<p class="msg" id="message"></p>

<p>
No account?
<a href="/signup">Signup</a>
</p>
<p>
<a href="/forgot-password">Forgot Password?</a>
</p>

</div>

</div>


<script>

const name=localStorage.getItem("username");

if(name){
welcome.innerHTML="👋 "+name;
}


showPassword.onchange=function(){
password.type=this.checked?"text":"password";
};


loginForm.onsubmit=function(e){

e.preventDefault();

fetch("/api/users/login",{

method:"POST",

headers:{
"Content-Type":"application/json"
},

body:JSON.stringify({
userEmail:email.value,
password:password.value
})

})

.then(res=>{

if(!res.ok){
return res.text().then(m=>{throw new Error(m)});
}

return res.json();

})

.then(r=>{
  console.log("FULL RESPONSE:", r);
localStorage.setItem("token", r.token);
localStorage.setItem("role", r.role);
localStorage.setItem("username", r.name);
localStorage.setItem("userId", r.userId);
alert("Welcome "+r.name);

window.location="/";

})

.catch(err=>{
message.innerText=err.message;
});

};

</script>

</body>
</html>
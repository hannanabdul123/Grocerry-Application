<%@ page contentType="text/html;charset=UTF-8" isELIgnored="true" %>
   <html>

   <head>

      <title>Admin Dashboard</title>
      <link rel="stylesheet" href="/style.css">

   </head>

   <body>


      <header>

         <div>

            🛠 Admin Dashboard

         </div>
         <nav id="navLinks">

            <a href="/">Home</a>

            <a href="#" onclick="logout()">Logout</a>

         </nav>
         <span id="welcome" style="float:right"></span>
      </header>


      <div class="usercontainer">

         <h3>All Users</h3>

         <div id="users" class="users"></div>

      </div>


      <script>

         const name = localStorage.getItem("username");

         if (name) {

            welcome.innerHTML = "👋 " + name;

         }


         function logout() {

            localStorage.clear();

            alert("Logged out");

            window.location = "/login";

         }


         function init() {

            const token = localStorage.getItem("token");

            if (!token) {

               alert("Login first");

               window.location = "/login";

               return;

            }


            fetch('/api/admin/users', {

               headers: {
                  'Authorization': 'Bearer ' + token
               }

            })

               .then(res => {

                  if (!res.ok) throw new Error();

                  return res.json();

               })

               .then(users => {

                  const div = document.getElementById("users");

                  if (users.length == 0) {

                     div.innerHTML = "No users";

                     return;

                  }


                  users.forEach(u => {

                     if (u.role == "ADMIN") return;


                     const card = document.createElement("div");

                     card.className = "card";


                     const btn =

                        u.active

                           ?

                           `



<button class="deactivate"
onclick="deactivateUser(${u.userId})">
Deactivate
</button>`

                           :

                           `<button class="activate"
onclick="activateUser(${u.userId})">
Activate
</button>`;


                     card.innerHTML = `

<b>ID:</b> ${u.userId} <br>

<b>Name:</b> ${u.name} <br>

<b>Email:</b> ${u.userEmail} <br>

<b>Status:</b> ${u.active} <br>

${btn}

<br>

<button class="view" onclick="viewUser(${u.userId})">
View Details
</button>

<br>

<br>

<button class="delete"
onclick="deleteUser(${u.userId})">

Delete

</button>

`;

                     div.appendChild(card);

                  });

               });

         }


         function deactivateUser(id) {

            const token = localStorage.getItem("token");

            fetch(`/api/admin/users/${id}/deactivate`, {

               method: "PUT",

               headers: {
                  'Authorization': 'Bearer ' + token
               }

            })

               .then(() => location.reload());

         }

         function viewUser(id) {
            window.location = "/admin-user?userId=" + id;
         }
         function activateUser(id) {

            const token = localStorage.getItem("token");

            fetch(`/api/admin/users/${id}/activate`, {

               method: "PUT",

               headers: {
                  'Authorization': 'Bearer ' + token
               }

            })

               .then(() => location.reload());

         }


         function deleteUser(id) {

            const token = localStorage.getItem("token");

            if (!confirm("Delete user?")) return;

            fetch(`/api/admin/users/${id}`, {

               method: "DELETE",

               headers: {
                  'Authorization': 'Bearer ' + token
               }

            })

               .then(() => location.reload());

         }


         init();

      </script>

   </body>

   </html>
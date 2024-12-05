<template>
  <div class="password-management">
    <header>
      <div class="header-top">
        <div class="header-left">
          <img src="@/assets/logo.png" alt="Logo" class="logo" />
          <h1>Game Store</h1>
        </div>
        <div class="header-right">
          <div v-if="userEmail">
            <span class="user-email">Welcome, {{ userName }}!</span>
            <button @click="$router.push({ name: 'Dashboard' })" class="dashboard-button">Dashboard</button>
            <button @click="signOut" class="sign-out-button">Sign Out</button>
          </div>
          <div v-else>
            <router-link to="/" class="sign-in-button">Sign Out</router-link>
          </div>
        </div>
      </div>
      <hr />
    </header>
    <h1>Customer DashBoard</h1>
    <form @submit.prevent="updatePassword">
      <label for="email">Email: </label><br>
      <input type="email" v-model="updateCustomer.email" placeholder="Enter your Email" required /><br>

      <label for="oldPassword">Old Password: </label><br>
      <input type="password" v-model="updateCustomer.oldPassword" placeholder="Enter your Old Password" required /><br>

      <label for="newPassword">New Password: </label><br>
      <input type="password" v-model="updateCustomer.newPassword" placeholder="Enter your New Password"
        required /><br><br>

      <button type="submit">Update Password</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      updateCustomer: {
        email: '',
        oldPassword: '',
        newPassword: ''
      }
    };
  },
  methods: {
    // Update customer password
    async updatePassword() {
      try {
        const { email, oldPassword, newPassword } = this.updateCustomer;
        const url = `http://localhost:8080/customer/${email}/updatePassword`;
        const params = new URLSearchParams({ oldPassword, newPassword });
        const response = await fetch(url, {
          method: "PUT",
          headers: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          body: params
        });
        if (!response.ok) throw new Error(await response.text());
        alert("Password updated successfully!");
        this.updateCustomer = { email: "", oldPassword: "", newPassword: "" };
      } catch (error) {
        console.error("Error updating password:", error);
        alert("Error updating password: " + error.message);
      }
    }
  }
};
</script>

<style>

html,
body {
  background-color: #000 !important;
}

.update-password-container {
  width: 900px;
  
  height: auto;
  background-color: #1c1c1c;
  border-radius: 8px;
  /* Rounded corners */
  padding: 30px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.5);
  text-align: center;
  margin-left: 10px;
}

.logo {
  height: 50px;
  width: 50px;
  margin-right: 10px;
  display: inline-block;
  vertical-align: middle;
  border-radius: 28px;
  margin-left: 10px;
}

header {
  background-color: #000;
  color: white;
  margin-bottom: 10px;
}

.header-top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 32px;
}

.header-top .header-left {
  display: flex;
  align-items: center;
}

.header-top .header-left h1 {
  font-size: 24px;
  font-weight: bold;
}


.header-top .header-right {
  display: flex;
  align-items: center;
}

.user-email {
  margin-right: 16px;
  font-size: 16px;
  color: #fff;
}

.sign-in-button,
.sign-out-button {
  background-color: #1a73e8;
  color: white;
  padding: 8px 12px;
  margin-left: 8px;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  text-decoration: none;
  font-size: 14px;
  line-height: normal;
  display: inline-flex;
  align-items: center;
  width: fit-content
}

.sign-in-button:hover,
.sign-out-button:hover {
  background-color: #155cb0;
}


hr {
  border: 0;
  border-top: 1px solid #333;
  margin: 0;
}

.header-center {
  display: flex;
  justify-content: left;
  align-items: center;
  gap: 8px;
  padding: 16px 32px;
}

.header-center input {
  width: 100%;
  max-width: 200px;
  padding: 8px 12px;
  border: 0.5px solid #555;
  border-radius: 16px;
  background-color: #333;
  color: white;
}

.header-center button {
  padding: 8px 16px;
  background-color: transparent;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: color 0.3s ease;
}

.header-center button:hover,
.header-center button:active {
  color: grey;
}

header button:hover {
  background-color: #000;
}

/* Form Group */
.form-group {
  position: relative;
  margin-bottom: 20px;
}

input[type="email"],
input[type="password"] {
  width: 100%;
  padding: 12px 15px;
  background-color: #292929;
  /* Input background */
  border: 1px solid #333;
  /* Input border */
  border-radius: 8px;
  /* Rounded input fields */
  color: white;
  /* Text color */
  font-size: 16px;
  box-sizing: border-box;
}

input[type="email"]:focus,
input[type="password"]:focus {
  outline: none;
  border-color: #555;
  /* Highlight border on focus */
  box-shadow: 0px 0px 5px rgba(255, 255, 255, 0.2);
}

/* Update Password Button */
.btn-update-password {
  background-color: transparent;
  /* Same as the sign-in button background */
  color: white;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.btn-update-password:hover {
  color: grey;
  /* Darker blue on hover */
}


/* Update Error */
.update-error {
  color: #ff4d4f;
  /* Error message color */
  font-size: 14px;
  margin-bottom: 10px;
}
</style>
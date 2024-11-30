<template>
  <div class="password-management">
    <h1>Update Your Password</h1>
    <form @submit.prevent="updatePassword">
      <label for="email">Email: </label><br>
      <input type="email" v-model="updateCustomer.email" placeholder="Enter your Email" required /><br>

      <label for="oldPassword">Old Password: </label><br>
      <input type="password" v-model="updateCustomer.oldPassword" placeholder="Enter your Old Password" required /><br>

      <label for="newPassword">New Password: </label><br>
      <input type="password" v-model="updateCustomer.newPassword" placeholder="Enter your New Password" required /><br><br>

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

/* General Styling for Global Background */
html, body {
  background-color: #000 !important; /* Black background */
}

/* Update Password Container Styling */
.update-password-container {
  width: 450px; /* Adjust the width of the container */
  height: auto; /* Let height adjust based on content */
  background-color: #1c1c1c; /* Darker background for the container */
  border-radius: 8px; /* Rounded corners */
  padding: 30px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.5); /* Subtle shadow effect */
  text-align: center;
  margin-left: 10px;
}

/* Heading */
h2 {
  font-size: 24px;
  margin-bottom: 30px;
  color: white;
  font-weight: bold;
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
  background-color: #292929; /* Input background */
  border: 1px solid #333; /* Input border */
  border-radius: 8px; /* Rounded input fields */
  color: white; /* Text color */
  font-size: 16px;
  box-sizing: border-box;
}

input[type="email"]:focus,
input[type="password"]:focus {
  outline: none;
  border-color: #555; /* Highlight border on focus */
  box-shadow: 0px 0px 5px rgba(255, 255, 255, 0.2);
}

/* Update Password Button */
.btn-update-password {
  background-color: transparent; /* Same as the sign-in button background */
    color: white;
    border: none;
    border-radius: 4px;
    padding: 8px 16px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.btn-update-password:hover {
  color: grey; /* Darker blue on hover */
}


/* Update Error */
.update-error {
  color: #ff4d4f; /* Error message color */
  font-size: 14px;
  margin-bottom: 10px;
}

</style>
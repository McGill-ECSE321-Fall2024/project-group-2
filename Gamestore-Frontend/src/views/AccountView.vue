<template>
  <div class="customer-management">
    <h1>Welcome to the Game Store!</h1>

    <!-- Create Customer Account Form -->
    <div>
      <h2>Create Your Account</h2>
      <form @submit.prevent="createCustomer">
        <label for="userID">User ID: </label><br>
        <input type="text" v-model="newCustomer.userID" placeholder="Enter your UserID" required /><br>

        <label for="name">Name: </label><br>
        <input type="text" v-model="newCustomer.name" placeholder="Enter your Name" required /><br>

        <label for="email">Email: </label><br>
        <input type="email" v-model="newCustomer.email" placeholder="Enter your Email" required /><br>

        <label for="password">Password: </label><br>
        <input type="password" v-model="newCustomer.password" placeholder="Enter your Password" required /><br><br>

        <button type="submit">Create Account</button>
      </form>
    </div>

    <!-- Update Customer Password Form -->
    <div>
      <h2>Update Your Password</h2>
      <form @submit.prevent="updatePassword">
        <label for="updateEmail">Email: </label><br>
        <input type="email" v-model="updateCustomer.email" placeholder="Enter your Email" required /><br>

        <label for="oldPassword">Old Password: </label><br>
        <input type="password" v-model="updateCustomer.oldPassword" placeholder="Enter your Old Password" required /><br>

        <label for="newPassword">New Password: </label><br>
        <input type="password" v-model="updateCustomer.newPassword" placeholder="Enter your New Password" required /><br><br>

        <button type="submit">Update Password</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newCustomer: {
        userID: '',
        name: '',
        email: '',
        password: ''
      },
      updateCustomer: {
        email: '',
        oldPassword: '',
        newPassword: ''
      }
    };
  },
  methods: {
    // Create a new customer
    async createCustomer() {
      try {
        const response = await fetch("http://localhost:8080/customer", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(this.newCustomer)
        });
        if (!response.ok) throw new Error(await response.text());
        alert("Account created successfully! Welcome aboard!");
        this.newCustomer = { userID: "", name: "", email: "", password: "" };
      } catch (error) {
        console.error("Error creating account:", error);
        alert("Error creating account: " + error.message);
      }
    },

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

<style scoped>
.customer-management {
  max-width: 800px;
  margin: 0 auto;
  font-family: Arial, sans-serif;
}

h1, h2 {
  color: #333;
}

form {
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 8px;
  margin: 5px 0;
  border-radius: 4px;
  border: 1px solid #ccc;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>

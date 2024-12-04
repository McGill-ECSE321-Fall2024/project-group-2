<template>
  <div class="login-container">
    <!-- Logo -->
    <div class="logo-container">
      <img :src="logo" alt="Logo" class="logo" />
    </div>

    <!-- Sign In Title -->
    <h2>Sign In</h2>

    <!-- Sign In Form -->
    <form @submit.prevent="submitSignIn" class="login-form">
      <!-- Email Input -->
      <div class="form-group">
        <input
          type="email"
          id="email"
          v-model="email"
          placeholder="Email address"
          required
        />
      </div>

      <!-- Password Input -->
      <div class="form-group">
        <input
          type="password"
          id="password"
          v-model="password"
          placeholder="Password"
          required
        />
      </div>

      <!-- Error Message -->
      <div v-if="loginError" class="login-error">
        {{ loginError }}
      </div>

      <!-- Submit Button -->
      <button type="submit" class="btn-signin" :disabled="!isFormValid()">Sign In</button>
    </form>

    <!-- Divider -->
    <div class="divider">
      <hr />
      <span>or create account</span>
      <hr />
    </div>

    <!-- Register Link -->
    <div class="register-container">
      <router-link to="/account" class="register-link">Register</router-link>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import logo from "@/assets/logo.png";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080", // Replace with your backend URL
});

export default {
  name: "SignInView",
  data() {
    return {
      email: "",
      password: "",
      loginError: "",
      logo, // Logo for the application
    };
  },
  methods: {
    /**
     * Validates if the form fields are filled out
     */
    isFormValid() {
      return this.email && this.password;
    },

    /**
     * Handles the sign-in process for Customer, Employee, and Owner
     */
    async submitSignIn() {
      this.loginError = ""; // Reset error message

      // Define the roles and their respective login endpoints
      const roles = [
        { role: "customer", view: "/search" }, // Redirects to CustomerView
        { role: "employee", view: "/employee" }, // Redirects to EmployeeView
        { role: "owner", view: "/owner" }, // Redirects to OwnerView
      ];

      // Try logging in for each role
      for (let { role, view } of roles) {
        try {
          console.log(`Attempting login as ${role}`, { email: this.email, password: this.password });

          const response = await axiosClient.post(
            `/${role}/login?email=${encodeURIComponent(this.email)}&password=${encodeURIComponent(this.password)}`
          );

          console.log(`${role.charAt(0).toUpperCase() + role.slice(1)} login successful:`, response.data);

          // Store the email and role in localStorage
          localStorage.setItem("userEmail", this.email);
          localStorage.setItem("userRole", role);
          localStorage.setItem("userName", response.data.name);

          // Redirect to the appropriate view
          await this.$router.push(view);
          console.log(`Redirected to ${view}`);
          return; // Exit loop after successful login
        } catch (error) {
          console.error(`${role} login failed. Error:`, error.response?.data || "Unexpected error");
        }
      }

      // If all attempts fail, show error message
      this.loginError = "Invalid email or password. Please try again.";
    },
  },
};
</script>

<style>
/* Add your styles here */
</style>


<style>

/* General Styling for Global Background */
html, body {
  background-color: #000 !important; /* Black background */
}

/* Login Container Styling */
.login-container {
  width: 450px; /* Adjust the width of the container */
  height: auto; /* Let height adjust based on content */
  background-color: #1c1c1c; /* Darker background for the container */
  border-radius: 8px; /* Rounded corners */
  padding: 30px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.5); /* Subtle shadow effect */
  text-align: center;
  margin-left: 370px;
}

/* Logo Styling */
.logo-container {
  margin-bottom: 20px; /* Add spacing below the logo */
}

.logo {
  width: 80px; /* Adjust the size of the logo */
  height: 80px; /* Maintain square ratio */
  margin: 0 auto; /* Center the logo */
  border-radius: 40px; /* Make the logo circular */
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

/* Divider Styling */
.divider {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px 0;
  color: #888;
  font-size: 14px;
}

.divider hr {
  flex: 1;
  border: none;
  border-top: 1px solid #555; /* Horizontal line color */
  margin: 0 10px; /* Spacing around text */
}

.divider span {
  color: #aaa; /* Text color */
}

/* Forgot Password Link */
.forgot-password {
  display: block;
  text-align: left;
  font-size: 14px;
  color: #888; /* Text color */
  text-decoration: none;
  margin-top: 10px;
}

.forgot-password:hover {
  color: #fff;
  text-decoration: underline;
}

/* Sign In Button */
.btn-signin {
  width: 100%;
  padding: 12px 20px;
  font-size: 16px;
  font-weight: bold;
  background-color: #0070f3; /* Blue button */
  color: white; /* Text color */
  border: none;
  border-radius: 8px; /* Rounded corners */
  cursor: pointer;
  margin-top: 20px;
  transition: background-color 0.3s ease;
}

.btn-signin:hover {
  background-color: #005bb5; /* Darker blue on hover */
}

/* Register Link */
.register-container {
  margin-top: 10px;
}

.register-link {
  color: #0070f3; /* Link color */
  font-size: 14px;
  text-decoration: none;
}

.register-link:hover {
  text-decoration: underline;
}

/* Login Error */
.login-error {
  color: #ff4d4f; /* Error message color */
  font-size: 14px;
  margin-bottom: 10px;
}
</style>

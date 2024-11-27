<template>
  <div class="employee-management">
    <h1>Employee Management</h1>

    <!-- Create Employee Form -->
    <div>
      <h2>Create Employee</h2>
      <form @submit.prevent="createEmployee">
        <label for="userID">User ID: </label><br>
        <input type="text" v-model="newEmployee.userID" required /><br>

        <label for="name">Name: </label><br>
        <input type="text" v-model="newEmployee.name" required /><br>

        <label for="email">Email: </label><br>
        <input type="email" v-model="newEmployee.email" required /><br>

        <label for="password">Password: </label><br>
        <input type="password" v-model="newEmployee.password" required /><br><br>

        <button type="submit">Create Employee</button>
      </form>
    </div>

    <!-- Update Employee Password Form -->
    <div>
      <h2>Update Employee Password</h2>
      <form @submit.prevent="updatePassword">
        <label for="updateEmail">Email: </label><br>
        <input type="email" v-model="updateEmployee.email" required /><br>

        <label for="oldPassword">Old Password: </label><br>
        <input type="password" v-model="updateEmployee.oldPassword" required /><br>

        <label for="newPassword">New Password: </label><br>
        <input type="password" v-model="updateEmployee.newPassword" required /><br><br>

        <button type="submit">Update Password</button>
      </form>
    </div>

    <!-- Employee List -->
    <div>
      <h2>Employee List</h2>
      <ul>
        <li v-for="employee in employees" :key="employee.email">
          <strong>{{ employee.name }}</strong> ({{ employee.email }}) - UserID: {{ employee.userID }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      employees: [],
      newEmployee: {
        userID: '',
        name: '',
        email: '',
        password: ''
      },
      updateEmployee: {
        email: '',
        oldPassword: '',
        newPassword: ''
      }
    };
  },
  methods: {
    // Fetch all employees
    fetchEmployees() {
      fetch("http://localhost:8080/employee")
        .then((response) => response.json())
        .then((data) => {
          this.employees = data.employees;
        })
        .catch((error) => {
          console.error("Error fetching employees:", error);
        });
    },

    // Create a new employee
    createEmployee() {
      const employeeData = { ...this.newEmployee };
      fetch("http://localhost:8080/employee", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(employeeData)
      })
        .then((response) => response.json())
        .then((data) => {
          alert("Employee created successfully!");
          this.fetchEmployees();
          this.newEmployee = { userID: "", name: "", email: "", password: "" };
        })
        .catch((error) => {
          console.error("Error creating employee:", error);
        });
    },

    // Update employee password
    updatePassword() {
      const { email, oldPassword, newPassword } = this.updateEmployee;
      const url = `http://localhost:8080/employee/${email}/`;
      const params = new URLSearchParams({ oldPassword, newPassword });

      fetch(url, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: params
      })
        .then((response) => response.json())
        .then((data) => {
          alert("Password updated successfully!");
          this.updateEmployee = { email: "", oldPassword: "", newPassword: "" };
        })
        .catch((error) => {
          console.error("Error updating password:", error);
        });
    }
  },
  mounted() {
    this.fetchEmployees();
  }
};
</script>

<style scoped>
.employee-management {
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

ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>

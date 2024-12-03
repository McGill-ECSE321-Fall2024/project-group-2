<template>
  <div class="staff-view">
    <h1>Owner Portal</h1>
     <section class="form-section">
        <h2>Employee Management</h2>

        <!-- Add New Employee -->
        <h3>Add New Employee</h3>
        <form @submit.prevent="addEmployee" class="form-grid">
          <div>
            <label>User ID:</label>
            <input v-model="newEmployee.userID" type="text" required />
          </div>
          <div>
            <label>Name:</label>
            <input v-model="newEmployee.name" type="text" required />
          </div>
          <div>
            <label>Email:</label>
            <input v-model="newEmployee.email" type="email" required />
          </div>
          <div>
            <label>Password:</label>
            <input v-model="newEmployee.password" type="password" required />
          </div>
          <button class="btn-primary" type="submit">Add Employee</button>
          <p v-if="employeeErrorMessage" class="error-message">{{ employeeErrorMessage }}</p>
          <p v-if="employeeSuccessMessage" class="success-message">{{ employeeSuccessMessage }}</p>
        </form>

        <!-- Edit Employee Password -->
        <h3>Update Employee Password</h3>
        <form @submit.prevent="updateEmployeePassword" class="form-grid">
          <div>
            <label>Email:</label>
            <input v-model="editEmployeeRequest.email" type="email" required />
          </div>
          <div>
            <label>Old Password:</label>
            <input v-model="editEmployeeRequest.oldPassword" type="password" required />
          </div>
          <div>
            <label>New Password:</label>
            <input v-model="editEmployeeRequest.newPassword" type="password" required />
          </div>
          <button class="btn-primary" type="submit">Update Password</button>
          <p v-if="passwordErrorMessage" class="error-message">{{ passwordErrorMessage }}</p>
          <p v-if="passwordSuccessMessage" class="success-message">{{ passwordSuccessMessage }}</p>
        </form>

        <!-- Delete Employee -->
        <h3>Delete Employee</h3>
        <form @submit.prevent="deleteEmployee" class="form-grid">
          <div>
            <label>Email:</label>
            <input v-model="deleteEmployeeEmail" type="email" required />
          </div>
          <button class="btn-primary" type="submit">Delete Employee</button>
          <p v-if="deleteEmployeeErrorMessage" class="error-message">{{ deleteEmployeeErrorMessage }}</p>
          <p v-if="deleteEmployeeSuccessMessage" class="success-message">{{ deleteEmployeeSuccessMessage }}</p>
        </form>

        <!-- View Employees -->
        <h3>View Employees</h3>
        <button @click="fetchEmployees" class="btn-primary">Fetch Employees</button>
        <div v-if="employees.length > 0" class="employee-list">
          <h4>Employee List</h4>
          <table>
            <thead>
              <tr>
                <th>User ID</th>
                <th>Name</th>
                <th>Email</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="employee in employees" :key="employee.email">
                <td>{{ employee.userID }}</td>
                <td>{{ employee.name }}</td>
                <td>{{ employee.email }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    <!-- Add New Game -->
    <section class="form-section">
      <h2>Add New Game</h2>
      <form @submit.prevent="addGame" class="form-grid">
        <div>
          <label>Image:</label>
          <input type="file" @change="onImageUpload" accept=".png, .jpeg, .jpg" />
        </div>
        <div>
          <label>Category:</label>
          <select v-model="newGame.category" required>
            <option disabled value="">Select a Category</option>
            <option v-for="category in categories" :key="category">{{ category.name }}</option>
          </select>
        </div>
        <div>
          <label>Name:</label>
          <input v-model="newGame.name" type="text" required />
        </div>
        <div>
          <label>Description:</label>
          <textarea v-model="newGame.description" required></textarea>
        </div>
        <div>
          <label>Price:</label>
          <input v-model="newGame.price" type="number" step="0.01" required />
        </div>
        <div>
          <label>Quantity:</label>
          <input v-model="newGame.quantity" type="number" min="0" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
        <p v-if="errorMessageP1" class="error-message">{{ errorMessageP1 }}</p>
        <p v-if="successMessageP1" class="success-message">{{ successMessageP1 }}</p>
      </form>
    </section>

    <!-- Edit Game Section -->
    <section class="form-section">
      <h2>Edit Game</h2>
      <!-- Edit Game Name -->
      <div class="form-grid">
        <h3>Edit Game Name</h3>
        <form @submit.prevent="editGameName" class="form-grid">
          <div>
            <label>Game Name:</label>
            <input v-model="editGameRequest.oldName" type="text" placeholder="Enter Game Name" required />
          </div>
          <div>
            <label>New Game Name:</label>
            <input v-model="editGameRequest.name" type="text" required />
          </div>
          <button class="btn-primary" type="submit">Submit</button>
          <p v-if="errorMessageP2" class="error-message">{{ errorMessageP2 }}</p>
          <p v-if="successMessageP2" class="success-message">{{ successMessageP2 }}</p>
        </form>
      </div>

      <!-- Edit Game Description -->
      <div class="form-grid">
        <h3>Edit Game Description</h3>
        <form @submit.prevent="editGameDescription" class="form-grid">
          <div>
            <label>Game Name:</label>
            <input v-model="editGameRequest.oldName" type="text" placeholder="Enter Game Name" required />
          </div>
          <div>
            <label>New Game Description:</label>
            <textarea v-model="editGameRequest.description" required></textarea>
          </div>
          <button class="btn-primary" type="submit">Submit</button>
          <p v-if="errorMessageP3" class="error-message">{{ errorMessageP3 }}</p>
          <p v-if="successMessageP3" class="success-message">{{ successMessageP3 }}</p>
        </form>
      </div>
    </section>

    <!-- Delete Game -->
    <section class="form-section">
      <h2>Delete Game</h2>
      <form @submit.prevent="deleteGame" class="form-grid">
        <div>
          <label>Game Name:</label>
          <input v-model="deleteGameName" type="text" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
        <p v-if="errorMessageP4" class="error-message">{{ errorMessageP4 }}</p>
        <p v-if="successMessageP4" class="success-message">{{ successMessageP4 }}</p>
      </form>
    </section>

    <!-- Manage Categories -->
    <section class="form-section">
      <h2>Manage Categories</h2>

      <!-- Add New Category -->
      <h3>Add New Category</h3>
      <form @submit.prevent="addCategory" class="form-grid">
        <div>
          <label>Category Name:</label>
          <input v-model="newCategory.name" type="text" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
      </form>
      <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
      <p v-if="successMessage" class="success-message">{{ successMessage }}</p>

      <!-- Edit Category -->
      <h3>Edit Category</h3>
      <form @submit.prevent="updateCategory" class="form-grid">
        <div>
          <label>Category Name:</label>
          <input v-model="updateCategoryRequest.oldName" type="text" required />
        </div>
        <div>
          <label>New Name:</label>
          <input v-model="updateCategoryRequest.name" type="text" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
      </form>
      <p v-if="errorMessage2" class="error-message">{{ errorMessage2 }}</p>
      <p v-if="successMessage2" class="success-message">{{ successMessage2 }}</p>

      <!-- Delete Category -->
      <h3>Delete Category</h3>
      <form @submit.prevent="deleteCategory" class="form-grid">
        <div>
          <label>Category Name:</label>
          <input v-model="deleteCategoryName" type="text" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
      </form>
      <p v-if="errorMessage3" class="error-message">{{ errorMessage3 }}</p>
      <p v-if="successMessage3" class="success-message">{{ successMessage3 }}</p>
    </section>

    <!-- View Orders -->
    <section class="form-section">
      <h2>View and Edit Order</h2>
      <form @submit.prevent="redirectToOrderView" class="form-grid">
      <div>
        <label>Order Number:</label>
        <input v-model="orderNumber" type="text" placeholder="Enter Order Number" required />
      </div>
      <button class="btn-primary" type="submit">Submit</button>
      <p v-if="orderErrorMessage" class="error-message">{{ orderErrorMessage }}</p>
      </form>
    </section>



        <!-- Manage Change Requests -->
        <section class="form-section">
          <h2>Manage Change Requests</h2>

          <!-- Fetch Change Requests Button -->
          <button @click="fetchChangeRequests" class="btn-primary">Fetch Change Requests</button>

          <!-- Change Requests List -->
          <div v-if="changeRequests.length > 0" class="change-requests-list">
            <h3>Change Requests</h3>
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Creator Email</th>
                  <th>Status</th>
                  <th>Created At</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="request in changeRequests" :key="request.id">
                  <td>{{ request.id }}</td>
                  <td>{{ request.requestCreatorEmail }}</td>
                  <td>{{ request.status }}</td>
                  <td>{{ formatDate(request.createdAt) }}</td>
                  <td>
                    <div class="action-buttons">
                      <button
                        @click="approveRequest(request.id)"
                        :disabled="request.status !== 'PENDING'"
                        class="btn-approve"
                      >
                        Approve
                      </button>
                      <button
                        @click="declineRequest(request.id)"
                        :disabled="request.status !== 'PENDING'"
                        class="btn-decline"
                      >
                        Decline
                      </button>
                      <button
                        @click="deleteRequest(request.id)"
                        class="btn-delete"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <!-- Status Messages -->
          <p v-if="changeRequestErrorMessage" class="error-message">{{ changeRequestErrorMessage }}</p>
          <p v-if="changeRequestSuccessMessage" class="success-message">{{ changeRequestSuccessMessage }}</p>
        </section>
      </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  name: "StaffView",
  setup() {
    const router = useRouter();

    // Form Data
    const newGame = ref({
      category: "",
      name: "",
      description: "",
      quantity: 0,
      price: 0,
      image: "",
    });
    const email= ref("");
    const categories = ref([]); // Example categories
    const newCategory = ref({ name: "" });
    const editGameRequest = ref({ oldName: "", name: "", description: "" });
    const deleteGameName = ref("");
    const updateCategoryRequest = ref({ oldName: "", name: "" });
    const deleteCategoryName = ref("");
    const orderNumber = ref("");

    // Category Status Messages
    const errorMessage = ref(null);
    const successMessage = ref(null);
    const errorMessage2 = ref(null);
    const successMessage2 = ref(null);
    const errorMessage3 = ref(null);
    const successMessage3 = ref(null);

    // Product Status Messages
    const errorMessageP1 = ref(null);
    const successMessageP1 = ref(null);
    const errorMessageP2 = ref(null);
    const successMessageP2 = ref(null);
    const errorMessageP3 = ref(null);
    const successMessageP3 = ref(null);
    const errorMessageP4 = ref(null);
    const successMessageP4 = ref(null);

    // Order Status Message
    const orderErrorMessage = ref(null);

    // Change_requests message
    const changeRequestErrorMessage= ref(null);
    const changeRequestSuccessMessage= ref (null);

    // Fetch categories from the backend
    const fetchCategories = async () => {
      try {
        const response = await axiosClient.get("/category");
        categories.value = response.data["category"];
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();

    const redirectToOrderView = async () => {
      orderErrorMessage.value= null;
      try{
        const response = await axiosClient.get("/orders/number/"+orderNumber.value);
        console.log(response)
        router.push({ name: "order_management", params: { id: orderNumber.value } });
      }
      catch  (error) {
        orderErrorMessage.value = error.response.data || "An error occurred.";
      }
    };

    const onImageUpload = (event) => {
      newGame.value.image = event.target.files[0];
      const reader = new FileReader();
      const file = event.target.files[0];
      if (file) {
        reader.readAsDataURL(file); // Convert file to Base64
      }
      reader.onloadend = () => {
        newGame.value.image = reader.result.split(',')[1]; // Extract Base64 string
      };
    };

    const addGame = async () => {
      successMessageP1.value= null;
      errorMessageP1.value= null;
      try {
        const new_line_item= {price: newGame.value.price, quantity: newGame.value.quantity};
        const new_line_item2=  await axiosClient.post("/lineitems", new_line_item);
        const choosen_cat= await axiosClient.get("/category/name/"+ newGame.value.category);
        const new_game= {name: newGame.value.name, description: newGame.value.description, imageURL: newGame.value.image,
          lineItem_id: new_line_item2.data.lineItemId, category_id: choosen_cat.data.id};
        await axiosClient.post("/product", new_game);
        successMessageP1.value = "Product added successfully!";
      } catch (error) {
        errorMessageP1.value = error.response.data || "An error occurred.";
      }
    };

    const editGameName= async () => {
      successMessageP2.value= null;
      errorMessageP2.value= null;
      try{
        console.log(editGameRequest.value.oldName)
        const game_edit= await axiosClient.get("/product/name/"+editGameRequest.value.oldName);
        console.log(game_edit)
        await axiosClient.put("/product/name/"+game_edit.data.id+"?newName="+editGameRequest.value.name);
        successMessageP2.value = "Product name updated successfully!";
      }
      catch (error) {
        errorMessageP2.value = error.response.data || "An error occurred.";
      }
    };

    const editGameDescription= async () => {
      successMessageP3.value= null;
      errorMessageP3.value= null;
      try{
        const game_edit= await axiosClient.get("/product/name/"+editGameRequest.value.oldName);
        await axiosClient.put("/product/description/"+game_edit.data.id+"?newDescription="+editGameRequest.value.description);
        successMessageP3.value = "Product description updated successfully!";
      }
      catch (error) {
        errorMessageP3.value = error.response.data || "An error occurred.";
      }
    };

    const deleteGame = async () => {
      successMessageP4.value= null;
      errorMessageP4.value= null;
      try {
        const game_edit= await axiosClient.get("/product/name/"+deleteGameName.value);
        await axiosClient.delete("/product/"+game_edit.data.id);
        successMessageP4.value = "Product deleted successfully!";
      } catch (error) {
        errorMessageP4.value = error.response.data || "An error occurred.";
      }
    };

    const addCategory = async () => {
      successMessage.value= null;
      errorMessage.value= null;
      try {
        await axiosClient.post("/category", { name: newCategory.value.name });
        successMessage.value = "Category added successfully!";
        categories.value.push(newCategory.value.name); // Update locally
      } catch (error) {
        errorMessage.value = error.response.data || "An error occurred.";
      }
    };

    const updateCategory = async () => {
      successMessage2.value= null;
      errorMessage2.value= null;
      try {
        const cat_edit= await axiosClient.get("/category/name/"+ updateCategoryRequest.value.oldName);
        await axiosClient.put("/category/"+cat_edit.data.id+"?newName="+updateCategoryRequest.value.name);
        successMessage2.value = "Category updated successfully!";
      } catch (error) {
        errorMessage2.value = error.response.data || "An error occurred.";
      }
    };

    const deleteCategory = async () => {
      successMessage3.value= null;
      errorMessage3.value= null;
      try {
        const cat_edit= await axiosClient.get("/category/name/"+ deleteCategoryName.value);
        await axiosClient.delete("/category/"+cat_edit.data.id);
        successMessage3.value = "Category deleted successfully!";
      } catch (error) {
        errorMessage3.value = error.response.data || "An error occurred.";
      }
    };

    const createRequest = async () => {
      changeRequestErrorMessage.value= null;
      changeRequestSuccessMessage.value= null;
      try {
        await axiosClient.post("/change-requests", {requestCreatorEmail: email.value});
        changeRequestSuccessMessage.value = "change-request created successfully!";
      } catch (error) {
        changeRequestErrorMessage.value = error.response.data || "Invalid email.";
      }
    };
    const newEmployee = ref({
      userID: "",
      name: "",
      email: "",
      password: ""
    });

    // Employee management messages
    const employeeErrorMessage = ref(null);
    const employeeSuccessMessage = ref(null);
    const passwordErrorMessage = ref(null);
    const passwordSuccessMessage = ref(null);
    const deleteEmployeeErrorMessage = ref(null);
    const deleteEmployeeSuccessMessage = ref(null);

    // Employee list
    const employees = ref([]);

    // Edit employee password request
    const editEmployeeRequest = ref({
      email: "",
      oldPassword: "",
      newPassword: ""
    });

    // Delete employee email
    const deleteEmployeeEmail = ref("");

    // Add Employee Method
    const addEmployee = async () => {
      employeeErrorMessage.value = null;
      employeeSuccessMessage.value = null;
      try {
        await axiosClient.post("/employee", {
          userID: newEmployee.value.userID,
          name: newEmployee.value.name,
          email: newEmployee.value.email,
          password: newEmployee.value.password
        });
        employeeSuccessMessage.value = "Employee added successfully!";
        // Reset form
        newEmployee.value = { userID: "", name: "", email: "", password: "" };
      } catch (error) {
        employeeErrorMessage.value = error.response.data || "An error occurred while adding employee.";
      }
    };

    // Update Employee Password Method
    const updateEmployeePassword = async () => {
      passwordErrorMessage.value = null;
      passwordSuccessMessage.value = null;
      try {
        await axiosClient.put(`/employee/${editEmployeeRequest.value.email}/`, null, {
          params: {
            oldPassword: editEmployeeRequest.value.oldPassword,
            newPassword: editEmployeeRequest.value.newPassword
          }
        });
        passwordSuccessMessage.value = "Employee password updated successfully!";
        // Reset form
        editEmployeeRequest.value = { email: "", oldPassword: "", newPassword: "" };
      } catch (error) {
        passwordErrorMessage.value = error.response.data || "An error occurred while updating password.";
      }
    };

    // Delete Employee Method
    const deleteEmployee = async () => {
      deleteEmployeeErrorMessage.value = null;
      deleteEmployeeSuccessMessage.value = null;
      try {
        await axiosClient.delete(`/employee/${deleteEmployeeEmail.value}`);
        deleteEmployeeSuccessMessage.value = "Employee deleted successfully!";
        // Reset form
        deleteEmployeeEmail.value = "";
      } catch (error) {
        deleteEmployeeErrorMessage.value = error.response.data || "An error occurred while deleting employee.";
      }
    };

    // Fetch Employees Method
    const fetchEmployees = async () => {
      try {
        const response = await axiosClient.get("/employee");
        employees.value = response.data.employees;
      } catch (error) {
        console.error("Error fetching employees:", error);
        employeeErrorMessage.value = "Could not fetch employees.";
      }
    };
    // Change Requests Management
        const changeRequests = ref([]);

        // Fetch Change Requests
        const fetchChangeRequests = async () => {
          changeRequestErrorMessage.value = null;
          changeRequestSuccessMessage.value = null;
          try {
            const response = await axiosClient.get("/change-requests");
            changeRequests.value = response.data.changeRequests;
          } catch (error) {
            changeRequestErrorMessage.value = error.response?.data || "Could not fetch change requests.";
          }
        };

        // Approve Change Request
        const approveRequest = async (requestId) => {
          changeRequestErrorMessage.value = null;
          changeRequestSuccessMessage.value = null;
          try {
            const userEmail = localStorage.getItem('userEmail'); // Assuming email is stored in localStorage
            if (!userEmail) {
              throw new Error("Manager email not found. Please log in.");
            }
            await axiosClient.put(`/change-requests/${requestId}/status`, null, {
              params: {
                managerEmail: userEmail,
                status: 'APPROVED'
              }
            });
            changeRequestSuccessMessage.value = "Change request approved successfully!";
            fetchChangeRequests(); // Refresh the list
          } catch (error) {
            changeRequestErrorMessage.value = error.response?.data || "Could not approve change request.";
          }
        };

        // Decline Change Request
        const declineRequest = async (requestId) => {
          changeRequestErrorMessage.value = null;
          changeRequestSuccessMessage.value = null;
          try {
            const userEmail = localStorage.getItem('userEmail'); // Assuming email is stored in localStorage
            if (!userEmail) {
              throw new Error("Manager email not found. Please log in.");
            }
            await axiosClient.put(`/change-requests/${requestId}/status`, null, {
              params: {
                managerEmail: userEmail,
                status: 'DECLINED'
              }
            });
            changeRequestSuccessMessage.value = "Change request declined successfully!";
            fetchChangeRequests(); // Refresh the list
          } catch (error) {
            changeRequestErrorMessage.value = error.response?.data || "Could not decline change request.";
          }
        };

        // Delete Change Request
        const deleteRequest = async (requestId) => {
          changeRequestErrorMessage.value = null;
          changeRequestSuccessMessage.value = null;
          try {
            const userEmail = localStorage.getItem('userEmail'); // Assuming email is stored in localStorage
            if (!userEmail) {
              throw new Error("Manager email not found. Please log in.");
            }
            await axiosClient.delete(`/change-requests/${requestId}`, {
              params: {
                managerEmail: userEmail
              }
            });
            changeRequestSuccessMessage.value = "Change request deleted successfully!";
            fetchChangeRequests(); // Refresh the list
          } catch (error) {
            changeRequestErrorMessage.value = error.response?.data || "Could not delete change request.";
          }
        };

        // Helper method to format date
        const formatDate = (dateString) => {
          return new Date(dateString).toLocaleString();
        };
    const axiosClient = axios.create({
      baseURL: "http://localhost:8080",
    });

    return {
      newGame,
      categories,
      newCategory,
      editGameRequest,
      deleteGameName,
      updateCategoryRequest,
      deleteCategoryName,
      redirectToOrderView,
      onImageUpload,
      addGame,
      editGameName,
      editGameDescription,
      deleteGame,
      addCategory,
      errorMessage,
      successMessage,
      updateCategory,
      deleteCategory,
      successMessage2,
      errorMessage2,
      successMessage3,
      errorMessage3,
      errorMessageP1,
      successMessageP1,
      successMessageP2,
      errorMessageP2,
      successMessageP3,
      errorMessageP3,
      successMessageP4,
      errorMessageP4,
      orderNumber,
      orderErrorMessage,
      createRequest,
      changeRequestErrorMessage,
      changeRequestSuccessMessage,
      email,
      newEmployee,
      addEmployee,
      updateEmployeePassword,
      deleteEmployee,
      fetchEmployees,
      employees,
      employeeErrorMessage,
      employeeSuccessMessage,
      editEmployeeRequest,
      passwordErrorMessage,
      passwordSuccessMessage,
      deleteEmployeeEmail,
      deleteEmployeeErrorMessage,
      deleteEmployeeSuccessMessage,
      changeRequests,
      fetchChangeRequests,
      approveRequest,
      declineRequest,
      deleteRequest,
      changeRequestErrorMessage,
      changeRequestSuccessMessage,
      formatDate
    };
  },
};
</script>

<style>
body {
  margin: 0;
  font-family: Arial, sans-serif;
  background-color: black;
  color: white;
}
.staff-view {
  padding: 20px;
  min-height: 100vh;
}
h1,
h2 {
  color: #00c4ff;
  text-align: center;
}
h3 {
  color: #00c4ff;
  margin-top: 20px;
  text-align: center;
}
.form-section {
  margin-bottom: 40px;
  padding: 20px;
  background-color: #222;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
}
.form-grid {
  display: grid;
  gap: 20px;
}
label {
  font-weight: bold;
}
input,
textarea,
select,
button {
  width: 100%;
  padding: 10px;
  margin-top: 5px;
  border: 1px solid #555;
  border-radius: 4px;
  background-color: #222;
  color: white;
}
button {
  cursor: pointer;
}
.btn-primary {
  background-color: #00c4ff;
  color: black;
  border: none;
  padding: 10px;
  font-weight: bold;
  transition: background 0.3s;
}
.btn-primary:hover {
  background-color: #0078a8;
}
.error-message {
  color: red;
  margin-top: 10px;
}
.success-message {
  color: green;
  margin-top: 10px;
}
.employee-list table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.employee-list th,
.employee-list td {
  border: 1px solid #555;
  padding: 10px;
  text-align: left;
}

.employee-list th {
  background-color: #333;
  color: #00c4ff;
}
.change-requests-list table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.change-requests-list th,
.change-requests-list td {
  border: 1px solid #555;
  padding: 10px;
  text-align: left;
}

.change-requests-list th {
  background-color: #333;
  color: #00c4ff;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.btn-approve, .btn-decline, .btn-delete {
  padding: 5px 10px;
  font-size: 0.8em;
}

.btn-approve {
  background-color: green;
  color: white;
}

.btn-decline {
  background-color: red;
  color: white;
}

.btn-delete {
  background-color: #555;
  color: white;
}

.btn-approve:disabled,
.btn-decline:disabled {
  background-color: #444;
  color: #888;
  cursor: not-allowed;
}
</style>
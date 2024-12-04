<template>
  <div class="staff-view">
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
            <h1>Owner DashBoard</h1>
    <!-- Employee Management Section -->
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

          <!-- View Employees -->
          <h3>Employee List</h3>
          <div v-if="employees.length > 0" class="employee-list">
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
          <p v-else>No employees found.</p>

          <!-- Delete Employee -->
          <h3>Delete Employee</h3>
          <form @submit.prevent="deleteEmployeeByEmail" class="form-grid">
            <div>
              <label>Employee Email:</label>
              <input v-model="deleteEmployeeEmail" type="email" required />
            </div>
            <button class="btn-primary" type="submit">Delete Employee</button>
            <p v-if="deleteEmployeeErrorMessage" class="error-message">{{ deleteEmployeeErrorMessage }}</p>
            <p v-if="deleteEmployeeSuccessMessage" class="success-message">{{ deleteEmployeeSuccessMessage }}</p>
          </form>
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
    <div>
      <input v-model="managerEmail" type="hidden" value="owner@email.com" />
    </div>
     <!-- Manage Change Requests -->
     <section class="form-section">
       <h2>Manage Change Requests</h2>

       <!-- View Change Requests -->
       <div v-if="changeRequests.length > 0" class="change-requests-list">
         <table>
           <thead>
             <tr>
               <th>ID</th>
               <th>Creator Email</th>
               <th>Status</th>
               <th>Actions</th>
             </tr>
           </thead>
           <tbody>
             <tr v-for="request in changeRequests" :key="request.id">
               <td>{{ request.id }}</td>
               <td>{{ request.requestCreatorEmail }}</td>
               <td>{{ request.status }}</td>
               <td>
                 <button @click="approveRequest(request.id)" class="btn-primary">Approve</button>
                 <button @click="declineRequest(request.id)" class="btn-delete">Decline</button>
               </td>
             </tr>
           </tbody>
         </table>
       </div>
       <p v-else>No change requests found.</p>
     </section>

  </div>
</template>

<script>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { onMounted } from "vue";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
});

export default {
  name: "StaffView",
  setup() {
    const router = useRouter();
    // Change Requests Management
    const changeRequests = ref([]);
    const managerEmail = ref("owner@email.com");

    // Fetch change requests
    const fetchChangeRequests = async () => {
      try {
        const response = await axiosClient.get("/change-requests");
        changeRequests.value = response.data.changeRequests;
      } catch (error) {
        console.error("Error fetching change requests:", error);
      }
    };

    // Approve request
    const approveRequest = async (id) => {
      try {
        await axiosClient.put(`/change-requests/${id}/status`, null, {
          params: {
            managerEmail: managerEmail.value,
            status: "APPROVED"
          }
        });
        fetchChangeRequests(); // Refresh list
      } catch (error) {
        console.error("Error approving request:", error);
      }
    };

    // Decline request
    const declineRequest = async (id) => {
      try {
        await axiosClient.put(`/change-requests/${id}/status`, null, {
          params: {
            managerEmail: managerEmail.value,
            status: "DECLINED"
          }
        });
        fetchChangeRequests(); // Refresh list
      } catch (error) {
        console.error("Error declining request:", error);
      }
    };

    // Fetch change requests when component mounts
    onMounted(fetchChangeRequests);

    // Employee Management
        const newEmployee = ref({
          userID: "",
          name: "",
          email: "",
          password: ""
        });
        const employees = ref([]);
        const employeeErrorMessage = ref(null);
        const employeeSuccessMessage = ref(null);
        const deleteEmployeeEmail = ref("");
        const deleteEmployeeErrorMessage = ref(null);
        const deleteEmployeeSuccessMessage = ref(null);

        // Fetch employees
        const fetchEmployees = async () => {
          try {
            const response = await axiosClient.get("/employee");
            employees.value = response.data.employees;
          } catch (error) {
            console.error("Error fetching employees:", error);
          }
        };

        // Add new employee
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
            fetchEmployees(); // Refresh employee list
            // Reset form
            newEmployee.value = {
              userID: "",
              name: "",
              email: "",
              password: ""
            };
          } catch (error) {
            employeeErrorMessage.value = error.response.data || "An error occurred.";
          }
        };

        // Delete employee by email
        const deleteEmployeeByEmail = async () => {
          deleteEmployeeErrorMessage.value = null;
          deleteEmployeeSuccessMessage.value = null;
          try {
            await axiosClient.delete(`/employee/${deleteEmployeeEmail.value}`);
            deleteEmployeeSuccessMessage.value = "Employee deleted successfully!";
            fetchEmployees(); // Refresh employee list
            deleteEmployeeEmail.value = ""; // Reset email input
          } catch (error) {
            deleteEmployeeErrorMessage.value = error.response.data || "An error occurred.";
          }
        };

        onMounted(fetchEmployees);

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

    return {
      newEmployee,
     employees,
     addEmployee,
     fetchEmployees,
     employeeErrorMessage,
     employeeSuccessMessage,
     deleteEmployeeEmail,
     deleteEmployeeByEmail,
     deleteEmployeeErrorMessage,
     deleteEmployeeSuccessMessage,
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
      changeRequests,
      managerEmail,
      fetchChangeRequests,
      approveRequest,
      declineRequest,
    };
  },
};
</script>

<style>

.staff-view {
  background-color: #000;
  color: #fff;
  min-height: 100vh;
  padding: 16px 32px;
  width: 900px; /* Increase the width of the container */
  margin-left: 130px;; /* Center the container */
  border-radius: 8px;
  overflow-y: auto;
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

.staff-view h1 {
  font-size: 32px;
  margin-bottom: 24px;
  text-align: center;
}

.staff-view h2 {
  font-size: 24px;
  margin-bottom: 16px;
  color: #1a73e8;
  text-align: left;
}

.staff-view h3 {
  font-size: 20px;
  margin-top: 24px;
  margin-bottom: 16px;
}

.form-section {
  background-color: #121212;
  padding: 24px;
  border-radius: 8px;
  margin-bottom: 32px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
  align-items: start;
}

.form-grid div {
  display: flex;
  flex-direction: column;
}

.form-grid label {
  font-size: 14px;
  margin-bottom: 8px;
  color: #bbb;
}

.form-grid input,
.form-grid select,
.form-grid textarea {
  padding: 8px 12px;
  border: 1px solid #333;
  border-radius: 4px;
  background-color: #1a1a1a;
  color: #fff;
}

.form-grid input::placeholder,
.form-grid textarea::placeholder {
  color: #777;
}

.form-grid input:focus,
.form-grid select:focus,
.form-grid textarea:focus {
  outline: none;
  border-color: #1a73e8;
}

.btn-primary {
  padding: 10px 20px;
  background-color: #1a73e8;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-weight: bold;
  cursor: pointer;
  text-align: center;
}

.btn-primary:hover {
  background-color: #155cb0;
}

.error-message {
  color: #ff6b6b;
  margin-top: 8px;
}

.success-message {
  color: #4caf50;
  margin-top: 8px;
}

.employee-list table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}

.employee-list th,
.employee-list td {
  padding: 12px 16px;
  border: 1px solid #333;
  text-align: left;
  color: #fff;
}

.employee-list th {
  background-color: #1a73e8;
  color: #fff;
}

.employee-list tbody tr:nth-child(even) {
  background-color: #121212;
}

.employee-list tbody tr:hover {
  background-color: #1a1a1a;
}

.change-requests-list table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 16px;
}

.change-requests-list th,
.change-requests-list td {
  padding: 12px 16px;
  border: 1px solid #333;
  text-align: left;
  color: #fff;
}

.change-requests-list th {
  background-color: #1a73e8;
  color: #fff;
}

.change-requests-list tbody tr:nth-child(even) {
  background-color: #121212;
}

.change-requests-list tbody tr:hover {
  background-color: #1a1a1a;
}

.btn-delete {
  background-color: #ff6b6b;
  color: white;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
}

.btn-delete:hover {
  background-color: #d9534f;
}

</style>
<template>
  <div class="staff-view">
    <h1>Employee Portal</h1>

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

     <!-- Create Requests -->
     <section class="form-section">
      <h2>Send a Request</h2>
      <form @submit.prevent="createRequest" class="form-grid">
        <div>
        <label>Request email address:</label>
        <input v-model="email" type="text" placeholder="Enter email address" required />
      </div>
      <button class="btn-primary" type="submit">Submit</button>
      <p v-if="changeRequestErrorMessage" class="error-message">{{ changeRequestErrorMessage }}</p>
      <p v-if="changeRequestSuccessMessage" class="success-message">{{ changeRequestSuccessMessage }}</p>
      </form>
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
</style>

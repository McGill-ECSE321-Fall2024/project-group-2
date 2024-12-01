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
            <label>Game ID:</label>
            <input v-model="editGameRequest.id" type="text" placeholder="Enter Game ID" required />
          </div>
          <div>
            <label>New Game Name:</label>
            <input v-model="editGameRequest.name" type="text" required />
          </div>
          <button class="btn-primary" type="submit">Submit</button>
        </form>
      </div>

      <!-- Edit Game Description -->
      <div class="form-grid">
        <h3>Edit Game Description</h3>
        <form @submit.prevent="editGameDescription" class="form-grid">
          <div>
            <label>Game ID:</label>
            <input v-model="editGameRequest.id" type="text" placeholder="Enter Game ID" required />
          </div>
          <div>
            <label>New Game Description:</label>
            <textarea v-model="editGameRequest.description" required></textarea>
          </div>
          <button class="btn-primary" type="submit">Submit</button>
        </form>
      </div>
    </section>

    <!-- Delete Game -->
    <section class="form-section">
      <h2>Delete Game</h2>
      <form @submit.prevent="deleteGame" class="form-grid">
        <div>
          <label>Game ID:</label>
          <input v-model="deleteGameId" type="text" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
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
          <label>Category ID:</label>
          <input v-model="updateCategoryRequest.id" type="text" required />
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
          <label>Category ID:</label>
          <input v-model="deleteCategoryId" type="text" required />
        </div>
        <button class="btn-primary" type="submit">Submit</button>
      </form>
      <p v-if="errorMessage3" class="error-message">{{ errorMessage3 }}</p>
      <p v-if="successMessage3" class="success-message">{{ successMessage3 }}</p>
    </section>

    <section class="form-section">
      <h2>View Orders</h2>
      <button class="btn-primary" @click="redirectToOrderView">Go to Order Management</button>
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

    const categories = ref([]); // Example categories
    const newCategory = ref({ name: "" });
    const editGameRequest = ref({ id: "", name: "", description: "" });
    const deleteGameId = ref("");
    const updateCategoryRequest = ref({ id: "", name: "" });
    const deleteCategoryId = ref("");

    // Category Status Messages
    const errorMessage = ref(null);
    const successMessage = ref(null);
    const errorMessage2 = ref(null);
    const successMessage2 = ref(null);
    const errorMessage3 = ref(null);
    const successMessage3 = ref(null);

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

    const redirectToOrderView = () => {
      router.push({ name: "orders" });
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
      try {
        const new_line_item= {price: newGame.value.price, quantity: newGame.value.quantity};
        const new_line_item2=  await axiosClient.post("/lineitems", new_line_item);
        const choosen_cat= await axiosClient.get("/category/name/"+ newGame.value.category);
        const new_game= {name: newGame.value.name, description: newGame.value.description, imageURL: newGame.value.image,
          lineItem_id: new_line_item2.data.lineItemId, category_id: choosen_cat.data.id};
        console.log(new_game)
        await axiosClient.post("/product", new_game);
      } catch (error) {
        errorMessage.value = error.response.data || "An error occurred.";
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
        await axiosClient.put("/category/"+updateCategoryRequest.value.id+"?newName="+updateCategoryRequest.value.name);
        successMessage2.value = "Category updated successfully!"; 
      } catch (error) {
        errorMessage2.value = error.response.data || "An error occurred.";
      }
    };

    const deleteCategory = async () => {
      successMessage3.value= null;
      errorMessage3.value= null;
      try {
        await axiosClient.delete("/category/"+deleteCategoryId.value);
        successMessage3.value = "Category deleted successfully!";
      } catch (error) {
        errorMessage3.value = error.response.data || "An error occurred.";
      }
    };

    return {
      newGame,
      categories,
      newCategory,
      editGameRequest,
      deleteGameId,
      updateCategoryRequest,
      deleteCategoryId,
      redirectToOrderView,
      onImageUpload,
      addGame,
      addCategory,
      errorMessage,
      successMessage,
      updateCategory,
      deleteCategory,
      successMessage2,
      errorMessage2,
      successMessage3,
      errorMessage3,
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

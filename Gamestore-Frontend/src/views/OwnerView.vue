<template>
  <div class="management">
    <h1>Management Portal</h1>

    <!-- Employee Management Section -->
    <div class="employee-management">
      <h2>Employee Management</h2>
      <div>
        <h3>Create Employee</h3>
        <form @submit.prevent="createEmployee">
          <label for="userID">User ID:</label><br />
          <input type="text" v-model="newEmployee.userID" required /><br />

          <label for="name">Name:</label><br />
          <input type="text" v-model="newEmployee.name" required /><br />

          <label for="email">Email:</label><br />
          <input type="email" v-model="newEmployee.email" required /><br />

          <label for="password">Password:</label><br />
          <input type="password" v-model="newEmployee.password" required /><br />

          <button type="submit">Create Employee</button>
        </form>
      </div>
    </div>

    <!-- Product Management Section -->
    <div class="product-management">
      <h2>Product Management</h2>

      <div>
        <h3>Create Product</h3>
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="name">Product Name:</label><br />
            <input v-model="product.name" id="name" required /><br />
          </div>

          <div>
            <label for="description">Description:</label><br />
            <textarea v-model="product.description" id="description" required></textarea><br />
          </div>

          <!-- Line Item Fields -->
          <div>
            <label for="quantity">Quantity:</label><br />
            <input v-model="lineItem.quantity" type="number" id="quantity" required /><br />
          </div>

          <div>
            <label for="price">Price:</label><br />
            <input v-model="lineItem.price" type="number" id="price" required /><br />
          </div>

          <!-- Category Field -->
          <div>
            <label for="category">Category:</label><br />
            <input v-model="category.name" id="category" required /><br />
          </div>

          <button type="submit">Create Product</button>
        </form>
      </div>

      <div>
        <h3>Product List</h3>
        <ul>
          <li v-for="product in products" :key="product.id">
            <strong>Name:</strong> {{ product.name }} |
            <strong>Description:</strong> {{ product.description }} |
            <strong>Line Item:</strong> Quantity: {{ product.lineItem.quantity }} | Price: {{ product.lineItem.price }} |
            <strong>Category:</strong> {{ product.category.name }}
            <button @click="deleteProduct(product.id)">Delete</button>
          </li>
        </ul>
      </div>
    </div>

    <!-- Employee List Section -->
    <div v-if="employees.length">
      <h2>Employee List</h2>
      <ul>
        <li v-for="employee in employees" :key="employee.email">
          {{ employee.name }} ({{ employee.email }})
        </li>
      </ul>
    </div>

    <!-- Response Message -->
    <div v-if="responseMessage">
      <p>{{ responseMessage }}</p>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Employee Data
      employees: [],
      newEmployee: {
        userID: '',
        name: '',
        email: '',
        password: '',
      },

      // Product Data
      products: [],
      product: {
        name: '',
        description: '',
        lineItem: {}, // LineItem is empty initially
        category: {}, // Category is empty initially
      },

      lineItem: {
        quantity: 0,
        price: 0,
      },

      category: {
        name: '',
      },

      responseMessage: '',
    };
  },
  methods: {
    // Employee Management Methods
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
    createEmployee() {
      fetch("http://localhost:8080/employee", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.newEmployee),
      })
        .then((response) => {
          if (response.ok) {
            alert("Employee created successfully!");
            this.fetchEmployees();
            this.newEmployee = { userID: '', name: '', email: '', password: '' };
          } else {
            alert("Failed to create employee.");
          }
        })
        .catch((error) => {
          console.error("Error creating employee:", error);
        });
    },

    // Product Management Methods
    fetchProducts() {
      fetch("http://localhost:8080/product")
        .then((response) => response.json())
        .then((data) => {
          this.products = data.products;
        })
        .catch((error) => {
          console.error("Error fetching products:", error);
        });
    },
    handleSubmit() {
      // Create LineItem and Category first
      const newLineItem = { ...this.lineItem };
      const newCategory = { ...this.category };

      // Create the product DTO with lineItem and category
      const productData = {
        ...this.product,
        lineItem: newLineItem,
        category: newCategory,
      };

      console.log("Request Payload:", productData); // Log the request payload

      fetch("http://localhost:8080/product", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(productData),
      })
        .then((response) => {
          if (response.ok) {
            this.responseMessage = `Product created successfully: ${this.product.name}`;
            this.fetchProducts();
            // Reset the form after submission
            this.product = { name: '', description: '', lineItem: {}, category: {} };
            this.lineItem = { quantity: 0, price: 0 };
            this.category = { name: '' };
          } else {
            response.text().then((errorMessage) => {
              console.error("Error message from backend:", errorMessage);
              this.responseMessage = `Failed to create product: ${errorMessage}`;
            });
          }
        })
        .catch((error) => {
          console.error("Error creating product:", error);
        });
    },
    deleteProduct(productId) {
      fetch(`http://localhost:8080/product/${productId}`, {
        method: "DELETE",
      })
        .then((response) => {
          if (response.ok) {
            alert("Product deleted successfully!");
            this.fetchProducts();
          } else {
            alert("Failed to delete product.");
          }
        })
        .catch((error) => {
          console.error("Error deleting product:", error);
        });
    }
  },
  created() {
    this.fetchEmployees();
    this.fetchProducts();
  }
};
</script>

<style scoped>
.management {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1, h2, h3 {
  color: #333;
}

button {
  background-color: #4CAF50;
  color: white;
  padding: 10px 15px;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

ul {
  list-style-type: none;
  padding: 0;
}

form div {
  margin-bottom: 15px;
}
</style>

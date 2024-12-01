<template>
    <div class="order-view">
      <h1>Order Management</h1>
      <div class="table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Number</th>
              <th>Ordered Date</th>
              <th>Shipped Date</th>
              <th>Shipping Address</th>
              <th>Total Price</th>
              <th>Status</th>
              <th>Payment</th>
              <th style="min-width: 200px;">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="order in orders" :key="order.number">
              <td>{{ order.number }}</td>
              <td>{{ order.orderedDate }}</td>
              <td>{{ order.shippedDate || "N/A" }}</td>
              <td>{{ order.shippingAddress }}</td>
              <td>${{ order.totalPrice.toFixed(2) }}</td>
              <td>{{ order.status }}</td>
              <td>{{ order.payment }}</td>
              <td>
                <div class="actions">
                  <select v-model="order.tempStatus">
                    <option>Pending</option>
                    <option>Shipped</option>
                    <option>Delivered</option>
                    <option>Cancelled</option>
                  </select>
                  <button @click="confirmAction(order)" class="btn-primary">Submit</button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <button class="btn-back" @click="goBack">Back to Portal</button>
    </div>
  </template>
  
  <script>
  import { ref } from "vue";
  import { useRouter } from "vue-router";
  
  export default {
    name: "OrderView",
    setup() {
      const orders = ref([
        {
          number: 1,
          orderedDate: "2024-11-01",
          shippedDate: null,
          shippingAddress: "123 Example St, Cityville",
          totalPrice: 59.99,
          status: "Pending",
          tempStatus: "Pending",
          payment: "Paid",
        },
        {
          number: 2,
          orderedDate: "2024-11-05",
          shippedDate: "2024-11-06",
          shippingAddress: "456 Market St, Townsville",
          totalPrice: 129.99,
          status: "Delivered",
          tempStatus: "Delivered",
          payment: "Paid",
        },
      ]);
  
      const router = useRouter();
  
      const confirmAction = (order) => {
        order.status = order.tempStatus; // Update the actual status
        console.log(`Order #${order.number} updated to status: ${order.status}`);
      };
  
      const goBack = () => {
        router.push({ name: "staff" });
      };
  
      return {
        orders,
        confirmAction,
        goBack,
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
  .order-view {
    padding: 20px;
    min-height: 100vh;
  }
  h1 {
    color: #00c4ff;
    text-align: center;
    margin-bottom: 20px;
  }
  .table-wrapper {
    overflow-x: auto;
    background-color: #222;
    padding: 10px;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  }
  table {
    width: 100%;
    table-layout: auto;
    border-collapse: collapse;
    margin-bottom: 20px;
  }
  table th, table td {
    padding: 15px;
    text-align: left;
    border: 1px solid #555;
  }
  table th {
    background-color: #00c4ff;
    color: black;
    font-weight: bold;
  }
  table td {
    background-color: #222;
    color: white;
  }
  .actions {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }
  select {
    padding: 8px;
    border: 1px solid #555;
    border-radius: 4px;
    background-color: #222;
    color: white;
  }
  .btn-primary {
    background-color: #00c4ff;
    color: black;
    border: none;
    padding: 10px;
    font-weight: bold;
    transition: background 0.3s;
    cursor: pointer;
  }
  .btn-primary:hover {
    background-color: #0078a3;
  }
  .btn-back {
    display: block;
    margin: 20px auto;
    background-color: #00c4ff;
    color: black;
    border: none;
    padding: 10px;
    font-weight: bold;
    transition: background 0.3s;
    cursor: pointer;
    border-radius: 4px;
  }
  .btn-back:hover {
    background-color: #0078a3;
  }
  </style>
  
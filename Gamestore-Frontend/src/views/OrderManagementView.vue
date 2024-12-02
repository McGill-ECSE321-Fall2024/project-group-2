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
          <tbody v-if="order">
            <tr>
              <td>{{ order.number }}</td>
              <td>{{ order.orderedDate }}</td>
              <td>{{ order.shippedDate || "N/A" }}</td>
              <td>{{ order.shipTo }}</td>
              <td>{{ order.total }}</td>
              <td>{{ order.status }}</td>
              <td>{{ order.paymentId }}</td>
              <td>
                <div class="actions">
                  <select v-model="tempStatus">
                    <option>Pending</option>
                    <option>Shipped</option>
                    <option>Delivered</option>
                    <option>Cancelled</option>
                  </select>
                  <button @click="confirmAction()" class="btn-primary">Submit</button>
                </div>
              </td>
            </tr>
          </tbody>
          <tbody v-else>
          <tr>
            <td colspan="8">Loading order details...</td>
          </tr>
        </tbody>
        </table>
        <p v-if="message" class="message">{{ message }}</p>
      </div>
      <button class="btn-back" @click="goBack">Back to Portal</button>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  import { useRouter } from "vue-router";
  import axios from "axios";

  const axiosClient = axios.create({
  baseURL: "http://localhost:8080",
  });
  
  export default {
    name: "order_management",
    props: ["id"],
    setup(props) {
      const order = ref(null);
      const tempStatus= ref("");
      const router = useRouter();
      const message = ref("");

      const fetchOrder = async () => {
      try {
        const response = await axiosClient.get("/orders/"+props.id);
        order.value = response.data;
      } catch (error) {
        console.error("Error fetching order:", error);
      }
    };
      
    onMounted(fetchOrder);

    const confirmAction = async () => {
      try{
        const response = await axiosClient.put("/orders/"+props.id+"/status"+"?newStatus="+tempStatus.value);
        fetchOrder();
        message.value= "Order status updated!"
      }
      catch (error) {
        console.error("Error fetching order:", error);
      }
    };

    const goBack = () => {
      router.push({ name: "staff" });
    };
    
    return {
      order,
      confirmAction,
      goBack,
      fetchOrder,
      tempStatus,
      message,
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
  .message {
  color: green;
  margin-top: 10px;
  text-align: center;
}
  </style>
  
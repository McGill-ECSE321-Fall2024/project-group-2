<template>
    <div class="review-view">
        <!-- Error Message Display -->
        <div v-if="showError" class="error-message">
            <p>{{ errorMessage }}</p>
            <div class="error-actions">
                <router-link v-if="!userEmail" to="/signin" class="sign-in-btn">Sign In</router-link>
                <button v-if="!userEmail" @click="showError = false" class="dismiss-btn">Cancel</button>
            </div>
        </div>
        <h2>Add a Review for {{ product.name }}</h2>
        <form @submit.prevent="submitReview">
            <div class="form-group">
                <label for="rating">Rating:</label>
                <select v-model="review.rating" id="rating" required>
                    <option disabled value="">Select Rating</option>
                    <option v-for="n in 5" :key="n" :value="n">{{ n }}</option>
                </select>
            </div>

            <div class="form-group">
                <label for="comment">Comments:</label>
                <textarea v-model="review.comments" id="comment" required></textarea>
            </div>

            <button type="submit" class="submit-review-btn">Submit Review</button>
        </form>
    </div>
</template>


<script>
import axios from "axios";

// for api calls
const axiosClient = axios.create({
    baseURL: "http://localhost:8080",
});

export default {
    name: 'ReviewView',
    // all data needed for reviews
    data() {
        return {
            product: {},
            review: {
                rating: '',
                comments: '',
            },
            userEmail: localStorage.getItem('userEmail') || '',
            userName: localStorage.getItem('userName') || '',
            errorMessage: '',
            showError: false
        };
    },
    // runs when component is created
    async created() {
        try {
            // get product data from id in route
            const productResponse = await axiosClient.get(`/product/${this.$route.params.id}`);
            this.product = productResponse.data;
        } catch (error) {
            console.error('Error fetching product data:', error);
        }
    },
    methods: {
        // for review submission
        async submitReview() {
            console.log('submitReview method called');
            try {
                // ensure the user is logged in
                if (!this.userEmail) {
                    this.errorMessage = 'You must be logged in to submit a review.';
                    this.showError = true;
                    return;  // don't redirect automatically - let user click the sign in button
                }

                
                console.log('Review form data:', this.review);
        
                // prepare the review data
                const reviewData = {
                    rating: parseInt(this.review.rating),
                    comment: this.review.comments,
                    reviewWriterEmail: this.userEmail,
                    reviewWriterName: this.userName,  
                    productId: this.product.id,
                };

                console.log('Review data to submit:', reviewData);

                // send POST request to create the review
                await axiosClient.post('/reviews', reviewData);

                // redirect back to the product page
                this.$router.push({ name: 'product', params: { id: this.product.id } });
            } catch (error) {
                console.error('Error submitting review:', error);
                this.errorMessage = 'An error occurred while submitting your review. Please try again.';
                this.showError = true;
            }
        },
    },
};
</script>

<style scoped>

.review-view {
    width: 50vw;
    min-height: 40vh;
    margin: 40px auto;
    padding: 40px;
    background-color: #121212;
    color: #fff;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);

}

.review-view h2 {
    font-size: 28px;
    margin-bottom: 32px;
    text-align: center;
    color: #fff;
}


.form-group {
    margin-bottom: 24px;
}


.form-group label {
    display: block;
    font-size: 16px;
    font-weight: 600;
    margin-bottom: 12px;
    color: #fff;
}

.form-group select {
    width: 100%;
    padding: 12px 16px;
    background-color: #1e1e1e;
    border: 1px solid #333;
    border-radius: 4px;
    color: #fff;
    font-size: 16px;
    cursor: pointer;
}


.form-group textarea {
    width: 100%;
    min-height: 200px;
    padding: 16px;
    background-color: #1e1e1e;
    border: 1px solid #333;
    border-radius: 4px;
    color: #fff;
    font-size: 16px;
    resize: vertical;
    line-height: 1.5;
}

.submit-review-btn {
    width: 100%;
    padding: 16px;
    background-color: #1a73e8;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    font-weight: 600;
    cursor: pointer;
    transition: background-color 0.2s ease;
}

.submit-review-btn:hover {
    background-color: #155cb0;
}

.error-message {
    background-color: #2c0b0e;
    border: 1px solid #842029;
    color: #ea868f;
    padding: 20px;
    border-radius: 4px;
    margin-bottom: 24px;
    text-align: center;
}

.error-actions {
    display: flex;
    gap: 12px;
    justify-content: center;
    margin-top: 16px;
}

.sign-in-btn {
    background-color: #1a73e8;
    color: white;
    padding: 10px 20px;
    border-radius: 4px;
    text-decoration: none;
    font-weight: 600;
    transition: background-color 0.2s ease;
}

.sign-in-btn:hover {
    background-color: #155cb0;
}

.dismiss-btn {
    background-color: transparent;
    border: 1px solid #ea868f;
    color: #ea868f;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: 600;
    transition: background-color 0.2s ease;
}

.dismiss-btn:hover {
    background-color: rgba(234, 134, 143, 0.1);
}

.form-group select:focus,
.form-group textarea:focus {
    outline: none;
    border-color: #1a73e8;
}
</style>

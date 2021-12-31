var eventBus = new Vue()

Vue.component('product-tabs', {
  props: {
    reviews: {
      type: Array,
      required: false
    }
  },
  template: `
  <div>
    <div>
      <span class="tab" 
            v-for="(tab, index) in tabs" 
            @click="selectedTab = tab"
            :class="{ activeTab: selectedTab === tab }">{{ tab }}</span>
    </div>

    <div v-show="selectedTab === 'Reviews'">
      <p v-if="!reviews.length">There are no reviews yet.</p>
      <ul v-else>
        <li v-for="review in reviews">
          <p>{{ review.name }}</p>
          <p>Rating: {{ review.review }}</p>
          <p>{{ review.rating }}</p>
        </li>
      </ul>
    </div>  
    
    <div v-show="selectedTab === 'Make a Review'">
      <product-review></product-review>
    </div>
  </div>
  `,
  data() {
    return {
      tabs: ['Reviews', 'Make a Review'],
      selectedTab: 'Reviews'
    }
  }
})

Vue.component('product-review', {
  template: `
    <form class="review-form" @submit.prevent="onSubmit">
      <p v-if="errors.length">
        <b>Please correct the following error(s):</b>
        <ul>
          <li v-for="error in errors">{{ error }}</li>
        </ul>
      </p

      <p>
        <label for="name">Name:</label>
        <input id="name" v-model="name"></input>
      </p>
      <p>
        <label for="review">Review:</label>
        <textarea id="review" v-model="review"></textarea>
      </p>
      <p>
        <label>Rating:</label>
        <select id="rating" for="rating" v-model.number="rating">
          <option>5</option>
          <option>4</option>
          <option>3</option>
          <option>2</option>
          <option>1</option>
        </select>
      </p>
      <p>
        <input type="submit" value="Submit">
      </p>
    </form>
  `,
  data() {
    return {
      name: null,
      review: null,
      rating: null,
      errors: []
    }
  },
  methods: {
    onSubmit() {
      if (this.name && this.review && this.rating) {
        let productReview = {
          name: this.name,
          review: this.review,
          rating: this.rating
        }
        eventBus.$emit('review-submitted', productReview)
        this.name = null
        this.review = null
        this.rating = null
      } else {
        if (!this.name) this.errors.push("Name required")
        if (!this.review) this.errors.push("Review required")
        if (!this.rating) this.errors.push("Rating required")
      }
      
    }
  }
})

Vue.component('product-details', {
  props: {
    details: {
      type: Array,
      required: true
    }
  },
  template: `
    <ul>
      <li v-for="detail in details">{{ detail }}</li>
    </ul>
  `,
})

Vue.component('product', {
    props: {
      premium: {
        type: Boolean,
        required: true
      }
    },
    template: `
      <div class="product">
          <div class="product-image">
              <img v-bind:src="image" v-bind:alt="altText"/>
          </div>
          <div class="product-info">
              <h1>{{ title }}</h1>
              <p v-if="inStock">In Stock</p>
              <p v-else :class="{outOfStock: !inStock}">Out of Stock</p>
              <p v-if="onSale">On Sale</p>
              <p>Shipping: {{ shipping }}</p>

              <product-details :details="details"></product-details>

              <div v-for="(variant, index) in variants" 
                  :key="variant.variantId"
                  class="color-box"
                  :style="{ backgroundColor: variant.variantColor }">
                  <p @mouseover="updateProduct(index)">{{ variant.variantColor }}</p>
              </div>

              <button v-on:click="addToCart" 
                      :disabled="!inStock"
                      :class="{ disabledButton: !inStock}">
                  Add to cart
              </button>
          </div>

          <product-tabs :reviews="reviews"></product-tabs>
      </div>
      `,
    data() {
      return {
        brand: "HaHa",
        product: 'This is a ghost',
        altText: 'A pair of socks',
        onSale: true,
        details:['%80 cotten', '%20 ployester', "Gender-neutral"],
        selectedVariant: 0,
        variants: [{
            variantId: 2234,
            variantColor: "green",
            variantImage: './assets/vmSocks-green.jpg',
            variantQuantity: 0
          }, {
            variantId: 2235,
            variantColor: "blue",
            variantImage: './assets/vmSocks-blue.jpg',
            variantQuantity: 10
          }
        ],
        reviews: []
      }
    },
    methods: {
      addToCart() {
        this.$emit("add-to-cart", this.variants[this.selectedVariant].variantId)
      },
      updateProduct(index) {
        this.selectedVariant = index
      },
      addReview(productReview) {
        this.reviews.push(productReview)
      }
    },
    computed: {
      title() {
        return this.brand + ' ' + this.product
      },
      image() {
        return this.variants[this.selectedVariant].variantImage
      },
      inStock() {
        return this.variants[this.selectedVariant].variantQuantity > 0
      },
      shipping() {
        if (this.premium) return "free"
        else return 6.66
      }
    },
    mounted() {
      eventBus.$on('review-submitted', productReview => {
        this.reviews.push(productReview)
      })
    }
})

var app = new Vue({
    el: '#app',
    data: {
      premium: false,
      cart: []
    },
    methods: {
      updateCart(id) {
        this.cart.push(id)
      }
    }
})
  
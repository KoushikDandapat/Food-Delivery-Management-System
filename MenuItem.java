package com.fooddeliverymanagementsystem;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class MenuItem {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long itemId;

	    private String name;
	    private String description;
	    private double price;
	    private String category;

	    @ManyToOne
	    @JoinColumn(name = "restaurant_id")
	    private Restaurant restaurant;

		/**
		 * @return the itemId
		 */
		public Long getItemId() {
			return itemId;
		}

		/**
		 * @param itemId the itemId to set
		 */
		public void setItemId(Long itemId) {
			this.itemId = itemId;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * @param description the description to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}

		/**
		 * @return the price
		 */
		public double getPrice() {
			return price;
		}

		/**
		 * @param price the price to set
		 */
		public void setPrice(double price) {
			this.price = price;
		}

		/**
		 * @return the category
		 */
		public String getCategory() {
			return category;
		}

		/**
		 * @param category the category to set
		 */
		public void setCategory(String category) {
			this.category = category;
		}

		/**
		 * @return the restaurant
		 */
		public Restaurant getRestaurant() {
			return restaurant;
		}

		/**
		 * @param restaurant the restaurant to set
		 */
		public void setRestaurant(Restaurant restaurant) {
			this.restaurant = restaurant;
		}

	   
	}
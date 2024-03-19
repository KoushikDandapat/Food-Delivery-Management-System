package com.fooddeliverymanagementsystem;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promotionId;

    private String name;
    private String description;
    private double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
	/**
	 * @return the promotionId
	 */
	public Long getPromotionId() {
		return promotionId;
	}
	/**
	 * @param promotionId the promotionId to set
	 */
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
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
	 * @return the discountPercentage
	 */
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	/**
	 * @param discountPercentage the discountPercentage to set
	 */
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public void setApplicableItems(String newApplicableItems) {
		// TODO Auto-generated method stub
		
	}
	public String getApplicableItems() {
		// TODO Auto-generated method stub
		return null;
	}
    
    // Additional logic to handle applicable items

  
}
package com.fooddeliverymanagementsystem;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private DeliveryDriver driver;

    private LocalDate deliveryDate;
    private String status;
    private String deliveryAddress;
	/**
	 * @return the deliveryId
	 */
	public Long getDeliveryId() {
		return deliveryId;
	}
	/**
	 * @param deliveryId the deliveryId to set
	 */
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	/**
	 * @return the driver
	 */
	public DeliveryDriver getDriver() {
		return driver;
	}
	/**
	 * @param driver the driver to set
	 */
	public void setDriver(DeliveryDriver driver) {
		this.driver = driver;
	}
	/**
	 * @return the deliveryDate
	 */
	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	/**
	 * @param deliveryDate2 the deliveryDate to set
	 */
	public void setDeliveryDate(LocalDate deliveryDate2) {
		this.deliveryDate = deliveryDate2;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the deliveryAddress
	 */
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	/**
	 * @param deliveryAddress the deliveryAddress to set
	 */
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

   
}
Feature: Order Management

	Scenario: Register order
		Given order has the following attributes:
			| customer id | item id | item quantity |
			| 1           | 1       | 1             |
		 When register a new order
		 Then the order is successfully registered  
		  And should be showed
		  
	Scenario: Retrieve order information
		Given order is already registered with the following attributes
			| customer id | item id | item quantity |
			| 1           | 1       | 1             |		
		 When ask for order information
		 Then the order information is retrieved
		 
	Scenario: Update order information
		Given order is already registered with the following attributes
			| customer id | item id | item quantity |
			| 1           | 1       | 1             |		
		 When the status of the order is changed
		 Then updates the order information with new status
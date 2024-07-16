Feature: Product Checkout Flow

  @checkout
  Scenario: User adds product to the cart and perform checkout operation
    Given User launches the application
    And User Enters UserName,Password
    And User clicks on the "login-button" Button
    Then User should login successfully
    And User store the product details and adds 3 products to the cart
    Then cart badge is updated with the added products count that is "3"
    And User clicks on the "shopping_cart_container" Button
    Then validate the product details on "Cart" page
    And User clicks on the "checkout" Button
    And User Enters the personal Information
    And User clicks on the "continue" Button
    Then validate the product details on "Overview" page
    Then validate item total and final total values
    And User clicks on the "finish" Button
    Then validate success message






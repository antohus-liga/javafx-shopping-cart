# JavaFX Shopping Cart

JavaFX Shopping Cart is a desktop application designed to provide a simple system to manage products, shopping carts, and payments. It is implemented in Kotlin with a focus on scalability, modern UI, and efficient data handling. The application is built using JavaFX for the user interface and SQLite with Exposed ORM for local data persistence. The project follows the MVVM (Model-View-ViewModel) architecture for clear separation of concerns and maintainability.

---

### Features

- **Product Management**: Add, edit, and remove products using an intuitive form.
- **Shopping Cart**: Add products to a shopping cart, adjust quantities, and review totals.
- **Payment System**:
  - Select a payment method.
  - Confirm payment to complete a transaction.
  - View transaction history and payment details.
- **Master-Detail View**:
  - Master: Display a list of payments.
  - Detail: Show the shopping cart details for any payment selected.

---

### Technology Stack

The following technologies and tools have been used to develop this project:

- **[JavaFX](https://openjfx.io/)**:
  - Enables the creation of rich, modern, and responsive user interfaces.
- **[Kotlin](https://kotlinlang.org/)**:
  - A statically typed language offering interoperability with Java.
- **[SQLite](https://sqlite.org/)**:
  - Lightweight relational database to manage and persist data locally.
- **[Exposed ORM](https://github.com/JetBrains/Exposed)**:
  - Simplifies database operations by abstracting SQL queries into Kotlin code.
- **[Gradle](https://gradle.org/)**:
  - Dependency management and build automation.

---

### Architecture

This project implements the **MVVM (Model-View-ViewModel)** architecture to separate concerns and promote modularity:

- **Model**: Responsible for data management using Exposed ORM entities like `Item` and `ShoppingCart`.
- **View**: Contains JavaFX FXML files for rendering UI components like product lists, shopping carts, and payment screens.
- **ViewModel**: Acts as a bridge between the View and the Model. Handles logic, state management, and binds data to the UI.

---

### Usage

1. **Add Products**:
   - Navigate to the "Shopping" tab.
   - Right-click on a product to edit it.
   - Use the "Create Product" form to add new products, specifying their name and unit price.

2. **Manage Shopping Cart**:
   - Add products to the cart and adjust their quantities.
   - Remove items from the cart if necessary.

3. **Complete a Payment**:
   - Proceed to the payment screen.
   - Select a payment method, confirm the payment, and finalize the transaction.

4. **View Payment History**:
   - Navigate to the "Payments" tab.
   - Click on a payment to view the detailed list of products associated with the transaction.

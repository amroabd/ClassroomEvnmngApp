# Classroom Environment Android App

This Android application leverages split screens to provide a user-friendly interface for classroom management through Internet of Things (IoT) technology.

## Getting Started

To get started with this project, you will need the following:

* Android Studio
* An Android device or emulator

1. Clone this repository to your local machine.
2. Open the project in Android Studio.
3. Connect your Android device or emulator to your computer.
4. Run the application.


## Features

**1. Login Page**
  - **User Account Field:** Allows entering username or email address.
  - **Password Field:** Allows entering password for secure login.
  - **Problem Logging In:** Provides an option to report login issues.
  - **New User Account:** Offers an option to create a new account within the app.

**2. Administrator's Main Page**

  **a) Add a Table**
    - **Add Hall Field:** Enables adding new halls to the app.
    - **Add Times Field:** Allows specifying available reservation times for halls.
    - **Add Doctor:** Provides a functionality to add information about doctors or personnel.

  **b) Checking Devices**
    - **Hall Selection:** Allows choosing a specific hall for device inspection.
    - **Start Inspection Button:** Initiates the process of scanning devices in the chosen hall.
    - **Fault Appearance Box:** Displays details of any detected faults during the scan.

  **c) Adjusting the Sensors**
    - **Selecting the Hall:** Allows choosing the hall for sensor adjustment.
    - **Sensor Selection:** Enables selecting a specific sensor within the chosen hall.
    - **Setting the Sensor:** Provides functionality to adjust settings for the chosen sensor.

  **d) Settings**
    - **Change the Secret Code:**
      - **Add New Password Box:** Allows entering a new password.
      - **Confirm Password Box:** Verifies the newly entered password.
    - **Change Language:** Enables switching the application language.

**3. User Home Page**

  **a) Halls Page**
    - **Booking Display:** 
      - Reserved hall: Displays information about the reserved hall with a button to end the reservation.
      - Unreserved hall: Shows the hall as unlit with a button to navigate to the reservation page.
    - **Resource Operation Button:**  Provides functionality to control resources within the reserved hall (details on specific resource types and controls to be defined based on your implementation).

  **b) Reserve a Hall**
    - **Hall Selection:** Allows choosing a hall for booking.
    - **Reservation Notification:** Informs the user of reservation acceptance or rejection.

  **c) Settings**
    - **Change the Secret Code:**
      - **Add New Password Box:** Allows entering a new password.
      - **Confirm Password Box:** Verifies the newly entered password.
    - **Change Language:** Enables switching the application language.






## Technologies Used :

  - **Android**
  - **Java
 
  - **ViewBinding:** 
ViewBinding is a feature introduced by Google in Android Studio 3.6 
that allows you to bind layout views directly to variables in your code. 
It generates a binding class for each XML layout file in your project,
which you can then use to access and manipulate the views. ViewBinding helps eliminate 
the need for findViewById() calls and reduces the risk of null pointer exceptions, resulting in more efficient and safe view handling.
 - **Navigation Component: 
The Navigation Component is a part of the Android Jetpack library that simplifies navigation in Android apps.
It provides a declarative way to define app navigation using a graph-based system. 
With the Navigation Component, you can define destinations, actions, and transitions between screens in a visual and centralized manner. 
It handles tasks like fragment transactions, back-stack management, deep linking, and passing arguments between destinations,
making navigation between different screens and features of your app more straightforward and maintainable.
 - **ViewModel and LiveData:**
ViewModel and LiveData are components of the Android Jetpack library that are often used together to
implement the MVVM (Model-View-ViewModel) architecture pattern. ViewModel is responsible for holding and managing UI-related data,
surviving configuration changes (e.g., screen rotations), and communicating with the underlying data sources.
LiveData is an observable data holder that allows you to create data objects that can be observed by UI components. 
LiveData ensures that UI components are always up-to-date with the latest data and automatically updates them when the data changes. 
ViewModel and LiveData help separate the business logic and data from the UI, promoting better app architecture and testability.
 - **Room Database:**
Room is a powerful and efficient SQLite object-relational mapping (ORM) library provided by the Android Jetpack.
It simplifies database operations in Android apps by providing an abstraction layer over SQLite.
Room allows you to define entities (data models), DAOs (data access objects), and database-related classes using annotations. 
It handles tasks like creating, querying, and caching data, and it supports LiveData and RxJava for reactive programming.
Room provides compile-time verification of SQL queries, reducing the risk of runtime errors.
It also offers convenient features like database migrations, query result mapping, and support for database transactions.

 - **Retrofit :** 
A type-safe HTTP client for Android and Java that simplifies the process of making API requests.
 - **Picasso :**
 A powerful image downloading and caching library for Android that simplifies the process of loading images from URLs.

 


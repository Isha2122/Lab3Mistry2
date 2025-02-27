/** Project:Lab3Mistry3
 * Purpose Details:
 * Course:IST242
 * Author:Ishaben Mistry
 * Date Developed:02/27/25
 * Last Date Changed:
 * Rev:1

 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    private static final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/retailstore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "IST888IST888";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Create
            insertCustomer(connection, 1, "John", "Doe", 21, "john@example.com");
            insertCustomer(connection, 2, "Jim", "Doe", 22, "jim@example.com");
            insertCustomer(connection, 3, "Joe", "Doe", 23, "joe@example.com");

            // Read
            List<Customer> customers = getAllCustomers(connection);
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            // Update
            updateCustomer(connection, 1, "Updated First Name");

            // Read again
            customers = getAllCustomers(connection);
            for (Customer customer : customers) {
                System.out.println(customer);
            }

            // Delete
            deleteCustomer(connection, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertCustomer(Connection connection, int id, String firstName, String lastName, int age, String email) throws SQLException {
        String sql = "INSERT INTO customer (id, firstName, lastName, age, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
        }
    }

    private static List<Customer> getAllCustomers(Connection connection) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT id, firstName, lastName, age, email FROM customer";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                customers.add(new Customer(id, firstName, lastName, age, email));
            }
        }
        return customers;
    }

    private static void updateCustomer(Connection connection, int id, String newFirstName) throws SQLException {
        String sql = "UPDATE customer SET firstName = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newFirstName);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    private static void deleteCustomer(Connection connection, int id) throws SQLException {
        String sql = "DELETE FROM customer WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }
}

class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;

    public Customer(int id, String firstName, String lastName, int age, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

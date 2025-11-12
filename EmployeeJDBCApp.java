import java.sql.*;

public class EmployeeJDBCApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/YOUR_DATABASE"; // Change YOUR_DATABASE
        String user = "YOUR_USERNAME"; // Change YOUR_USERNAME
        String password = "YOUR_PASSWORD"; // Change YOUR_PASSWORD

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            // a) Display all employees using Statement
            System.out.println("All Employee Records:");
            String selectQuery = "SELECT * FROM emp";
            try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(selectQuery)) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String city = rs.getString("city");
                    int age = rs.getInt("age");
                    System.out.println(id + "\t" + name + "\t" + city + "\t" + age);
                }
            }

            // b) Insert a record using PreparedStatement
            String insertQuery = "INSERT INTO emp (id, name, city, age) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setInt(1, 101);                // Example ID
                pstmt.setString(2, "John Doe");      // Example Name
                pstmt.setString(3, "Delhi");         // Example City
                pstmt.setInt(4, 25);                 // Example Age

                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Employee inserted successfully!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

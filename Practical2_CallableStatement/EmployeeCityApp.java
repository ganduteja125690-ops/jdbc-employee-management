import java.sql.*;
import java.util.Scanner;

public class EmployeeCityApp {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/YOUR_DATABASE";
        String user = "YOUR_USERNAME";
        String password = "YOUR_PASSWORD";
        
        Scanner scanner = new Scanner(System.in);
        
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.print("Enter Employee ID: ");
            int empId = scanner.nextInt();
            
            // Call stored procedure to get city
            String sql = "{CALL GetEmployeeCity(?, ?)}";
            CallableStatement cstmt = con.prepareCall(sql);
            
            // Set input parameter
            cstmt.setInt(1, empId);
            
            // Register output parameter
            cstmt.registerOutParameter(2, Types.VARCHAR);
            
            // Execute the stored procedure
            cstmt.execute();
            
            // Get the output parameter value
            String city = cstmt.getString(2);
            
            if (city != null) {
                System.out.println("City of Employee ID " + empId + ": " + city);
            } else {
                System.out.println("Employee not found!");
            }
            
            cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

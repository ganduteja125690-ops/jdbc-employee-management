import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    // Hardcoded credentials for testing
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get parameters from login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate credentials
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("loginTime", new java.util.Date().toString());
            
            // Set session timeout (30 minutes)
            session.setMaxInactiveInterval(30 * 60);
            
            // Forward to profile servlet
            RequestDispatcher rd = request.getRequestDispatcher("ProfileServlet");
            rd.forward(request, response);
            
        } else {
            // Login failed - include login page in output
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login Failed</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".error-container { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 25px rgba(0,0,0,0.2); text-align: center; }");
            out.println(".error-message { color: #dc3545; font-size: 18px; margin-bottom: 20px; }");
            out.println(".btn { padding: 10px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 15px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='error-container'>");
            out.println("<h2>Login Failed!</h2>");
            out.println("<p class='error-message'>Invalid username or password</p>");
            out.println("<p>Please check your credentials and try again.</p>");
            out.println("<a href='login.html' class='btn'>Back to Login</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        
        out.close();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.html");
    }
}

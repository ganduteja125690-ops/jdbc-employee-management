import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get the session
        HttpSession session = request.getSession(false);
        
        // Check if session exists and user is logged in
        if (session != null && session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            String loginTime = (String) session.getAttribute("loginTime");
            String sessionId = session.getId();
            int maxInactiveInterval = session.getMaxInactiveInterval();
            
            // Display profile page
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>User Profile</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); margin: 0; padding: 20px; }");
            out.println(".profile-container { max-width: 800px; margin: 50px auto; background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 25px rgba(0,0,0,0.2); }");
            out.println("h1 { color: #667eea; text-align: center; margin-bottom: 30px; }");
            out.println(".info-box { background: #f8f9fa; padding: 20px; border-radius: 5px; margin-bottom: 20px; border-left: 4px solid #667eea; }");
            out.println(".info-box h3 { margin-top: 0; color: #333; }");
            out.println(".info-item { margin: 10px 0; padding: 10px; background: white; border-radius: 5px; }");
            out.println(".label { font-weight: bold; color: #555; display: inline-block; width: 180px; }");
            out.println(".value { color: #667eea; }");
            out.println(".success-message { background: #d4edda; color: #155724; padding: 15px; border-radius: 5px; margin-bottom: 20px; border: 1px solid #c3e6cb; text-align: center; }");
            out.println(".btn { padding: 12px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 5px; display: inline-block; margin: 10px 5px; border: none; cursor: pointer; font-size: 16px; }");
            out.println(".btn:hover { transform: translateY(-2px); }");
            out.println(".btn-logout { background: linear-gradient(135deg, #dc3545 0%, #c82333 100%); }");
            out.println(".button-container { text-align: center; margin-top: 30px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='profile-container'>");
            out.println("<div class='success-message'>");
            out.println("<h2>✓ Login Successful!</h2>");
            out.println("<p>Welcome to your profile page</p>");
            out.println("</div>");
            out.println("<h1>User Profile</h1>");
            
            out.println("<div class='info-box'>");
            out.println("<h3>Session Information</h3>");
            out.println("<div class='info-item'>");
            out.println("<span class='label'>Username:</span>");
            out.println("<span class='value'>" + username + "</span>");
            out.println("</div>");
            out.println("<div class='info-item'>");
            out.println("<span class='label'>Login Time:</span>");
            out.println("<span class='value'>" + loginTime + "</span>");
            out.println("</div>");
            out.println("<div class='info-item'>");
            out.println("<span class='label'>Session ID:</span>");
            out.println("<span class='value'>" + sessionId + "</span>");
            out.println("</div>");
            out.println("<div class='info-item'>");
            out.println("<span class='label'>Session Timeout:</span>");
            out.println("<span class='value'>" + (maxInactiveInterval / 60) + " minutes</span>");
            out.println("</div>");
            out.println("</div>");
            
            out.println("<div class='info-box'>");
            out.println("<h3>Session Management Demo</h3>");
            out.println("<p>This page demonstrates HttpSession state management in Servlets.</p>");
            out.println("<p>Your session will remain active for " + (maxInactiveInterval / 60) + " minutes of inactivity.</p>");
            out.println("</div>");
            
            out.println("<div class='button-container'>");
            out.println("<form action='LogoutServlet' method='post' style='display:inline;'>");
            out.println("<button type='submit' class='btn btn-logout'>Logout</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
            
        } else {
            // No session or user not logged in - redirect to login
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Access Denied</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }");
            out.println(".error-container { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 10px 25px rgba(0,0,0,0.2); text-align: center; }");
            out.println(".error-message { color: #dc3545; font-size: 18px; margin: 20px 0; }");
            out.println(".btn { padding: 10px 30px; background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 15px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='error-container'>");
            out.println("<h2>Access Denied</h2>");
            out.println("<p class='error-message'>You must be logged in to view this page</p>");
            out.println("<a href='login.html' class='btn'>Go to Login</a>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
        
        out.close();
    }
}

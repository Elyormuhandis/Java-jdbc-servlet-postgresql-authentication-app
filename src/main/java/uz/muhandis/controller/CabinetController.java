package uz.muhandis.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import uz.muhandis.model.Result;
import uz.muhandis.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

@WebServlet("/cabinet")
public class CabinetController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserService();
        PrintWriter printWriter = resp.getWriter();

        //with cookies
//        Cookie[] cookies = req.getCookies();
//        String username="";
//        if (cookies!=null)
//            for (Cookie cookie:cookies
//                 ) {
//                if (cookie.getName().equals("authUser")) {
//                    username = cookie.getValue();
//                    break;
//                }
//
//            }

        // with session
        HttpSession session = req.getSession();
        String username = (String)session.getAttribute("authUser");
        if (username==null){
            resp.sendRedirect("/");
            return;
        }
        try {
           Result result = userService.loadUserByCookie(username);
           if (result.isSuccess()){
               printWriter.write( "<div style=\"display:flex; justify-content:space-around; align-items:center; \">"+
                       "<h1>Welcome to your cabinet</h1>"
                       +"<a href=\"/logout\" style=\"text-decoration:none; padding:5px; border:1px solid black;\">Logout</a>"
                        +"</div>"
               );
               printWriter.write("<h1>Your full name "+result.getUser().getFirstName()+" "+result.getUser().getLastName()+"</h1>");
               printWriter.write("<h1>Your phone "+result.getUser().getPhoneNumber()+"</h1>");

               //show session id, creation time, last accessed time
//               HttpSession session = req.getSession();
//               session.setAttribute("username", "authUser");
//               PrintWriter writer = resp.getWriter();
//               writer.println("<p>Session ID: " + session.getId() + "|");
//               writer.println("Creation Time: " + new Date(session.getCreationTime())+ "|");
//               writer. println("Last Accessed Time: " + new Date(session.getLastAccessedTime())+ "|" + "</p>");
           }else {
//               Cookie cookie = new Cookie("authUser", "");
//               cookie.setMaxAge(0);
//               resp.addCookie(cookie);
               session.invalidate();
               resp.sendRedirect("/");
           }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }


    }
}

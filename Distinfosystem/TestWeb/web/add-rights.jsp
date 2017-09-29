<%-- 
    Document   : add-rights
    Created on : 2017-sep-28, 13:47:31
    Author     : Jakob
--%>

<%@page import="Facade.UserController"%>
<%@page import="BO.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add rights</title>
    </head>
    <body>
       <%
            
            User user = (User) session.getAttribute("user");
            if (user == null) {
                out.println("You are not logged in.");
            } else if (!user.isAdministrator()) {
                out.println("Insufficient rights.");
            } else {
                //gogo
                User userToEdit = (User) session.getAttribute("userToEdit");
                if(userToEdit!=null && request.getParameter("rightToAdd") != null){
                    UserController uc = new UserController();
                    try{
                        uc.addRights(userToEdit.getName(), request.getParameter("rightToAdd"));
                        out.println("Success!");
                    }catch(Exception e){
                        out.println(e.toString());
                    }
                }else{
                    out.println("An error occured :( (Error: 5)");
                }
                
                
            }
            session.removeAttribute("userToEdit");
        %>
    </body>
</html>
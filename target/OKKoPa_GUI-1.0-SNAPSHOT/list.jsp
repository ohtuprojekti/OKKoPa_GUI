<%-- 
    Document   : list
    Created on : 08-Aug-2013, 10:57:33
    Author     : hannahir
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${message}</title>
    </head>
    <body>
        <h1>${message}</h1>
        
        <p>${warning}</p>

        <form name="newReference"
              action="${pageContext.request.contextPath}/addReference"
              method="post">
            <table>
                <tr>
                    <td>Käyttäjätunnus</td><td><input type="text" name="id" /></td>
                </tr>
                <tr>
                    <td>Viite</td><td><input type="text" name="code" /></td>
                </tr>
                <tr>
                    <td>Sähköpostiosoite (valinnainen)</td><td><input type="text" name="email" /></td>
                </tr>
                <tr>
                    <td></td><td><input type="submit" name="Lähetä" /></td>
                </tr>
            </table>
        </form>

    </body>
</html>

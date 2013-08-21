<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>${message}</title>

            <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
            <script type="text/javascript" src="jquery.searchabledropdown-1.0.8.min.js"></script>

            <script type="text/javascript">
                $(document).ready(function() {
                    $("select").searchable({
                        maxListSize: 1000, // if list size are less than maxListSize, show them all
                        maxMultiMatch: 1000, // how many matching entries should be displayed
                        exactMatch: false, // Exact matching on search
                        wildcards: true, // Support for wildcard characters (*, ?)
                        ignoreCase: true, // Ignore case sensitivity
                        latency: 200, // how many millis to wait until starting search
                        warnMultiMatch: 'Parhaat {0} osumaa...', // string to append to a list of entries cut short by maxMultiMatch
                        warnNoMatch: 'Ei osumia ...', // string to show in the list when no entries match
                        zIndex: 'auto'                          // zIndex for elements generated by this plugin
                    });
                });
            </script>
    </head>
    <body>

        <h1>${message}</h1>

        <p>${help}</p>

        <p> - - - - - - - - </p>

        <form name="getReference"
              action="${pageContext.request.contextPath}/getfront"
              method="post">
            <select name="cource">
                <option value="null"></option>
                <c:forEach var="cource" items="${courceCodes}">
                    ${cource}</c:forEach>
            </select>
            <table>
                <tr>
                    <td>${info}</td><td><textarea rows="4" cols="50" name="info"></textarea></td>
                </tr>
                <tr>
                    <td>${email}</td><td><input type="text" name="email" /></td>
                </tr>
                <tr>
                    <td></td><td><input type="submit" name="Lähetä" /></td>
                </tr>
            </table>      
        </form>
    </body>
</html>

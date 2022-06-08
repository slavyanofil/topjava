<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit and add meal</h2>
<form method="POST" action="meals" name="formEditAddMeal">
    DateTime: <input type="text" name="dateTime"
                     value="<c:out value="${meal.dateTime}" />" placeholder="2007-12-03T10:15:30"/> <br/>
    Description : <textarea name="description" rows=5
                            cols=75>${meal.description}</textarea> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />"/> <br/>
    <button type="submit">Save</button>
    <button onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meal</h2>
<a href="meals?action=add"><img src="img/add.png"></a>
<table border="1">
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.excess?"red":"green"}">
            <td>${meal.stringDateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?mealId=${meal.id}&action=delete"><img src="img/delete.png"></a></td>
            <td><a href="meals?mealId=${meal.id}&action=edit"><img src="img/edit.png"></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
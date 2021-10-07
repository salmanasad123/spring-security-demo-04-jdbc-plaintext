<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- uri for spring security, we added the dependency in pom because its not available in spring core
it is a different project / different framework -->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<html>
<head>
    <title>Luv 2 code Systems Home Page</title>
</head>

<body>

<h2>Luv 2 code Systems Home Page </h2>
<hr>

<p>
    Admin specifications
</p>

<hr>
<!-- ageContext.request.contextPath refers to application root -->
<a href="${pageContext.request.contextPath}/">Back to home page</a>

</body>
</html>
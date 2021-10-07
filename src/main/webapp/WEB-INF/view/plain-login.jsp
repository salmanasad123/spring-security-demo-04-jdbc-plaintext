<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Custom Login Form</title>
    <style>
        .failed {
            color:red;
        }

    </style>
</head>

<body>
<h3>
    My Custom Login Page
</h3>
<!-- dynamic access to the name of our context root -->
<form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="post">

    <!-- Check for login error, spring will pass in the error if login is failed-->
    <c:if test="${param.error!=null}">
        <i class="failed">Sorry, You entered invalid username / password</i>
    </c:if>

    <!-- name="username" is very important because spring will look for this when processing the form-->
    <p>
        User name : <input type="text" name="username"/>
    </p>
    <p>
        Password : <input type="password" name="password"/>
    </p>

    <input type="submit" value="Login"/>
</form:form>
</body>

</html>
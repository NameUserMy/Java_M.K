<%--
  Created by IntelliJ IDEA.
  User: Work_Man
  Date: 22.01.2025
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Title</title>
  </head>
    <body>
      <h1>JSP</h1>
      <h2>Expression</h2>
      <%=2+3%>
      <h2>variable</h2>
      <%
        int x=10;
      %>
      <%= x %>

      <h2>conditional operators</h2>
    <%if(x%2==0){%>

      <b>nuber number <%=x%><b>
    <%}else{%>

      <b>nuber Nonumber <%=x%><b>
    <%}%>

        <%for(int i=0;i<3;i++){%>

      <p><%=i%><p>

<%}%>



    </body>
</html>

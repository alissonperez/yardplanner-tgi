<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:include value="/WEB-INF/content/includes/head.inc.jsp">
    <s:param name="title"><s:property value="title" escape="true"/></s:param>
    <s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

<body>

<s:action name="navbar" namespace="/" executeResult="true"/>

<!-- Container -->
<div class="container">

<div class="page-header">
<h1>Login</h1>
</div>

<s:fielderror cssClass="alert alert-block"/>

<form class="form-horizontal" action="<s:url action="login" namespace="/usuario"/>" method="post">
<fieldset>
<div class="control-group">
<label class="control-label" for="user_email">E-mail</label>
<div class="controls">
<input id="user_email" name="user.email" type="text" value="<s:property value="user.email"/>">
</div>
</div>

<div class="control-group">
<label class="control-label" for="user_password">Senha</label>
<div class="controls">
<input id="user_password" name="user.passwordMD5" type="password">
</div>
</div>

<p><a href="<s:url action="registro" namespace="/usuario"/>">Cadastrar-se</a></p>

<div class="form-actions">
<input class="btn btn-primary btn-large" type="submit" name="send" value="Logar">
</div>
</fieldset>

</form>

<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>

</div>
<!-- Container -->


<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

</body>
</html>

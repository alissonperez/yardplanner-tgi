<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:include value="/WEB-INF/content/includes/head.inc.jsp">
    <s:param name="title"><s:property value="title" escape="true"/></s:param>
    <s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

<body>

<s:action name="navbar" namespace="/" executeResult="true"/>

<!-- Container -->
<div class="container register">

<div class="page-header">
<h1>Meus dados</h1>
</div>

<s:fielderror cssClass="alert alert-block"/>

<form action="<s:url action="editar" namespace="/usuario" />" class="form-horizontal" method="post">

<fieldset>

<!-- Field: FirstName -->
<div class="control-group">

<label class="control-label" for="first_name">Primeiro nome</label>
<div class="controls">
<input id="first_name" type="text" name="user.firstName" value="<s:property value="user.firstName"/>"/>
</div>

</div>
<!-- /Field: FirstName -->

<!-- Field: LastName -->
<div class="control-group">

<label class="control-label" for="last_name">Último nome</label>

<div class="controls">
<input id="last_name" type="text" name="user.lastName" value="<s:property value="user.lastName"/>"/>
</div>

</div>
<!-- /Field: LastName -->

<!-- Field: E-mail -->
<div class="control-group">

<label class="control-label" for="email">E-mail</label>

<div class="controls">
<input id="email" type="text" name="user.email" value="<s:property value="user.email"/>"/>
</div>

</div>
<!-- /Field: E-mail -->

<p>Para alterar a senha, digite a senha atual e em seguida a nova.</p>

<!-- Senha antiga -->
<div class="control-group">

<label class="control-label" for="oldPassword">Senha atual</label>

<div class="controls">
<input id="oldPassword" type="password" name="oldPassword" value=""/>
</div>
</div>
<!-- /Senha antiga -->

<!-- Field: Senha 1 -->
<div class="control-group">

<label class="control-label" for="password">Nova Senha</label>

<div class="controls">
<input id="password" type="password" name="user.password" value=""/>
</div>

</div>
<!-- /Field: Senha 1 -->

<!-- Field: Senha 2 -->
<div class="control-group">

<label class="control-label" for="passwordConf">Confirmação de Senha</label>

<div class="controls">
<input id="passwordConf" type="password" name="passwordConfirmation" value=""/>
</div>

</div>
<!-- /Field: Senha 2 -->

<!-- FormActions -->
<div class="form-actions">
<input class="btn btn-primary" type="submit" name="send" value="Gravar">
</div>
<!-- /FormActions -->

</fieldset>

</form>

<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>
<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

</div>
<!-- /Container -->

</body>
</html>
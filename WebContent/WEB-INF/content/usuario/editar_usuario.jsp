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

<!-- Navigation -->
<ul class="breadcrumb">
<li>
<a href="<s:url action="index" namespace="/"/>">Home</a> <span class="divider">/</span>
</li>
<li>
<a href="<s:url action="todos" namespace="/usuario"/>">Usuários</a> <span class="divider">/</span>
</li>
<li class="active">Editar usuário</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>Editar usuário
<small><s:property value="user.firstName"/> <s:property value="user.lastName"/></small>
</h1>
</div>

<s:if test="message">
<div class="alert alert-<s:property value="message.level"/>"><s:property value="message.message"/></div>
</s:if>

<form action="<s:url action="usuario/%{user.userId}" namespace="/usuario" />" class="form-horizontal" method="post">

<fieldset>

<div class="row">
<!-- Coluna 1 -->
<div class="span6">
<!-- Field: FirstName -->
<div class="control-group">

<label class="control-label" for="first_name">Primeiro Nome</label>
<div class="controls">
<input id="first_name" type="text" name="user.firstName" value="<s:property value="user.firstName"/>" readonly="readonly"/>
</div>

</div>
<!-- /Field: FirstName -->

<!-- Field: LastName -->
<div class="control-group">

<label class="control-label" for="last_name">Último Nome</label>
<div class="controls">
<input id="last_name" type="text" name="user.lastName" value="<s:property value="user.lastName"/>" readonly="readonly"/>
</div>

</div>
<!-- /Field: LastName -->

<!-- Field: E-mail -->
<div class="control-group">

<label class="control-label" for="email">E-mail</label>

<div class="controls">
<input id="email" type="text" name="user.email" value="<s:property value="user.email"/>" readonly="readonly"/>
</div>

</div>
<!-- /Field: E-mail -->

<!-- Field: Adm -->
<div class="control-group">

<label class="control-label" for="user_root">Administrador</label>

<div class="controls">
<s:checkbox id="user_root" name="user.root"/>
</div>

</div>
<!-- /Field: Adm -->

<!-- Field: Bloqueado -->
<div class="control-group">

<label class="control-label" for="user_blocked">Bloqueado</label>

<div class="controls">
<s:checkbox id="user_blocked" name="user.blocked"/>
</div>

</div>
<!-- /Field: Bloqueado -->
</div>
<!-- Coluna 1 -->

<!-- Coluna 2 -->
<div class="span6">
<h3>Configurações</h3>
<p><b>Administrador</b>: Usuário pode visualizar, editar tipo (Administrador ou normal) ou bloquear usuários.</p>
<p><b>Bloqueado</b>: Usuário não poderá acessar a aplicação.</p>
</div>
<!-- Coluna 2 -->
</div>

<!-- FormActions -->
<div class="form-actions">
<input class="btn btn-primary" type="submit" name="sendBtn" value="Gravar"> <input class="btn" type="submit" name="cancelBtn" value="Cancelar">
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
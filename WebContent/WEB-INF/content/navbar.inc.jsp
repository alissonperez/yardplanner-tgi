<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!-- Nav Bar -->
<div class="navbar navbar-fixed-top">

<div class="navbar-inner">

<div class="container">
<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
</a>
<a class="brand" href="<s:url action="index" encode="false" namespace="/" />">YardPlanner</a>
<div class="nav-collapse">
<ul class="nav">
<li><a href="<s:url action="index" encode="false" namespace="/patio" />">Pátios</a></li>
<s:if test="%{!logged}">
<li><a href="<s:url action="registro" encode="false" namespace="/usuario" />">Cadastro</a></li>
</s:if>
<s:else>
<s:if test="user.root"><li><a href="<s:url action="todos" encode="false" namespace="/usuario"/>">Usuários</a></li></s:if>
<li><a href="<s:url action="editar" encode="false" namespace="/usuario"/>">Meus dados</a></li>
</s:else>
<li><a href="<s:url action="sobre" encode="false" namespace="/site" />">Sobre</a></li>
<li><a href="<s:url action="ajuda" encode="false" namespace="/site" />">Ajuda</a></li>
</ul>
<s:if test="logged">
<ul class="nav pull-right">
<li class="dropdown">
<a data-toggle="dropdown" class="dropdown-toggle" href="#">Olá <s:property value="user.firstName"/> <b class="caret"></b></a>
<ul class="dropdown-menu">
<s:if test="user.root"><li><a href="<s:url action="todos" encode="false" namespace="/usuario"/>">Usuários</a></li></s:if>
<li><a href="<s:url action="editar" encode="false" namespace="/usuario"/>">Meus dados</a></li>
<li class="divider"></li>
<li><a href="<s:url action="logout" encode="false" namespace="/usuario"/>">Logout</a></li>
</ul>
</li>
</ul>
</s:if>
<s:else>
<form action="<s:url action="login" encode="false" namespace="/usuario"/>" class="navbar-form pull-right" method="post">
<input type="text" name="user.email" value="" placeholder="E-mail" size="10">
<input type="password" name="user.passwordMD5" value="" placeholder="Senha" size="10">
<input type="submit" class="btn navbar-btn" name="send" value="Logar">
</form>
</s:else>
</div>
</div>

</div>
</div>
<!--/NavBar-->

<!-- Header - Logo -->
<div class="container">
<div class="header-logo"><a href="<s:url value="/" encode="false" namespace="/"/>"><img src="<s:url value="/images/logo_v3.jpg" encode="false"/>" alt="YardPlanner"></a></div>
</div>
<!-- /Header - Logo -->
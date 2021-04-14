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

<!-- Navigation -->
<ul class="breadcrumb">
<li>
<a href="<s:url action="index" namespace="/"/>">Home</a> <span class="divider">/</span>
</li>
<li class="active">Sobre</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>Sobre</h1>
</div>

<!-- GRID -->
<div class="row show-grid">

<!-- Content (span16) -->
<div class="span16 manual">

<p>O YardPlanner � uma ferramenta computacional desenvolvida com base nos mais modernos recursos cient�ficos e tecnol�gicos com o objetivo de fazer a <i><b>programa��o da sequ�ncia de retirada de cont�ineres</b></i> em um p�tio de um Terminal de Cont�ineres. Sob o ponto de vista cient�fico, o YardPlanner usa algoritmos baseados em Computa��o Natural para calcular uma sequ�ncia de custo m�nimo de retirada dos cont�ineres do p�tio. Tecnologicamente, o produto foi desenvolvido em Java usando Struts, Hibernate e Banco de Dados MySQL.</p> 
<p>O projeto foi desenvolvido dentro do Laborat�rio de Computa��o Natural (<a href="http://www.mackenzie.br/lcon.html" target="_blank">LCoN</a>) da Universidade Mackenzie usando recursos financeiros do Conselho Nacional de Desenvolvimento Cient�fico e Tecnol�gico (<a href="http://www.cnpq.br/" target="_blank">CNPq</a>) via Edital de Pesquisa, Desenvolvimento e Inova��o em Transportes n. 18/2009. A equipe de trabalho incluiu os seguintes pesquisadores: Leandro Nunes de Castro (Coordenador), Alisson dos Reis Perez, James Lee, Josu� Salvador, Luiz Ant�nio Carraro e Willyan Abilhoa.</p>

</div>
<!-- /Content (span16) -->

</div>
<!-- /GRID -->

<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>
<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>
</div>
<!-- /Container -->

</body>
</html>
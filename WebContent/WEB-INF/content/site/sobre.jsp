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

<p>O YardPlanner é uma ferramenta computacional desenvolvida com base nos mais modernos recursos científicos e tecnológicos com o objetivo de fazer a <i><b>programação da sequência de retirada de contêineres</b></i> em um pátio de um Terminal de Contêineres. Sob o ponto de vista científico, o YardPlanner usa algoritmos baseados em Computação Natural para calcular uma sequência de custo mínimo de retirada dos contêineres do pátio. Tecnologicamente, o produto foi desenvolvido em Java usando Struts, Hibernate e Banco de Dados MySQL.</p> 
<p>O projeto foi desenvolvido dentro do Laboratório de Computação Natural (<a href="http://www.mackenzie.br/lcon.html" target="_blank">LCoN</a>) da Universidade Mackenzie usando recursos financeiros do Conselho Nacional de Desenvolvimento Científico e Tecnológico (<a href="http://www.cnpq.br/" target="_blank">CNPq</a>) via Edital de Pesquisa, Desenvolvimento e Inovação em Transportes n. 18/2009. A equipe de trabalho incluiu os seguintes pesquisadores: Leandro Nunes de Castro (Coordenador), Alisson dos Reis Perez, James Lee, Josué Salvador, Luiz Antônio Carraro e Willyan Abilhoa.</p>

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
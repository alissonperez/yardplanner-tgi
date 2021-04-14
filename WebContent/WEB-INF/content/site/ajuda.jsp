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
<li class="active">Ajuda</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>Ajuda
<small>Manual do usuário</small></h1>
</div>

<!-- GRID -->
<div class="row show-grid">

<!-- Content (span16) -->
<div class="span16 manual">

<!-- Item -->
<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/novo_patio.png" alt="Formulário para criação de um novo pátio.">
<div class="description">Formulário para criação de um novo pátio.</div>
</div>
<!-- /Figura -->
<h2>Pátios</h2>
<p>Os containers são armazenados temporariamente em pátios, divididos em regiões comumente conhecidas como blocos. Para criar um pátio, clique na opção <b>Pátios</b> na barra de navegação superior. Na página seguinte entre com o nome desejado para o pátio e clique no botão <b>Gravar</b>.</p>
<div class="clear_both"></div>
</div>
<!-- Item -->

<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/novo_bloco.png" alt="Formulário para criação de um novo pátio.">
<div class="description">Formulário para criação de um novo bloco.</div>
</div>
<!-- /Figura -->
<h2>Blocos</h2>
<p>Cada bloco possui um determinado número de linhas, colunas e camadas. Sua finalidade é armazenar contêineres em pilhas. Os blocos podem armazenar contêineres de diversos tipos e tamanhos. No YardPlanner é possível utilizar dois tamanhos de contêineres: 1 ou 2 TEUs (<i>Twenty Feet Equivalent Unit container</i>). Para criar um bloco, clique sobre o pátio desejado. Na página seguinte informe o nome do contêiner, o número de linhas, colunas e camadas. Em seguida, selecione o tamanho desejado e clique no botão <b>Gravar</b>.</p>
<div class="clear_both"></div>
</div>

<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/bloco.jpg" alt="Visualização dos containers em um bloco de 3 colunas, 4 linhas e 2 camadas.">
<div class="description">Visualização dos containers em um bloco de 3 colunas, 4 linhas e 2 camadas.</div>
</div>
<!-- /Figura -->
<h2>Containers</h2>
<p>Os contêineres estão dispostos em linhas, colunas e camadas. Cada camada é representada por uma matriz (conforme figura). Há duas formas de popular um bloco com contêineres:<br>Manualmente por meio de inclusão individual; ou em Lote por meio de um arquivo XML.</p>

<div class="separator"></div>
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/novo_container.png" alt="Formulário para a inserção manual de um container.">
<div class="description">Formulário para a inserção manual de um container.</div>
</div>
<!-- /Figura -->
<div class="indent2">
<h3>Inclusão individual</h3>
<p>Possibilita a inclusão de containers individuais no bloco. Informe o código do contêiner (ISO 6346) e a sua posição nos campos <b>coluna</b>, <b>linha</b> e <b>camada</b>. Em seguida, clique no botão <b>Gravar</b>. O contêiner será inserido no bloco.</p>
</div>

<div class="separator"></div>
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/upload_xml.png" alt="Formulário upload de arquivo xml.">
<div class="description">Formulário upload de arquivo xml.</div>
</div>
<!-- /Figura -->
<div class="indent2">
<h3>Inclusão em lote (arquivo XML)</h3>
<p>Possibilita informar os contêineres presentes em um bloco de maneira rápida e prática. Para isso, entre no bloco desejado. No formulário para upload selecione o arquivo e clique no botão <b>Gravar</b>.</p>

<h6>Obs: É possível limpar todo o bloco no processo de upload de XML. Para isso, marque a opção <b>Limpar bloco</b> no formulário de upload.</h6>
<h6>Obs: Caso não seja informado o código para o container, o sistema gerará um de forma aleatória.</h6>
</div>

<div class="clear_both"></div>
</div>

<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/remover_containers.jpg" alt="Tela para marcação dos containers que se deseja remover.">
<div class="description">Tela para marcação dos containers que se deseja remover.</div>
</div>
<!-- /Figura -->
<h2>Remoção de containers</h2>
<p>Entre no bloco desejado e clique sobre o botão <b>Remover contêineres</b>. Na tela seguinte (semelhante à tela do bloco), marque os containers desejados e clique sobre o botão <b>Remover</b>.<br>Será exibida a sequência ótima para remoção, seu custo e a movimentação necessária dos contêineres.</p>

<div class="separator"></div>
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/remocao.jpg" alt="Resultado da remoção com a sequência ótima, seu custo e a movimentação necessária dos containers.">
<div class="description">Resultado da remoção com a sequência ótima, seu custo e a movimentação<br>necessária dos containers.</div>
</div>
<!-- /Figura -->
<div class="indent2">
<h3>Resultado da remoção</h3>
<p>O sistema efetuará o processamento da remoção calculando a melhor sequência de retirada e exibindo-a em seguida.<br>É possível confirmar a remoção (botão <b>Confirmar</b>) ou cancelá-la (botão <b>Cancelar</b>).</p>
<div class="clear_both"></div>
</div>
</div>

<div class="manual_item">
<h2>Video tutorial</h2>
<p>Baixe <a href="<s:url value="/xmlexample/block_video_tutorial.xml"/>" target="_blank">aqui</a> o xml utilzado para popular o pátio visto no vídeo abaixo.</p>

<div class="video_tutorial">
<iframe width="840" height="630" src="http://www.youtube.com/embed/2wTYdQ7KiqM" allowFullScreen="true"></iframe>
</div>
</div>

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
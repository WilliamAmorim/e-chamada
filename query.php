<?php
	
	require "Db.class.php";
	$db = new Db();
    ini_set('error_reporting', E_ALL^E_NOTICE);
	$sqlBusca = $_POST['sql'];// Pega o código sql enviado pelo aplicativo
	$resultado = $db->query($sqlBusca);
	
	// Transforma o resultado da consulta em um array associativo
	while ($array = mysqli_fetch_assoc($resultado)) {
		$dados[] = $array;
	}
	
	echo json_encode($dados);// Retorna o resultado da consulta no formato JSON

?>
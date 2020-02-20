<?php
		
	/**
	*  conexão com o banco de dados usando mysqli
	*/
	class Db{
		
		private $con;

		// Faz a conexão com o banco assim que o objeto é criado
		function __construct(){
		    // host - user - senha - database
			$this->con = mysqli_connect('dbechamada.mysql.uhserver.com', 'williamamorimws', '@zul4337', 'dbechamada');
			if (mysqli_connect_errno($this->con)) {
				echo "Problemas para conectar no banco. Verifique os dados!";
				die();
			}else{
			    
			}
		}

		// Faz a consulta sql
		public function query($sql){
			return mysqli_query($this->con, $sql);
		}
	}

?>
<?php
class User {
	private $conn;
	private $tableName = "users";
	
	public $id;
	public $email;
	public $password;
	public $salt;
	public $firstName;
	public $lastName;
	public $companyId;
	public $createdOn;
	public $updatedOn;
	
	public function __construct($db) {
		$this->conn = $db;
	}
	
	function getUser() {
		$query = "SELECT USER_ID as id, EMAIL as email, PASSWORD as password, SALT as salt,
						 FIRST_NAME as firstName, LAST_NAME as lastName, COMPANY_ID as companyId, 
						 CREATED_ON as createdOn, UPDATED_ON as updatedOn
				  FROM $this->tableName 
				  WHERE EMAIL = $this->email AND PASSWORD = $this->password";
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		
		return $stmt;
	}
	
	function getSalt() {
		$query = "SELECT SALT
				  FROM $this->tableName
				  WHERE EMAIL = $this->email";
				  
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		
		$result = $stmt->fetch(PDF::FETCH_ASSOC);
		return $result['salt'];
	}
	
	function putUser() {
		$query = "INSERT INTO $this->tableName (EMAIL, PASSWORD, SALT, FIRST_NAME, LAST_NAME, COMPANY_ID) 
				  VALUES('$this->email','$this->password','$this->salt','$this->firstName','$this->lastName','$this->companyId')";
		
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		
		return $stmt;
	}
	
	function updateUser() {
		$query = "UPDATE $this->tableName
				  SET EMAIL = '$this->email', PASSWORD = '$this->password', SALT = $this->salt,
					  FIRST_NAME = '$this->firstName', LAST_NAME = '$this->lastName', COMPANY_ID = '$this->companyId'
					  UPDATED_ON = CURRENT_TIMESTAMP
				  WHERE ID = $this->id"
	}
}
?>
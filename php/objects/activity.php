<?php
class Activity {
	private $conn;
	private $tableName = "activities";
	
	public $id;
	public $activityDate;
	public $activityType;
	public $activitySubtype;
	public $activityDetails;
	public $activityStart;
	public $activityEnd;
	public $loggedBy;
	
	public function __construct($db) {
		$this->conn = $db;
	}
	
	function getAllActivities() {
		$query = "SELECT ACTIVITY_ID as id, ACTIVITY_DATE as activityDate, ACTIVITY_TYPE as activityType, ACTIVITY_SUBTYPE as activitySubtype,
						 ACTIVITY_DETAILS as activityDetails, ACTIVITY_START as activityStart, ACTIVITY_END as activityEnd, LOGGED_BY as loggedBy 
				  FROM $this->tableName 
				  ORDER BY ACTIVITY_DATE";
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		
		return $stmt;
	}
	
	function getActivitiesByDate($date) {
		$query = "SELECT ACTIVITY_ID as id, ACTIVITY_DATE as activityDate, ACTIVITY_TYPE as activityType, ACTIVITY_SUBTYPE as activitySubtype,
						 ACTIVITY_DETAILS as activityDetails, ACTIVITY_START as activityStart, ACTIVITY_END as activityEnd, LOGGED_BY as loggedBy
				  FROM $this->tableName 
				  WHERE ACTIVITY_DATE = STR_TO_DATE('$date', '%Y-%m-%d') 
				  ORDER BY ACTIVITY_DATE";
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		
		return $stmt;
	}
	
	function putActivity() {
		if (isset($this->activityStart, $this->activityEnd)) {
			$query = "INSERT INTO $this->tableName (ACTIVITY_DATE,ACTIVITY_TYPE,ACTIVITY_SUBTYPE,ACTIVITY_DETAILS,ACTIVITY_START,ACTIVITY_END,LOGGED_BY) 
					  VALUES('$this->activityDate','$this->activityType','$this->activitySubtype','$this->activityDetails','$this->activityStart','$this->activityEnd','$this->loggedBy')";
		} else {
			$query = "INSERT INTO $this->tableName (ACTIVITY_DATE,ACTIVITY_TYPE,ACTIVITY_SUBTYPE,ACTIVITY_DETAILS,LOGGED_BY) 
					  VALUES('$this->activityDate','$this->activityType','$this->activitySubtype','$this->activityDetails','$this->loggedBy')";
		}
		
		$stmt = $this->conn->prepare($query);
		$stmt->execute();
		
		return $stmt;
	}
}
?>
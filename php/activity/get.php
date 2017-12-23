<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/database.php';
include_once '../objects/activity.php';

$database = new Database();
$db = $database->getConnection();

$activity = new Activity($db);

if (isset($_GET['date'])) {
	$stmt = $activity->getActivitiesByDate($_GET['date']);
} else {
	$stmt = $activity->getAllActivities();
}

$num = $stmt->rowCount();

if ($num > 0) {
	$activities_arr = array();
	$activities_arr["records"] = array();
	
	while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
		extract($row);
		
		$activity_item = array(
			"id" => $id,
			"activityDate" => $activityDate,
			"activityType" => $activityType,
			"activitySubtype" => $activitySubtype,
			"activityDetails" => $activityDetails,
			"activityStart" => $activityStart,
			"activityEnd" => $activityEnd,
			"loggedBy" => $loggedBy
		);
		
		array_push($activities_arr["records"], $activity_item);
	}
	
	echo json_encode($activities_arr);
} else {
	echo json_encode(
		array("message" => "No activities found!")
	);
}
?>
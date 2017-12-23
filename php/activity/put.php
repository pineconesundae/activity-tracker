<?php
include_once '../config/database.php';
include_once '../objects/activity.php';

$database = new Database();
$db = $database->getConnection();

$activity = new Activity($db);

if (!isset($_GET['date'], $_GET['type'], $_GET['logged'])) {
	header($_SERVER["SERVER_PROTOCOL"] . " 404 Not Found");
	echo "Required fields date, type, and logged are missing!";
} else {
	$activity->activityDate = $_GET['date'];
	$activity->activityType = $_GET['type'];
	$activity->activitySubtype = $_GET['subtype'];
	$activity->activityDetails = $_GET['details'];
	$activity->activityStart = $_GET['start'];
	$activity->activityEnd = $_GET['end'];
	$activity->loggedBy = $_GET['logged'];

	$stmt = $activity->putActivity();
	
	header($_SERVER["SERVER_PROTOCOL"] . " 200 Ok");
	echo "Record successfully submitted!";
}
?>
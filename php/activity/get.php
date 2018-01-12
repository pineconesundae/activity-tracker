<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");

include_once '../config/database.php';
include_once '../objects/activity.php';
include_once '../objects/user.php';

$database = new Database();
$db = $database->getConnection();

$object = $_GET['object'];

switch ($object) {
	case "activity":
		$activity = new Activity($db);

		if (isset($_GET['date'])) {
			$stmt = $activity->getActivitiesByDate($_GET['date']);
		} else {
			$stmt = $activity->getAllActivities();
		}

		if ($stmt->rowCount() > 0) {
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
		
		break;
	case "user":
		$user = new User($db);
		$stmt = $user->getUserByEmail($_GET['email']);
		
		if ($stmt->rowCount() > 0) {
			$users_arr = array();
			$users_arr["records"] = array();
			
			while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
				extract($row);
				
				$user_item = array(
					"id" => $id,
					"email" => $email,
					"password" => $password,
					"salt" => $salt,
					"firstName" => $firstName,
					"lastName" => $lastName,
					"companyId" => $companyId,
					"createdOn" => $createdOn,
					"updatedOn" => $updatedOn
				);
				
				array_push($users_arr["records"], $user_item);
			}
			
			echo json_encode($users_arr);
		} else {
			echo json_encode(
				array("message" => "No users found!")
			);
		}
		
		break;
	default:
		echo json_encode(
			array("message" => "Unknown object requested!")
		);
}
?>
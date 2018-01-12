<?php
include_once '../config/database.php';
include_once '../objects/activity.php';
include_once '../objects/user.php';

$database = new Database();
$db = $database->getConnection();

$object = $_GET['object'];

switch ($object) {
	case "activity":
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

		break;
	case "user":
		$user = new User($db);
		
		if (isset($_GET['id'])) {
			echo "update";
			$stmt = $user->getUserById($_GET['id']);
			$row = $stmt->fetch(PDO::FETCH_ASSOC);
			extract($row);
			
			$user->id = $id;
			$user->email = (isset($_GET['email']) ? $_GET['email'] : $email);
			$user->password = isset($_GET['password']) ? $_GET['password'] : $password;
			$user->salt = isset($_GET['salt']) ? $_GET['salt'] : $salt;
			$user->firstName = isset($_GET['firstname']) ? $_GET['firstname'] : $firstName;
			$user->lastName = isset($_GET['lastname']) ? $_GET['lastname'] : $lastName;
			$user->companyId = isset($_GET['companyid']) ? $_GET['companyid'] : $companyId;
			
			$user->updateUser();
		} else {
			echo "put";
			$user->email = $_GET['email'];
			$user->password = $_GET['password'];
			$user->salt = $_GET['salt'];
			$user->firstName = $_GET['firstname'];
			$user->lastName = $_GET['lastname'];
			$user->companyId = isset($_GET['companyid']) ? $_GET['companyid'] : null;
			
			$user->putUser();
		}
		
		header($_SERVER["SERVER_PROTOCOL"] . " 200 Ok");
		echo "Record successfully submitted!";
		break;
	default:
		echo json_encode(
			array("message" => "Unknown object requested!")
		);
}

?>
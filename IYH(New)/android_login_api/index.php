<?php
require_once 'include/DB_Functions.php';

$db = new DB_Functions();

if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    $tag = $_POST['tag'];
	
	$response = array("tag" => $tag, "success" => 0, "error" => 0);
	
    if ($tag == 'login') 
	{
        $email = $_POST['email'];
        $password = $_POST['password'];
		
        $user = $db->getUserByUsernameAndPassword($email, $password);
		
        if ($user != false) 
		{
            $response["success"] = 1;
            $response["uid"] = $user["unique_id"];
            $response["user"]["username"] = $user["username"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["created_at"] = $user["created_at"];
            $response["user"]["updated_at"] = $user["updated_at"];
        } 
		else
		{
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
        }
    } 
	else if ($tag == "register") 
	{
        $username = $_POST['username'];
		$fullname = $_POST['fullname'];
        $password = $_POST['password'];
		$email = $_POST['email'];
		$businessname = $_POST['businessname'];
		
		$user = $db->storeUser($username, $fullname, $email, $password, $businessname);
		
		if ($user) 
		{
			$response["success"] = 1;
			$response["uid"] = $user["unique_id"];
			$response["user"]["username"] = $user["username"];
			$response["user"]["fullname"] = $user["fullname"];
			$response["user"]["email"] = $user["email"];
			$response["user"]["businessname"] = $user["businessname"];
			$response["user"]["created_at"] = $user["created_at"];
			$response["user"]["updated_at"] = $user["updated_at"];
		}
    }
	
	echo json_encode($response);
} 
else 
{
}

?>

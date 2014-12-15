<?php

if (isset($_POST['tag']) && $_POST['tag'] != '') 
{
    
    $tag = $_POST['tag'];

    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

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
            echo json_encode($response);
        } 
		else
		{
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } 
	else if ($tag == 'register') 
	{
        $username = $_POST['username'];
		$fullname = $_POST['fullname'];
        $password = $_POST['password'];
		$email = $_POST['email'];
		$businessname = $_POST['businessname'];

        if ($db->isUserExisted($email)) 
		{
            $response["error"] = 9;
            $response["error_msg"] = "Email already existed";
            echo json_encode($response);
        } 
		else 
		{
			if ( !empty($username) && !empty($fullname) && !empty($password) && !empty($email) && !empty($businessname) ) 
			{
				if (!filter_var($email, FILTER_VALIDATE_EMAIL)) 
				{
					$response["error"] = 7;
					$response["error_msg"] = "Not a Valid Email";
					echo json_encode($response);
				}
				elseif (strlen($password) < 6)
				{	
					$response["error"] = 8;
					$response["error_msg"] = "Minimum password length is 6";
					echo json_encode($response);
				}
				else
				{
					$user = $db->storeUser($username, $fullname, $password,  $email, $businessname);
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
						echo json_encode($response);
					}
				}
			}
			elseif ( empty($username)  ) 
			{
				$response["error"] = 2;
				$response["error_msg"] = "Username field is empty";
				echo json_encode($response);
			}
			elseif ( empty($fullname)  ) 
			{
				$response["error"] = 3;
				$response["error_msg"] = "Full name field is empty";
				echo json_encode($response);
			}
			elseif ( empty($password) ) 
			{
				$response["error"] = 4;
				$response["error_msg"] = "Password field is empty";
				echo json_encode($response);
			}
			elseif ( empty($email) ) 
			{
				$response["error"] = 5;
				$response["error_msg"] = "Email field is empty";
				echo json_encode($response);
			}
			elseif ( empty($businessname) ) 
			{
				$response["error"] = 6;
				$response["error_msg"] = "Business name field is empty";
				echo json_encode($response);
			}
			else
			{
				$response["error"] = 1;
				$response["error_msg"] = "One or more fields are empty";
				echo json_encode($response);
			}
        }
    } 
	else 
	{
        echo "Invalid Request";
    }
} 
else 
{
    echo "Access Denied";
}
?>

// Samuel Benicewicz // PHP needed for login page until app is deployed
<?php
if ($_POST['utd-id'] == 'admin' && $_POST['pwd'] == 'admin') {
    echo "true";
} else {
    echo "false";
}
?>
<?php // Samuel Benicewicz // PHP needed for quiz page until app is deployed
if ($_POST['utd-id'] == 'admin' && $_POST['pwd'] == 'admin') {
    echo "true";
} else {
    echo "false";
}
?>
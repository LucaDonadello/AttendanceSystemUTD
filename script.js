// This script assumes the IP is dynamically rendered into the span with id "ipValue"
document.addEventListener('DOMContentLoaded', function() {
    var schoolIP = '192.168.1.'; // Simplified example, use actual school IP/pattern
    var userIP = document.getElementById('ipValue').textContent;
    
    if (userIP.startsWith(schoolIP)) {
        // IP matches the school's pattern
        document.getElementById('ipAddress').style.backgroundColor = '#ccffcc'; // Green background
        document.getElementById('submitBtn').disabled = false;
    } else {
        // IP does not match
        document.getElementById('ipAddress').style.backgroundColor = '#ffcccc'; // Red background
        document.getElementById('submitBtn').disabled = true;
    }

    
});

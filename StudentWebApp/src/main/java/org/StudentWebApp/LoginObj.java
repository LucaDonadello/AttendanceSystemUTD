// Samuel Benicewicz // Login Object

package org.StudentWebApp;

// Login Object Class
public class LoginObj {
    public String utdID;
    public String password;

    // Constructor
    public LoginObj(String utdID, String password) {
        this.utdID = utdID;
        this.password = password;
    }

    // Constructor
    public LoginObj() {

    }

    // Setter for ID
    public void setUtdID(String utdID) {
        this.utdID = utdID;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }
}

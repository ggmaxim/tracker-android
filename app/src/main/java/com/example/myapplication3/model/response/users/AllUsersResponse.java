package com.example.myapplication3.model.response.users;

import java.io.Serializable;
import java.util.List;

public class AllUsersResponse implements Serializable {


    /**
     * isSuccess : 1
     * message : login sucessful
     * users : [{"id":"1","username":"martin1","password":"827ccb0eea8a706c4c34a16891f84e7b","expiry":null},{"id":"2","username":"janet","password":"81dc9bdb52d04dc20036dbd8313ed055","expiry":null}]
     */

    private int isSuccess;
    private String message;
    /**
     * id : 1
     * username : martin1
     * password : 827ccb0eea8a706c4c34a16891f84e7b
     * expiry : null
     */

    private List<UsersBean> users;

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean implements Serializable {
        private String _id;
        private String email;
        private String cnp;
        private String role;
        private String full_name;

        public String getId() {
            return _id;
        }

        public void setId(String id) {
            this._id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCNP() {
            return cnp;
        }

        public void setCNP(String cnp) {
            this.cnp = cnp;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getFullName() {
            return full_name;
        }

        public void setFullName(String full_name) {
            this.full_name = full_name;
        }
    }
}

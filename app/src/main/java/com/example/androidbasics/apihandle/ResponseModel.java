package com.example.androidbasics.apihandle;

import java.util.List;

public class ResponseModel {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubmittedReturn> getSubmittedReturns() {
        return submittedReturns;
    }

    public void setSubmittedReturns(List<SubmittedReturn> submittedReturns) {
        this.submittedReturns = submittedReturns;
    }

    private List<SubmittedReturn> submittedReturns;


    public class SubmittedReturn {
        private String returnId;
        private String returnStatus;


        public String getReturnStatus() {
            return returnStatus;
        }

        public void setReturnStatus(String returnStatus) {
            this.returnStatus = returnStatus;
        }

        public String getReturnId() {
            return returnId;
        }

        public void setReturnId(String returnId) {
            this.returnId = returnId;
        }
    }
}
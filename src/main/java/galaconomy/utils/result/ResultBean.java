package galaconomy.utils.result;

public class ResultBean {
    
    private boolean success;
    private String message;

    public ResultBean() {
    }

    public ResultBean(boolean success) {
        this.success = success;
    }

    public ResultBean(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
        this.setMessage("Operation performed");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}

package galaconomy.utils.result;

public class ResultBean {
    
    private boolean success;
    private String message;
    private Object returnObject;

    public ResultBean() {
    }

    public ResultBean(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess() {
        this.success = true;
        this.setMessage("Operation performed");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }
    
    public static ResultBean createDefaultBean() {
        return new ResultBean(false, "Operation wasn't performed");
    }
    
}

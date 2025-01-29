package ir.ac.kntu.project4;

public class SupportRequest {
    private String requestText = new String();
    private String answer = new String();

    public SupportRequest() {
    }

    public SupportRequest(String requestText, String answer) {
        this.requestText = requestText;
        this.answer = answer;
    }

    public SupportRequest(String requestText) {
        this.requestText = requestText;
    }

    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String fileToString() {
        return getRequestText() + "," + getAnswer() + "\n";
    }

}

package cjnt.test.study;

public enum QuestionAnswerType {
    INTRO(""),
    MULTIPLE_CHOICE(""),
    PARAGRAPH("paragraph"),
    STAR_RATING(""),
    CONSTANT_SUM("");
    private String code;
    QuestionAnswerType(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}

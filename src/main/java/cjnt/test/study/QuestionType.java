package cjnt.test.study;

public enum QuestionType {
    INTRO("intro"),
    MULTIPLE_CHOICE("multiple"),
    PARAGRAPH("capture"),
    STAR_RATING("starrating"),
    CONSTANT_SUM("constantsum"),
    SUCCESS("success");
    private String code;
    QuestionType(String code){
        this.code = code;
    }
    public String getCode(){
        return code;
    }
}

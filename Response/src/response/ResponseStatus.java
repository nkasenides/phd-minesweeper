package response;

public enum ResponseStatus {

    OK("ok"),
    ERROR("error"),
    WARNING("warning")

    ;

    private String name;

    ResponseStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

package nkorange.secretary.core;

/**
 * @author pengfei.zhu.
 */
public class Command {

    private long moduleId;
    private String[] args;

    static final long ERROR_MODULE_NOT_FOUND_ID = -1;
    static final long ERROR_MODULE_ARG_ERROR_ID = -2;

    public Command(long id) {
        this.moduleId = id;
    }

    public Command(long id, String[] args) {
        this.moduleId = id;
        this.args = args;
    }

    public long getModuleId() {
        return moduleId;
    }

    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public static Command noMatchedModule() {
        return new Command(ERROR_MODULE_NOT_FOUND_ID);
    }

    public static Command argumentError() {
        return new Command(ERROR_MODULE_ARG_ERROR_ID);
    }

    public static Command normalCommand(long moduleId, String[] args) {
        return new Command(moduleId, args);
    }
}
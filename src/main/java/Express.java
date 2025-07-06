import io.javalin.Javalin;

public class Express {

    public static void main(String[] args){
        Javalin app = Javalin.create().start(5001);
        app.get("/", ctx -> ctx.result("Hello World!"));
    }

}

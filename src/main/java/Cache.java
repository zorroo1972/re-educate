import java.lang.annotation.*;
@Target(value=ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Cache {
    int value() default 0;
}

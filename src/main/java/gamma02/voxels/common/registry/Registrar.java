package gamma02.voxels.common.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Registrar {
    //FOR THIS TO FUNCTION, THE REGISTRAR CLASS MUST HAVE *ONE* STORED IForgeRegistry!
    Class<?> element();
    String modid();
}

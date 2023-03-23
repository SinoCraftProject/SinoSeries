package games.moegirl.sinocraft.sinocore.utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 实现该接口后，可以直接获取泛型对应的 Class 对象
 *
 * @author luqin2007
 */
public interface IGenericClass {

    /**
     * 获取父类中的泛型类型
     */
    default <T> Class<T> getGenericClass(int i) {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType pt)
            return (Class<T>) pt.getActualTypeArguments()[i];
        throw new RuntimeException("Can't found parameterized type in type " + superclass.getTypeName() + "[" + i + "]");
    }

    /**
     * 获取接口中的泛型类型
     */
    default <T> Class<T> getGenericClass(int i, Class<?> inter) {
        for (Type type : getClass().getGenericInterfaces())
            if (type instanceof ParameterizedType pt && pt.getRawType() == inter)
                return (Class<T>) pt.getActualTypeArguments()[i];
        throw new RuntimeException("Can't found parameterized type in type " + inter.getTypeName() + "[" + i + "]");
    }
}

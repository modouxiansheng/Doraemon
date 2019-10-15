package aboutjava.tool;

import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;

import java.lang.reflect.Field;
import java.util.*;


/**
 * @program: springBootPractice
 * @description: 找出类属性的不同
 * @author: hu_pf
 * @create: 2019-10-12 22:54
 **/
public class FindDifferent {

    private static List<Class> TYPE_ARRAY = new ArrayList<>();
    private static Map<Object, Class> classCache = new HashMap<>();
    private static Map<Object, Field[]> fieldsCache = new HashMap<>();

    static {
        TYPE_ARRAY.add(String.class);
        TYPE_ARRAY.add(Integer.class);
        TYPE_ARRAY.add(Byte.class);
        TYPE_ARRAY.add(Short.class);
        TYPE_ARRAY.add(Long.class);
        TYPE_ARRAY.add(Float.class);
        TYPE_ARRAY.add(Double.class);
        TYPE_ARRAY.add(Boolean.class);
        TYPE_ARRAY.add(byte.class);
        TYPE_ARRAY.add(short.class);
        TYPE_ARRAY.add(int.class);
        TYPE_ARRAY.add(long.class);
        TYPE_ARRAY.add(char.class);
        TYPE_ARRAY.add(float.class);
        TYPE_ARRAY.add(double.class);
        TYPE_ARRAY.add(boolean.class);
    }

    public static void main(String[] args) {
        People oldData = new People("zhangsan", "男", new BaseInfo("1", "1", "1"));
        People newData = new People("zhangsan", "女", new BaseInfo("2", "2", "2"));
        findDiff(oldData, newData);
    }

    public static <T, U> List<CompareResult> findDiff(T oldData, U newData) {

        DiffNode root = ObjectDifferBuilder.buildDefault().compare(oldData, newData);
        List<CompareResult> compareResultList = new ArrayList<>();
        root.visit((node, visit) -> {
            Class<?> valueType = node.getValueType();
            if (TYPE_ARRAY.contains(valueType)) {
                final Object oldValue = node.canonicalGet(oldData);
                final Object newValue = node.canonicalGet(newData);
                String path = node.getPath().toString();
                String paraName = path.substring(path.lastIndexOf("/") + 1);
                //                final String message = paraName + " changed from " +
                //                        oldValue + " to " + newValue;
                //                System.out.println(message + getDescribe(oldData,paraName));
                CompareResult compareResult = new CompareResult();
                compareResult.setDescribe(getDescribe(oldData, paraName));
                compareResult.setNewValue(newValue);
                compareResult.setOldValue(oldValue);
                compareResult.setParaName(paraName);
                compareResultList.add(compareResult);
            }
        });
        return compareResultList;
    }

    public static <T> String getDescribe(T object, String paraName) {
        String describe = "";
        try {
            InvokeField findField = findField(object, paraName);
            AliasForCompare annotation = findField.getField().getAnnotation(AliasForCompare.class);
            describe = annotation.describe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return describe;
    }

    public static <T> T setValue(T initObject, String paraName, Object value) {
        InvokeField invokeField = findField(initObject, paraName);
        setFieldValue(invokeField.getField(), invokeField.getObject(), value);
        return initObject;
    }

    public static <T> InvokeField findField(T initObject, String paraName) {
        // 从缓存中获取Class
        Class initClass = getClassCache(initObject);
        // 从缓存中获取Field
        Field[] allFields = getFieldsCache(initClass, initObject);

        InvokeField findField = new InvokeField();
        for (Field field : allFields) {
            // 属性类型
            Class<?> type = field.getType();
            // 如果属性是实体类的话就进去查找
            if (!TYPE_ARRAY.contains(type)) {
                // 递归查找
                findField = findField(setFieldObject(field, initObject), paraName);
            }
            if (paraName.equals(findField.getField().getName())){
                break;
            }
            if (paraName.equals(field.getName())) {
                findField.setField(field);
                findField.setObject(initObject);
                break;
            }
        }
        return findField;
    }

    public static Object setFieldObject(Field field, Object initObject) {
        Object fieldObject = null;
        field.setAccessible(true);
        try {
            fieldObject = field.get(initObject);
            if (fieldObject == null) {
                fieldObject = field.getType().newInstance();
                setFieldValue(field, initObject, fieldObject);
            }
        } catch (Exception e) {
            System.out.println("setFieldObject Failed " + e);
        }
        return fieldObject;
    }

    public static <T> void setFieldValue(Field field, T object, Object value) {
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static <T> Class getClassCache(T initObject) {
        Class initClass;
        if (classCache.containsKey(initObject)) {
            initClass = classCache.get(initObject);
        } else {
            initClass = initObject.getClass();
            classCache.put(initObject, initClass);
        }
        return initClass;
    }

    public static <T> Field[] getFieldsCache(Class initClass, T initObject) {
        Field[] allFields;
        if (fieldsCache.containsKey(initObject)) {
            allFields = fieldsCache.get(initObject);
        } else {
            allFields = initClass.getDeclaredFields();
            fieldsCache.put(initObject, allFields);
        }
        return allFields;
    }
}

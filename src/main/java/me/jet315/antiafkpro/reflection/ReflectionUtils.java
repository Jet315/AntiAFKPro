package me.jet315.antiafkpro.reflection;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Method;

public class ReflectionUtils {


    public static String serverVersionString = Bukkit.getServer().getClass().getPackage().getName().substring(Bukkit.getServer().getClass().getPackage().getName().lastIndexOf('.') + 1);

    //Minecraft version, can be by default 1.8
    public static ServerVersion serverVersion = ServerVersion.VERSION1_8;


    static {
        if(serverVersionString.startsWith("v1_8")){
            serverVersion = ServerVersion.VERSION1_8;
        }else if(serverVersionString.startsWith("v1_9")){
            serverVersion = ServerVersion.VERSION1_9;
        }else if(serverVersionString.startsWith("v1_10")){
            serverVersion = ServerVersion.VERSION1_10;
        }else if(serverVersionString.startsWith("v1_11")){
            serverVersion = ServerVersion.VERSION1_11;
        }else if(serverVersionString.startsWith("v1_12")){
            serverVersion = ServerVersion.VERSION1_12;
        }else if(serverVersionString.startsWith("v1_13")){
            serverVersion = ServerVersion.VERSION1_13;
        }
    }


    public static Method getMethod(Class clazz, String methodName){
        for(Method method : clazz.getDeclaredMethods()){
            if(method.getName().equalsIgnoreCase(methodName)){
                return method;
            }
        }
        return null;
    }

    public static Class getClass(String clazz){
        try {
            return Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}


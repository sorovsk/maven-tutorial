package com.example;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Gson 序列化 / 反序列化最小示例。
 *
 * 说明：Gson 通过反射直接读写字段，POJO 不需要 getter / setter，
 * 也不需要无参构造方法。
 */
public class App
{
    /** 演示用 POJO */
    static class User
    {
        private int id;
        private String name;
        private boolean active;

        User( int id, String name, boolean active )
        {
            this.id = id;
            this.name = name;
            this.active = active;
        }

        @Override
        public String toString()
        {
            return "User{id=" + id + ", name=" + name + ", active=" + active + "}";
        }
    }

    public static void main( String[] args )
    {
        // setPrettyPrinting() 让输出带缩进，便于阅读
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // ---------- 1. 对象 -> JSON ----------
        User user = new User( 1, "Alice", true );
        String json = gson.toJson( user );

        System.out.println( "[1] POJO -> JSON" );
        System.out.println( json );

        // ---------- 2. JSON -> 对象 ----------
        User parsed = gson.fromJson( json, User.class );

        System.out.println( "[2] JSON -> POJO" );
        System.out.println( parsed );
        System.out.println( "round-trip ok: " + parsed.name.equals( user.name ) );

        // ---------- 3. 集合 -> JSON，以及泛型反序列化 ----------
        List<User> users = new ArrayList<User>();
        users.add( user );
        users.add( new User( 2, "Bob", false ) );

        String listJson = gson.toJson( users );

        System.out.println( "[3] List<POJO> -> JSON" );
        System.out.println( listJson );

        // 泛型类型在运行时被擦除，需借助 TypeToken 保留 List<User> 信息
        List<User> restored = gson.fromJson( listJson, new TypeToken<List<User>>() {}.getType() );
        System.out.println( "restored size: " + restored.size() );
        System.out.println( "first element: " + restored.get( 0 ) );
    }
}

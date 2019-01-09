package io.schneezey.prepare;

import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

/**
 * The purpose of this is to quick start creation of a complex object using pojo's that have been built elsewhere.
 * 
 * Simply cut-and-paste into an avsc file in the src/main/avro directory. (this is defined in the pom plugin)
 *
 * DONT FORGET to change the namespace in the copied schema
 *
 */
public class GenarateSchema 
{
    public static void main( String[] args )
    {
    	Schema schema = ReflectData.get().getSchema(io.schneezey.prepare.SmallPojo.class);
    	
        System.out.println( schema.toString(true) );
    }

}
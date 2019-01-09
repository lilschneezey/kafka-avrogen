package io.schneezey.prepare;

import org.apache.avro.reflect.AvroIgnore;

/*
 * This is only used to create an avro schema. it is not used in the code.
 */
public class SmallPojo {
	
	private Integer id = 0;

	// Primitives
	private String testString = new String();
	@AvroIgnore private float testPFloat = (float) 0.0;
	@AvroIgnore private double testPDouble = 0.0;
	@AvroIgnore private int testPInt = 2000;
	@AvroIgnore private long testPLong = 3000L;
	@AvroIgnore private boolean testPBoolean = true;	
	@AvroIgnore private byte[] testPBytes = new byte[0];
	
}
package com.hkg.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class TestLambdaFunctionHandler implements RequestHandler<String, String> {

    @Override
    public String handleRequest(String input, Context context) {
    	  String output = "Hello, " + input + "!";
    	  System.out.println("The output is "+ output);
    	  return output;
    }

}
